package com.xqs.service.first;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.xqs.entity.Feature;
import com.xqs.entity.Resource;
import com.xqs.entity.Role;
import com.xqs.entity.User;
import com.xqs.service.base.intf.FeatureService;
import com.xqs.service.base.intf.UserService;
import com.xqs.service.first.intf.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private FeatureService featureService;
	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public Map<Long, Map<Long, List<Feature>>> getFeatureLayers(User user) {
		Assert.notNull(user, "用户不应为空");
		user = userService.get(user.getId());
		
		// 对所有功能进行分层
		return featureService.layerFilter(findFeatures(user));
	}

	@Override
	@Transactional
	public Set<Feature> findFeatures(User user) {
		Assert.notNull(user, "用户不应为空");
		user = userService.get(user.getId());
		Set<Feature> result = new HashSet<Feature>();

		// 获取用户拥有的所有角色
		Set<Role> roles = user.getRole();
		// 获取用户拥有的所有功能
		for (Role role : roles) {
			result.addAll(role.getFeature());
		}

		return result;
	}

	@Override
	@Transactional
	public Set<Resource> findResources(User user) {
		Assert.notNull(user, "用户不应为空");
		user = userService.get(user.getId());
		Set<Resource> result = new HashSet<Resource>();

		// 获取用户拥有的所有功能
		Set<Feature> features = findFeatures(user);
		// 获取用户拥有的所有资源
		for (Feature feature : features) {
			result.add(feature.getResource());
		}

		return result;
	}

	@Override
	@Transactional
	public Set<String> capsuleRoles(User user) {
		Assert.notNull(user, "用户不应为空");
		user = userService.get(user.getId());
		Set<String> strRoles = new HashSet<String>();
		
		// 组装角色字符串
		Set<Role> roles = user.getRole();
		for (Role role : roles) {
			strRoles.add(role.getRole());
		}
		
		return strRoles;
	}

	@Override
	@Transactional
	public Set<String> capsuleResources(User user) {
		Assert.notNull(user, "用户不应为空");
		user = userService.get(user.getId());
		Set<String> strPermissions = new HashSet<String>();
		
		// 组装权限字符串
		Set<Resource> resources = findResources(user);
		for (Resource resource : resources) {
			if (resource != null) {
				strPermissions.add(resource.getPermission());
			}
		}
		
		return strPermissions;
	}

}
