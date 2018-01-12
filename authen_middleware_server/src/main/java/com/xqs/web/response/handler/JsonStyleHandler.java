package com.xqs.web.response.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;
import com.xqs.web.Result;

/**
 * 响应处理器
 * 
 * @author ycr
 *
 */
@Deprecated
public class JsonStyleHandler implements HandlerMethodReturnValueHandler {
	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return returnType.getMethod().getReturnType().equals(Result.class);
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		// 标识请求已经被处理，后续不要渲染model
		mavContainer.setRequestHandled(true);
		if (!(returnValue instanceof Result)) {
			return;
		}
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType(ContentType.APPLICATION_JSON.toString());
			response.getWriter().print(JSON.toJSONString(returnValue));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
