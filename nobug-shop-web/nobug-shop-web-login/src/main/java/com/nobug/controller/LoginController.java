package com.nobug.controller;

import com.nobug.ResultBean;
import com.nobug.UserDTO;
import com.nobug.constant.IRegisterConstant;
import com.nobug.service.LoginService;
import com.nobug.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("login")
public class LoginController implements IRegisterConstant {

    @Autowired
    private LoginService loginService;

//    @Autowired


    @RequestMapping(path = "user", method = RequestMethod.POST)
    public ResultBean userLogin(@RequestParam String username, @RequestParam String password,
                                @CookieValue(REDIS_USER_KEY) String userId,
                                HttpServletResponse response) {
        //组织redisKey
        String redisKey = StringUtils.getRedisKey(REDIS_USER_KEY, userId);
        //0.先从Redis中获取用户信息（如果能获取到，表示之前处于登录状态）


        //1.从数据库获取用户信息
        ResultBean resultBean = null;
        if (username.contains("@")) {
            resultBean = loginService.userLogin("email", username);
        } else {
            resultBean = loginService.userLogin("phone", username);
        }

        if (resultBean.getErrno()==1){
            return resultBean;
        }
        //2.校验密码
        UserDTO userDTO = (UserDTO)resultBean.getData();
        //判断是否一致
        boolean equals = userDTO.getPassword().equals(password);
        if (!equals) {
            return ResultBean.error("密码错误,请重新输入");
        }
        //3.存入redis缓存中，有效期设置为三十分钟


        //4.将redis中用户的信息存入cookie中
        Cookie cookie = new Cookie(REDIS_USER_KEY,userDTO.getId().toString());
        response.addCookie(cookie);

        return ResultBean.success(1);
    }
}
