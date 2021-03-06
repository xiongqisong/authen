package com.xqs.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xqs.entity.App;
import com.xqs.entity.Role;
import com.xqs.service.base.intf.AppService;
import com.xqs.service.base.intf.ResourceService;
import com.xqs.service.base.intf.RoleService;
import com.xqs.service.base.intf.UserService;
import com.xqs.web.Result;

@Controller
@RequestMapping("/app")
public class AppController {
	private AppService appService;
	private RoleService roleService;
	private ResourceService resourceService;
	private UserService userService;

	@Autowired
	public AppController(AppService appService, RoleService roleService, ResourceService resourceService,
			UserService userService) {
		super();
		this.appService = appService;
		this.roleService = roleService;
		this.resourceService = resourceService;
		this.userService = userService;
	}
}
