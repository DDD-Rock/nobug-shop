package com.nobug.dao;

import com.nobug.entity.TOrderdetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (TOrderdetail)表数据库访问层
 */
@Mapper
public interface TOrderdetailDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TOrderdetail queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TOrderdetail> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tOrderdetail 实例对象
     * @return 对象列表
     */
    List<TOrderdetail> queryAll(TOrderdetail tOrderdetail);

    /**
     * 新增数据
     *
     * @param tOrderdetail 实例对象
     * @return 影响行数
     */
    int insert(TOrderdetail tOrderdetail);

    /**
     * 修改数据
     *
     * @param tOrderdetail 实例对象
     * @return 影响行数
     */
    int update(TOrderdetail tOrderdetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}