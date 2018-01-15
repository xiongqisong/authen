package com.xqs.entity;
// Generated 2017-11-3 17:03:53 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.xqs.entity.Feature.FeatureType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * SysOrganization generated by hbm2java
 */
@Table(name = "organization")
@DynamicInsert
@DynamicUpdate
@Entity
@Data
public class Organization extends IdEntity<Organization> implements java.io.Serializable {
	@JoinColumn(name = "app_id")
	@ManyToOne(targetEntity = com.xqs.entity.App.class, fetch = FetchType.EAGER)
	private App app;

	private String name;

	@Column(name = "parent_id")
	private Long parentId;

	@Column(name = "parent_ids")
	private String parentIds;

	private Boolean available;

	@Column(name = "crete_time")
	private Date createTime;

	@Column(name = "update_time")
	private Date updateTime;

	public boolean isRootNode() {
		return parentId == 0;
	}

	public void setParent(Organization parent) {
		if (parent != null) {
			this.setParentId(parent.getId());
			this.setParentIds(parent.getParentIds() + parent.getId() + "/");
		} else {
			this.setParentId(0L);
			this.setParentIds("0/");
		}
	}
}
