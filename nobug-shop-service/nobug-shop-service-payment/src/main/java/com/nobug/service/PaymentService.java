package com.nobug.service;


import entity.Payment;


/**
 * (Payment)表服务接口
 *
 * @author makejava
 * @since 2020-03-10 15:26:07
 */
public interface PaymentService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Payment queryById(Long id);



    /**
     * 新增数据
     *
     * @param payment 实例对象
     * @return 实例对象
     */
    int creat(Payment payment);



}