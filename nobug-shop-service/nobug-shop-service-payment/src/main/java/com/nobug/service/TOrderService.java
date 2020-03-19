package com.nobug.service;

import com.nobug.entity.TOrder;
import java.util.List;

/**
 * (TOrder)表服务接口
 *从前端接受用户对象
 */
public interface TOrderService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TOrder queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<TOrder> queryAll();

    /**
     * 新增数据
     *
     * @param tOrder 实例对象
     * @return 实例对象
     */
    TOrder insert(TOrder tOrder);

    /**
     * 修改数据
     *
     * @param tOrder 实例对象
     * @return 实例对象
     */
    TOrder update(TOrder tOrder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}