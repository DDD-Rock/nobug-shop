package com.nobug.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

@Data
public class TOrder implements Serializable {

    private Integer id;

    private String account;

    private Integer paytype;

    private Integer carry;

    private Double rebate;

    private Date createdate;

    private String status;

    private String refundstatus;

    private Double amount;

    private Double fee;

    private Double ptotal;

    private Integer quantity;

    private String paystatus;

    private String updateamount;

    private String expresscode;

    private String expressname;

    private String otherrequirement;

    private String remark;

    private String expressno;

    private String expresscompanyname;

    private String lowstocks;

    private String confirmsendproductremark;

    private String closedcomment;

    private Integer score;


}