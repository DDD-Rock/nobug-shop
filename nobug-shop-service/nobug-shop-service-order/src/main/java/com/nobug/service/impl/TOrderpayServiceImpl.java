package com.nobug.service.impl;

import com.nobug.dao.TOrderpayDao;
import com.nobug.entity.TOrderpay;
import com.nobug.service.TOrderpayService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("tOrderpayService")
public class TOrderpayServiceImpl implements TOrderpayService {
    @Resource
    private TOrderpayDao tOrderpayDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TOrderpay queryById(Integer id) {
        return this.tOrderpayDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<TOrderpay> queryAllByLimit(int offset, int limit) {
        return this.tOrderpayDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tOrderpay 实例对象
     * @return 实例对象
     */
    @Override
    public TOrderpay insert(TOrderpay tOrderpay) {
        this.tOrderpayDao.insert(tOrderpay);
        return tOrderpay;
    }

    /**
     * 修改数据
     *
     * @param tOrderpay 实例对象
     * @return 实例对象
     */
    @Override
    @RabbitListener(queues = "nobug_direct_queue")
    public TOrderpay update(TOrderpay tOrderpay) {
        this.tOrderpayDao.update(tOrderpay);
        return this.queryById(tOrderpay.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tOrderpayDao.deleteById(id) > 0;
    }
}