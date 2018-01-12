package com.xqs.controller.entrance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.xqs.entity.App;
import com.xqs.service.first.intf.RegisterService;
import com.xqs.web.Result;

@Controller("register")
public class RegisterController {
	@Autowired
	private RegisterService registerService;

	@PostMapping(value = "app")
	public Result<Void> registerApp(App app) {
		registerService.registerApp(app);
		return Result.success();
	}
}
