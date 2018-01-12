package com.xqs.dao;

import org.springframework.stereotype.Repository;

import com.xqs.entity.Sessions;

@Repository
public class SessionsDao extends IdEntityDao<Sessions> {
	public void delete(String sid) {
		getSession().delete(getSessionBySid(sid));
	}

	public Sessions getSessionBySid(String sid) {
		String hql = "from Sessions where sid = :sid";
		return (Sessions) createQuery(hql).setParameter("sid", sid).uniqueResult();
	}
}
