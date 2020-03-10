package com.nobug.controller;


import com.nobug.entity.CommonResult;
import com.nobug.entity.Payment;
import com.nobug.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (Payment)表控制层
 *
 * @author makejava
 * @since 2020-03-10 21:26:07
 */
@RestController
@Slf4j
public class PaymentController {
    /**
     * 服务对象
     */
    @Resource
    private PaymentService paymentService;

    /**
     * 通过主键查询单条数据
     *
     * @param payment 主键
     * @return 单条数据
     */
    @PostMapping("/payment/creat")
    public CommonResult creat(Payment payment) {
        int result = paymentService.creat(payment);
        log.info("*******插入结果："+result);
        if (result>0){
            return new CommonResult(200,"支付成功",result);
        }else{
            return new CommonResult(444,"支付失败",null);
        }
    }
    @GetMapping("/payment/query/{id}")
    public CommonResult queryById(@PathVariable("id") Long id) {
        Payment payment = paymentService.queryById(id);
        log.info("*******插入结果："+payment);
        if (payment !=null){
            return new CommonResult(200,"查询支付流水号成功",payment);
        }else{
            return new CommonResult(444,"查询支付流水号失败",null);
        }
    }
}