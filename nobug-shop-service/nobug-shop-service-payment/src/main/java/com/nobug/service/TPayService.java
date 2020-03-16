package  com.nobug.service;

import  com.nobug.entity.TPay;
import java.util.List;


public interface TPayService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TPay queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TPay> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param tPay 实例对象
     * @return 实例对象
     */
    TPay insert(TPay tPay);

    /**
     * 修改数据
     *
     * @param tPay 实例对象
     * @return 实例对象
     */
    TPay update(TPay tPay);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}