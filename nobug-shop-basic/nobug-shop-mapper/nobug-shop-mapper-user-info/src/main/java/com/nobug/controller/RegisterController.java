package com.nobug.controller;

import com.nobug.ResultBean;
import com.nobug.UserDTO;
import com.nobug.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mapper")
public class RegisterController {

    @Autowired(required = false)
    private ProductMapper productMapper;

    @RequestMapping("email")
    public ResultBean registerEmail(@RequestBody UserDTO userDTO){
        //todo 存库
        return ResultBean.success("存入数据库成功");


    }

}
