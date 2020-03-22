package com.nobug.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem implements Serializable {

    private Integer pid;//商品id

    private Integer count;//数量

    private Date updateTime;//更新时间

}
