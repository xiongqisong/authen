package com.xqs.web.shiro.filter;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.xqs.Constants;
import com.xqs.service.base.intf.UserService;

public class SysUserFilter extends PathMatchingFilter {
	@Autowired
	private UserService userService;

	@Override
	protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Map<String, String> principal = (Map<String, String>) SecurityUtils.getSubject().getPrincipal();
		String username = null;
		String appKey = null;
		for (Map.Entry<String, String> entry : principal.entrySet()) {
			username = entry.getKey();
			appKey = entry.getValue();
		}
		request.setAttribute(Constants.CURRENT_USER, userService.findByUsernameAndApp(username, appKey));
		return true;
	}

}
