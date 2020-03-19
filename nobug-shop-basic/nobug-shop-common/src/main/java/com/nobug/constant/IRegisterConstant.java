package com.nobug.constant;

public interface IRegisterConstant {

    String REGISTER_UUID = "register_uuid"; //邮箱激活时使用的临时key

    String REGISTER_SMS = "register_sms";   //验证短信时使用的临时key

    String REDIS_USER_KEY="redis_user_key"; //标志用户登录状态、存储用户信息的临时key

    String REDIS_TEMP_CART_UUID="redis_temp_cart_uuid";   //未登录状态下的购物车使用的key

    String REDIS_USER_CART_UID="redis_user_cart_uid";  //登录状态下的用户购物车key



}
