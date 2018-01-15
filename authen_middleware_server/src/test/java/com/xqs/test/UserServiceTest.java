package com.xqs.test;

import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xqs.entity.Role;
import com.xqs.entity.User;
import com.xqs.service.base.intf.UserService;

public class UserServiceTest extends BaseTest {
	@Autowired
	UserService userService;

	@Test
	@Transactional
	public void test() {
		User user = userService.findByUsernameAndApp("admin", "abcdef");
		Set<Role> roles = user.getRole();
		for (Role r : roles) {
			System.out.println(r.getRole());
		}
		System.out.println(user);
	}
}
