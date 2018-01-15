package com.xqs.service.base;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xqs.dao.AppDao;
import com.xqs.entity.App;
import com.xqs.service.base.intf.AppService;

@Service
public class AppServiceImpl implements AppService {
	@Autowired
	private AppDao dao;

	public App create(App app) {
		Date now = new Date();
		app.setCreateTime(now);
		app.setUpdateTime(now);
		return dao.create(app);
	}

	public App update(App base, App changed) {
		throw new UnsupportedOperationException("暂不支持更新App");
	}

	public void delete(Long id) {
		dao.delete(id);
	}

	public App get(Long id) {
		return dao.get(id);
	}

	public List<App> findAll() {
		return dao.findAll();
	}

	@Override
	public App findByName(String name) {
		Assert.notNull(name, "应用名称不应为空");
		return dao.findByName(name);
	}
}
