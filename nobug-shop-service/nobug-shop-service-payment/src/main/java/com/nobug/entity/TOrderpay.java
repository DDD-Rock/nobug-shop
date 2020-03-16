package com.nobug.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;


@Data
public class TOrderpay implements Serializable {
    private static final long serialVersionUID = 449255832569650021L;
    
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }

    public Double getPayamount() {
        return payamount;
    }

    public void setPayamount(Double payamount) {
        this.payamount = payamount;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

    public Date getConfirmdate() {
        return confirmdate;
    }

    public void setConfirmdate(Date confirmdate) {
        this.confirmdate = confirmdate;
    }

    public String getConfirmuser() {
        return confirmuser;
    }

    public void setConfirmuser(String confirmuser) {
        this.confirmuser = confirmuser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTradeno() {
        return tradeno;
    }

    public void setTradeno(String tradeno) {
        this.tradeno = tradeno;
    }

}