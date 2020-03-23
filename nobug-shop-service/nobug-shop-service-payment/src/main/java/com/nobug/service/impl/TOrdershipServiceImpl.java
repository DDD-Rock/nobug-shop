package com.nobug.service.impl;

import com.nobug.dao.TOrdershipDao;
import com.nobug.entity.TOrdership;
import com.nobug.service.TOrdershipService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (TOrdership)表服务实现类
 *
 * @author makejava
 * @since 2020-03-21 21:27:33
 */
@Service("tOrdershipService")
public class TOrdershipServiceImpl implements TOrdershipService {
    @Resource
    private TOrdershipDao tOrdershipDao;
    /**
     * 通过ORDERID查询单条数据

     * @return 实例对象
     */
    @Override
    public TOrdership queryByOrderId(Integer orderId) {
        TOrdership tOrdership = tOrdershipDao.queryByOrderId(orderId);
        return tOrdership;
    }



}