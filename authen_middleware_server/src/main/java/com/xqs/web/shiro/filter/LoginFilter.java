package com.xqs.web.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.alibaba.druid.util.StringUtils;
import com.xqs.authenticate.token.AppUsernamePasswordToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginFilter extends FormAuthenticationFilter {
	public static final String DEFAULT_APPKEY_PARAM = "appKey";

	private String appKeyParam = DEFAULT_APPKEY_PARAM;

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String appkey = getAppkey(request);
		String username = getUsername(request);
		String password = getPassword(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		return new AppUsernamePasswordToken(appkey, username, password, rememberMe, host);
	}

	@Override
	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
		String fallbackUrl = (String) getSubject(request, response).getSession().getAttribute("authc.fallbackUrl");
		if (StringUtils.isEmpty(fallbackUrl)) {
			fallbackUrl = getSuccessUrl();
		}
		WebUtils.redirectToSavedRequest(request, response, fallbackUrl);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return super.onAccessDenied(request, response);
	}

	/**
	 * 登陆成功后交由控制器处理，不再进行跳转
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		return false;
	}

	protected String getAppkey(ServletRequest request) {
		return WebUtils.getCleanParam(request, getAppKeyParam());
	}
}
