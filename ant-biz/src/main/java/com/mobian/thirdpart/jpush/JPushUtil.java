package com.mobian.thirdpart.jpush;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by guxin on 2016/9/16.
 *
 * 极光推送工具类，消息内容可以model的消息推送表组成json字符串
 */
public class JPushUtil {

    final private static Logger logger = LoggerFactory.getLogger(JPushUtil.class);

    final private static String masterSecret = "5dc3923edd907b0d0b7ee0f1";
    final private static String appKey = "8014b282002be30319005c56";

    //最大重试次数为3
    private static JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);

    public static JPushClient instance() {
        return jpushClient;
    }

    /**
     * 向所有平台，所有设备推送消息
     */
    public static PushResult pushMessageToAll(String message) {
        PushPayload payload = PushPayload.alertAll(message);
        PushResult result = null;
        try {
            result = jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            logger.error("连接错误，稍后重试", e);
        } catch (APIRequestException e) {
            logger.error("发送错误", e);
        }

        return result;
    }
    /**
     * 向所有平台，所有设备推送消息
     */
    public static PushResult pushMyMessageToAll(String message) {
        PushPayload payload = PushPayload.messageAll(message);
        PushResult result = null;
        try {
            result = jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            logger.error("连接错误，稍后重试", e);
        } catch (APIRequestException e) {
            logger.error("发送错误", e);
        }

        return result;
    }

    /**
     * 向某个平台，某个别名，推送一条消息，单推时用此方法
     *
     * platformString的值为:all ios android android_ios(android和ios)
     *
     * alias:用户别名，每个用户设置不同的别名(同账号的意思)，则可进行单推；若若干用户同一个别名，则为群推
     */
    public static PushResult pushMessageToAlias(String platformString, String alias, String message) {

        Platform platform = null;
        if("android".equals(platformString)) {
            platform = Platform.android();
        } else if("ios".equals(platformString)) {
            platform = Platform.ios();
        } else if("android_ios".equals(platformString)) {
            platform = Platform.android_ios();
        } else {
            platform = Platform.all();
        }
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(platform)
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.alert(message))
                .build();
        PushResult result = null;
        try {
            result = jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            logger.error("连接错误，稍后重试", e);
        } catch (APIRequestException e) {
            logger.error("发送错误", e);
        }

        return result;
    }

    /**
     * 向某个平台，某个别名，推送一条消息，单推时用此方法
     *
     * platformString的值为:all ios android android_ios(android和ios)
     *
     * alias:用户别名，每个用户设置不同的别名(同账号的意思)，则可进行单推；若若干用户同一个别名，则为群推
     */
    public static PushResult pushMyMessageToAlias(String platformString, String alias, String message) {

        Platform platform = null;
        if("android".equals(platformString)) {
            platform = Platform.android();
        } else if("ios".equals(platformString)) {
            platform = Platform.ios();
        } else if("android_ios".equals(platformString)) {
            platform = Platform.android_ios();
        } else {
            platform = Platform.all();
        }
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(platform)
                .setAudience(Audience.alias(alias))
                .setMessage(Message.content(message))
                .build();
        PushResult result = null;
        try {
            result = jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            logger.error("连接错误，稍后重试", e);
        } catch (APIRequestException e) {
            logger.error("发送错误", e);
        }

        return result;
    }

    /**
     * 向某个平台，某些标签，推送一条消息，群推时用此方法
     *
     * platformString的值为:all ios android android_ios(android和ios)
     *
     * tag:标签，多用户打上同一标签，可用于群推
     */
    public static PushResult pushMessageToTag(String platformString, String[] tag, String message) {

        Platform platform = null;
        if("android".equals(platformString)) {
            platform = Platform.android();
        } else if("ios".equals(platformString)) {
            platform = Platform.ios();
        } else if("android_ios".equals(platformString)) {
            platform = Platform.android_ios();
        } else {
            platform = Platform.all();
        }
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(platform)
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.alert(message))
                .build();
        PushResult result = null;
        try {
            result = jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            logger.error("连接错误，稍后重试", e);
        } catch (APIRequestException e) {
            logger.error("发送错误", e);
        }

        return result;
    }

}
