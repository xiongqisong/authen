package com.xqs.service.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xqs.dao.OrganizationDao;
import com.xqs.entity.Organization;
import com.xqs.service.base.intf.OrganizationService;

@Service
public class OrganizationServiceImpl implements OrganizationService {
	@Autowired
	private OrganizationDao dao;

	@Override
	public Organization create(Organization organization) {
		return dao.create(organization);
	}

	@Override
	public Organization get(Long id) {
		return dao.get(id);
	}

	@Override
	public Organization update(Organization base, Organization changed) {
		return dao.safeUpdate(base, changed);
	}

	public void delete(Long id) {
		dao.delete(id);
	}

	public List<Organization> findAll(Long appId) {
		return dao.findAll(appId);
	}

}
