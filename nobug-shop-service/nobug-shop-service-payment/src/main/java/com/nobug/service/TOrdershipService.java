package com.nobug.service;

import com.nobug.entity.TOrdership;

/**
 * (TOrdership)表服务接口
 *
 * @author makejava
 * @since 2020-03-21 21:27:33
 */
public interface TOrdershipService {

    /**
     * 通过ORDERID查询单条数据
     *
     * @return 实例对象
     */
    TOrdership queryByOrderId(Integer orderId);



}