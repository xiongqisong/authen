package com.xqs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xqs.web.Result;

@Controller
public class LoginController {
	/**
	 * 登录如果成功则会由FormAuthenticationFilter.onLoginSuccess重定向至业务系统的资源 如果失败则返回失败信息
	 * 
	 * @param req
	 * @param model
	 * @return
	 */
	/*@RequestMapping("/login")
	@ResponseBody
	public Result<String> showLoginForm(HttpServletRequest req, HttpServletResponse resp, Model model) {
		String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");// shiro自己包装的登录失败信息
		String error = null;
		resp.setHeader("Access-Control-Allow-Origin", "*");
		if (exceptionClassName != null) {
			if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
				error = "用户名/密码错误";
			} else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
				error = "用户名/密码错误";
			} else if (exceptionClassName != null) {
				error = "其他错误" + exceptionClassName;
			}
			return Result.errorWithMsg(error);
		} else {
			return Result.success();
		}
	}*/
	
	@RequestMapping("/login")
	public String showLoginForm(HttpServletRequest req, HttpServletResponse resp, Model model) {
		String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");// shiro自己包装的登录失败信息
		String error = null;
		resp.setHeader("Access-Control-Allow-Origin", "*");
		if (exceptionClassName != null) {
			if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
				error = "用户名/密码错误";
			} else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
				error = "用户名/密码错误";
			} else if (exceptionClassName != null) {
				error = "其他错误" + exceptionClassName;
			}
			model.addAttribute("error", error);
		}
		return "login";
	}
}
