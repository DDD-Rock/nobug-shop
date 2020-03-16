package com.nobug.controller;


import com.nobug.entity.TPay;
import com.nobug.service.TPayService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("tPay")
public class TPayController {
    /**
     * 服务对象
     */
    @Resource
    private TPayService tPayService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public TPay selectOne(Integer id) {
        return this.tPayService.queryById(id);
    }

}