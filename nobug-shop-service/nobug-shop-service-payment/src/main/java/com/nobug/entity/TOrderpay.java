package com.nobug.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;


@Data
public class TOrderpay implements Serializable {
    
    private Integer id;
    
    private String orderid;
    
    private String paystatus;
    
    private Double payamount;
    
    private Date createtime;
    
    private String paymethod;
    
    private Date confirmdate;
    
    private String confirmuser;
    
    private String remark;
    
    private String tradeno;




}