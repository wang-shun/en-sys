package com.chinacreator.asp.comp.sys.core.security.shiro.service;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.chinacreator.asp.comp.sys.core.AccessControlMessages;
import com.chinacreator.asp.comp.sys.core.security.shiro.bean.SimpleUser;
import com.chinacreator.asp.comp.sys.core.security.shiro.interfaces.IdentitifyInfomationFetcher;
import com.chinacreator.c2.ioc.ApplicationContextManager;

@Component
public class AuthorizeService extends AuthorizingRealm {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		String username = token.getUsername();
		if (null == username || username.trim().equals("")) {
			throw new AuthenticationException(
					AccessControlMessages
							.getString("ACCESSCONTROL.LOGINNAME_IS_NULL"));
		}

		SimpleUser user = null; 
		try {
			user = getIdentitifyInfomationFetcher().findBy(username.toLowerCase());
		} catch (Exception e) {
			logger.error(AccessControlMessages
					.getString("ACCESSCONTROL.FAIL_TO_GET_USER_LOGININGO"), e);
			throw new AuthenticationException(e.getMessage());
		}

		SimpleAuthenticationInfo Info = new SimpleAuthenticationInfo(user,
				user.getUserPassword(), getName());

		return Info;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if (null != principals) {
			SimpleUser csUser = principals.oneByType(SimpleUser.class);
			if (null != csUser) {
				String userid = csUser.getUserId();
				String username = csUser.getUserName();
				Collection<String> roles = getIdentitifyInfomationFetcher()
						.getUserRoles(userid);
				Collection<String> permExpr = getIdentitifyInfomationFetcher()
						.getPermExprs(userid);

				// 获取用户的所有角色
				if (null != roles && !roles.isEmpty()) {
					info.addRoles(roles);
				}

				// 获取用户的所有权限
				if (null != permExpr && !permExpr.isEmpty()) {
					info.addStringPermissions(permExpr);
				}

				logger.debug(AccessControlMessages
						.getString("ACCESSCONTROL.CURRENTUSER_LEFT")
						+ username
						+ AccessControlMessages
								.getString("ACCESSCONTROL.HAS_ROLES_RIGHT")
						+ roles);
				logger.debug(AccessControlMessages
						.getString("ACCESSCONTROL.CURRENTUSER_LEFT")
						+ username
						+ AccessControlMessages
								.getString("ACCESSCONTROL.HAS_PRIVILEGES_RIGHT")
						+ permExpr);

				csUser.setAuthorizationInfo(info);

			} else {
				logger.error(AccessControlMessages
						.getString("ACCESSCONTROL.USER_INFO_IS_NULL"));
			}
		} else {
			logger.error(AccessControlMessages
					.getString("ACCESSCONTROL.CURRENT_USER_INFO_IS_NULL"));
		}
		return info;
	}

	public void clearCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	public void clearCachedAuthorizationInfo(Object principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	public IdentitifyInfomationFetcher getIdentitifyInfomationFetcher() {
		return (IdentitifyInfomationFetcher) ApplicationContextManager
				.getContext().getBean("identitifyInfomationFetcher");
	}
}