package com.xqs.authenticate.token;

import org.apache.shiro.authc.UsernamePasswordToken;

import lombok.Getter;

/**
 * 扩展UsernamePasswordToken，支持不同应用的用户登录
 * 
 * @author ycr
 *
 */
@Getter
public class AppUsernamePasswordToken extends UsernamePasswordToken {
	private String appkey;

	public AppUsernamePasswordToken(String appkey, String username, String password, boolean rememberMe, String host) {
		super(username, password, rememberMe, host);
		this.appkey = appkey;
	}

}
