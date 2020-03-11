package com.nobug.entity;

/*
 *SELECT
  a.pid AS id,
  a.pname AS t_product_name,
  a.sale_price AS t_product_sale_price,
  a.pimage AS t_product_pimage,
  b.pdesc AS t_product_pdesc
FROM
  t_product a
  LEFT JOIN t_product_desc b
    ON a.pid = b.`pid`
 */

import java.io.Serializable;
import java.math.BigDecimal;

public class TProductSearchDTO implements Serializable {

    private Long id;
    private String tProductName;
    private Double tProductSalePrice;
    private String tProductPimage;
    private String tProductPdesc;

    public TProductSearchDTO() {
    }

    public TProductSearchDTO(Long id, String tProductName, Double tProductSalePrice, String tProductPimage, String tProductPdesc) {
        this.id = id;
        this.tProductName = tProductName;
        this.tProductSalePrice = tProductSalePrice;
        this.tProductPimage = tProductPimage;
        this.tProductPdesc = tProductPdesc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String gettProductName() {
        return tProductName;
    }

    public void settProductName(String tProductName) {
        this.tProductName = tProductName;
    }

    public Double gettProductSalePrice() {
        return tProductSalePrice;
    }

    public void settProductSalePrice(Double tProductSalePrice) {
        this.tProductSalePrice = tProductSalePrice;
    }

    public String gettProductPimage() {
        return tProductPimage;
    }

    public void settProductPimage(String tProductPimage) {
        this.tProductPimage = tProductPimage;
    }

    public String gettProductPdesc() {
        return tProductPdesc;
    }

    public void settProductPdesc(String tProductPdesc) {
        this.tProductPdesc = tProductPdesc;
    }
}
