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
}
