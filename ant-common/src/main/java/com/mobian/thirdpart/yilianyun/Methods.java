package com.mobian.thirdpart.yilianyun;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import com.mobian.util.BeanUtil;
import com.mobian.util.ConvertNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 易联云接口工具类
 */

public class Methods {

    private static Logger log = LoggerFactory.getLogger(Methods.class);

    /**
     * 易联云颁发给开发者的应用ID 非空值
     */
    public static String CLIENT_ID;

    /**
     * 易联云颁发给开发者的应用secret 非空值
     */
    public static String CLIENT_SECRET;

    /**
     * token:a6047017e5c941e9951048b55a1d57c2
     */
    public static String token;

    /**
     * 刷新token需要的 refreshtoken
     */
    public static String refresh_token;

    /**
     * code
     */
    public static String CODE;

    private Methods() {
    }

    private static class SingleMethods {
        private static final Methods COCOS_MANGER = new Methods();
    }

    public static final Methods getInstance() {
        return SingleMethods.COCOS_MANGER;
    }

    /**
     * 开放式初始化
     *
     * @param client_id
     * @param client_secret
     * @param code
     */
    public void init(String client_id, String client_secret, String code) {
        CLIENT_ID = client_id;
        CLIENT_SECRET = client_secret;
        CODE = code;
    }

    /**
     * 自有初始化
     *
     * @param client_id
     * @param client_secret
     */
    public void init(String client_id, String client_secret) {
        CLIENT_ID = client_id;
        CLIENT_SECRET = client_secret;
    }

    public Methods init() {
        CLIENT_ID = ConvertNameUtil.getString("LAV01");
        CLIENT_SECRET = ConvertNameUtil.getString("LAV02");
        RedisUtil redisUtil = BeanUtil.getBean(RedisUtil.class);
        String token = (String)redisUtil.get(Key.build(Namespace.YILIANYUN_CONFIG, "access_token"));
        if(token == null) {
            getFreedomToken();
            redisUtil.set(Key.build(Namespace.YILIANYUN_CONFIG, "access_token"), this.token, 10*24*60*60, TimeUnit.SECONDS);
        } else {
            this.token = token;
        }

        return this;
    }

    /**
     * 开放应用
     */
    public String getToken() {
        String result = LAVApi.getToken(CLIENT_ID,
                "authorization_code",
                LAVApi.getSin(),
                CODE,
                "all",
                String.valueOf(System.currentTimeMillis() / 1000),
                LAVApi.getuuid());
        try {
            JSONObject json = JSONObject.parseObject(result);
            JSONObject body = json.getJSONObject("body");
            token = body.getString("access_token");
            refresh_token = body.getString("refresh_token");
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("getToken出现Json异常！" + e);
        }
        return result;
    }

    /**
     * 自有应用服务
     */
    public String getFreedomToken() {
        String result = LAVApi.getToken(CLIENT_ID,
                "client_credentials",
                LAVApi.getSin(),
                "all",
                String.valueOf(System.currentTimeMillis() / 1000),
                LAVApi.getuuid());
        try {
            log.info("getFreedomToken结果", result);
            JSONObject json = JSONObject.parseObject(result);
            JSONObject body = json.getJSONObject("body");
            token = body.getString("access_token");
            refresh_token = body.getString("refresh_token");
        } catch (JSONException e) {
            log.error("getFreedomToken出现Json异常！", e);
        }
        return result;
    }

    /**
     * 刷新token
     */
    public String refreshToken() {
        String result = LAVApi.refreshToken(CLIENT_ID,
                "refresh_token",
                "all",
                LAVApi.getSin(),
                refresh_token,
                LAVApi.getuuid(),
                String.valueOf(System.currentTimeMillis() / 1000));
        try {
            JSONObject json = JSONObject.parseObject(result);
            JSONObject body = json.getJSONObject("body");
            token = body.getString("access_token");
            refresh_token = body.getString("refresh_token");
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("refreshToken出现Json异常！" + e);
        }
        return result;
    }

    /**
     * 添加终端授权 开放应用服务模式不需要此接口 ,自有应用服务模式所需参数
     */
    public String addPrinter(String machine_code, String msign) {
        String result = LAVApi.addPrinter(CLIENT_ID,
                machine_code,
                msign,
                token,
                LAVApi.getSin(),
                LAVApi.getuuid(),
                String.valueOf(System.currentTimeMillis() / 1000));
        log.info("添加终端授权addPrinter结果", result);
        return result;
    }

    /**
     * 极速授权
     */
    public String speedAu(String machine_code, String qr_key) {
        String result = LAVApi.speedAu(CLIENT_ID,
                machine_code,
                qr_key,
                "all",
                LAVApi.getSin(),
                LAVApi.getuuid(),
                String.valueOf(System.currentTimeMillis() / 1000));

        log.info("极速授权speedAu结果", result);
        return result;
    }

    /**
     * 打印
     */
    public String print(String machine_code, String content, String origin_id) {
        String result = LAVApi.print(CLIENT_ID,
                token,
                machine_code,
                content,
                origin_id,
                LAVApi.getSin(),
                LAVApi.getuuid(),
                String.valueOf(System.currentTimeMillis() / 1000));

        log.info("打印print结果", result);
        return result;
    }

    /**
     * 删除终端授权
     */
    public String deletePrinter(String machine_code) {
        String result = LAVApi.deletePrinter(CLIENT_ID,
                token,
                machine_code,
                LAVApi.getSin(),
                LAVApi.getuuid(),
                String.valueOf(System.currentTimeMillis() / 1000));

        log.info("删除终端授权deletePrinter结果", result);
        return result;
    }

    /**
     * 添加应用菜单
     */
    public String addPrintMenu(String machine_code, String content) {
        return LAVApi.addPrintMenu(CLIENT_ID,
                token,
                machine_code,
                content,
                LAVApi.getSin(),
                LAVApi.getuuid(),
                String.valueOf(System.currentTimeMillis() / 1000));
    }

    /**
     * 关机重启接口
     */
    public String shutDownRestart(String machine_code, String response_type) {
        return LAVApi.shutDownRestart(CLIENT_ID,
                token,
                machine_code,
                response_type,
                LAVApi.getSin(),
                LAVApi.getuuid(),
                String.valueOf(System.currentTimeMillis() / 1000));
    }

    /**
     * 声音调节
     */
    public String setSound(String machine_code, String response_type, String voice) {
        return LAVApi.setSound(CLIENT_ID,
                token,
                machine_code,
                response_type,
                voice,
                LAVApi.getSin(),
                LAVApi.getuuid(),
                String.valueOf(System.currentTimeMillis() / 1000));
    }

    /**
     * 获取机型打印宽度接口
     */
    public String getPrintInfo(String machine_code) {
        return LAVApi.getPrintInfo(CLIENT_ID,
                token,
                machine_code,
                LAVApi.getSin(),
                LAVApi.getuuid(),
                String.valueOf(System.currentTimeMillis() / 1000));
    }

    /**
     * 获取机型软硬件版本接口
     */
    public String getVersion(String machine_code) {
        return LAVApi.getVersion(CLIENT_ID,
                token,
                machine_code,
                LAVApi.getSin(),
                LAVApi.getuuid(),
                String.valueOf(System.currentTimeMillis() / 1000));
    }

    /**
     * 取消所有未打印订单
     */
    public String cancelAll(String machine_code) {
        return LAVApi.cancelAll(CLIENT_ID,
                token,
                machine_code,
                LAVApi.getSin(),
                LAVApi.getuuid(),
                String.valueOf(System.currentTimeMillis() / 1000));
    }

    /**
     * 取消单条未打印订单
     */
    public String cancelOne(String machine_code, String order_id) {
        return LAVApi.cancelOne(CLIENT_ID,
                token,
                machine_code,
                order_id,
                LAVApi.getSin(),
                LAVApi.getuuid(),
                String.valueOf(System.currentTimeMillis() / 1000));
    }

    /**
     * 设置logo
     */
    public String setIcon(String machine_code, String img_url) {
        return LAVApi.setIcon(CLIENT_ID,
                token,
                machine_code,
                img_url,
                LAVApi.getSin(),
                LAVApi.getuuid(),
                String.valueOf(System.currentTimeMillis() / 1000));
    }

    /**
     * 删除logo
     */
    public String deleteIcon(String machine_code) {
        return LAVApi.deleteIcon(CLIENT_ID,
                token,
                machine_code,
                LAVApi.getSin(),
                LAVApi.getuuid(),
                String.valueOf(System.currentTimeMillis() / 1000));
    }

    /**
     * 打印方式
     */
    public String btnPrint(String machine_code, String response_type) {
        return LAVApi.btnPrint(CLIENT_ID,
                token,
                machine_code,
                response_type,
                LAVApi.getSin(),
                LAVApi.getuuid(),
                String.valueOf(System.currentTimeMillis() / 1000));
    }

    /**
     * 接单拒单设置接口
     */
    public String getOrder(String machine_code, String response_type) {
        return LAVApi.getOrder(CLIENT_ID,
                token,
                machine_code,
                response_type,
                LAVApi.getSin(),
                LAVApi.getuuid(),
                String.valueOf(System.currentTimeMillis() / 1000));
    }

}
