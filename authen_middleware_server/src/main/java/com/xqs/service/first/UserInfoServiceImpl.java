package com.xqs.service.first;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xqs.entity.Feature;
import com.xqs.entity.Resource;
import com.xqs.entity.Role;
import com.xqs.entity.User;
import com.xqs.service.base.intf.FeatureService;
import com.xqs.service.base.intf.RoleService;
import com.xqs.service.first.intf.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private RoleService roleService;
	@Autowired
	private FeatureService featureService;

	@Override
	public Map<Long, Map<Long, List<Feature>>> getFeatureLayers(User user) {
		// 对所有功能进行分层
		return featureService.layerFilter(findFeatures(user));
	}

	public List<Role> findRoles(User user) {
		Assert.notNull(user, "用户不应为空");
		List<Role> result = new ArrayList<Role>();

		List<Long> roleIds = user.getRoles();
		if (roleIds != null && !roleIds.isEmpty()) {
			result = roleService.findRoles(roleIds.toArray(new Long[roleIds.size()]));
		}

		return result;
	}

	@Override
	public List<Feature> findFeatures(User user) {
		Assert.notNull(user, "用户不应为空");
		List<Feature> result = new ArrayList<Feature>();

		// 获取用户拥有的所有角色
		List<Role> roles = findRoles(user);
		// 获取用户拥有的所有功能
		for (Role role : roles) {
			List<Long> featureIds = role.getFeatures();
			result.addAll(featureService.findFeatures(featureIds.toArray(new Long[featureIds.size()])));
		}

		return result;
	}

	@Override
	public Set<Resource> findResources(User user) {
		Assert.notNull(user, "用户不应为空");
		Set<Resource> result = new HashSet<Resource>();

		// 获取用户拥有的所有功能
		List<Feature> features = findFeatures(user);
		// 获取用户拥有的所有资源
		for (Feature feature : features) {
			result.add(feature.getResource());
		}

		return result;
	}

	@Override
	public Set<String> capsuleRoles(User user) {
		// 组装角色字符串
		List<Role> roles = findRoles(user);
		Set<String> strRoles = new HashSet<String>();
		for (Role role : roles) {
			strRoles.add(role.getRole());
		}
		return strRoles;
	}

	@Override
	public Set<String> capsuleResources(User user) {
		// 组装权限字符串
		Set<Resource> resources = findResources(user);
		Set<String> strPermissions = new HashSet<String>();
		for (Resource resource : resources) {
			if (resource != null) {
				strPermissions.add(resource.getPermission());
			}
		}
		return strPermissions;
	}

}
