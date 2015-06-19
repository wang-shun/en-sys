package com.chinacreator.asp.comp.sys.core.security.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonPropertiesUtil;
import com.chinacreator.asp.comp.sys.common.exception.SysException;
import com.chinacreator.asp.comp.sys.common.spiutil.CommonSortSpiUtil;
import com.chinacreator.asp.comp.sys.core.AccessControlMessages;
import com.chinacreator.asp.comp.sys.core.privilege.dao.PrivilegeDao;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.asp.comp.sys.core.role.dao.RoleDao;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.role.eo.RoleEO;
import com.chinacreator.asp.comp.sys.core.security.shiro.bean.SimpleUser;
import com.chinacreator.asp.comp.sys.core.security.shiro.service.AuthorizeService;
import com.chinacreator.asp.comp.sys.core.security.shiro.token.SysMgrUsernamePasswordToken;
import com.chinacreator.asp.comp.sys.core.security.spi.AfterLoginSpi;
import com.chinacreator.asp.comp.sys.core.security.spi.AfterLogoutSpi;
import com.chinacreator.asp.comp.sys.core.security.spi.BeforeLoginSpi;
import com.chinacreator.asp.comp.sys.core.security.spi.BeforeLogoutSpi;
import com.chinacreator.asp.comp.sys.core.user.dao.UserDao;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserEO;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.sysmgr.AuthenticationToken;

/**
 * 访问控制服务接口实现类
 * 
 * @author 彭盛
 * 
 */
@Service
public class AccessControlServiceImpl implements AccessControlService {

	private static final Logger logger = LoggerFactory
			.getLogger(AccessControlServiceImpl.class);

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private PrivilegeDao privilegeDao;

	@Autowired
	PasswordService encryptionService;

	@Autowired
	private UserDao userDao;

	public boolean login(String userName, String password) {
		if (null != userName && null != password && !"".equals(userName.trim())
				&& !"".equals(password.trim())) {
			AuthenticationToken token = new SysMgrUsernamePasswordToken();
			((SysMgrUsernamePasswordToken) token).setUsername(userName);
			((SysMgrUsernamePasswordToken) token).setPassword(password
					.toCharArray());
			return login(token);
		} else {
			throw new SysException(
					AccessControlMessages
							.getString("ACCESSCONTROL.USERNAME_ORPASSWORD_NULL"));
		}
	}

	@Override
	public boolean login(AuthenticationToken token) {
		// 登录前操作
		beforeLogin(token);

		// 系统管理登录操作
		sysMgrLogin(token);

		try {
			// 登录后操作
			afterLogin(token);
		} catch (Exception e) {
			logout();
			throw new SysException(e.getMessage(), e);
		}

		return true;
	}

	private void sysMgrLogin(AuthenticationToken token) {
		if (null == token) {
			throw new SysException(
					AccessControlMessages
							.getString("ACCESSCONTROL.TOKEN_IS_NULL"));
		} else if (token instanceof SysMgrUsernamePasswordToken) {
			try {
				SysMgrUsernamePasswordToken sysMgrUsernamePasswordToken = (SysMgrUsernamePasswordToken) token;
				String userName = sysMgrUsernamePasswordToken.getUsername();
				char[] passWord = sysMgrUsernamePasswordToken.getPassword();
				if (null != userName && null != passWord
						&& !"".equals(userName.trim()) && passWord.length > 0) {
					SecurityUtils.getSubject().login(
							sysMgrUsernamePasswordToken);

					if (null == sysMgrUsernamePasswordToken.getHost()
							|| sysMgrUsernamePasswordToken.getHost().trim()
									.equals("")) {
						Session session = SecurityUtils.getSubject()
								.getSession();
						if (null != session) {
							sysMgrUsernamePasswordToken.setHost(session
									.getHost());
						}
					}
				} else {
					throw new SysException(
							AccessControlMessages
									.getString("ACCESSCONTROL.USERNAME_ORPASSWORD_NULL"));
				}
			} catch (IncorrectCredentialsException e) {
				throw new SysException(
						AccessControlMessages
								.getString("ACCESSCONTROL.PASSWORD_ERROR"));
			} catch (AuthenticationException e) {
				throw new SysException(e.getMessage(), e);
			} catch (Exception e) {
				throw new SysException(e.getMessage(), e);
			}
		} else {
			throw new SysException(
					AccessControlMessages
							.getString("ACCESSCONTROL.TOKEN_IS_UNRECOGNIZED"));
		}
	}

	/**
	 * 登录前操作
	 * 
	 * @param token
	 *            身份认证信息
	 */
	private void beforeLogin(AuthenticationToken token) {
		Map<String, BeforeLoginSpi> maps = null;

		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(
					BeforeLoginSpi.class);
		} catch (Exception e) {
			logger.debug("无登录前操作！");
		}

		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(
						AccessControlMessages
								.getString("ACCESSCONTROL.SORTSPI_IS_ERROR"),
						e);
			}
			try {
				for (String key : maps.keySet()) {
					logger.debug("调用登录前操作：" + key);
					maps.get(key).beforeLogin(token);
				}
			} catch (Exception e) {
				throw new SysException(e.getMessage(), e);
			}
		} else {
			logger.debug("无登录前操作！");
		}
	}

	/**
	 * 登录后操作
	 * 
	 * @param token
	 *            身份认证信息
	 */
	private void afterLogin(AuthenticationToken token) {
		Map<String, AfterLoginSpi> maps = null;

		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(
					AfterLoginSpi.class);
		} catch (Exception e) {
			logger.debug("无登录后操作！");
		}

		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(
						AccessControlMessages
								.getString("ACCESSCONTROL.SORTSPI_IS_ERROR"),
						e);
			}
			try {
				for (String key : maps.keySet()) {
					logger.debug("调用登录后操作：" + key);
					maps.get(key).afterLogin(token);
				}
			} catch (Exception e) {
				throw new SysException(e.getMessage(), e);
			}
		} else {
			logger.debug("无登录后操作！");
		}
	}

	public String logout() {
		String url = null;
		String outUrl = null;
		try {
			// 登出前操作
			outUrl = beforeLogout();
			if (null != outUrl) {
				url = outUrl;
			}
		} catch (Exception e) {
			SecurityUtils.getSubject().logout();
			throw new SysException(e.getMessage(), e);
		}

		SecurityUtils.getSubject().logout();

		// 登出后操作
		outUrl = afterLogout();
		if (null != outUrl) {
			url = outUrl;
		}

		return url;
	}

	/**
	 * 登出前操作
	 * 
	 * @return 返回跳转url，为null时刷新页面
	 */
	private String beforeLogout() {
		Map<String, BeforeLogoutSpi> maps = null;
		String url = null;
		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(
					BeforeLogoutSpi.class);
		} catch (Exception e) {
			logger.debug("无登出前操作！");
		}

		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(
						AccessControlMessages
								.getString("ACCESSCONTROL.SORTSPI_IS_ERROR"),
						e);
			}
			try {
				String outUrl = null;
				for (String key : maps.keySet()) {
					logger.debug("调用登出前操作：" + key);
					outUrl = maps.get(key).beforeLogout();
					if (null != outUrl) {
						url = outUrl;
					}
				}
			} catch (Exception e) {
				throw new SysException(e.getMessage(), e);
			}
		} else {
			logger.debug("无登出前操作！");
		}
		return url;
	}

	/**
	 * 登出后操作
	 * 
	 * @return 返回跳转url，为null时刷新页面
	 * 
	 */
	private String afterLogout() {
		Map<String, AfterLogoutSpi> maps = null;
		String url = null;
		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(
					AfterLogoutSpi.class);
		} catch (Exception e) {
			logger.debug("无登出后操作！");
		}

		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(
						AccessControlMessages
								.getString("ACCESSCONTROL.SORTSPI_IS_ERROR"),
						e);
			}
			try {
				String outUrl = null;
				for (String key : maps.keySet()) {
					logger.debug("调用登出后操作：" + key);
					outUrl = maps.get(key).afterLogout();
					if (null != outUrl) {
						url = outUrl;
					}
				}
			} catch (Exception e) {
				throw new SysException(e.getMessage(), e);
			}
		} else {
			logger.debug("无登出后操作！");
		}
		return url;
	}

	public boolean isAuthenticated() {
		return SecurityUtils.getSubject().isAuthenticated();
	}

	public String getUserID() {
		return getSimpleUser().getUserId();
	}

	public String getUserName() {
		return getUser().getUserName();
	}

	public String getUserRealName() {
		return getUser().getUserRealname();
	}

	public UserDTO getUser() {
		UserDTO userDTO = new UserDTO();
		String userId = getUserID();
		if (null != userId && !userId.trim().equals("")) {
			UserEO userEO = userDao.queryByPK(userId);
			BeanCopierUtil.copy(userEO, userDTO);
			userDTO.setUserPassword(null);
		}
		return userDTO;
	}

	public List<RoleDTO> getRoles() {
		SimpleUser simpleUser = getSimpleUser(true);
		AuthorizationInfo authorizationInfo = simpleUser.getAuthorizationInfo();
		List<RoleDTO> roleDTOList = new ArrayList<RoleDTO>();
		if (null != authorizationInfo) {
			Collection<String> collection = authorizationInfo.getRoles();
			if (null != collection && !collection.isEmpty())
				for (String roleName : collection) {
					RoleEO roleEO = roleDao.queryByRoleName(roleName);
					if (null != roleEO && null != roleEO.getRoleId()
							&& !roleEO.getRoleId().trim().equals("")
							&& null != roleEO.getRoleName()
							&& !roleEO.getRoleName().trim().equals("")) {
						RoleDTO roleDTO = new RoleDTO();
						BeanCopierUtil.copy(roleEO, roleDTO);
						roleDTOList.add(roleDTO);
					}
				}
		}
		return roleDTOList;
	}

	public List<PrivilegeDTO> getPrivileges() {
		SimpleUser simpleUser = getSimpleUser(true);
		AuthorizationInfo authorizationInfo = simpleUser.getAuthorizationInfo();
		List<PrivilegeDTO> privilegeDTOList = new ArrayList<PrivilegeDTO>();
		if (null != authorizationInfo) {
			Collection<String> collection = authorizationInfo
					.getStringPermissions();
			if (null != collection && !collection.isEmpty()) {
				for (String permExpr : collection) {
					PrivilegeEO privilegeEO = privilegeDao
							.queryByPermExpr(permExpr);
					if (null != privilegeEO
							&& null != privilegeEO.getPrivilegeId()
							&& !privilegeEO.getPrivilegeId().trim().equals("")
							&& null != privilegeEO.getPermExpr()
							&& !privilegeEO.getPermExpr().trim().equals("")) {
						PrivilegeDTO privilegeDTO = new PrivilegeDTO();
						BeanCopierUtil.copy(privilegeEO, privilegeDTO);
						privilegeDTOList.add(privilegeDTO);
					}
				}
			}
		}
		return privilegeDTOList;
	}

	public boolean isPermitted(String permExpr) {
		if (null != permExpr && !permExpr.trim().equals("")) {
			if (isAdmin()) {
				return true;
			}
			Subject subject = SecurityUtils.getSubject();
			return subject.isPermitted(permExpr);
		} else {
			throw new NullPointerException(
					AccessControlMessages
							.getString("ACCESSCONTROL.PRIVILEGE_PERM_EXPR_CANT_BE_NULL_EMPTY_BLANK"));
		}
	}

	public boolean[] isPermitted(String... permExpr) {
		if (null != permExpr && permExpr.length > 0) {
			validatePermitStringArray(permExpr);
			if (isAdmin()) {
				boolean[] isPerm = new boolean[permExpr.length];
				Arrays.fill(isPerm, true);
				return isPerm;
			}
			Subject subject = SecurityUtils.getSubject();
			return subject.isPermitted(permExpr);
		} else {
			throw new NullPointerException(
					AccessControlMessages
							.getString("ACCESSCONTROL.PRIVILEGE_PERM_EXPR_CANT_BE_NULL_NONE"));
		}
	}

	public Map<String, Boolean> isPermittedByBatch(String... permExpr) {
		if (null != permExpr && permExpr.length > 0) {
			validatePermitStringArray(permExpr);
			Map<String, Boolean> map = new HashMap<String, Boolean>();
			for (String perm : permExpr) {
				map.put(perm, isPermitted(perm));
			}
			return map;
		} else {
			throw new NullPointerException(
					AccessControlMessages
							.getString("ACCESSCONTROL.PRIVILEGE_PERM_EXPR_CANT_BE_NULL_NONE"));
		}
	}

	private void validatePermitStringArray(String[] permExpr) {
		for (int i = 0; i < permExpr.length; i++) {
			if (null == permExpr[i] || "".equals(permExpr[i].trim())) {
				throw new NullPointerException(
						AccessControlMessages
								.getString("ACCESSCONTROL.PRIVILEGE_PERM_EXPR_ARRAY_ITEM_CANT_BE_NULL_EMPTY_BLANK"));
			}
		}
	}

	public boolean isAllPermitted(String... permExpr) {
		boolean[] isPerms = isPermitted(permExpr);
		boolean b = false;
		if (null != isPerms && isPerms.length > 0) {
			for (boolean isPerm : isPerms) {
				if (!isPerm) {
					return b;
				}
			}
			b = true;
		}
		return b;
	}

	public boolean hasRole(String roleName) {
		if (null != roleName && !roleName.trim().equals("")) {
			Subject subject = SecurityUtils.getSubject();
			return subject.hasRole(roleName);
		} else {
			throw new NullPointerException(
					AccessControlMessages
							.getString("ACCESSCONTROL.ROLENAME_CANT_BE_EMPTY"));
		}
	}

	public boolean[] hasRoles(String... roleName) {
		if (null != roleName && roleName.length > 0) {
			validateRoleStringArray(roleName);
			Subject subject = SecurityUtils.getSubject();
			return subject.hasRoles(Arrays.asList(roleName));
		} else {
			throw new NullPointerException(
					AccessControlMessages
							.getString("ACCESSCONTROL.ROLENAME_CANT_BE_NULL_OR_NOVALUE"));
		}
	}

	private void validateRoleStringArray(String[] roleName) {
		for (int i = 0; i < roleName.length; i++) {
			if (null == roleName[i] || "".equals(roleName[i].trim())) {
				throw new NullPointerException(
						AccessControlMessages
								.getString("ACCESSCONTROL.ROLENAME_ARRAY_ITEM_CANT_BE_NULL_EMPTY_BLANK"));
			}
		}
	}

	public boolean hasAllRoles(String... roleName) {
		boolean[] isRoles = hasRoles(roleName);
		boolean b = false;
		if (null != isRoles && isRoles.length > 0) {
			for (boolean isRole : isRoles) {
				if (!isRole) {
					return b;
				}
			}
			b = true;
		}
		return b;
	}

	/**
	 * 获取内置登录用户信息
	 * 
	 * @return 内置登录用户信息
	 */
	private SimpleUser getSimpleUser() {
		return getSimpleUser(false);
	}

	/**
	 * 获取内置登录用户信息
	 * 
	 * @param isHas
	 *            调用前是否进行权限验证
	 * @return 内置登录用户信息
	 */
	private SimpleUser getSimpleUser(boolean isHas) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			if (isHas) {
				subject.hasRole("");
			}
			SimpleUser simpleUser = subject.getPrincipals().oneByType(
					SimpleUser.class);
			return simpleUser;
		} else {
			throw new SysException(
					AccessControlMessages.getString("ACCESSCONTROL.NOT_LOGIN"));
		}
	}

	public boolean isAdmin() {
		return hasRole(CommonPropertiesUtil.getAdministratorRoleName());
	}

	public void cleanPermittedCache() {
		Subject subject = SecurityUtils.getSubject();
		RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils
				.getSecurityManager();
		Collection<Realm> realms = securityManager.getRealms();
		for (Realm realm : realms) {
			if (realm instanceof AuthorizeService) {
				((AuthorizeService) realm).clearCachedAuthorizationInfo(subject
						.getPrincipal());
			}
		}
	}

}
