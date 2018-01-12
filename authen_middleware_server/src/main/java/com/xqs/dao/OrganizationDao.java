package com.xqs.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xqs.entity.Organization;

@Repository
public class OrganizationDao extends IdEntityDao<Organization> {
	public List<Organization> findAll(Long appId) {
		String hql = "from Organization where app.id = :appId";
		return createQuery(hql).setParameter("appId", appId).list();
	}
}
