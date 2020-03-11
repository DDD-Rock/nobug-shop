package com.nobug.service;

import entity.Payment;

import java.util.Map;

public interface WeiXinPayService  {
    /**
     * 生成二维码链接
     * @param payment
     * @param total_fee
     * @return
     */
    public String  createNative(Payment payment, String total_fee) throws Exception;
    //TODO 后期改为订单

    /**
     * 查询支付订单状态
     * @param out_trade_no
     * @return
     */
    public Map queryPayStatus(String out_trade_no);


    /**
     * 关闭订单
     * @param out_trade_no
     * @return
     */
    public Map closePay(String out_trade_no);
}
