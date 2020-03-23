package com.nobug.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {


    private String address;  //收件地址

    private String addressee;  //收件人

    private String zhifb;     //支付方式

    private String shunfeng;  // 物流





}
