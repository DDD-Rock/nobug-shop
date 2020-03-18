package com.nobug.service.impl;

import com.nobug.entity.TOrderdetail;
import com.nobug.dao.TOrderdetailDao;
import com.nobug.service.TOrderdetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TOrderdetail)表服务实现类
 *
 *
 */
@Service("tOrderdetailService")
public class TOrderdetailServiceImpl implements TOrderdetailService {
    @Resource
    private TOrderdetailDao tOrderdetailDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TOrderdetail queryById(Integer id) {
        return this.tOrderdetailDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<TOrderdetail> queryAllByLimit(int offset, int limit) {
        return this.tOrderdetailDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tOrderdetail 实例对象
     * @return 实例对象
     */
    @Override
    public TOrderdetail insert(TOrderdetail tOrderdetail) {
        this.tOrderdetailDao.insert(tOrderdetail);
        return tOrderdetail;
    }

    /**
     * 修改数据
     *
     * @param tOrderdetail 实例对象
     * @return 实例对象
     */
    @Override
    public TOrderdetail update(TOrderdetail tOrderdetail) {
        this.tOrderdetailDao.update(tOrderdetail);
        return this.queryById(tOrderdetail.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tOrderdetailDao.deleteById(id) > 0;
    }
}