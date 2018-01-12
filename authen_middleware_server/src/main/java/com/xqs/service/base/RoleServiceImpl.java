package com.xqs.service.base;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.xqs.dao.RoleDao;
import com.xqs.entity.App;
import com.xqs.entity.Feature;
import com.xqs.entity.Role;
import com.xqs.service.base.intf.ResourceService;
import com.xqs.service.base.intf.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	public RoleDao dao;
	@Autowired
	public ResourceService resourceService;

	@Override
	@Transactional
	public Role create(Role role) {
		return dao.create(role);
	}

	@Override
	public Role get(Long id) {
		return dao.get(id);
	}

	@Override
	@Transactional
	public Role update(Role base, Role changed) {
		return dao.safeUpdate(base, changed);
	}

	@Transactional
	public void delete(Long id) {
		dao.delete(id);
	}

	public List<Role> findAll(Long appId) {
		return dao.findAll(appId);
	}

	public List<Role> findRoles(Long... roleIds) {
		return dao.findRoles(roleIds);
	}

	@Override
	public Role initRoles(App app, String featureIds) {
		Role admin = new Role(app, "admin", "超级管理员", featureIds, 0L, "0/", true, null, null);
		return create(admin);
	}

}
