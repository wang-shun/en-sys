package com.chinacreator.c2.sysmgr;

import java.util.Map;

import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.sysmgr.exception.AuthenticationException;

/**
 * 鉴权服务接口实现
 * 
 * @author 彭盛
 * 
 */
public class SysMgrAuthorizationProviderImpl implements AuthorizationProvider {

	@Override
	public boolean isPermitted(String permExpr) throws AuthenticationException {
		try {
			return getAccessControlService().isPermitted(permExpr);
		} catch (Exception e) {
			throw new AuthenticationException(e.getMessage(), e);
		}
	}

	@Override
	public boolean[] isPermitted(String... permExpr)
			throws AuthenticationException {
		try {
			return getAccessControlService().isPermitted(permExpr);
		} catch (Exception e) {
			throw new AuthenticationException(e.getMessage(), e);
		}
	}

	@Override
	public Map<String, Boolean> isPermittedByBatch(String... permExpr)
			throws AuthenticationException {
		try {
			return getAccessControlService().isPermittedByBatch(permExpr);
		} catch (Exception e) {
			throw new AuthenticationException(e.getMessage(), e);
		}
	}

	@Override
	public boolean isAllPermitted(String... permExpr)
			throws AuthenticationException {
		try {
			return getAccessControlService().isAllPermitted(permExpr);
		} catch (Exception e) {
			throw new AuthenticationException(e.getMessage(), e);
		}
	}

	private AccessControlService getAccessControlService() {
		return ApplicationContextManager.getContext().getBean(
				AccessControlService.class);
	}
}
