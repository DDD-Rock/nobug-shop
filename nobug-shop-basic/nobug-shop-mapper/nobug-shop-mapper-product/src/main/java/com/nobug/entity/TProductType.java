package com.nobug.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_product_type")
public class TProductType  implements Serializable {

	private Integer cid;

	private String cname;

	private Integer pid;

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
