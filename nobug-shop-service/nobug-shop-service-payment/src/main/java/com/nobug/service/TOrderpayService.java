package com.nobug.service;

import com.nobug.entity.TOrderpay;
import java.util.List;


public interface TOrderpayService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TOrderpay queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TOrderpay> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param tOrderpay 实例对象
     * @return 实例对象
     */
    TOrderpay insert(TOrderpay tOrderpay);

    /**
     * 修改数据
     *
     * @param tOrderpay 实例对象
     * @return 实例对象
     */
    TOrderpay update(TOrderpay tOrderpay);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}