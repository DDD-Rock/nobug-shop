package com.nobug.controller;

import com.nobug.entity.TOrderdetail;
import com.nobug.service.TOrderdetailService;
import org.springframework.ui.Model;
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
     * 通过订单id查询订单详情
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("queryDetail")
    public String  selectOne(Integer id, Model model) {
        TOrderdetail tOrderdetail = tOrderdetailService.queryById(id);
        model.addAttribute("tOrderdetail",tOrderdetail);

        return "orderinfo";
    }

}