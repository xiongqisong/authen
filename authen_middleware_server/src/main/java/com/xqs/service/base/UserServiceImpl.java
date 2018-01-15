package com.xqs.service.base;

import java.util.Date;
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
		Date now = new Date();
		user.setCreateTime(now);
		user.setUpdateTime(now);
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
}
