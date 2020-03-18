package com.nobug.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DownOrderController {


    //跳转到订单页面

    @RequestMapping("/order/orderConfirm")
    public String orderConfirm() {
        return "orderConfirm";
    }


    /**
     * 点击 创建订单 按钮
     * @return
     * @throws Exception
     */
    @RequestMapping("/down/order")
    @ResponseBody
    public String pay(String province) throws Exception{

        return "下单";
    }
}
