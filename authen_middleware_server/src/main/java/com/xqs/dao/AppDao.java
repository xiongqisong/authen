package com.xqs.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xqs.entity.App;

@Repository
public class AppDao extends IdEntityDao<App> {
	public List<App> findAll() {
		String hql = "from App";
		return createQuery(hql).list();
	}

	public App findByName(String name) {
		String hql = "from App where name = :name";
		return (App) createQuery(hql).setParameter("name", name).uniqueResult();
	}
}
