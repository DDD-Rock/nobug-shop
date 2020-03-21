package com.nobug.controller;

import com.nobug.ResultBean;
import com.nobug.dto.UserDTO;
import com.nobug.entity.TUser;
import com.nobug.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;


@RestController
@RequestMapping("login")
public class LoginController {


    @Autowired(required = false)
    private UserMapper userMapper;

    @RequestMapping("user")
    public ResultBean userLogin(@RequestParam String flag, @RequestParam String username) {
        Example example = new Example(TUser.class);
        Example.Criteria criteria = example.createCriteria();
        if ("phone".equals(flag)) {
            criteria.andEqualTo("phone", username);
        } else if ("email".equals(flag)) {
            criteria.andEqualTo("email", username);
        }

//        TUser tUser = new TUser();
//        if ("phone".equals(flag)) {
//            tUser.setPhone(username);
//        }else if("email".equals(flag)){
//            tUser.setEmail(username);
//        }
        TUser tUser = userMapper.selectOneByExample(example);
        if (tUser == null) {
            return ResultBean.error("登录失败,您的用户名不存在");
        }
        //java对象复制工具类
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(tUser, userDTO);

        return ResultBean.success(userDTO);
    }

}
