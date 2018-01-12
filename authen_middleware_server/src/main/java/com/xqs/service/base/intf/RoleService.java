package com.xqs.service.base.intf;

import java.util.List;
import java.util.Set;

import com.xqs.entity.App;
import com.xqs.entity.Feature;
import com.xqs.entity.Role;

public interface RoleService {
	Role create(Role role);

	Role get(Long id);

	Role update(Role base, Role changed);

	void delete(Long id);

	/**
	 * 根据应用id得到应用的所有角色
	 * 
	 * @param appId
	 * @return
	 */
	List<Role> findAll(Long appId);

	/**
	 * 批量得到角色
	 * 
	 * @param roleIds
	 * @return
	 */
	List<Role> findRoles(Long... roleIds);


	/**
	 * 为新建App初始化“超级管理员”角色
	 * 
	 * @param sysApp
	 * @param resourceIds	所有一级目录资源的id字符串
	 */
	Role initRoles(App sysApp, String resourceIds);
}
