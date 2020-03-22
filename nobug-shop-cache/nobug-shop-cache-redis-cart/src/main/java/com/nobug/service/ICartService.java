package com.nobug.service;

import com.nobug.ResultBean;
import org.springframework.stereotype.Service;

public interface ICartService {
    ResultBean addProductToCart(String cart_uuid, Integer productId, Integer count);

    ResultBean MergeCartInfo(String uuid, String user_id);

    ResultBean show(String uuid);

    ResultBean clean(String uuid);

//    ResultBean update(String uuid, Integer productId, Integer count);

}
