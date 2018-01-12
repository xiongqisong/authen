package com.xqs.service.base.intf;

import java.util.List;

import com.xqs.entity.App;

public interface AppService {
	App create(App sysResource);

	App update(App base, App changed);

	void delete(Long id);

	App get(Long id);

	List<App> findAll();
}
