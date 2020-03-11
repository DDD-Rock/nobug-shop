package com.nobug.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.nobug.config.WeiXinPayConfig;
import com.nobug.entity.Payment;
import com.nobug.service.WeiXinPayService;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
@Service
public class WeiXinPayServiceImpl implements WeiXinPayService {


    @Override
    public String  createNative(Payment payment, String total_fee) throws Exception {
        WeiXinPayConfig weiXinPayConfig = new WeiXinPayConfig();
        WXPay wxPay = new WXPay(weiXinPayConfig);
        //1.参数封装
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", "nobug商场消费"); //商品标题
        data.put("out_trade_no", payment.getSerial());//订单编号
        //TODO 增加商品编号
        data.put("device_info", "WEB");
        data.put("fee_type", "CNY");
        data.put("total_fee", total_fee);//单位是分
        //TODO 增加商品价格
        data.put("spbill_create_ip", "");
        //TODO 终端IP地址
        //回调地址，用来通知支付结果的地址
        data.put("notify_url", "");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        data.put("product_id", payment.getId().toString());
        //TODO 增加商品id
        //返回值：下单成功之后的支付地址
        Map<String, String> order = wxPay.unifiedOrder(data);
        System.out.println(order.get("code_url"));
        String payUrl = order.get("code_url"); //付款短链接
        return payUrl;
    }

    @Override
    public Map queryPayStatus(String out_trade_no) {
        return null;
    }

    @Override
    public Map closePay(String out_trade_no) {
        return null;
    }
}
