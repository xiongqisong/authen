package com.xqs.test;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.xqs.dao.AppDao;
import com.xqs.dao.FeatureDao;
import com.xqs.dao.ResourceDao;
import com.xqs.entity.App;
import com.xqs.entity.Feature;
import com.xqs.entity.Feature.FeatureType;
import com.xqs.entity.Resource;
import com.xqs.service.base.FeatureServiceImpl;

public class FeatureDaoTest extends BaseTest {
	@Autowired
	FeatureDao dao;
	@Autowired
	AppDao appDao;
	@Autowired
	ResourceDao resourceDao;
	@Autowired
	FeatureServiceImpl service;

	@Test
	public void test() {
		App app = appDao.get(12L);
		Date now = new Date();
		Resource r1 = resourceDao.get(217L);
		Resource r2 = resourceDao.get(221L);
		Feature f1 = new Feature(app, "用户", FeatureType.menu, null, 0L, "0/", true, now, now);
		dao.create(f1);
		Feature f2 = new Feature(app, "用户管理", FeatureType.menu, r1, null, "", true, now, now);
		f2.setParent(f1);
		dao.create(f2);
		Feature f3 = new Feature(app, "用户查看", FeatureType.button, r2, null, "", true, now, now);
		f3.setParent(f2);
		dao.create(f3);
	}

	@Test
	public void testGet() {
		Feature f = dao.get(2L);
		System.out.println(f.toString());
	}

	@Test
	public void testUpdate() {
		Feature f = dao.get(8L);
		Feature changed = f.clone();
		changed.setName("测试");
		dao.safeUpdate(f, changed);
	}

}