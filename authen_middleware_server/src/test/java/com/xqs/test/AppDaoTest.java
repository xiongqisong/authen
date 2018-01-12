package com.xqs.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xqs.dao.AppDao;
import com.xqs.entity.App;

public class AppDaoTest extends BaseTest {
	@Autowired
	AppDao dao;

	@Test
	public void testAdd() {
		App app = new App();
		app.setName("test");
		app.setContextPath("/test");
		app.setAppKey("123456");
		dao.create(app);
	}

	@Test
	public void testGet() {
		App app = dao.get(14L);
		System.out.println(app);
	}
}
