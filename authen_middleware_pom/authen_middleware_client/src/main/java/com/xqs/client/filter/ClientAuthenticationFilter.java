package com.xqs.client.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;

import com.xqs.core.ClientSavedRequest;

public class ClientAuthenticationFilter extends AuthenticationFilter {
	private String sid;
	/**
	 * 1.判断是否已经登录成功
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		// 从当前线程中获取subject，如果subject不存在则会创建一个subject，该subject的session是空的
		// PS：由于最初进入的一定是AbstarctShiroFilter的doFilterInternal方法，该方法会创建一个初始的subject
		// 在创建的过程中会尝试去request的cookie中取出sessionId，然后去缓存中查询是否有保存对应的Session对象
		// 如果有，则设置到subject中，最后将subject绑定到当前线程中---ThreadContext的InheritableThreadLocalMap
		// 以KV形式保存Subject及SecurityManager，InheritableThreadLocalMap继承自InheritableThreadLocal(必看该类！Josh Bloch & Doug Lea两位大神编写)
		Subject subject = getSubject(request, response);
		return subject.isAuthenticated();
	}

	/**
	 * 2.如果用户未登录，则会保存用户的请求并跳转到login页面
	 * 这里和FormAuthenticationFilter的onAccessDenied的实现不一样
	 * FormAuthenticationFilter是作为shiro的登录过滤器存在的，所以
	 * 对于“不是请求登录”的request，它应该拦截，并且要保存当前的request，重定向到“登录”；
	 * 而对于“请求登录”的request则不应该拦截，而是：对于GET请求则跳转至登录页/对于POST请求则进行身份验证，如果身份认证成功则重定向至之前保存的request(session中是否保存了名为"shiroSavedRequest"的SaveRequest对象)或者默认的successUrl
	 * 这里注意，如果身份验证成功，则后续的过滤器链将不再执行。
	 * 
	 * 改造后，ClientAuthenticatoinFilter在用户未进行验证时，保存当前请求，重定向到“服务端登录”。客户端不做任何登录功能，登录全部交由服务端处理
	 * 
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 1.请求登录
		if(isLoginRequest(request, response)) {
			return true;
		} else {
			HttpServletRequest hRequest = WebUtils.toHttp(request);
			HttpServletResponse hResponse = WebUtils.toHttp(response);
			
			// 获取请求的资源地址
			String backUrl = hRequest.getRequestURI();
			// 保存请求
			saveRequest(request, backUrl, getDefaultBackUrl(hRequest));
			Cookie cookie = new SimpleCookie("sid");
			cookie.setHttpOnly(true);
			cookie.setDomain("");
			cookie.setPath("/");
			cookie.setMaxAge(3600);
			cookie.setValue(this.sid);
			cookie.saveTo(hRequest, hResponse);
			// 重定向至登录
			redirectToLogin(request, response);
			return false;
		}
		
		
	}

	protected void saveRequest(ServletRequest request, String backUrl, String fallbackUrl) {
		Subject subject = SecurityUtils.getSubject();// 获取当前线程中的subject
		// 预期：这里应该由服务器创建一个session。
		// 1.调用create(DefaultSessionManager实现)创建session（交由具体的SessionDAO实现完成，
		// AbstractSessionDAO在create方法定义了基础执行模板:
		//		(1)doCreate---创建session，MySessionDaoAdapter实现
		//		(2)验证sessionId是否合法
		//		(3)返回sessionId
		// CachingSessionDAO对create方法进行了扩展：
		//		(1)调用super.create
		//		(2)缓存session---KV存储，K=sessionId，V=session对象
		// 因此我们只要关心doCreate方法就可以了
		// 2.调用onStart创建一个保存了sessionId的Cookie(实现类为SimpleCookie，可以在配置文件中设置)存入至requ
		Session session = subject.getSession();// 获取session，如果session为空，则创建session。该session由服务器创建并持久化至DB
		System.out.println(session.getId());
		this.sid = (String) session.getId();
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		session.setAttribute("authc.fallbackUrl", fallbackUrl);
		SavedRequest savedRequest = new ClientSavedRequest(httpRequest, backUrl);
		session.setAttribute(WebUtils.SAVED_REQUEST_KEY, savedRequest);
	}

	private String getDefaultBackUrl(HttpServletRequest request) {
		String scheme = request.getScheme();
		String domain = request.getServerName();
		int port = request.getServerPort();
		String contextPath = request.getContextPath();
		StringBuilder backUrl = new StringBuilder(scheme);
		backUrl.append("://").append(domain);
		if ("http".equalsIgnoreCase(scheme) && port != 80) {
			backUrl.append(":").append(String.valueOf(port));
		} else if ("https".equalsIgnoreCase(scheme) && port != 443) {
			backUrl.append(":").append(String.valueOf("port"));
		}
		backUrl.append(contextPath).append(getSuccessUrl());
		return backUrl.toString();
	}

	@Override
	protected final void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		String loginUrl = getLoginUrl();
		Map<String,Object> queryParams = new HashMap<String,Object>();
		queryParams.put("sid", getSubject(request, response).getSession().getId());
		WebUtils.issueRedirect(request, response, loginUrl, queryParams);
	}

}
