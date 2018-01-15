package com.xqs.service.base.intf;

import java.util.List;

import com.xqs.entity.App;

public interface AppService {
	App create(App sysResource);

	App update(App base, App changed);

	void delete(Long id);

	App get(Long id);

	List<App> findAll();

	/**
	 * 根据"应用名称"获取应用 
	 * 该方法用于检查应用是否唯一，name字段上有唯一索引限制
	 * 
	 * @param name	应用名称
	 * @return
	 */
	App findByName(String name);
}
