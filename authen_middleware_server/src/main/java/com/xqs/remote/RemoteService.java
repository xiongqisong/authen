package com.xqs.remote;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import com.xqs.entity.User;
import com.xqs.service.base.intf.UserService;
import com.xqs.service.first.intf.UserInfoService;

public class RemoteService implements RemoteServiceInterface {
	@Autowired
	private UserService userService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private SessionDAO sessionDao;

	@Override
	public Session getSession(Long appId, Serializable sessionId) {
		return sessionDao.readSession(sessionId);
	}

	@Override
	public Serializable createSession(Session session) {
		return sessionDao.create(session);
	}

	@Override
	public void updateSession(Long appId, Session session) {
		sessionDao.update(session);
	}

	@Override
	public void deleteSession(Long appId, Session session) {
		sessionDao.delete(session);
	}

	@Override
	public PermissionContext getPermissions(String appKey, String username) {
		PermissionContext permissionContext = new PermissionContext();
		User user = userService.findByUsernameAndApp(username, appKey);
		permissionContext.setRoles(userInfoService.capsuleRoles(user));
		permissionContext.setPermissions(userInfoService.capsuleResources(user));
		return permissionContext;
	}
}
