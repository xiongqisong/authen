package com.xqs.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {
	@RequestMapping(value="/login")
	public String login(Model model){
		return "login";
	}
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello(Model model) {
		model.addAttribute("sid", SecurityUtils.getSubject().getSession().getId());
		return "hello";
	}

	@RequestMapping(value = "/byebye", method = RequestMethod.GET)
	@RequiresPermissions("risk:order:*")
	public String byebye() {
		return "byebye";
	}
}
