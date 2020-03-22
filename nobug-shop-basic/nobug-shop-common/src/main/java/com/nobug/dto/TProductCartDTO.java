package com.nobug.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TProductCartDTO implements Serializable {

    private TProductDTO product;

    private Integer count;

    private Date updateTime;
}

