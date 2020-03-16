package com.nobug.controller;

import com.nobug.entity.TOrderpay;
import com.nobug.service.TOrderpayService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("tOrderpay")
public class TOrderpayController {
    /**
     * 服务对象
     */
    @Resource
    private TOrderpayService tOrderpayService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public TOrderpay selectOne(Integer id) {
        return this.tOrderpayService.queryById(id);
    }

}