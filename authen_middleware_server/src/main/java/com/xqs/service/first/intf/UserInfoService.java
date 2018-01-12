package com.xqs.service.first.intf;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xqs.entity.Feature;
import com.xqs.entity.Resource;
import com.xqs.entity.Role;
import com.xqs.entity.User;

public interface UserInfoService {
	/**
	 * 获取用户的功能菜单数据，按层级排列 0---
	 * 
	 * @param user
	 * @return
	 */
	public Map<Long, Map<Long, List<Feature>>> getFeatureLayers(User user);

	/**
	 * 得到用户拥有的所有角色
	 * 
	 * @param sysUser
	 * @return
	 */
	List<Role> findRoles(User user);

	/**
	 * 得到用户拥有的所有功能
	 * 
	 * @param user
	 * @return
	 */
	List<Feature> findFeatures(User user);

	/**
	 * 得到用户拥有的所有资源
	 * 
	 * @param user
	 * @return
	 */
	Set<Resource> findResources(User user);

	/**
	 * 得到用户拥有的所有角色的"角色字符串"概要版本
	 * 
	 * @param user
	 * @return
	 */
	Set<String> capsuleRoles(User user);

	/**
	 * 得到用户拥有的所有资源的的"权限字符串"概要版本
	 * 
	 * @param user
	 * @return
	 */
	Set<String> capsuleResources(User user);
}
