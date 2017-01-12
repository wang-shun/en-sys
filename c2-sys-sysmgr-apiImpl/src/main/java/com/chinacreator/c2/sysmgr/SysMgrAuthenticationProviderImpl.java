package com.chinacreator.c2.sysmgr;

import com.chinacreator.asp.comp.sys.common.exception.SysException;
import com.chinacreator.asp.comp.sys.core.AccessControlMessages;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;
import com.chinacreator.asp.comp.sys.core.security.shiro.token.SysMgrUsernamePasswordToken;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.sysmgr.exception.AuthenticationException;

/**
 * 访问控制服务接口实现
 * 
 * @author 彭盛
 * 
 */
public class SysMgrAuthenticationProviderImpl implements AuthenticationProvider {
	@Override
	public boolean login(AuthenticationToken token) throws AuthenticationException {
		boolean isLogin = false;
		try {
			isLogin = getAccessControlService().login(token);
		} catch (SysException e) {
			throw new AuthenticationException(e);
		} catch (Exception e) {
			throw new AuthenticationException(e);
		}
		return isLogin;
	}

	@Override
	public User login(String username, String password) throws AuthenticationException {
		if (null != username && null != password && !"".equals(username.trim()) && !"".equals(password.trim())) {
			AuthenticationToken token = new SysMgrUsernamePasswordToken();
			((SysMgrUsernamePasswordToken) token).setUsername(username);
			((SysMgrUsernamePasswordToken) token).setPassword(password.toCharArray());
			if (login(token)) {
				return getSubject();
			}
		} else {
			throw new SysException(AccessControlMessages.getString("ACCESSCONTROL.USERNAME_ORPASSWORD_NULL"));
		}
		return null;
	}

	@Override
	public String loginToRedirectUri(String username, String password) throws AuthenticationException {
		if (null != username && null != password && !"".equals(username.trim()) && !"".equals(password.trim())) {
			AuthenticationToken token = new SysMgrUsernamePasswordToken();
			((SysMgrUsernamePasswordToken) token).setUsername(username);
			((SysMgrUsernamePasswordToken) token).setPassword(password.toCharArray());
			if (login(token)) {
				LoginSuccessToRedirectUri loginSuccessToRedirectUri = null;
				try {
					loginSuccessToRedirectUri = (LoginSuccessToRedirectUri) ApplicationContextManager.getContext()
							.getBean("LoginSuccessToRedirectUri");
					if (null != loginSuccessToRedirectUri) {
						return loginSuccessToRedirectUri.getRedirectUri();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			throw new SysException(AccessControlMessages.getString("ACCESSCONTROL.USERNAME_ORPASSWORD_NULL"));
		}
		return null;
	}

	@Override
	public String logout() {
		return getAccessControlService().logout();
	}

	@Override
	public boolean isAuthenticated() {
		return getAccessControlService().isAuthenticated();
	}

	@Override
	public User getSubject() {
		AccessControlService accessControlService = getAccessControlService();
		if (accessControlService.isAuthenticated()) {
			UserDTO dto = accessControlService.getUser();
			if (null != dto) {
				dto.setUserPassword(null);

				User user = new User();
				user.setId(dto.getUserId());
				user.setName(dto.getUserName());
				user.setRealname(dto.getUserRealname());
				user.put("userDTO", dto);
				return user;
			}
		}
		return null;
	}

	private AccessControlService getAccessControlService() {
		return ApplicationContextManager.getContext().getBean(AccessControlService.class);
	}
}
