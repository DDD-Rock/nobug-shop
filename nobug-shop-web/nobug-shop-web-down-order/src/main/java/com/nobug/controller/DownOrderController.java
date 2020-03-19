package com.nobug.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.wrappers.CodecWrappers;
import com.nobug.bean.TProduct;
import com.nobug.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    public String downOrder(String addrInfo,HttpServletRequest request) throws Exception{

        //解析对象
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

        Object userCartUid = redisTemplate.opsForValue().get("REDIS_USER_CART_UID");

        //获取购物车中的值


        //获取购物车中的数据
        List<TProduct> list = objectMapper.readValue((byte[]) userCartUid, List.class);




        return "";
    }



}
