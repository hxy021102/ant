package com.bx.ant.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.model.TopicMessage;
import com.bx.ant.pageModel.session.TokenWrap;
import com.bx.ant.service.ShopDeliverAccountServiceI;
import com.bx.ant.service.session.TokenServiceI;
import com.mobian.absx.F;
import com.mobian.absx.UUID;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.ShopDeliverAccount;
import com.mobian.service.MbShopServiceI;
import com.mobian.thirdpart.mns.MNSTemplate;
import com.mobian.thirdpart.mns.MNSUtil;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import com.mobian.thirdpart.wx.HttpUtil;
import com.mobian.thirdpart.wx.WeixinUtil;
import com.mobian.util.ConvertNameUtil;
import com.mobian.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
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
    private TokenServiceI tokenService;

    @Resource
    private RedisUtil redisUtil;

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
            params.put("product", "骆驼送");
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
            /*String oldCode = (String) redisUtil.getString(Key.build(Namespace.SHOP_LOGIN_VALIDATE_CODE, userName));
            if(F.empty(oldCode)) {
                j.setMsg("验证码已过期！");
                return j;
            }
            if(!oldCode.equals(vcode)) {
                j.setMsg("验证码错误！");
                return j;
            }*/
            // 验证手机号码是否已绑定
            if(shopDeliverAccountService.checkUserName(userName)) {
                j.setMsg("手机号码已绑定！");
                return j;
            }

            shopDeliverAccountService.add(account);
            j.setSuccess(true);
            j.setMsg("注册成功！");
            return j;
        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("用户注册接口异常", e);
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
