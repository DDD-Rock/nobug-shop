package com.nobug.service;

import com.nobug.ResultBean;

public interface RegisterService {

    ResultBean register(int num, String username, String password, String code);
}
