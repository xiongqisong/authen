package com.xqs.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xqs.entity.User;
import com.xqs.service.base.intf.UserService;

public class UserTest extends BaseTest {
	@Autowired
	UserService userService;

	@Test
	public void test() {
		User user = userService.findByUsernameAndApp("admin", "1234567");
		System.out.println(user);
	}
}
