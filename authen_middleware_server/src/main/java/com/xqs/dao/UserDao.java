package com.xqs.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xqs.entity.User;

@Repository
public class UserDao extends IdEntityDao<User> {
	@Transactional
	public List<User> findAll(Long appId) {
		String hql = "from User where app.id = :appId";
		return this.createQuery(hql).setParameter("appId", appId).list();
	}

	@Transactional
	public User findByUsernameAndApp(String username, String appKey) {
		String hql = "from User where username = :username and app.appKey = :appKey";
		return (User) this.createQuery(hql).setParameter("username", username).setParameter("appKey", appKey)
				.uniqueResult();
	}
}
