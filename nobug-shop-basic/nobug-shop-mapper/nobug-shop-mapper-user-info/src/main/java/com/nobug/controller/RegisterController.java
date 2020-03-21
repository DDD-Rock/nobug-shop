package com.nobug.controller;

import com.nobug.ResultBean;
import com.nobug.dto.UserDTO;
import com.nobug.entity.TUser;
import com.nobug.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@RestController
@RequestMapping("mapper")
public class RegisterController {

    @Autowired(required = false)
    private UserMapper userMapper;

    @RequestMapping("user")
    public ResultBean registerEmail(@RequestBody UserDTO userDTO) {

        TUser tUser = new TUser();
        BeanUtils.copyProperties(userDTO, tUser);

        //将数据存库
        int insert = userMapper.insert(tUser);

        if (insert == 1) {
            return ResultBean.success("存入数据库成功");
        } else {
            return ResultBean.error("存入数据库失败");
        }

    }

    @RequestMapping("active/{email}")
    public ResultBean activeAccount(@PathVariable String email){

        //待修改的数据
        TUser tUser = new TUser();
        tUser.setFlag(1);
        tUser.setUpdateTime(new Date());
        //创建判断规则
        Example example = new Example(TUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("email",email);

        //提交修改
        int update = userMapper.updateByExampleSelective(tUser, example);
        if (update==0){
            return ResultBean.error("激活失败");
        }else{
            return ResultBean.success("激活成功");
        }
    }

}
