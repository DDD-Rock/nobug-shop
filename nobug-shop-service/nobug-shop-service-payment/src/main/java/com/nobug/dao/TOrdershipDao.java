package com.nobug.dao;

import com.nobug.entity.TOrdership;
import org.apache.ibatis.annotations.Param;

public interface TOrdershipDao {

    /**
     * 通过ORDERID查询单条数据

     * @return 实例对象
     */
    TOrdership queryByOrderId(@Param("orderId") Integer orderId);


}