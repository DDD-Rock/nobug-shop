package com.nobug.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobug.ResultBean;
import com.nobug.UserDTO;
import com.nobug.constant.IRegisterConstant;
import com.nobug.service.LoginCacheService;
import com.nobug.service.LoginMapperService;
import com.nobug.utils.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("login")
public class LoginController implements IRegisterConstant {

    @Resource
    private LoginMapperService loginMapperService;

    @Resource
    private LoginCacheService loginCacheService;


    @RequestMapping(path = "user")
    public ResultBean userLogin(@RequestParam String username, @RequestParam String password,
                                @CookieValue(value = REDIS_USER_KEY, required = false) String uuid,
                                HttpServletResponse response) {
        ResultBean resultBean = null;
        String redisKey = "";
        Boolean newRedisKeyFlag = false;
        //发过来的请求cookie中是否携带了uuid
        if (uuid != null && !uuid.equals("")) {
            //组织redisKey
            redisKey = StringUtils.getRedisKey(REDIS_USER_KEY, uuid);
            //1.先从Redis中获取用户信息（如果能获取到，表示之前处于登录状态）
            resultBean = loginCacheService.getUserInfoByKey(redisKey);
        }
        //如果没从redis中取过，或者没有取到
        if (resultBean == null || resultBean.getErrno() == 1) {
            //从数据库获取用户信息
            if (username.contains("@")) {
                resultBean = loginMapperService.userLogin("email", username);
            } else {
                resultBean = loginMapperService.userLogin("phone", username);
            }
            if (resultBean.getErrno() == 1) {
                return resultBean;
            }
            newRedisKeyFlag = true;
        }

        //数据库中没有此账号
        if (resultBean.getErrno() == 1) {
            return resultBean;
        }

        //2.校验密码
        /**此处大坑!,直接使用resultBean.getData()取出来的数据是LinkedHashMap，不能强转成userDTO对象！
         解决方案：使用jackson的ObjectMapper转换
         */
        Object data = resultBean.getData();
        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO userDTO = objectMapper.convertValue(data, new TypeReference<UserDTO>() {
        });



        //判断密码是否一致
        boolean equals = userDTO.getPassword().equals(password);
        if (!equals) {
            return ResultBean.error("密码错误,请重新输入");
        }

        //判断需要生成新key，还是使用旧key
        if (newRedisKeyFlag){
            //3.存入redis缓存中，有效期设置为24小时。
            String new_uuid = UUID.randomUUID().toString();
            redisKey = StringUtils.getRedisKey(REDIS_USER_KEY, new_uuid);//组织存放的key
            loginCacheService.setUserInfo(redisKey, userDTO);//存入
            //4.将redis中存放用户登录信息的key,存入cookie中,24小时内有效。
            Cookie cookie = new Cookie(REDIS_USER_KEY, new_uuid);
            cookie.setMaxAge(86400);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }else{
            //重置redis有效期
            loginCacheService.setUserInfo(redisKey, userDTO);
            //重置cookie有效期
            Cookie cookie = new Cookie(REDIS_USER_KEY, uuid);
            cookie.setMaxAge(86400);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }

        return ResultBean.success("登录成功");
    }
}
