package com.nobug.constant;

public interface IConstant {

    String REGISTER_UUID = "register_uuid"; //邮箱激活时使用的临时key

    String REGISTER_SMS = "register_sms";   //验证短信时使用的临时key

    String REDIS_USER_KEY = "redis_user_key"; //标志用户登录状态、存储用户信息的临时key

    String REDIS_CART_UUID = "redis_cart_uuid";   //购物车存取使用的key

    String REDIS_INDEX_GUIDE = "redis_index_guide"; //主页的商品列表key

    String REDIS_INDEX_GUIDE_LOCK = "redis_index_guide_lock"; //主页的商品列表查询锁

    String  REDIS_ORDER_KEY = "redis_order_key";  //订单key

    String REDIS_ORDER_ADDR = "redis_order_key";  //地址信息

    String REDIS_PRODUCT_ID = "redis_product_id";   //redis中保存商品信息的key

    String REDIS_USER_PRODUCT_CART="REDIS_USER_PRODUCT_CART";   //存放了商品细节的购物车key
}
