package com.mobian.enums;

/**
 * Created by john on 16/10/23.
 */
public enum MessageType {
    认证通过("认证通过","您的实名认证已通过"),
    认证失败("认证失败","您的实名认证失败，点击重新提交验证"),
    商品被推送至三品一标并发布("商品被推送至三品一标并发布","您的“%s”商品已被发布至三品一标"),
    商品被推送至广告并发布("商品被推送至广告并发布","您的“%s”商品已被发布至活动广告"),
    帐户被冻结("帐户被冻结","您的帐户已被冻结，请找客服申诉");

    private String title;
    private String content;

    MessageType(String title,String content){
        this.title = title;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
