package com.xqs.realm;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.xqs.authenticate.token.AppUsernamePasswordToken;
import com.xqs.entity.User;
import com.xqs.service.base.intf.UserService;
import com.xqs.service.first.intf.UserInfoService;

public class UserRealm extends AuthorizingRealm {
	@Autowired
	private UserService userService;
	@Autowired
	private UserInfoService userInfoService;

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof AppUsernamePasswordToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Map<String, String> principal = (Map<String, String>) principals.getPrimaryPrincipal();
		String username = null;
		String appKey = null;
		for (Map.Entry<String, String> entry : principal.entrySet()) {
			username = entry.getKey();
			appKey = entry.getValue();
		}

		User user = userService.findByUsernameAndApp(username, appKey);

		// 组装授权信息
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(userInfoService.capsuleRoles(user));
		authorizationInfo.setStringPermissions(userInfoService.capsuleResources(user));
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		String appKey = ((AppUsernamePasswordToken) token).getAppkey();

		User user = userService.findByUsernameAndApp(username, appKey);
		if (user == null) {
			throw new UnknownAccountException();
		}
		if (Boolean.TRUE.equals(user.getLocked())) {
			throw new LockedAccountException();
		}

		// 创建凭证
		Map<String, String> principals = new HashMap<String, String>();
		principals.put(user.getUsername(), user.getApp().getAppKey());
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(principals, user.getPassword(),
				ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
		return authenticationInfo;
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}
}
