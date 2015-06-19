package com.chinacreator.asp.comp.sys.core.security.shiro.impls;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.core.AccessControlMessages;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.asp.comp.sys.core.role.eo.RoleEO;
import com.chinacreator.asp.comp.sys.core.security.shiro.bean.SimpleUser;
import com.chinacreator.asp.comp.sys.core.security.shiro.interfaces.IdentitifyInfomationFetcher;
import com.chinacreator.asp.comp.sys.core.user.dao.UserDao;
import com.chinacreator.asp.comp.sys.core.user.dao.UserInstanceDao;
import com.chinacreator.asp.comp.sys.core.user.eo.UserEO;
import com.chinacreator.c2.ioc.ApplicationContextManager;

public class SimpleIdentitifyInfomationFetcher implements
		IdentitifyInfomationFetcher {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private UserDao getUserDao() {
		return ApplicationContextManager.getContext().getBean(UserDao.class);
	}

	private UserInstanceDao getUserInstanceDao() {
		return ApplicationContextManager.getContext().getBean(
				UserInstanceDao.class);
	}

	public SimpleUser findBy(String loginName) throws Exception {
		SimpleUser simpleUser = new SimpleUser();
		UserEO userEO = getUserDao().queryByUserName(loginName);
		if (null == userEO || null == userEO.getUserId()
				|| userEO.getUserId().trim().equals("")
				&& null == userEO.getUserName()
				&& userEO.getUserName().trim().equals("")) {
			logger.error(AccessControlMessages
					.getString("ACCESSCONTROL.ACCOUNT_LEFT")
					+ loginName
					+ AccessControlMessages
							.getString("ACCESSCONTROL.ACCOUNT_RIGHT"));
			throw new Exception(
					AccessControlMessages
							.getString("ACCESSCONTROL.LOGINNAME_IS_EXISTS"));
		}
		BeanCopierUtil.copy(userEO, simpleUser);
		return simpleUser;
	}

	public Collection<String> getUserRoles(String userId) {
		Collection<String> roles = new HashSet<String>();
		List<RoleEO> roleEOList = getUserInstanceDao().queryRolesByUserId(
				userId);
		if (null != roleEOList && !roleEOList.isEmpty()) {
			for (RoleEO roleEO : roleEOList) {
				if (null != roleEO && null != roleEO.getRoleName()
						&& !roleEO.getRoleName().trim().equals("")) {
					roles.add(roleEO.getRoleName());
				}
			}
		}
		return roles;
	}

	public Collection<String> getPermExprs(String userId) {
		Collection<String> permExprs = new HashSet<String>();
		List<PrivilegeEO> privilegeEOList = getUserInstanceDao()
				.queryPrivilegesByUserId(userId);
		if (null != privilegeEOList && !privilegeEOList.isEmpty()) {
			for (PrivilegeEO privilegeEO : privilegeEOList) {
				if (null != privilegeEO && null != privilegeEO.getPermExpr()
						&& !privilegeEO.getPermExpr().trim().equals("")) {
					permExprs.add(privilegeEO.getPermExpr());
				}
			}
		}
		return permExprs;
	}
}
