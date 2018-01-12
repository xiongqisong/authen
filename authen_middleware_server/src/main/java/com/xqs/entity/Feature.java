package com.xqs.entity;
// Generated 2018-1-3 17:37:35 by Hibernate Tools 3.4.0.CR1

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.xqs.Constants;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Feature generated by hbm2java
 */
@Table(name = "feature")
@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class Feature extends IdEntity<Feature> implements java.io.Serializable {
	@JoinColumn(name = "app_id")
	@ManyToOne(targetEntity = com.xqs.entity.App.class, fetch = FetchType.EAGER)
	private App app;

	private String name;

	private FeatureType type;

	@JoinColumn(name = "resource_id")
	@ManyToOne(targetEntity = com.xqs.entity.Resource.class, fetch = FetchType.EAGER)
	private Resource resource;

	@Column(name = "parent_id")
	private Long parentId;// 根功能的parentId为0

	@Column(name = "parent_ids")
	private String parentIds;// 根功能的parentIds的表示形式为0/

	private Boolean available;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "update_time")
	private Date updateTime;

	private static final List<String> UNCHANGED_FEATURE_NAMES = Arrays.asList("组织机构管理", "用户管理", "资源管理", "角色管理");

	
	public static enum FeatureType {
		menu("菜单"), button("按钮");

		private final String info;

		private FeatureType(String info) {
			this.info = info;
		}

		public String getInfo() {
			return info;
		}
	}

	/**
	 * 是否为根功能
	 * 
	 * @return
	 */
	public boolean isRootNode() {
		return this.parentId == 0;
	}

	/**
	 * 设置父功能节点
	 * 
	 * @param parent
	 *            父功能
	 */
	public void setParent(Feature parent) {
		if (parent != null) {
			this.setParentId(parent.getId());
			this.setParentIds(parent.getParentIds() + parent.getId() + "/");
		} else {
			this.setParentId(0L);
			this.setParentIds("0/");
		}
	}

	/**
	 * 获取真实资源路径
	 * 
	 * @param app
	 *            应用
	 * @return
	 */
	public String getRealUrl(App app) {
		if (UNCHANGED_FEATURE_NAMES.contains(this.getName())) {
			return this.resource != null ? Constants.SERVER_CONTEXT_PATH + this.resource.getUrl() : "";
		} else {
			return this.resource != null ? app.getContextPath() + this.resource.getUrl() : "";
		}
	}

	public Feature(App app, String name, FeatureType type, Resource resource, Boolean available, Date createTime,
			Date updateTime) {
		this.app = app;
		this.name = name;
		this.type = type;
		this.resource = resource;
		this.available = available;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
}
