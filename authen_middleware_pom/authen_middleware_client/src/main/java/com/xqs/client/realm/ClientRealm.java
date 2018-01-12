package com.xqs.client.realm;

import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.xqs.remote.PermissionContext;
import com.xqs.remote.RemoteServiceInterface;

public class ClientRealm extends AuthorizingRealm {
	private RemoteServiceInterface remoteService;
	private String appId;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public RemoteServiceInterface getRemoteService() {
		return remoteService;
	}

	public void setRemoteService(RemoteServiceInterface remoteService) {
		this.remoteService = remoteService;
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
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		PermissionContext permissionContext = remoteService.getPermissions(appKey, username);
		authorizationInfo.setRoles(permissionContext.getRoles());
		authorizationInfo.setStringPermissions(permissionContext.getPermissions());
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		throw new RuntimeException("不可调用");
	}

}
