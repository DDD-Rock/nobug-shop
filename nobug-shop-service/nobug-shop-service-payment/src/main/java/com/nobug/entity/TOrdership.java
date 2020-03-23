package com.nobug.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TOrdership implements Serializable {

    
    private Integer id;
    
    private String orderid;
    
    private String shipname;
    
    private String shipaddress;
    
    private String provincecode;
    
    private String province;
    
    private String citycode;
    
    private String city;
    
    private String areacode;
    
    private String area;
    
    private String phone;
    
    private String tel;
    
    private String zip;
    
    private String sex;
    
    private String remark;
    
}