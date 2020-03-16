package com.nobug.service.impl;

import  com.nobug.entity.TPay;
import  com.nobug.dao.TPayDao;
import  com.nobug.service.TPayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("tPayService")
public class TPayServiceImpl implements TPayService {
    @Resource
    private TPayDao tPayDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TPay queryById(Integer id) {
        return this.tPayDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<TPay> queryAllByLimit(int offset, int limit) {
        return this.tPayDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tPay 实例对象
     * @return 实例对象
     */
    @Override
    public TPay insert(TPay tPay) {
        this.tPayDao.insert(tPay);
        return tPay;
    }

    /**
     * 修改数据
     *
     * @param tPay 实例对象
     * @return 实例对象
     */
    @Override
    public TPay update(TPay tPay) {
        this.tPayDao.update(tPay);
        return this.queryById(tPay.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tPayDao.deleteById(id) > 0;
    }
}