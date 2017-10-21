package com.bx.ant.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.model.TopicMessage;
import com.bx.ant.pageModel.session.TokenWrap;
import com.bx.ant.service.ShopDeliverAccountServiceI;
import com.bx.ant.service.ShopDeliverApplyServiceI;
import com.bx.ant.service.impl.DeliverOrderShopServiceImpl;
import com.bx.ant.service.session.TokenServiceI;
import com.mobian.absx.F;
import com.mobian.absx.UUID;
import com.mobian.pageModel.*;
import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.pageModel.ShopDeliverAccount;
import com.bx.ant.pageModel.ShopDeliverApply;
import com.mobian.service.MbBalanceLogServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.thirdpart.mns.MNSTemplate;
import com.mobian.thirdpart.mns.MNSUtil;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import com.mobian.thirdpart.wx.DownloadMediaUtil;
import com.mobian.thirdpart.wx.HttpUtil;
import com.mobian.thirdpart.wx.WeixinUtil;
import com.mobian.util.ConvertNameUtil;
import com.mobian.util.Util;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by guxin on 2017/4/22.
 *
 * 用户相关接口
 */
@Controller
@RequestMapping("/api/deliver/account")
public class ApiAccountController extends BaseController {

    @Resource
    private MbShopServiceI mbShopService;

    @Resource
    private ShopDeliverAccountServiceI shopDeliverAccountService;

    @Resource
    private ShopDeliverApplyServiceI shopDeliverApplyService;

    @Resource
    private TokenServiceI tokenService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private MbBalanceLogServiceI mbBalanceLogService;

    @Resource
    private DeliverOrderShopServiceImpl deliverOrderShopService;

    /**
     * 登录接口
     * @param code
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public Json login(String code){
        Json j = new Json();
        try {
            JSONObject jsonObject = JSONObject.parseObject(HttpUtil.httpsRequest(WeixinUtil.getJscode2sessionUrl(code), "get", null));
            if (jsonObject != null && jsonObject.containsKey("openid")) {
                ShopDeliverAccount account = shopDeliverAccountService.getByRef(jsonObject.getString("openid"), "wx");
                if(account != null) {
                    TokenWrap token = new TokenWrap();
                    token.setTokenId(UUID.uuid());
                    token.setUid(account.getId().toString());
                    token.setName(account.getUserName());
                    tokenService.setToken(token);

                    j.success();
                    j.setObj(token.getTokenId());
                } else {
                    j.setObj(jsonObject.getString("openid"));
                }
            }
        } catch (Exception e) {
            logger.error("登录接口异常", e);
        }

        return j;
    }

    /**
     * 获取短信验证码
     */
    @RequestMapping("/getVCode")
    @ResponseBody
    public Json getVCode(String mobile) {
        Json j = new Json();
        try{
            if(F.empty(mobile)) {
                j.setMsg("手机号码不能为空！");
                return j;
            }
            if(!Util.isMobilePhone(mobile)) {
                j.setMsg("手机号码格式不正确！");
                return j;
            }
            String oldCode = (String) redisUtil.getString(Key.build(Namespace.SHOP_LOGIN_VALIDATE_CODE, mobile));
            if(!F.empty(oldCode)) {
                j.setMsg("访问过于频繁，请秒后重试！");
                return j;
            }
            String code = Util.CreateNonceNumstr(6); // 生成短信验证码
            MNSTemplate template = new MNSTemplate();
            template.setTemplateCode("SMS_63345368");
            Map<String, String> params = new HashMap<String, String>();
            params.put("code", code);
            params.put("product", "仓蚁管家门店版");
            template.setParams(params);
            TopicMessage topicMessage = MNSUtil.sendMns(mobile, template);
            if(topicMessage != null) {
                redisUtil.set(Key.build(Namespace.SHOP_LOGIN_VALIDATE_CODE, mobile), code, 60, TimeUnit.SECONDS);
                j.success();
                j.setMsg("获取短信验证码成功！");
                j.setObj(params);
                return j;
            }
            j.setMsg("获取短信验证码失败！");
        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("获取短信验证码接口异常", e);
        }

        return j;
    }

    /**
     * 用户注册接口
     */
    @RequestMapping("/register")
    @ResponseBody
    public Json register(ShopDeliverAccount account, String vcode) {
        Json j = new Json();
        try{
            String userName = account.getUserName();
            if(!Util.isMobilePhone(userName)) {
                j.setMsg("手机号码格式不正确！");
                return j;
            }
            if(F.empty(vcode)) {
                j.setMsg("验证码不能为空！");
                return j;
            }
            String oldCode = (String) redisUtil.getString(Key.build(Namespace.SHOP_LOGIN_VALIDATE_CODE, userName));
            if(F.empty(oldCode)) {
                j.setMsg("验证码已过期！");
                return j;
            }
            if(!oldCode.equals(vcode)) {
                j.setMsg("验证码错误！");
                return j;
            }
            // 验证手机号码是否已绑定
            if(shopDeliverAccountService.checkUserName(userName)) {
                j.setMsg("手机号码已绑定！");
                return j;
            }

            // 头像保存本地防止失效
            account.setIcon(DownloadMediaUtil.downloadHeadImage(account.getIcon()));
            account.setNickName(Util.filterEmoji(account.getNickName()));

            shopDeliverAccountService.add(account);

            TokenWrap token = new TokenWrap();
            token.setTokenId(UUID.uuid());
            token.setUid(account.getId().toString());
            token.setName(account.getUserName());
            tokenService.setToken(token);

            j.setSuccess(true);
            j.setMsg("注册成功！");
            j.setObj(token.getTokenId());
            return j;
        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("用户注册接口异常", e);
        }

        return j;
    }

    /**
     * 获取用户信息
     * @param request
     * @return
     */
    @RequestMapping("/get")
    @ResponseBody
    public Json get(HttpServletRequest request){
        Json j = new Json();
        try{
            TokenWrap token = tokenService.getToken(request);
            if(!F.empty(token.getUid())) {
                ShopDeliverAccount account = shopDeliverAccountService.get(Integer.valueOf(token.getUid()));
                ShopDeliverApply shopDeliverApply = shopDeliverApplyService.getByAccountId(Integer.valueOf(token.getUid()));
                MbShop mbShop = mbShopService.getFromCache(shopDeliverApply.getShopId());

                // TODO 统计今日有效订单和今日营业额
                //获取当天结束与开始
                Calendar todayC = Calendar.getInstance();
                todayC.set(Calendar.HOUR_OF_DAY,0);
                todayC.set(Calendar.MINUTE,0);
                todayC.set(Calendar.SECOND,0);
                Date todayStart = todayC.getTime();
                todayC.set(Calendar.HOUR_OF_DAY,23);
                todayC.set(Calendar.MINUTE,59);
                todayC.set(Calendar.SECOND,59);
                Date todayEnd = todayC.getTime();

                //获取今日营业额
                MbBalanceLog mbBalanceLog = new MbBalanceLog();
                mbBalanceLog.setUpdatetimeBegin(todayStart);
                mbBalanceLog.setUpdatetimeEnd(todayEnd);
                mbBalanceLog.setShopId(shopDeliverApply.getShopId());
                DataGrid dataGrid = mbBalanceLogService.updateDeliveryBalanceLogDataGrid(mbBalanceLog,new PageHelper());
                Integer todayAmount = new Integer(0) ;
                if (dataGrid.getFooter() != null && CollectionUtils.isNotEmpty(dataGrid.getFooter())) {
                    MbBalanceLog  balanceLog =(MbBalanceLog) dataGrid.getFooter().get(0);
                    todayAmount = balanceLog.getAmountIn() + balanceLog.getAmountOut();
                }
                //获取有效订单数量
                Integer todayQuantity = new Integer(0);
                DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
                deliverOrderShop.setShopId(shopDeliverApply.getShopId());
                deliverOrderShop.setUpdatetimeBegin(todayStart);
                deliverOrderShop.setUpdatetimeEnd(todayEnd);
                List<DeliverOrderShop> deliverOrderShopList = deliverOrderShopService.dataGrid(deliverOrderShop, new PageHelper()).getRows();
                if (CollectionUtils.isNotEmpty(deliverOrderShopList)) todayQuantity = deliverOrderShopList.size();

                Map<String, Object> obj = new HashMap<String, Object>();
                obj.put("account", account);
                obj.put("shopDeliverApply", shopDeliverApply);
                obj.put("mbShop", mbShop);
                obj.put("todayAmount", todayAmount);
                obj.put("todayQuantity", todayQuantity);


                j.setSuccess(true);
                j.setMsg("获取成功！");
                j.setObj(obj);
            }

        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("获取用户信息接口异常", e);
        }

        return j;
    }

    /**
     * 用户信息修改
     * @param account
     * @param request
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Json update(ShopDeliverAccount account, HttpServletRequest request){
        Json j = new Json();
        try{
            TokenWrap token = tokenService.getToken(request);

            if(!F.empty(token.getUid())) {
                account.setId(Integer.valueOf(token.getUid()));

                // 头像保存本地防止失效
                account.setIcon(DownloadMediaUtil.downloadHeadImage(account.getIcon()));
                account.setNickName(Util.filterEmoji(account.getNickName()));

                shopDeliverAccountService.edit(account);

                j.setSuccess(true);
                j.setMsg("修改成功！");
            }

        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("用户修改接口异常", e);
        }

        return j;
    }

    /**
     * 更新营业状态
     * @param request
     * @return
     */
    @RequestMapping("/updateOnline")
    @ResponseBody
    public Json updateOnline(ShopDeliverApply apply, HttpServletRequest request){
        Json j = new Json();
        try{
            TokenWrap token = tokenService.getToken(request);
            if(!F.empty(token.getUid())) {
                ShopDeliverApply shopDeliverApply = shopDeliverApplyService.getByAccountId(Integer.valueOf(token.getUid()));
                if(shopDeliverApply != null) {
                    apply.setId(shopDeliverApply.getId());
                    shopDeliverApplyService.edit(apply);
                    j.setSuccess(true);
                    j.setMsg("切换成功！");
                }
            }

        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("更新营业状态接口异常", e);
        }

        return j;
    }

    /**
     * 绑定门店申请
     * @param shopDeliverApply
     * @param request
     * @return
     */
    @RequestMapping("/addShopApply")
    @ResponseBody
    public Json addShopApply(ShopDeliverApply shopDeliverApply, HttpServletRequest request){
        Json j = new Json();
        try{
            TokenWrap token = tokenService.getToken(request);
            if(!F.empty(token.getUid())) {
                ShopDeliverApply exist = shopDeliverApplyService.getByAccountId(Integer.valueOf(token.getUid()));
                if(exist == null) {
                    shopDeliverApply.setAccountId(Integer.valueOf(token.getUid()));
                    shopDeliverApplyService.add(shopDeliverApply);
                }

                j.setSuccess(true);
                j.setMsg("申请成功！");
            }

        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("绑定门店申请接口异常", e);
        }

        return j;
    }


    /**
     * 获取用户绑定门店
     * @param request
     * @return
     */
    @RequestMapping("/getShopApply")
    @ResponseBody
    public Json getShopApply(HttpServletRequest request){
        Json j = new Json();
        try{
            TokenWrap token = tokenService.getToken(request);
            if(!F.empty(token.getUid())) {
                ShopDeliverApply shopDeliverApply = shopDeliverApplyService.getByAccountId(Integer.valueOf(token.getUid()));
                if(shopDeliverApply != null) {
                    shopDeliverApply.setMbShop(mbShopService.getFromCache(shopDeliverApply.getShopId()));
                    if("DAS02".equals(shopDeliverApply.getStatus())) {
                        token.setShopId(shopDeliverApply.getShopId());
                        tokenService.setToken(token);
                    }
                }
                j.setSuccess(true);
                j.setMsg("获取成功！");
                j.setObj(shopDeliverApply);
            }

        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("获取用户绑定门店接口异常", e);
        }

        return j;
    }

    @RequestMapping("/test")
    @ResponseBody
    public Json getShop(){
        Json json = new Json();
        json.setObj(mbShopService.getFromCache(35));
        return json;
    }

    /**
     * 获取门店账号信息
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public Json getInfo(){
        Json json = new Json();
        return json;
    }

}
