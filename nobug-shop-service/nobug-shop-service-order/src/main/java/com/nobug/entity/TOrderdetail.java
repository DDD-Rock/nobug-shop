package com.nobug.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (TOrderdetail)实体类
 *
 */
@Data
public class TOrderdetail implements Serializable {
    private static final long serialVersionUID = 230711654164101803L;
    
    private Integer id;
    
    private Integer orderid;
    
    private Integer productid;
    
    private Double price;
    
    private Integer number;
    
    private String productname;
    
    private Double fee;
    
    private Double total0;
    
    private String iscomment;
    
    private String lowstocks;
    
    private Integer score;
    
    private String specinfo;
    
    private String giftid;




}