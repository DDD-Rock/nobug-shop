package com.nobug.controller;

import com.nobug.entity.TOrder;
import com.nobug.service.TOrderService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("tOrder")
public class TOrderController {
    /**
     * 服务对象
     */
    @Resource
    private TOrderService tOrderService;

    /**
     * 查询所有订单
     * @return 单条数据
     */
    @GetMapping("queryAll")
    public String queryAllOrder(Model model) {
        List<TOrder> tOrders = tOrderService.queryAll();
        model.addAttribute("tOrders",tOrders);
        return "order";
    }

}