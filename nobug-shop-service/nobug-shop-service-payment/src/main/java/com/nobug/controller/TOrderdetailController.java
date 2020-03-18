package com.nobug.controller;

import com.nobug.entity.TOrderdetail;
import com.nobug.service.TOrderdetailService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TOrderdetail)表控制层
 * 订单详情
 */
@RestController
@RequestMapping("tOrderdetail")
public class TOrderdetailController {
    /**
     * 服务对象
     */
    @Resource
    private TOrderdetailService tOrderdetailService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public TOrderdetail selectOne(Integer id) {
        return this.tOrderdetailService.queryById(id);
    }

}