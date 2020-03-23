package com.nobug.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (TPay)实体类
 *
 * @author makejava
 * @since 2020-03-14 16:55:49
 */
@Data
public class TPay implements Serializable {
    
    private Integer id;
    
    private String name;
    
    private String code;
    
    private String seller;
    
    private Integer order1;
    
    private String status;
    
    private String partner;
    
    private String key1;
    
    private String icon;
    
    private String picture;




}