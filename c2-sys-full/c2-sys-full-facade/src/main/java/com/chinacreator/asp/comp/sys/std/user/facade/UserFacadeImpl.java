package com.chinacreator.asp.comp.sys.std.user.facade;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.UserMessages;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.exception.SysException;
import com.chinacreator.asp.comp.sys.common.spiutil.CommonSortSpiUtil;
import com.chinacreator.asp.comp.sys.core.AccessControlMessages;
import com.chinacreator.asp.comp.sys.std.user.spi.AfterSetMainOrgSpi;
import com.chinacreator.asp.comp.sys.std.user.spi.BeforeSetMainOrgSpi;
import com.chinacreator.c2.ioc.ApplicationContextManager;

@Component
public class UserFacadeImpl implements UserFacade {

	private static final Logger logger = LoggerFactory
			.getLogger(UserFacadeImpl.class);

	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.advanced.user.service.UserServiceImpl")
	private UserService userService;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setMainOrg(String[] userIds, String orgId, boolean isRetain) {
		// 用户设置主机构前操作
		beforeSetMainOrg(userIds, orgId, isRetain);

		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ID_IS_NULL"));
		}
		if (null == orgId || orgId.trim().equals("")) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}

		for (String userId : userIds) {
			if (null == userId || userId.trim().equals("")) {
				throw new NullPointerException(
						UserMessages.getString("USER.USER_ID_IS_NULL"));
			}
			// 判断是否用户主机构
			if (!userService.isMainOrg(userId, orgId)) {
				// 判断用户是否在该机构下
				if (!userService.belongsToOrg(userId, orgId)) {
					userService.addToOrg(userId, orgId);
				}
				// 设置用户主机构
				userService.setMainOrg(userId, orgId);
			}

			// 如果用户不保留在原机构下
			if (!isRetain) {
				// 用户所在机构
				List<OrgDTO> userOrgList = userService.queryOrgs(userId);
				Set<String> removeOrgSet = new HashSet<String>();
				for (OrgDTO orgDTO : userOrgList) {
					if (!orgId.equals(orgDTO.getOrgId())) {
						removeOrgSet.add(orgDTO.getOrgId());
					}
				}

				// 回收用户的机构
				if (!removeOrgSet.isEmpty()) {
					userService
							.removeFromOrgs(new String[] { userId },
									removeOrgSet
											.toArray(new String[removeOrgSet
													.size()]));
				}
			}
		}

		// 用户设置主机构后操作
		afterSetMainOrg(userIds, orgId, isRetain);
	}

	/**
	 * 用户设置主机构前操作
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param orgId
	 *            主机构ID
	 * @param isRetain
	 *            用户是否保留在原机构下(true:是，false:否)
	 */
	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void beforeSetMainOrg(String[] userIds, String orgId,
			boolean isRetain) {
		Map<String, BeforeSetMainOrgSpi> maps = null;

		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(
					BeforeSetMainOrgSpi.class);
		} catch (Exception e) {
			logger.debug("无用户设置主机构前操作！");
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
					logger.debug("调用用户设置主机构前操作：" + key);
					maps.get(key).beforeSetMainOrg(userIds, orgId, isRetain);
				}
			} catch (Exception e) {
				throw new SysException(e.getMessage(), e);
			}
		} else {
			logger.debug("无用户设置主机构前操作！");
		}
	}

	/**
	 * 用户设置主机构后操作
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param orgId
	 *            主机构ID
	 * @param isRetain
	 *            用户是否保留在原机构下(true:是，false:否)
	 */
	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void afterSetMainOrg(String[] userIds, String orgId,
			boolean isRetain) {
		Map<String, AfterSetMainOrgSpi> maps = null;

		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(
					AfterSetMainOrgSpi.class);
		} catch (Exception e) {
			logger.debug("无用户设置主机构后操作！");
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
					logger.debug("调用用户设置主机构后操作：" + key);
					maps.get(key).afterSetMainOrg(userIds, orgId, isRetain);
				}
			} catch (Exception e) {
				throw new SysException(e.getMessage(), e);
			}
		} else {
			logger.debug("无用户设置主机构后操作！");
		}
	}
}
