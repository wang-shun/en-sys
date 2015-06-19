package com.chinacreator.asp.comp.sys.std.exp.security.shiro.impls;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.asp.comp.sys.advanced.job.dao.JobDao;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonPropertiesUtil;
import com.chinacreator.asp.comp.sys.core.AccessControlMessages;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.asp.comp.sys.core.role.dao.RoleDao;
import com.chinacreator.asp.comp.sys.core.role.eo.RoleEO;
import com.chinacreator.asp.comp.sys.core.security.shiro.bean.SimpleUser;
import com.chinacreator.asp.comp.sys.core.security.shiro.interfaces.IdentitifyInfomationFetcher;
import com.chinacreator.asp.comp.sys.core.user.dao.UserDao;
import com.chinacreator.asp.comp.sys.core.user.dao.UserInstanceDao;
import com.chinacreator.asp.comp.sys.core.user.eo.UserEO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserInstanceEO;
import com.chinacreator.asp.comp.sys.std.exp.changejob.ChangeJobMgt;
import com.chinacreator.c2.ioc.ApplicationContextManager;

public class JobIdentitifyInfomationFetcher implements
		IdentitifyInfomationFetcher {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private UserDao getUserDao() {
		return ApplicationContextManager.getContext().getBean(UserDao.class);
	}

	private UserInstanceDao getUserInstanceDao() {
		return ApplicationContextManager.getContext().getBean(
				UserInstanceDao.class);
	}

	private JobDao getJobDao() {
		return ApplicationContextManager.getContext().getBean(JobDao.class);
	}

	private RoleDao getRoleDao() {
		return ApplicationContextManager.getContext().getBean(RoleDao.class);
	}

	@Override
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

	@Override
	public Collection<String> getUserRoles(String userId) {
		Collection<String> roles = new HashSet<String>();
		if (null != userId && !userId.trim().equals("")) {
			Set<RoleEO> roleSet = getRoleSet(userId);
			for (RoleEO roleEO : roleSet) {
				if (null != roleEO && null != roleEO.getRoleName()
						&& !roleEO.getRoleName().trim().equals("")) {
					roles.add(roleEO.getRoleName());
				}
			}
			if (!roles.contains(CommonPropertiesUtil
					.getRoleofeveryoneRoleName())) {
				roles.add(CommonPropertiesUtil.getRoleofeveryoneRoleName());
			}
		}
		return roles;
	}

	@Override
	public Collection<String> getPermExprs(String userId) {
		Collection<String> permExprs = new HashSet<String>();
		if (null != userId && !userId.trim().equals("")) {
			Set<String> roleIdSet = new HashSet<String>();
			Set<RoleEO> roleSet = getRoleSet(userId);
			for (RoleEO roleEO : roleSet) {
				if (null != roleEO && null != roleEO.getRoleId()
						&& !roleEO.getRoleId().trim().equals("")) {
					roleIdSet.add(roleEO.getRoleId());
				}
			}
			if (!roleIdSet.contains(CommonPropertiesUtil
					.getRoleofeveryoneRoleId())) {
				roleIdSet.add(CommonPropertiesUtil.getRoleofeveryoneRoleId());
			}

			if (!roleIdSet.isEmpty()) {
				for (String roleId : roleIdSet) {
					List<PrivilegeEO> privilegeDTOs = getRoleDao()
							.queryPrivileges(roleId);
					for (PrivilegeEO privilegeEO : privilegeDTOs) {
						if (null != privilegeEO
								&& null != privilegeEO.getPermExpr()
								&& !privilegeEO.getPermExpr().trim().equals("")) {
							permExprs.add(privilegeEO.getPermExpr());
						}
					}
				}
			}
		}
		return permExprs;
	}

	private Set<RoleEO> getRoleSet(String userId) {
		Set<RoleEO> roleSet = new HashSet<RoleEO>();
		// 如果是超级管理员admin
		if (CommonPropertiesUtil.getAdminUserId().equals(userId)) {
			RoleEO roleEO = getRoleDao().queryByPK(
					CommonPropertiesUtil.getAdministratorRoleId());
			roleSet.add(roleEO);
		} else {
			Set<String> currentJobIdSet = ChangeJobMgt.getCurrentJobIds();
			if (!currentJobIdSet.isEmpty()) {
				// 如果是所有岗位
				if (currentJobIdSet.contains(ChangeJobMgt.sfs_ALLJOB)) {
					List<RoleEO> roleEOList = getUserInstanceDao()
							.queryRolesByUserId(userId);
					if (null != roleEOList && !roleEOList.isEmpty()) {
						for (RoleEO roleEO : roleEOList) {
							if (null != roleEO && null != roleEO.getRoleName()
									&& !roleEO.getRoleName().trim().equals("")) {
								roleSet.add(roleEO);
							}
						}
					}
				} else {
					// 通过用户找岗位
					UserInstanceEO userInstanceEO = new UserInstanceEO();
					userInstanceEO.setUserId(userId);
					userInstanceEO.setScopeType("2");
					List<UserInstanceEO> userInstanceEOList = getUserInstanceDao()
							.queryByUserInstance(userInstanceEO);
					if (null != userInstanceEOList
							&& !userInstanceEOList.isEmpty()) {
						String jobId = null;
						for (UserInstanceEO uiEO : userInstanceEOList) {
							if (null != uiEO) {
								jobId = uiEO.getScopeId();
								if (null != jobId && !jobId.trim().equals("")
										&& currentJobIdSet.contains(jobId)) {
									if (CommonPropertiesUtil
											.getAdministratorJobId().equals(
													jobId)) {
										RoleEO roleEO = getRoleDao()
												.queryByPK(
														CommonPropertiesUtil
																.getAdministratorRoleId());
										roleSet.add(roleEO);
									} else if (CommonPropertiesUtil
											.getRoleofeveryoneJobId().equals(
													jobId)) {
										RoleEO roleEO = getRoleDao()
												.queryByPK(
														CommonPropertiesUtil
																.getRoleofeveryoneRoleId());
										roleSet.add(roleEO);
									} else {
										// 查询岗位的匿名角色名称
										RoleEO roleEO = getJobDao()
												.queryAnonymousRoles(
														jobId,
														CommonPropertiesUtil
																.getAnonymousRoleTypeId());
										if (null != roleEO) {
											roleSet.add(roleEO);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return roleSet;
	}
}
