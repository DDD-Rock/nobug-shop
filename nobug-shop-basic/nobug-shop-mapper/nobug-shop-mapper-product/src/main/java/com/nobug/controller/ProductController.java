package com.nobug.controller;

import com.nobug.ResultBean;
import com.nobug.dto.TProductDTO;
import com.nobug.entity.TProduct;
import com.nobug.mapper.TProductMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired(required = false)
    private TProductMapper productMapper;

    @RequestMapping("getById")
    public ResultBean getById(@RequestParam Integer id){

        TProduct tProduct = productMapper.selectByPrimaryKey(id);

        if (tProduct==null){
            return ResultBean.error("数据库中没有这个商品");
        }

        TProductDTO productDTO = new TProductDTO();
        BeanUtils.copyProperties(tProduct,productDTO);

        return ResultBean.success(productDTO);

    }

}
