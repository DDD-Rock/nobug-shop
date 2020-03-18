package com.nobug.service;

import com.nobug.entity.TOrderdetail;
import java.util.List;

/**
 * (TOrderdetail)表服务接口
 * 付款成功后查询订单信息
 * @author
 * @since 2020-03-18 20:53:52
 */
public interface TOrderdetailService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TOrderdetail queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TOrderdetail> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param tOrderdetail 实例对象
     * @return 实例对象
     */
    TOrderdetail insert(TOrderdetail tOrderdetail);

    /**
     * 修改数据
     *
     * @param tOrderdetail 实例对象
     * @return 实例对象
     */
    TOrderdetail update(TOrderdetail tOrderdetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}