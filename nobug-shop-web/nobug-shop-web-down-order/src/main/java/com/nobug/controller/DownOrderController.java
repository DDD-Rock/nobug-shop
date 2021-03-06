package com.nobug.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobug.ResultBean;
import com.nobug.bean.Address;
import com.nobug.bean.CartInfo;
import com.nobug.bean.Order;
import com.nobug.bean.TProduct;
import com.nobug.constant.IConstant;
import com.nobug.dto.AddressDTO;
import com.nobug.dto.TProductDTO;
import com.nobug.dto.UserDTO;
import com.nobug.utils.StringUtils;
import com.nobug.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Controller
public class DownOrderController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RestTemplate restTemplate;


    //跳转到下单页面

    @RequestMapping("/order/orderConfirm")
    public String orderConfirm() {
        return "orderConfirm";
    }

    /**
     * 搜索
     * @param searchInput
     * @param model
     * @return
     */
    @RequestMapping("/searchInput")
    public String search(@RequestParam String searchInput, Model model){

        String url = "http://nobug-shop-service-search/Keyword?keyWord="+searchInput;

        ResultBean bean = restTemplate.getForObject(url, ResultBean.class);

        model.addAttribute("products",bean.getData());

        return "search";

    }


    /**
     * 点击 创建订单 按钮
     * @return
     * @throws Exception
     * String user_cart_key = StringUtils.getRedisKey(REDIS_CART_UUID, user_id);
     */
    @RequestMapping("/down/order")
    public String downOrder(String addrInfo, HttpServletRequest request) throws Exception{

        ObjectMapper objectMapper = new ObjectMapper();




        String uuid = null;

        //获取cookie中的用户的uuid
        Cookie[] cookies = request.getCookies();
        for (Cookie c:cookies) {

            if(c.getName() == IConstant.REDIS_USER_KEY){
                uuid = c.getValue();
            }
        }


        //判断用户登录状态
        String userKey = StringUtils.getRedisKey(IConstant.REDIS_USER_KEY,uuid);

        //获取用户值
        Object o = redisTemplate.opsForValue().get(userKey);

        if(o == null){

            return "用户未登录";
        }

        //已登录状态


        // 强转成一个user对象
        UserDTO userDTO=(UserDTO )o;

        //用户id
        Integer id = userDTO.getId();


        //获取购物车redis中的key
        String redisKey = StringUtils.getRedisKey(IConstant.REDIS_CART_UUID, id.toString());
        //获取购物车中的值
        Object userCart = redisTemplate.opsForValue().get(redisKey);

        if(userCart == null){

            return "购物车中没有商品，下单失败";

        }

        //获取地址信息
        AddressDTO addr = objectMapper.readValue(addrInfo, AddressDTO.class);

        //强转成List<CartItem>
        List<CartItem>  userCarts = (List<CartItem>)(userCart);

        //创建订单对象
        Order order = new Order();

        //生成订单编号
        UUID uuid1 = UUID.randomUUID();

        String orderNumber = "xiadan"+uuid1+id;

        //订单总价
        BigDecimal amount = null;


        //遍历
        for (CartItem cart: userCarts) {

            Integer pid = cart.getPid();
            //获取产品id的键
            String productId = StringUtils.getRedisKey(IConstant.REDIS_PRODUCT_ID, pid.toString());

            //获取指定的产品
            TProductDTO product = (TProductDTO) redisTemplate.opsForValue().get(productId);
            //获取指定产品的价格
            BigDecimal price = product.getPrice();
            // 获取指定商品的数量
            Integer count = cart.getCount();
            //商品总价
            //讲数量转换成BigDecimal类型
            BigDecimal bgCount = new BigDecimal(count);
            BigDecimal totalPrice = price.multiply(bgCount);
            //订单总金额
            amount= amount.add(totalPrice);


            //订单编号
            order.setAccount(orderNumber);

            //设置日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //生成订单时间
            order.setCreatedate(df.format(new Date()));
            //商品总金额
            order.setPtotal(totalPrice.toString());
            //商品总数量
            order.setQuantity(count);

        }

        //订单总金额
        order.setAmount(amount.toString());
        //物流号
        order.setExpressNo("wuliu"+uuid1+id);
        //配送方式
        order.setExpressName(addr.getShunfeng());
        //将订单数据放到redis中
        redisTemplate.opsForValue().set(IConstant.REDIS_ORDER_KEY,order);
        redisTemplate.opsForValue().set(IConstant.REDIS_ORDER_ADDR,addr);

//        //将购物车中的值封装到list集合中
//        List<TProduct> products = objectMapper.readValue((byte[]) userCart, List.class);
//
//
//        //组装订单
//        CartInfo cartInfo = new CartInfo();
//        cartInfo.setProductList(products);
//        cartInfo.setAddress(addr);
//
//        //将数据存放到model中
//        model.addAttribute("CartInfo",cartInfo);


        return "下单成功";
    }



}
