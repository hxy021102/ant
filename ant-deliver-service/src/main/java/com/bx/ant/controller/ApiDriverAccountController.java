package com.bx.ant.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.model.TopicMessage;
import com.bx.ant.pageModel.DriverAccount;
import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.pageModel.session.TokenWrap;
import com.bx.ant.service.DriverAccountServiceI;
import com.bx.ant.service.DriverOrderShopBillServiceI;
import com.bx.ant.service.DriverOrderShopServiceI;
import com.bx.ant.service.session.TokenServiceI;
import com.mobian.absx.F;
import com.mobian.absx.UUID;
import com.mobian.pageModel.Json;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanxp on 2017/11/06.
 *
 * 用户相关接口
 */
@Controller
@RequestMapping("/api/driver/account")
public class ApiDriverAccountController extends BaseController {

    @Resource
    private MbShopServiceI mbShopService;

    @Resource
    private TokenServiceI tokenService;

    @Resource
    private RedisUtil redisUtil;


    @Resource
    private DriverAccountServiceI driverAccountService;

    @Resource
    private DriverOrderShopServiceI driverOrderShopService;

    @Resource
    private DriverOrderShopBillServiceI driverOrderShopBillService;


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
            JSONObject jsonObject = JSONObject.parseObject(HttpUtil.httpsRequest(WeixinUtil.getJscode2sessionUrlByDriver(code), "get", null));
            if (jsonObject != null && jsonObject.containsKey("openid")) {
                DriverAccount account = driverAccountService.getByRef(jsonObject.getString("openid"), "wx");
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
            String oldCode = (String) redisUtil.getString(Key.build(Namespace.DRIVER_LOGIN_VALIDATE_CODE, mobile));
            if(!F.empty(oldCode)) {
                j.setMsg("访问过于频繁，请60秒后重试！");
                return j;
            }
            String code = Util.CreateNonceNumstr(6); // 生成短信验证码
            MNSTemplate template = new MNSTemplate();

            //TODO  模板需要更改
            template.setTemplateCode("SMS_63345368");
            Map<String, String> params = new HashMap<String, String>();
            params.put("code", code);
            params.put("product", "仓蚁管家骑手版");
            template.setParams(params);
            TopicMessage topicMessage = MNSUtil.sendMns(mobile, template);
            if(topicMessage != null) {
                redisUtil.set(Key.build(Namespace.DRIVER_LOGIN_VALIDATE_CODE, mobile), code, 60, TimeUnit.SECONDS);
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
    public Json register(DriverAccount account, String vcode) {
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
            String oldCode = (String) redisUtil.getString(Key.build(Namespace.DRIVER_LOGIN_VALIDATE_CODE, userName));
            if(F.empty(oldCode)) {
                j.setMsg("验证码已过期！");
                return j;
            }
            if(!oldCode.equals(vcode)) {
                j.setMsg("验证码错误！");
                return j;
            }
            // 验证手机号码是否已绑定
            if(driverAccountService.checkUserName(userName)) {
                j.setMsg("手机号码已绑定！");
                return j;
            }

            // 头像保存本地防止失效
            account.setIcon(DownloadMediaUtil.downloadHeadImage(account.getIcon()));
            account.setNickName(Util.filterEmoji(account.getNickName()));
            account.setHandleStatus(DriverAccountServiceI.HANDLE_STATUS_ADUIT);
            account.setOnline(false);
            account.setAutoPay(false);
            account.setOrderQuantity(Integer.parseInt(ConvertNameUtil.getString("DDSV200","1")));

            driverAccountService.add(account);

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
                Integer accountId = Integer.valueOf(token.getUid());
                DriverAccount account = driverAccountService.getFromCache(accountId);
                Integer todayAmount = new Integer(0) ;
                //获取有效订单数量
                Integer todayQuantity = new Integer(0);
                List<DriverOrderShop> driverOrderShops = driverOrderShopService.listTodayOrderByAccountId(accountId).getRows();
                if (CollectionUtils.isNotEmpty(driverOrderShops)) {
                    todayQuantity = driverOrderShops.size();
                    for (DriverOrderShop o : driverOrderShops) {
                      /*  if (DriverOrderShopServiceI.STATUS_SETTLEED.equals(o.getStatus()) || DriverOrderShopServiceI.STATUS_DELIVERED.equals(o.getStatus())) {*/
                            if(o.getAmount() != null) todayAmount += o.getAmount();
                       /* }*/
                    }
                }


                Map<String, Object> obj = new HashMap<String, Object>();
                obj.put("account", account);
                obj.put("todayAmount", todayAmount);
                obj.put("todayQuantity", todayQuantity);
                obj.put("handleStatus", account.getHandleStatus());


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
    public Json update(DriverAccount account, HttpServletRequest request){
        Json j = new Json();
        try{
            TokenWrap token = tokenService.getToken(request);

            if(!F.empty(token.getUid())) {
                account.setId(Integer.valueOf(token.getUid()));

                // 头像保存本地防止失效
                account.setIcon(DownloadMediaUtil.downloadHeadImage(account.getIcon()));
                account.setNickName(Util.filterEmoji(account.getNickName()));

                driverAccountService.edit(account);

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
    public Json updateOnline(DriverAccount apply, HttpServletRequest request){
        Json j = new Json();
        try{
            TokenWrap token = tokenService.getToken(request);
            Integer accountId = Integer.parseInt(token.getUid());
//            Integer accountId = 2;
            if(!F.empty(token.getUid())) {
                apply.setId(accountId);
                driverAccountService.edit(apply);
                j.setSuccess(true);
                j.setMsg("切换成功！");
            }

        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("更新营业状态接口异常", e);
        }

        return j;
    }

    /**
     * 更新位置
     * @param longitude
     * @param latitude
     * @param request
     * @return
     */
    @RequestMapping("/updateLocation")
    @ResponseBody
    public Json updateLocation(String longitude, String latitude, HttpServletRequest request) {
        Json json = new Json();

        //TODO 正式使用需更更改为
        TokenWrap tokenWrap = getTokenWrap(request);
        String accountId = tokenWrap.getUid();


        if (!F.empty(latitude) && !F.empty(longitude)) {
            redisUtil.set(Key.build(Namespace.DRIVER_REALTIME_LOCATION, accountId.toString()),
                    longitude + "," + latitude, Integer.parseInt(ConvertNameUtil.getString("DDSV100", "10")), TimeUnit.SECONDS);
            json.setMsg("更新成功！");
            json.setSuccess(true);
        }else {
            json.setMsg("无法获取位置信息");
            json.setSuccess(false);
        }
        return json;
    }

    @RequestMapping("/test")
    @ResponseBody
    public Json getShop(){
        Json json = new Json();
        json.setMsg("getIn");
//        json.setObj(mbShopService.getFromCache(35));
        driverOrderShopBillService.addPayOperation();
        json.setMsg(json.getMsg() + " : getOut");
        json.setSuccess(true);
        return json;
    }

    /**
     * 获取门店账号信息
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public Json getInfo(String accountId){
        Json json = new Json();
        String location = (String) redisUtil.getString(Key.build(Namespace.DRIVER_REALTIME_LOCATION, accountId));
        json.setMsg(location);
        json.setSuccess(true);
        return json;
    }


}
