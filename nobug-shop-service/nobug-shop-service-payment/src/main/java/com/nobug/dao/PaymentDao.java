package com.nobug.dao;

import entity.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (Payment)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-10 15:26:06
 */
@Mapper
public interface PaymentDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Payment queryById(@Param("id") Long id);

    /**
     * 新增
     *
     * @param payment 实例对象
     * @return 实例对象
     */
    int creat(Payment payment);



}