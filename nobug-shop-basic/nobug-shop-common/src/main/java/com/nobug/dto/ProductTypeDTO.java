package com.nobug.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTypeDTO implements Serializable {
    private Integer cid;

    private String cname;

    private Integer pid;

    private Integer flag;

    private java.util.Date createTime;

    private java.util.Date updateTime;

    private Integer createUser;

    private Integer updateUser;
}
