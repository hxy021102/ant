package com.camel.front.controller;

import com.aliyun.mns.model.TopicMessage;
import com.mobian.absx.F;
import com.mobian.concurrent.CompletionService;
import com.mobian.concurrent.Task;
import com.mobian.controller.BaseController;
import com.mobian.interceptors.TokenManage;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.service.impl.CompletionFactory;
import com.mobian.service.impl.RedisUserServiceImpl;
import com.mobian.thirdpart.mns.MNSTemplate;
import com.mobian.thirdpart.mns.MNSUtil;
import com.mobian.thirdpart.wx.WeixinUtil;
import com.mobian.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guxin on 2017/4/22.
 *
 * 用户相关接口
 */
@Controller
@RequestMapping("/api/apiUserController")
public class ApiUserController extends BaseController {

    @Autowired
    private MbUserServiceI mbUserService;
    @Autowired
    private TokenManage tokenManage;
    @Autowired
    private MbUserAddressServiceI mbUserAddressService;
    @Autowired
    private MbContractServiceI mbContractService;
    @Autowired
    private RedisUserServiceImpl redisUserService;
    @Autowired
    private MbBalanceServiceI mbBalanceService;
    @Autowired
    private MbShopServiceI mbShopService;
    @Autowired
    private DiveRegionServiceI diveRegionService;

    @Autowired
    private MbOrderServiceI mbOrderService;

    /**
     * 登录接口
     */
    @RequestMapping("/login")
    @ResponseBody
    public Json login(MbUser mbUser) {
        Json j = new Json();
        try{
            String userName = mbUser.getUserName();
            String password = mbUser.getPassword();
            if(!F.empty(userName) && !F.empty(password)) {
                mbUser.setUserName(RSAUtil.decryptByPravite(userName, RSAUtil.privateKey));
                mbUser.setPassword(RSAUtil.decryptByPravite(password, RSAUtil.privateKey));
                MbUser o = mbUserService.get(mbUser);
                if(o == null) {
                    j.setMsg("账号名或密码错误！");
                    return j;
                }
                Integer shopId = null;
                //门店认证通过
                MbShop mbShop = mbShopService.get(o.getShopId());
                if (mbShop != null && "AS02".equals(mbShop.getAuditStatus())) {
                    shopId = mbShop.getId();
                }

                String tokenId = tokenManage.buildToken(o.getId().toString(), o.getUserName(), shopId);
                o.setTokenId(tokenId);
                o.setUserName(userName);
                o.setPassword(password);
                j.setSuccess(true);
                j.setMsg("登陆成功！");
                j.setObj(o);
                return j;
            }
            j.setMsg("账号密码不能为空！");
        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("用户登录接口异常", e);
        }

        return j;
    }

    /**
     * 微信授权登录
     * @param code
     * @return
     */
    @RequestMapping("/loginByWx")
    @ResponseBody
    public void loginByWx(String code, String state, HttpServletRequest request, HttpServletResponse response) {
        try{
            String appId = ConvertNameUtil.getString(WeixinUtil.APPID);
            String server_url = ConfigUtil.get("server_url").replaceAll("[\"|']", "");
            String redirect_uri = server_url + "api/apiUserController/loginByWx";
            if(F.empty(code)) {
                response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+ URLEncoder.encode(redirect_uri, "UTF-8") +"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect");
                return;
            } else {
                boolean snsapi_userinfo = false;
                if(!F.empty(state) && "userinfo".equals(state)) {
                    snsapi_userinfo = true;
                }
                MbUser mbUser = mbUserService.loginByWx(code, snsapi_userinfo);
                if(mbUser != null) {
                    String tokenId = tokenManage.buildToken(mbUser.getId().toString(), mbUser.getUserName(), mbUser.getShopId());
                    response.sendRedirect(server_url + "shopweb/home/index.html?tokenId=" + tokenId);
                    return;
                } else {
                    response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+URLEncoder.encode(redirect_uri, "UTF-8")+"&response_type=code&scope=snsapi_userinfo&state=userinfo&connect_redirect=1#wechat_redirect");
                    return;
                }
            }
        }catch(Exception e){
            logger.error("微信授权登录接口异常", e);
        }
    }

    /**
     * 获取短信验证码
     */
    @RequestMapping("/getValidateCode")
    @ResponseBody
    public Json getValidateCode(MbUser mbUser) {
        Json j = new Json();
        try{
            String phone = mbUser.getPhone();
            if(F.empty(phone)) {
                j.setMsg("手机号码不能为空！");
                return j;
            }
            if(!Util.isMobilePhone(phone)) {
                j.setMsg("手机号码格式不正确！");
                return j;
            }
            String oldCode = redisUserService.getValidateCode(phone);
            if(!F.empty(oldCode)) {
                j.setMsg("访问过于频繁，请秒后重试！");
                return j;
            }
            String code = Util.CreateNonceNumstr(6); //生成短信验证码
            MNSTemplate template = new MNSTemplate();
            template.setTemplateCode("SMS_63345368");
            Map<String, String> params = new HashMap<String, String>();
            params.put("code", code);
            params.put("product", "骆驼送");
            template.setParams(params);
            TopicMessage topicMessage = MNSUtil.sendMns(phone, template);
            if(topicMessage != null) {
                redisUserService.setValidateCode(phone, code);
                j.setSuccess(true);
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
    public Json register(MbUser mbUser) {
        Json j = new Json();
        try{
            String password = mbUser.getPassword();
            String phone = mbUser.getPhone();
            String userName = mbUser.getUserName();
            if(F.empty(password)) {
                j.setMsg("密码不能为空！");
                return j;
            }
            if(F.empty(phone) && F.empty(userName)) {
                j.setMsg("账号或手机号码不能为空！");
                return j;
            }
            if(!F.empty(phone)) {
                if(!Util.isMobilePhone(phone)) {
                    j.setMsg("手机号码格式不正确！");
                    return j;
                }
                if(F.empty(mbUser.getValidateCode())) {
                    j.setMsg("验证码不能为空！");
                    return j;
                }
                String oldCode = redisUserService.getValidateCode(phone);
                if(F.empty(oldCode)) {
                    j.setMsg("验证码已过期！");
                    return j;
                }
                if(!oldCode.equals(mbUser.getValidateCode())) {
                    j.setMsg("验证码错误！");
                    return j;
                }
                //验证手机号码是否已绑定
                boolean hasPhone = mbUserService.hasPhone(phone);
                if(hasPhone) {
                    j.setMsg("手机号码已绑定！");
                    return j;
                }
            } else {
                if(!Util.isUserName(userName)) {
                    j.setMsg("用户名格式错误，必需为6-11个数字或字符！");
                    return j;
                }
            }
            //对用户密码MD5处理
            String password_de = RSAUtil.decryptByPravite(password, RSAUtil.privateKey);
            mbUser.setPassword(MD5Util.md5(password_de));
            mbUser.setShopId(null);
            if(F.empty(userName)) {
                mbUser.setUserName(phone);
            }
            if(F.empty(mbUser.getNickName())) {
                mbUser.setNickName(mbUser.getUserName());
            }
            MbUser o = mbUserService.addMbUser(mbUser);
            if(o != null) {
                String tokenId = tokenManage.buildToken(o.getId().toString(), o.getUserName(), o.getShopId());
                o.setTokenId(tokenId);
                o.setPassword(password);
                j.setSuccess(true);
                j.setMsg("注册成功！");
                j.setObj(o);
                return j;
            }
            j.setMsg("注册失败！");
        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("用户注册接口异常", e);
        }

        return j;
    }

    /**
     * 修改、补充用户信息接口
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(MbUser mbUser, HttpServletRequest request) {
        Json j = new Json();
        try{
            String uid = tokenManage.getUid(request);
            mbUser.setId(Integer.valueOf(uid));
            String phone = mbUser.getPhone();
            if(!F.empty(phone)) {
                mbUser.setUserName(phone);
                if(!Util.isMobilePhone(phone)) {
                    j.setMsg("手机号码格式不正确！");
                    return j;
                }
                if(F.empty(mbUser.getValidateCode())) {
                    j.setMsg("验证码不能为空！");
                    return j;
                }
                String oldCode = redisUserService.getValidateCode(phone);
                if(F.empty(oldCode)) {
                    j.setMsg("验证码已过期！");
                    return j;
                }
                if(!oldCode.equals(mbUser.getValidateCode())) {
                    j.setMsg("验证码错误！");
                    return j;
                }
                //如果修改了手机号码，验证手机号码是否已绑定
                MbUser o = mbUserService.get(mbUser.getId());
                if(F.empty(o.getPhone()) || !phone.equals(o.getPhone())) {
                    boolean hasPhone = mbUserService.hasPhone(phone);
                    if(hasPhone) {
                        j.setMsg("手机号码已绑定！");
                        return j;
                    }
                }

                // 根据手机号查询门店信息
                /*List<MbShop> mbShops = mbShopService.getByPhone(phone);
                if(mbShops.size() == 1) {
                    mbUser.setShopId(mbShops.get(0).getId());
                }*/
            }
            //如果修改了用户密码，对用户密码MD5处理
            String password = mbUser.getPassword();
            if(!F.empty(password)) {
                String password_de = RSAUtil.decryptByPravite(password, RSAUtil.privateKey);
                mbUser.setPassword(MD5Util.md5(password_de));
            }
            mbUserService.edit(mbUser);
            j.setSuccess(true);
            j.setMsg("修改成功！");
        }catch(Exception e) {
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("修改、补充用户信息接口异常", e);
        }

        return j;
    }

    /**
     * 获取用户信息接口
     */
    @RequestMapping("/getInfo")
    @ResponseBody
    public Json getInfo(String userId, HttpServletRequest request) {
        Json j = new Json();
        try {
            SessionInfo s = tokenManage.getSessionInfo(request);
            if(F.empty(userId)) userId = s.getId();
            MbUser o = mbUserService.get(Integer.valueOf(userId));
            j.setSuccess(true);
            j.setMsg("获取用户信息成功！");
            j.setObj(o);
        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("获取用户信息接口异常", e);
        }

        return j;
    }

    /**
     * 获取用户信息接口
     */
    @RequestMapping("/get")
    @ResponseBody
    public Json get(HttpServletRequest request) {
        Json j = new Json();
        try {
            String uid = tokenManage.getUid(request);
            MbUser o = mbUserService.get(Integer.parseInt(uid));
            if(o != null) {
                final CompletionService completionService = CompletionFactory.initCompletion();
                if(!F.empty(o.getShopId())) {
                    //获取用户余额
                    completionService.submit(new Task<MbUser, MbBalance>(o) {
                        @Override
                        public MbBalance call() throws Exception {
                            MbBalance balance = mbBalanceService.addOrGetMbBalance(getD().getShopId());
                            Integer debt = mbOrderService.getOrderDebtMoney(getD().getShopId());
                            debt = debt == null ? 0 : debt;
                            balance.setAmount(balance.getAmount() - debt);
                            return balance;
                        }

                        protected void set(MbUser d, MbBalance mbBalance) {
                            if(mbBalance != null)
                                d.setMbBalance(mbBalance.getAmount());
                        }
                    });
                    // 获取用户桶账余额
                    completionService.submit(new Task<MbUser, MbBalance>(o) {
                        @Override
                        public MbBalance call() throws Exception {
                            return mbBalanceService.addOrGetMbBalanceCash(getD().getShopId());
                        }

                        protected void set(MbUser d, MbBalance mbBalance) {
                            if(mbBalance != null)
                                d.setCashBalance(mbBalance.getAmount());
                        }
                    });
                    //获取认证门店信息
                    completionService.submit(new Task<MbUser, MbShop>(o) {
                        @Override
                        public MbShop call() throws Exception {
                            return mbShopService.get(getD().getShopId());
                        }

                        protected void set(MbUser d, MbShop mbShop) {
                            d.setMbShop(mbShop);
                        }
                    });
                    //获取最新有效合同编号
                    completionService.submit(new Task<MbUser, MbContract>(o) {
                        @Override
                        public MbContract call() throws Exception {
                            return mbContractService.getNewMbContract(getD().getShopId());
                        }

                        protected void set(MbUser d, MbContract mbContract) {
                            if(mbContract != null)
                                d.setMbContract(mbContract.getCode());
                        }
                    });
                }
                //获取默认收货地址
                completionService.submit(new Task<MbUser, MbUserAddress>(o) {
                    @Override
                    public MbUserAddress call() throws Exception {
                        return mbUserAddressService.getDefaultAddress(getD().getId());
                    }

                    protected void set(MbUser d, MbUserAddress mbUserAddress) {
                        d.setMbUserAddress(mbUserAddress);
                    }
                });
                completionService.sync();
            }
            j.setSuccess(true);
            j.setMsg("获取用户信息成功！");
            j.setObj(o);
        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("获取用户信息接口异常", e);
        }

        return j;
    }

    /**
     * 获取用户门店信息
     */
    @RequestMapping("/getShop")
    @ResponseBody
    public Json getShop(HttpServletRequest request) {
        Json j = new Json();
        try {
            SessionInfo s = tokenManage.getSessionInfo(request);
            MbUser user = mbUserService.get(Integer.valueOf(s.getId()));
            MbShop mbShop = null;
            if(!F.empty(user.getShopId())) {
                mbShop = mbShopService.get(user.getShopId());
            }
            j.setSuccess(true);
            j.setMsg("获取用户门店信息成功！");
            j.setObj(mbShop);
        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("获取用户门店信息接口异常", e);
        }

        return j;
    }

    /**
     * 获取用户门店列表
     */
    @RequestMapping("/getShops")
    @ResponseBody
    public Json getShops(HttpServletRequest request) {
        Json j = new Json();
        try {
            SessionInfo s = tokenManage.getSessionInfo(request);
            MbUser user = mbUserService.get(Integer.valueOf(s.getId()));
            List<MbShop> shops = null;
            if(!F.empty(user.getShopId())) {
                shops = mbShopService.queryListById(user.getShopId());
            }
            j.setSuccess(true);
            j.setMsg("获取用户门店列表成功！");
            j.setObj(shops);
        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("获取用户门店列表接口异常", e);
        }

        return j;
    }

    /**
     * 提交用户门店审核信息
     */
    @RequestMapping("/submitShop")
    @ResponseBody
    public Json submitShop(MbShop mbShop, HttpServletRequest request) {
        Json j = new Json();
        try {
            SessionInfo s = tokenManage.getSessionInfo(request);
            mbShop.setUserId(Integer.valueOf(s.getId()));
            String regionPath = mbShop.getRegionPath();
            if(!F.empty(regionPath)){
                String[] regions = regionPath.split("[-]");
                if(regions.length == 3){
                    DiveRegion diveRegion = new DiveRegion();
                    diveRegion.setRegionNameZh(regions[2]);
                    List<DiveRegion> diveRegionList = diveRegionService.findAllByParams(diveRegion);
                    if(CollectionUtils.isNotEmpty(diveRegionList)) {
                        for (DiveRegion region : diveRegionList) {
                            String path = diveRegionService.getRegionPath(region.getRegionId());
                            if (regionPath.equals(path) || path.indexOf("-" + regions[1] + "-") > -1) {
                                mbShop.setRegionId(region.getId());
                                break;
                            }
                        }
                    }
                }
            }

            if(F.empty(mbShop.getId())) {
                MbUser user = mbUserService.get(Integer.valueOf(s.getId()));
                mbShop.setContactPhone(user.getPhone());
                mbShopService.addOrUpdate(mbShop);
            } else {
                mbShop.setAuditStatus("AS01");
                mbShopService.addOrUpdate(mbShop);
            }

            j.setSuccess(true);
            j.setMsg("提交用户门店审核信息成功！");

        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("提交用户门店审核信息接口异常", e);
        }

        return j;
    }

    /**
     * 获取RSA公钥接口
     */
    @RequestMapping("/getPublicKey")
    @ResponseBody
    public Json getPublicKey() {
        Json j = new Json();
        try {
            String publicKey = RSAUtil.publicKey;
            j.setSuccess(true);
            j.setMsg("获取RSA公钥接口成功！");
            j.setObj(publicKey);
        } catch (Exception e) {
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("获取RSA公钥接口异常", e);
        }

        return j;
    }

}
