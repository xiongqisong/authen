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
}
