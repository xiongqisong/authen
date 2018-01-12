package com.xqs.interceptor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xqs.entity.App;
import com.xqs.service.base.intf.AppService;

/**
 * 处理跨域请求拦截器
 * 
 * @author ycr
 *
 */
public class CORSInterceptor extends HandlerInterceptorAdapter {
	private AppService sysAppService;
	private static List<String> LEGAL_ORIGINS;// 合法的源集合

	@Autowired
	public CORSInterceptor(AppService sysAppService) {
		this.sysAppService = sysAppService;
	}

	/**
	 * 初始化合法的源
	 */
	@PostConstruct
	private void initAllLegalOrigin() {
		List<App> sysAppList = sysAppService.findAll();
		if (sysAppList != null && !sysAppList.isEmpty()) {
			LEGAL_ORIGINS = new CopyOnWriteArrayList<String>();
			for (App sysApp : sysAppList) {
				LEGAL_ORIGINS.add(sysApp.getContextPath());
			}
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String origin = request.getHeader("Origin");
		if (LEGAL_ORIGINS.contains(origin)) {
			response.setHeader("Access-Control-Allow-Origin", origin);
		}
		response.setHeader("Access-Control-Allow-Method", "GET,POST,OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "content-type,x-client-ip,x-forwarded-for");
		response.setHeader("Access-control-Allow-Credentials", "true");
		return true;
	}

}
