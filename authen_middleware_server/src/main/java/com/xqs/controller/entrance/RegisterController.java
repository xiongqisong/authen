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

	@SuppressWarnings("unchecked")
	@PostMapping(value = "app")
	public Result<App> registerApp(App app) {
		registerService.registerApp(app);
		return Result.sucWithMsgData(
				"please hold APP_KEY and APP_SECRET carefully, the server only distribute them once", app);
	}
}
