package com.xqs.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xqs.entity.App;
import com.xqs.service.base.intf.AppService;
import com.xqs.service.first.intf.RegisterService;

public class RegisterServiceTest extends BaseTest {
	@Autowired
	RegisterService registerService;
	@Autowired
	AppService appService;

	@Test
	public void testRegisterApp() {
		App app = new App("测试应用", "test/", "abcdef", "testsecret", true, new Date(), new Date());
		registerService.registerApp(app);
	}
}
