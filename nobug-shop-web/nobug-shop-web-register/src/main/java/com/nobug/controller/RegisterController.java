package com.nobug.controller;

import com.nobug.ResultBean;
import com.nobug.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping("register")
    public ResultBean register(@RequestParam int num, @RequestParam String username, @RequestParam String password, @RequestParam(required = false) String code) {


        ResultBean resultBean = registerService.register(num, username, password, code);


        return resultBean;
    }

}
