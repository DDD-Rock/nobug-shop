package com.nobug.controller;

import com.nobug.ResultBean;
import com.nobug.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    /**
     * 主要功能:购物车新增商品
     * @param cart_uuid
     * @param productId
     * @param count
     * @return
     */
    @RequestMapping("add/{cart_uuid}/{productId}/{count}")
    public ResultBean addProduct(
            @PathVariable String cart_uuid,
            @PathVariable Integer productId,
            @PathVariable Integer count
    ){
        ResultBean resultBean = cartService.addProductToCart(cart_uuid, productId, count);
        return resultBean;
    }

    /**
     * 合并已登录和未登录状态下的购物车
     * @param uuid
     * @param user_id
     * @return
     */
    @RequestMapping("merge/{uuid}/{user_id}")
    public ResultBean merge(@PathVariable String uuid,
                            @PathVariable String user_id){
        return  cartService.MergeCartInfo(uuid,user_id);
    }

    /**
     * 获取当前用户的购物车
     * @param uuid
     * @return
     */
    @RequestMapping("show/{uuid}")
    public  ResultBean show(@PathVariable String uuid){
        return cartService.show(uuid);
    }

    /**
     * 清空指定的购物车
     * @param uuid
     * @return
     */
    @RequestMapping("clean/{uuid}")
    public ResultBean clean(@PathVariable String uuid){
        return cartService.clean(uuid);
    }

//    /**
//     * 更新购物车
//     * @param uuid
//     * @param productId
//     * @param count
//     * @return
//     */
//    @RequestMapping("update/{uuid}/{productId}/{count}")
//    public ResultBean update(@PathVariable String uuid,
//                             @PathVariable Integer productId,
//                             @PathVariable Integer count
//    ){
//        return cartService.update(uuid, productId, count);
//    }

}
