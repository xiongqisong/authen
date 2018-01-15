package com.xqs.test;

import java.util.Set;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xqs.entity.Role;
import com.xqs.entity.User;
import com.xqs.service.base.intf.UserService;
import com.xqs.service.first.intf.UserInfoService;

public class UserInfoServiceTest extends BaseTest {
	@Autowired
	UserInfoService service;
	@Autowired
	UserService userService;

	@Test
	@Transactional
	public void test() {
		User user = userService.findByUsernameAndApp("admin", "abcdef");
		service.findFeatures(user);
	}
}
