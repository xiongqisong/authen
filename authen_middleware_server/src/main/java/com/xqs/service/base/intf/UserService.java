package com.xqs.service.base.intf;

import java.util.List;
import java.util.Set;

import com.xqs.entity.App;
import com.xqs.entity.Role;
import com.xqs.entity.User;

public interface UserService {
	User create(User user);

	User get(Long id);

	User update(User base, User changed);

	void delete(Long id);

	void changePassword(Long id, String newPassword);

	List<User> findAll(Long appId);

	/**
	 * 根据用户名+应用id查找用户
	 * 
	 * @param username
	 * @param appKey
	 * @return
	 */
	User findByUsernameAndApp(String username, String appKey);

	
	/**
	 * 得到用户的角色字符串集合
	 * @param sysUser
	 * @return
	 */
//	Set<String> findStringRoles(User sysUser);

	/**
	 * 得到用户的权限字符串集合
	 * 
	 * @param username
	 * @param appId
	 * @return
	 */
//	Set<String> findPermissions(User sysUser);

	/**
	 * 初始化“超级管理员”用户
	 * 
	 * @param sysApp
	 * @param admin
	 * @return
	 */
	User initAdmin(App sysApp, Role admin);
}
