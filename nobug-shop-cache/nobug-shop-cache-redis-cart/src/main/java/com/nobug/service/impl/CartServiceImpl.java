package com.nobug.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobug.ResultBean;
import com.nobug.constant.IConstant;
import com.nobug.dto.TProductCartDTO;
import com.nobug.dto.TProductDTO;
import com.nobug.service.ICartService;
import com.nobug.service.IProductService;
import com.nobug.utils.StringUtils;
import com.nobug.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 对Redis中购物车数据操作的核心类
 *
 * @Author Rock
 */
@Service
public class CartServiceImpl implements ICartService, IConstant {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IProductService productService;


    /**
     * 主要功能:购物车新增商品
     *
     * @param cart_uuid
     * @param productId
     * @param count
     * @return
     */
    @Override
    public ResultBean addProductToCart(@DefaultValue("") String cart_uuid, Integer productId, Integer count) {

        //先试取原有的购物车
        String redisKey = StringUtils.getRedisKey(REDIS_CART_UUID, cart_uuid);
        Object o = redisTemplate.opsForValue().get(redisKey);
        //将待传入的商品对象包装一下
        CartItem cartItem = new CartItem();
        cartItem.setPid(productId);
        cartItem.setCount(count);
        cartItem.setUpdateTime(new Date());

        List<CartItem> cartItemList =null;
        //判断之前是否有购物车
        if (o == null) {
            // todo 之前没有购物车
            cart_uuid = UUID.randomUUID().toString();
            cartItemList = new ArrayList<>();
            cartItemList.add(cartItem);
        } else {
            cartItemList = (List<CartItem>) o;
            Boolean flag = false;    //是否已经新增完毕

            //之前有购物车
            //遍历(用迭代器,边遍历边修改数据)
            ListIterator<CartItem> li = cartItemList.listIterator();
            while (li.hasNext()) {
                CartItem next = li.next();
                //判断购物车是否存在此商品
                if (next.getPid().equals(productId)) {
                    //已存在:增加个数
                    next.setCount(next.getCount() + count);
                    next.setUpdateTime(new Date());
                    flag = true;
                }
            }
            //如果刚才没加过,则需要插入新数据
            if (!flag) {
                cartItemList.add(cartItem);
            }
        }

        //购物车修改完毕,存回Redis
        redisKey = StringUtils.getRedisKey(REDIS_CART_UUID, cart_uuid);
        redisTemplate.opsForValue().set(redisKey, cartItemList);

        return ResultBean.success(cartItemList);
    }

    /**
     * 登录后,将临时购物车和用户购物车合并
     *
     * @param uuid
     * @param user_id
     * @return
     */
    @Override
    public ResultBean MergeCartInfo(String uuid, String user_id) {

        //从redis获取购物车数据
        String temp_cart_key = StringUtils.getRedisKey(REDIS_CART_UUID, uuid);
        String user_cart_key = StringUtils.getRedisKey(REDIS_CART_UUID, user_id);
        List<CartItem> tempCart = (List<CartItem>) redisTemplate.opsForValue().get(temp_cart_key);
        List<CartItem> userCart = (List<CartItem>) redisTemplate.opsForValue().get(user_cart_key);

        /* 分情况
         * 1. 临时购物车为空，用户购物车也为空
         * 2. 临时购物车不为空，用户购物为空
         * 3. 临时购物车为空，用户购物不为空
         * 4. 临时购物车不为空，用户购物不为空
         * */
        if (tempCart == null && userCart == null) {
            return ResultBean.success("购物车没有商品");
        } else if (tempCart == null && userCart != null) {
            return ResultBean.success("临时购物车没有商品");
        } else if (tempCart != null && userCart == null) {
            redisTemplate.opsForValue().set(user_cart_key, tempCart);
            redisTemplate.delete(temp_cart_key);
            return ResultBean.success("购物车合并完成");
        } else {
            //先把临时购物车的数据改造一下,方便接下来比较.
            HashMap<Integer, CartItem> tempHashMap = new HashMap<>();

            //迭代器
            ListIterator<CartItem> listIterator = userCart.listIterator();
            while (listIterator.hasNext()) {
                CartItem next = listIterator.next();
                if (tempHashMap.containsKey(next.getPid())) {
                    //增加数量
                    next.setCount(next.getCount() + tempHashMap.get(next.getPid()).getCount());
                    next.setUpdateTime(new Date());
                    //从map删除已经合并过的项目
                    tempHashMap.remove(next.getPid());
                }
            }
            //合并剩下的不重复商品
            Collection<CartItem> values = tempHashMap.values();
            for (CartItem value : values) {
                userCart.add(value);
            }

            //存入redis,删除临时购物车
            redisTemplate.opsForValue().set(user_cart_key, userCart);
            redisTemplate.delete(temp_cart_key);

            return ResultBean.success(userCart);
        }

    }

    /**
     * 显示指定购物车的数据
     *
     * @param id
     * @return
     */
    @Override
    public ResultBean show(String id) {
        if (null == id || "".equals(id)) {
            return ResultBean.success("购物车空空如也");
        }
        //从缓存中取购物车
        String redisKey = StringUtils.getRedisKey(REDIS_CART_UUID, id);
        Object object = redisTemplate.opsForValue().get(redisKey);
        if (object == null) {
            return ResultBean.success("购物车空空如也");
        }
        List<CartItem> cartItemList = (List<CartItem>) object;

        //从redis查询信息,封装起来
        List<TProductCartDTO> products = new ArrayList<>();

        //遍历
        for (CartItem cartItem : cartItemList) {
            //组织商品键
            String key = StringUtils.getRedisKey(REDIS_PRODUCT_ID, cartItem.getPid().toString());
            //从Redis中获取商品对象
            Object o = redisTemplate.opsForValue().get(key);
            //判断redis中是否取到
            if (o == null) {
                //没有查到,从数据库取
                ResultBean resultBean = productService.getById(cartItem.getPid());
                if (resultBean.getErrno() == 0) {
                    // TODO 可能强转会出错,未测试
                    ObjectMapper objectMapper = new ObjectMapper();
                    TProductDTO productDTO = objectMapper.convertValue(resultBean.getData(), new TypeReference<TProductDTO>() {
                    });

                    //存到redis中一份,下次直接取
                    redisTemplate.opsForValue().set(key, productDTO);
                    TProductCartDTO productCartDTO = new TProductCartDTO();
                    productCartDTO.setProduct(productDTO);
                    productCartDTO.setCount(cartItem.getCount());
                    productCartDTO.setUpdateTime(cartItem.getUpdateTime());
                    //存到结果集中
                    products.add(productCartDTO);

                } else {
                    return ResultBean.error("购物车中的商品仓库中已经不存在.");
                }


            } else {
                //redis取到数据
                TProductDTO productDTO = (TProductDTO) o;
                TProductCartDTO productCartDTO = new TProductCartDTO();
                productCartDTO.setProduct(productDTO);
                productCartDTO.setCount(cartItem.getCount());
                productCartDTO.setUpdateTime(cartItem.getUpdateTime());

                products.add(productCartDTO);
            }
        }

        //对商品进行排序(根据更新时间)
//        Collections.sort(products, new Comparator<TProductCartDTO>() {
//            @Override
//            public int compare(TProductCartDTO o1, TProductCartDTO o2) {
//                return (int) (o1.getUpdateTime().getTime()-o2.getUpdateTime().getTime());
//            }
//        });

        //lambda写法
        Collections.sort(products, (o1, o2) -> (int) (o1.getUpdateTime().getTime() - o2.getUpdateTime().getTime()));

        //将含有商品细节的购物车存入Redis(用于一键下单)
        String productCartKey = StringUtils.getRedisKey(REDIS_USER_PRODUCT_CART, id);
        redisTemplate.opsForValue().set(productCartKey, products);


        return ResultBean.success(products);
    }

    /**
     * 清除指定购物车的数据
     *
     * @param uuid
     * @return
     */
    @Override
    public ResultBean clean(String uuid) {
        if (null == uuid || "".equals(uuid)) {
            return ResultBean.success("购物车空空如也");
        }
        String redisKey = StringUtils.getRedisKey(REDIS_CART_UUID, uuid);
        redisTemplate.delete(redisKey);
        return ResultBean.success("购物车已清空");
    }

//    /**
//     * 更新购物车
//     *
//     * @param uuid
//     * @param productId
//     * @param count
//     * @return
//     */
//    @Override
//    public ResultBean update(String uuid, Integer productId, Integer count) {
//
//        return null;
//    }
}
