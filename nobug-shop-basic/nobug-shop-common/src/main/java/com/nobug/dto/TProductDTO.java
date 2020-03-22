package com.nobug.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TProductDTO implements Serializable {

    private Integer pid;

    private String pname;

    private java.math.BigDecimal price;

    private java.math.BigDecimal salePrice;

    private Integer typeId;

    private Integer status;

    private String pimage;

    private Integer flag;

    private java.util.Date createTime;

    private java.util.Date updateTime;

    private Integer createUser;

    private Integer updateUser;

}
