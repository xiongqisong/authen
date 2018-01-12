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

	/**
	 * 注册应用 1.创建应用 2.为应用初始化基础资源，并创建“超级管理员”角色
	 * 
	 * @param name
	 * @param appKey
	 * @param appSecret
	 * @param available
	 * @return
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	@Transactional
	public Result<Void> register(App sysApp) {
		appService.create(sysApp);
		String resourceIds = resourceService.initResources(sysApp);
		Role admin = roleService.initRoles(sysApp, resourceIds);
		userService.initAdmin(sysApp, admin);
		return Result.success();
	}
}
