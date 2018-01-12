package com.xqs.remote;

import java.io.Serializable;

import org.apache.shiro.session.Session;

public interface RemoteServiceInterface {
	Session getSession(Long appId, Serializable sessionId);

	Serializable createSession(Session session);

	void updateSession(Long appId, Session session);

	void deleteSession(Long appId, Session session);

	PermissionContext getPermissions(String appKey, String username);
	
}
