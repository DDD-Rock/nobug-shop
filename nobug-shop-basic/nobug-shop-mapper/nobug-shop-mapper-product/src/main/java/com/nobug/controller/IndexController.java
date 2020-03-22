package com.nobug.controller;

import com.nobug.ResultBean;
import com.nobug.entity.TProductType;
import com.nobug.mapper.TProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired(required = false)
    private TProductTypeMapper productTypeMapper;

    @RequestMapping("getProductType")
    public ResultBean getProductType() {
        List<TProductType> productTypeList = productTypeMapper.selectAll();
        if (productTypeMapper != null) {
//            List<ProductTypeDTO> productTypeDTOS = new ArrayList<>();
//
//            for (TProductType type : productTypeList) {
//                ProductTypeDTO productTypeDTO = new ProductTypeDTO();
//                BeanUtils.copyProperties(type,productTypeDTO);
//                productTypeDTOS.add(productTypeDTO);
//            }
//
//            //组装数据
//            for (ProductTypeDTO type1 : productTypeDTOS) {
//                for (ProductTypeDTO type2 : productTypeDTOS) {
//                    if (type1.getCid().equals(type2.getPid())){
//                        type1.setProductTypeDTOList(type2);
//                    }
//                }
//            }

            return ResultBean.success(productTypeList);
        } else {
            return ResultBean.error();
        }
    }


}
