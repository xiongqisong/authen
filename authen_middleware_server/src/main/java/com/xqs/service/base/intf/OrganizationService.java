package com.xqs.service.base.intf;

import java.util.List;

import com.xqs.entity.Organization;
import com.xqs.entity.Role;

public interface OrganizationService {
	Organization create(Organization organization);

	Organization get(Long id);

	Organization update(Organization base, Organization changed);

	void delete(Long id);

	List<Organization> findAll(Long appId);
}
