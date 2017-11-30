package com.mobian.thirdpart.redis;

/**
 * Created by john on 15/12/30.
 */
public interface Namespace {
    String USER_LOGIN_SERVER_HOST = "user_login_server_host";
    String USER_LOGIN_TOKEN = "user_login_token";
    String USER_USERID_TOKEN = "user_userId_token";
    String USER_LOGIN_VALIDATE_CODE = "user_login_validate_code";
    String USER_APPLE_TOKEN = "user_apple_token"; //apns  token;
    String USER_CONTRACT_PRICE = "user_contract_price";
    String WX_CONFIG = "wx_config";
    String YOUZAN_CONFIG = "youzan_config";
    String USER_BALANCE_PAY_VALIDATE_CODE = "user_balance_pay_validate_code";
    String ORDER_QUANTITY="order_quantity";
    String REMIND_DRIVERS="remind_drivers";
    String ACTIVITY_TYPE_REFID = "activity_type_refid";
    String SHOP_USER_LOGIN_TOKEN = "shop_user_login_token";
    String SHOP_USER_SHOPID_TOKEN = "shop_user_shopId_token";
    String SHOP_LOGIN_VALIDATE_CODE = "shop_login_validate_code";
    String SHOP_BALANCE_ROLL_VALIDATE_CODE = "shop_balance_roll_validate_code";
    String ORDERLOG_MESSAGE="order_log_message";
    String MB_SHOP_CACHE = "mb_shop_cache";
    String DELVIER_ORDER_NEW_ASSIGNMENT_COUNT = "new_delvier_order_assignment_count";
    String DRIVER_LOGIN_VALIDATE_CODE = "driver_login_validate_code";
    String DRIVER_REALTIME_LOCATION = "driver_realtime_location";
    String DRIVER_ORDER_SHOP_CACHE  = "driver_order_shop_cache";
    String DRIVER_ORDER_SHOP_NEW_ASSIGNMENT_COUNT = "driver_order_shop_new_assignment_count";
    String DRIVER_ORDER_SHOP_REFUSE_CACHE = "driver_order_shop_refuse_cache";
    String DRIVER_ACCOUNT_CACHE = "driver_account_cache";
}
