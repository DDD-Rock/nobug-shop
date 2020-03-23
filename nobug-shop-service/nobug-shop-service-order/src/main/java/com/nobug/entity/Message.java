package com.nobug.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {

    private Integer id;
    private String bizId;
    private String bizType;
    private String bizData;
    private int status;




}

