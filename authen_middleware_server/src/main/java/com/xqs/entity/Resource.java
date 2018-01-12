package com.xqs.entity;
// Generated 2017-11-3 17:03:53 by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.sun.tools.javac.code.Attribute.Constant;
import com.xqs.Constants;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * SysResource generated by hbm2java
 */
@Table(name = "resource")
@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class Resource extends IdEntity<Resource> implements Serializable {
	@JoinColumn(name = "app_id")
	@ManyToOne(targetEntity = com.xqs.entity.App.class, fetch = FetchType.EAGER)
	private App app;
	private String name;
	private String url;
	private String permission;
	private Boolean available;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "update_time")
	private Date updateTime;

	public Resource(App app, String name, String url, String permission, Boolean available) {
		this.app = app;
		this.name = name;
		this.url = url;
		this.permission = permission;
		this.available = available;
	}
}
