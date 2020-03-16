package com.nobug.controller;

import com.nobug.ResultBean;
import com.nobug.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     * 用户账号注册接口
     *
     * @param num      1 -> email注册 ， 2 -> 手机号注册
     * @param username 用户名是手机号或者邮箱
     * @param password 密码
     * @param code     验证码（如果是手机注册才有）
     * @return
     */
    @RequestMapping(path = "register",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ResultBean register(@RequestParam int num, @RequestParam String username, @RequestParam String password, @RequestParam(required = false) String code) {
        System.out.println(num+username+password);
        System.out.println(code);
        ResultBean resultBean = registerService.register(num, username, password, code);

        return resultBean;
    }

    @RequestMapping("active/account/{uuid}")
    @ResponseBody
    @CrossOrigin
    public ResultBean activeAccount(@PathVariable String uuid, Model model) {

        ResultBean resultBean = registerService.active(uuid);
        model.addAttribute("resultBean",resultBean);
        return resultBean;
    }

        @RequestMapping("sms/{phoneNum}")
        @ResponseBody
        @CrossOrigin
        public ResultBean sendSMS(@PathVariable String phoneNum){
            ResultBean resultBean = registerService.sendSMS(phoneNum);
            return resultBean;
    }

}
