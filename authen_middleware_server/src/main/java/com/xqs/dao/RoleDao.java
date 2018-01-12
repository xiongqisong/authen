package com.xqs.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xqs.entity.Role;

@Repository
public class RoleDao extends IdEntityDao<Role> {
	public List<Role> findAll(Long appId) {
		String hql = "from Role where app.id = :appId";
		return createQuery(hql).setParameter("appId", appId).list();
	}

	public List<Role> findRoles(Long[] roleIds) {
		String hql = "from Role where id in :roleIds";
		return createQuery(hql).setParameterList("roleIds", roleIds).list();
	}
}
