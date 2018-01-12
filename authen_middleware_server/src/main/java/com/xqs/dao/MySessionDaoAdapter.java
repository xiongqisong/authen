package com.xqs.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import com.xqs.entity.Sessions;
import com.xqs.util.SerializableUtil;

public class MySessionDaoAdapter extends CachingSessionDAO {
	@Autowired
	private SessionsDao sessionsDao;

	@Override
	protected void doUpdate(Session session) {
		if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
			return;
		}
		String sid = session.getId().toString();
		Sessions base = sessionsDao.getSessionBySid(sid);
		Sessions changed = base.clone();
		changed.setSession(SerializableUtil.serialize(session));
		changed.setUpdateTime(new Date());
		sessionsDao.safeUpdate(base, changed);
	}

	@Override
	protected void doDelete(Session session) {
		sessionsDao.delete(session.getId().toString());
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		Date now = new Date();
		Sessions mySession = new Sessions(sessionId.toString(), SerializableUtil.serialize(session), now, now);
		return sessionsDao.create(mySession).getSid();
	}

	@Override
	protected Session doReadSession(Serializable sid) {
		Sessions sessions = sessionsDao.getSessionBySid(sid.toString());
		if (sessions != null) {
			return SerializableUtil.deserialize(sessions.getSession());
		}
		return null;
	}

}
