package com.xqs.web.shiro.cookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.SimpleCookie;

public class EnhanceSimpleCookie extends SimpleCookie {
	public EnhanceSimpleCookie(String name) {
		super(name);	
	}

	@Override
	public String readValue(HttpServletRequest request, HttpServletResponse ignored) {
		String sid = request.getParameter("sid");
		return sid;
	}
	
}
