package com.nobug.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_product")
public class TProduct  implements Serializable {

	private Integer pid;

	private String pname;

	private java.math.BigDecimal price;

	@Column(name = "sale_price")
	private java.math.BigDecimal salePrice;

	@Column(name = "type_id")
	private Integer typeId;

	private Integer status;

	private String pimage;

	private Integer flag;

	@Column(name = "create_time")
	private java.util.Date createTime;

	@Column(name = "update_time")
	private java.util.Date updateTime;

	@Column(name = "create_user")
	private Integer createUser;

	@Column(name = "update_user")
	private Integer updateUser;

}
