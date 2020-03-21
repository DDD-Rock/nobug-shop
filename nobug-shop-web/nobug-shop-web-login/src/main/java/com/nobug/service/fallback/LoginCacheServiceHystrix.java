package com.nobug.service.fallback;

import com.nobug.ResultBean;
import com.nobug.dto.UserDTO;
import com.nobug.service.LoginCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginCacheServiceHystrix implements LoginCacheService {
    @Override
    public ResultBean getUserInfoByKey(String redisKey) {
        log.error("getUserInfoByKey服务被熔断，请及时检查服务状态！");
        return ResultBean.error("服务被熔断，暂时不可用");
    }

    @Override
    public ResultBean setUserInfo(String redisKey, UserDTO userDTO) {
        log.error("setUserInfo服务被熔断，请及时检查服务状态！");
        return ResultBean.error("服务被熔断，暂时不可用");
    }
}
