package com.nobug.controller;

import com.nobug.entity.CommonResult;
import com.nobug.entity.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class OrderController {

    private final static String PAYMENT_URL = "http://localhost:8765";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/query/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
      return restTemplate.getForObject(PAYMENT_URL + "/payment/query/" + id, CommonResult.class, id);
    }
    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }
    @GetMapping("/consumer/payment/weiXinPay/{total_fee}")
    public CommonResult createNative(Payment payment, @PathVariable("total_fee")String total_fee) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/weiXinPay", payment, CommonResult.class);
 }



}
