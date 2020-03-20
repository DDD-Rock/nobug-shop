package com.nobug.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobug.bean.Address;
import com.nobug.bean.CartInfo;
import com.nobug.bean.TProduct;
import com.nobug.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class DownOrderController {

    @Autowired
    private RedisTemplate redisTemplate;


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
    @RequestMapping("//down/order")
    public String downOrder(String addrInfo, HttpServletRequest request, Model model) throws Exception{

        ObjectMapper objectMapper = new ObjectMapper();




        String uuid = null;


        //判断用户登录状态
        String userKey = (String)redisTemplate.opsForValue().get("REDIS_USER_KEY");
        if(userKey == null){

            return "用户未登录";
        }

        //已登录状态


        //获取cookie中的用户的uuid
        Cookie[] cookies = request.getCookies();
        for (Cookie c:cookies) {

            if(c.getName() == "REDIS_USER_KEY"){
                uuid = c.getValue();
            }
        }


        //获取购物车redis中的key
        String redisKey = StringUtils.getRedisKey("REDIS_USER_CART_UID", uuid);
        //获取购物车中的值
        Object userCartUid = redisTemplate.opsForValue().get("REDIS_USER_CART_UID");

        //将购物车中的值封装到list集合中
        List<TProduct> products = objectMapper.readValue((byte[]) userCartUid, List.class);

        //获取地址信息
        Address addr = objectMapper.readValue(addrInfo, Address.class);

        //组装订单
        CartInfo cartInfo = new CartInfo();
        cartInfo.setProductList(products);
        cartInfo.setAddress(addr);

        //将数据存放到model中
        model.addAttribute("CartInfo",cartInfo);


        return "success";
    }



}
