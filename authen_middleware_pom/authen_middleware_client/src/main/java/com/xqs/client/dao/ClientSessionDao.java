package com.xqs.client.dao;

import java.io.Serializable;
import java.util.Map;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;

import com.xqs.remote.RemoteServiceInterface;

public class ClientSessionDao extends CachingSessionDAO {
	private RemoteServiceInterface remoteService;
	private String appId;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public RemoteServiceInterface getRemoteService() {
		return remoteService;
	}

	public void setRemoteService(RemoteServiceInterface remoteService) {
		this.remoteService = remoteService;
	}

	@Override
	protected void doUpdate(Session session) {
		remoteService.updateSession(Long.valueOf(appId), session);
	}

	@Override
	protected void doDelete(Session session) {
		remoteService.deleteSession(Long.valueOf(appId), session);
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId  = remoteService.createSession(session);
		assignSessionId(session, sessionId);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		return remoteService.getSession(Long.valueOf(appId), sessionId);
	}

}
