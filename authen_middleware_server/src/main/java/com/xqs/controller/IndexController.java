package com.xqs.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.xqs.entity.Feature;
import com.xqs.entity.User;
import com.xqs.service.base.intf.ResourceService;
import com.xqs.service.base.intf.UserService;
import com.xqs.service.first.intf.UserInfoService;
import com.xqs.web.bind.annotation.CurrentUser;

@Controller
public class IndexController {
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserInfoService userInfoService;

	@RequestMapping("/")
	public String index(@CurrentUser User loginUser, Model model) {
		Map<Long, Map<Long, List<Feature>>> layers = userInfoService.getFeatureLayers(loginUser);
		model.addAttribute("layers", layers);
		model.addAttribute("app", loginUser.getApp());
		return "index";
	}

}
