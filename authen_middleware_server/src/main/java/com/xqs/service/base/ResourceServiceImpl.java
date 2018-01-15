package com.xqs.service.base;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.xqs.dao.ResourceDao;
import com.xqs.entity.Resource;
import com.xqs.service.base.intf.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	public ResourceDao dao;

	@Transactional
	public Resource create(Resource resource) {
		Date now = new Date();
		resource.setCreateTime(now);
		resource.setUpdateTime(now);
		return dao.create(resource);
	}

	public Resource get(Long id) {
		return dao.get(id);
	}

	@Transactional
	public Resource update(Resource base, Resource changed) {
		return dao.safeUpdate(base, changed);
	}

	@Transactional
	public void delete(Long id) {
		dao.delete(id);
	}

	public List<Resource> findAll(Long appId) {
		return dao.findAll(appId);
	}

	public Set<String> findPermissions(Set<Long> resourceIds) {
		Set<String> permissions = new HashSet<String>();
		for (Long resourceId : resourceIds) {
			Resource sysResource = get(resourceId);
			if (sysResource != null && !StringUtils.isEmpty(sysResource.getPermission())) {
				permissions.add(sysResource.getPermission());
			}
		}
		return permissions;
	}

	/*
	 * public List<Resource> findMenus(User sysUser, Set<String> permissions) {
	 * List<Resource> allMenuResources = findAllMenus(sysUser.getApp().getId());
	 * List<Resource> menus = new ArrayList<Resource>(); for (Resource
	 * menuResource : allMenuResources) { if (menuResource.isRootNode()) {
	 * continue; } if (!hasPermission(permissions, menuResource)) { continue; }
	 * menus.add(menuResource); } return menus; }
	 */

	private boolean hasPermission(Set<String> permissions, Resource resource) {
		if (StringUtils.isEmpty(resource.getPermission())) {
			return true;
		}
		for (String permission : permissions) {
			WildcardPermission p1 = new WildcardPermission(permission);
			WildcardPermission p2 = new WildcardPermission(resource.getPermission());
			if (p1.implies(p2) || p2.implies(p1)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Resource> findResources(Set<Long> resourceIds) {
		return dao.findResources(resourceIds);
	}

	public static void main(String[] args) {
		Set<String> permissions = new HashSet<>();
		permissions.add("user:tab:a:view");
		permissions.add("user:tab:a:sign");
		String p = "user:*"; // 等价于user:tab:*
		for (String permission : permissions) {
			WildcardPermission p1 = new WildcardPermission(permission);
			WildcardPermission p2 = new WildcardPermission(p);
			if (p1.implies(p2) || p2.implies(p1)) {
				System.out.println("permission: " + permission);
			}
		}
	}

	@Override
	@Transactional
	public void batchCreate(List<Resource> resources){
		dao.batchCreate(resources);
	}

}
