package com.nobug.service.fallback;

import com.nobug.ResultBean;
import com.nobug.service.LoginService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginServiceHystrix implements LoginService {

    @Override
    public ResultBean userLogin(String flag, String username) {
        log.error("userLogin服务被熔断，请及时检查服务状态！");
        return ResultBean.error("服务被熔断，暂时不可用");
    }
}
