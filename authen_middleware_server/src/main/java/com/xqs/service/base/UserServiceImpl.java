package com.xqs.service.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.xqs.dao.UserDao;
import com.xqs.entity.App;
import com.xqs.entity.Role;
import com.xqs.entity.User;
import com.xqs.service.base.intf.RoleService;
import com.xqs.service.base.intf.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao dao;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PasswordHelper passwordHelper;

	@Override
	@Transactional
	public User create(User user) {
		passwordHelper.encryptPassword(user);
		return dao.create(user);
	}

	@Override
	public User get(Long id) {
		return dao.get(id);
	}

	@Override
	@Transactional
	public User update(User base, User changed) {
		return dao.safeUpdate(base, changed);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional
	public void changePassword(Long id, String newPassword) {
		User user = dao.get(id);
		User changed = user.clone();
		changed.setPassword(newPassword);
		passwordHelper.encryptPassword(changed);
		update(user, changed);
	}

	@Override
	public List<User> findAll(Long appId) {
		return dao.findAll(appId);
	}

	@Override
	public User findByUsernameAndApp(String username, String appKey) {
		return dao.findByUsernameAndApp(username, appKey);
	}

	/*
	 * @Override public Set<String> findStringRoles(User user) {
	 * Assert.notNull(user, "用户不应为空"); Set<String> stringRoles = new
	 * HashSet<String>(); List<Role> roles = findRoles(user); if (roles != null
	 * && !roles.isEmpty()) { for (Role role : roles) {
	 * stringRoles.add(role.getRole()); } } return stringRoles; }
	 * 
	 * public Set<String> findPermissions(User user) { Assert.notNull(user,
	 * "用户不应为空"); List<Long> roleIds = user.getRoles(); return
	 * roleService.findFeatures(roleIds.toArray(new Long[roleIds.size()])); }
	 */

	@Override
	public User initAdmin(App app, Role admin) {
		Assert.notNull(app, "应用不应为空");
		Assert.notNull(admin, "超级管理员角色不应不存在");
		User sysUser = new User(0L, app, "admin", "123456", admin.getId().toString(), false);
		create(sysUser);
		return sysUser;
	}
}
