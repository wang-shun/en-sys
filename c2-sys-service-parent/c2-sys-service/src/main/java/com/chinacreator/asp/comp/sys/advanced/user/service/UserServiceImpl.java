package com.chinacreator.asp.comp.sys.advanced.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.advanced.JobMessages;
import com.chinacreator.asp.comp.sys.advanced.job.dao.JobDao;
import com.chinacreator.asp.comp.sys.advanced.job.eo.JobEO;
import com.chinacreator.asp.comp.sys.advanced.user.dao.UserInstanceJobDao;
import com.chinacreator.asp.comp.sys.advanced.user.eo.UserInstanceJobEO;
import com.chinacreator.asp.comp.sys.basic.OrgMessages;
import com.chinacreator.asp.comp.sys.basic.UserMessages;
import com.chinacreator.asp.comp.sys.basic.org.dao.OrgDao;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.basic.org.eo.OrgEO;
import com.chinacreator.asp.comp.sys.basic.role.service.RoleService;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.CommonPropertiesUtil;
import com.chinacreator.asp.comp.sys.common.PKGenerator;
import com.chinacreator.asp.comp.sys.common.exception.SysException;
import com.chinacreator.asp.comp.sys.common.spiutil.CommonSortSpiUtil;
import com.chinacreator.asp.comp.sys.core.common.UserInstanceUtil;
import com.chinacreator.asp.comp.sys.core.group.service.GroupService;
import com.chinacreator.asp.comp.sys.core.user.dao.UserDao;
import com.chinacreator.asp.comp.sys.core.user.dao.UserInstanceDao;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserEO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserInstanceEO;
import com.chinacreator.asp.comp.sys.core.user.spi.AfterDeleteUserSpi;
import com.chinacreator.asp.comp.sys.core.user.spi.BeforeDeleteUserSpi;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;
import com.chinacreator.c2.ioc.ApplicationContextManager;

/**
 * 用户服务接口实现类
 * 
 * @author 杨祎程
 * 
 */
@Service
public class UserServiceImpl extends com.chinacreator.asp.comp.sys.basic.user.service.UserServiceImpl implements
		UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserInstanceUtil userInstanceUtil;

	@Autowired
	private UserInstanceDao userInstanceDao;

	@Autowired
	private UserInstanceJobDao userInstanceJobDao;

	@Autowired
	private JobDao jobDao;

	@Autowired
	private GroupService groupService;

	@Autowired
	private OrgDao orgDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private com.chinacreator.asp.comp.sys.advanced.user.dao.UserDao userAdvancedDao;

	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.basic.role.service.RoleServiceImpl")
	private RoleService roleService;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByPKs(String... userIds) {
		try {
			// 删除用户前操作
			beforeDeleteByPKs(userIds);

			// 系统管理删除用户
			sysMgrDeleteByPKs(userIds);
		} catch (Exception e) {
			// 删除用户前失败异常回调
			beforeDeleteByPKsExceptionCallback(userIds);
			throw new SysException(e.getMessage(), e);
		}

		try {
			// 删除用户后操作
			afterDeleteByPKs(userIds);
		} catch (Exception e) {
			// 删除用户前失败异常回调
			beforeDeleteByPKsExceptionCallback(userIds);
			// 删除用户后失败异常回调
			afterDeleteByPKsExceptionCallback(userIds);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void beforeDeleteByPKs(String... userIds) {
		Map<String, BeforeDeleteUserSpi> maps = null;
		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(BeforeDeleteUserSpi.class);
		} catch (Exception e) {
			logger.debug("无删除用户前操作！");
		}
		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(UserMessages.getString("USER.SORTSPI_IS_ERROR"), e);
			}
			try {
				for (String key : maps.keySet()) {
					logger.debug("调用删除用户前操作：" + key);
					maps.get(key).beforeDeleteByPKs(userIds);
				}
			} catch (Exception e) {
				throw new SysException(e.getMessage(), e);
			}
		} else {
			logger.debug("无删除用户前操作！");
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void sysMgrDeleteByPKs(String... userIds) {
		// 获取用户实例
		String[] userInstanceIds = userInstanceUtil.getUserInstanceIdByUserId(userIds);
		// 删除用户实例
		deleteUserInstancesByUserInstanceIds(userInstanceIds);
		super.deleteByPKs(userIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void afterDeleteByPKs(String... userIds) {
		Map<String, AfterDeleteUserSpi> maps = null;
		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(AfterDeleteUserSpi.class);
		} catch (Exception e) {
			logger.debug("无删除用户后操作！");
		}
		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(UserMessages.getString("USER.SORTSPI_IS_ERROR"), e);
			}
			try {
				for (String key : maps.keySet()) {
					logger.debug("调用删除用户后操作：" + key);
					maps.get(key).afterDeleteByPKs(userIds);
				}
			} catch (Exception e) {
				throw new SysException(e.getMessage(), e);
			}
		} else {
			logger.debug("无删除用户后操作！");
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void beforeDeleteByPKsExceptionCallback(String... userIds) {
		Map<String, BeforeDeleteUserSpi> maps = null;
		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(BeforeDeleteUserSpi.class);
		} catch (Exception e) {
			logger.debug("无删除用户前失败异常回调！");
		}
		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(UserMessages.getString("USER.SORTSPI_IS_ERROR"), e);
			}
			for (String key : maps.keySet()) {
				try {
					logger.debug("调用删除用户前失败异常回调：" + key);
					maps.get(key).deleteByPKsExceptionCallback(userIds);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.debug("无删除用户前失败异常回调！");
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void afterDeleteByPKsExceptionCallback(String... userIds) {
		Map<String, AfterDeleteUserSpi> maps = null;
		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(AfterDeleteUserSpi.class);
		} catch (Exception e) {
			logger.debug("无删除用户后失败异常回调！");
		}
		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(UserMessages.getString("USER.SORTSPI_IS_ERROR"), e);
			}
			for (String key : maps.keySet()) {
				try {
					logger.debug("调用删除用户后失败异常回调：" + key);
					maps.get(key).deleteByPKsExceptionCallback(userIds);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.debug("无删除用户后失败异常回调！");
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteUserInstanceByScope(String userId, int scopeType, String scopeId) {
		// 获取用户实例
		String userInstanceId = userInstanceUtil.getUserInstanceIdByUserIdAndScope(userId, scopeType, scopeId);
		// 删除用户实例
		if (null != userInstanceId && !userInstanceId.trim().equals("")) {
			deleteUserInstancesByUserInstanceIds(userInstanceId);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteUserInstancesByUserInstanceIds(String... userInstanceIds) {
		if (null != userInstanceIds && userInstanceIds.length > 0) {
			// 删除用户实例的岗位扩展信息
			userInstanceJobDao.deleteByUserInstanceIds(userInstanceIds);

			super.deleteUserInstancesByUserInstanceIds(userInstanceIds);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteAllByOrg(String... orgIds) {
		Set<String> delOrgIdSet = new HashSet<String>();
		if (null == orgIds || orgIds.length == 0) {
			throw new NullPointerException(UserMessages.getString("USER.ORG_ID_ARRAY_IS_NULL"));
		} else {
			for (String orgId : orgIds) {
				if (isBlank(orgId)) {
					throw new NullPointerException(UserMessages.getString("USER.ORG_ID_ARRAY_HAS_NULL_ITEM"));
				}
				delOrgIdSet.add(orgId);
				List<OrgEO> orgEOList = orgDao.queryChildOrgs(orgId);
				for (OrgEO orgEO : orgEOList) {
					delOrgIdSet.add(orgEO.getOrgId());
				}
			}
		}

		String[] delOrgIds = delOrgIdSet.toArray(new String[delOrgIdSet.size()]);

		List<UserInstanceEO> userInstanceEOs = userInstanceDao.queryByScopeTypeScopeIds("1", delOrgIds);

		if (null != userInstanceEOs && !userInstanceEOs.isEmpty()) {
			// 获取用户ID
			List<String> userIdsList = getUserIdsList(userInstanceEOs);
			for (String orgId : delOrgIds) {
				deleteByOrg(orgId, userIdsList.toArray(new String[userIdsList.size()]));
			}
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByOrg(String orgId, String... userIds) {
		if (isBlank(orgId)) {
			throw new NullPointerException(UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(UserMessages.getString("USER.USERID_ARRAY_IS_NULL"));
		} else {
			for (String userId : userIds) {
				if (isBlank(userId)) {
					throw new NullPointerException(UserMessages.getString("USER.USERID_ARRAY_HAS_NULL_ITEM"));
				} else {
					if ("all".equals(CommonPropertiesUtil.getDelMainOrgUserMode())) {
						// 查询用户所属主机构
						OrgDTO orgDTO = queryMainOrg(userId);
						// 如果当前机构为用户所属主机构，则删除用户，反之仅删除该机构下用户
						if (null != orgDTO && orgId.equals(orgDTO.getOrgId())) {
							deleteByPKs(userId);
						} else {
							deleteUserInstanceByScope(userId, 1, orgId);

							removeAllJobs(orgId, userIds);
						}

					} else if ("only".equals(CommonPropertiesUtil.getDelMainOrgUserMode())) {
						// 查询用户所属机构
						List<OrgDTO> orgDTOs = queryOrgs(userId);
						// 如果用户所属机构仅有1个，则删除用户，反之仅删除该机构下用户
						if (orgDTOs.size() == 1) {
							deleteByPKs(userId);
						} else if (orgDTOs.size() > 1) {
							deleteUserInstanceByScope(userId, 1, orgId);

							removeAllJobs(orgId, userIds);
						}
					} else {
						throw new IllegalArgumentException(UserMessages.getString("USER.DELMAINORGUSERMODE_IS_ERROR"));
					}
				}
			}
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByUserName(String userName) {
		if (null != userName && !userName.trim().equals("")) {
			UserEO userEO = userDao.queryByUserName(userName);
			deleteByPKs(userEO.getUserId());
		} else {
			throw new NullPointerException(UserMessages.getString("USER.USERNAME_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addJobs(String userId, String... jobIds) {
		addJobs(new String[] { userId }, jobIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addJobs(String[] userIds, String[] jobIds) {
		if (null == jobIds || jobIds.length == 0) {
			throw new NullPointerException(JobMessages.getString("JOB.JOB_ID_ARRAY_IS_NULL"));
		}
		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(UserMessages.getString("USER.USER_ID_ARRAY_IS_NULL"));
		}
		for (String jobId : jobIds) {
			if (isBlank(jobId)) {
				throw new NullPointerException(JobMessages.getString("JOB.JOB_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		for (String userId : userIds) {
			if (isBlank(userId)) {
				throw new NullPointerException(UserMessages.getString("USER.USER_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		for (String jobId : jobIds) {
			String groupId = jobDao.queryGroupIdByJobId(jobId);
			for (String userId : userIds) {
				// 判断用户是否拥有该岗位
				if (!belongsJob(userId, jobId)) {
					// 获取岗位信息
					JobEO jobEO = jobDao.queryByPK(jobId);
					if (null == jobEO) {
						throw new NullPointerException(JobMessages.getString("JOB.JOB_DTO_IS_NULL"));
					}
					if ("1".equals(jobEO.getJobScope())) {
						// 获取岗位所属机构
						OrgEO orgEO = jobDao.queryOrgByJobId(jobId);
						// 判断用户是否属于该机构
						if (!belongsToOrg(userId, orgEO.getOrgId())) {
							// 添加用户到该机构下
							addToOrg(userId, orgEO.getOrgId());
						}
					}
					// 添加用户到岗位
					String userInstanceId = PKGenerator.generate();
					UserInstanceEO userInstanceEO = new UserInstanceEO();
					userInstanceEO.setId(userInstanceId);
					userInstanceEO.setIsEnabled(true);
					userInstanceEO.setScopeId(jobId);
					userInstanceEO.setScopeType("2");
					userInstanceEO.setUserId(userId);
					userInstanceDao.create(userInstanceEO);

					// 插入用户与缺省角色关系
					assignRole(userId, roleService.getRoleofeveryoneRoleId(), 2, jobId);

					UserInstanceJobEO userInstanceJobEO = new UserInstanceJobEO();
					userInstanceJobEO.setUserinstanceId(userInstanceId);
					userInstanceJobDao.create(userInstanceJobEO);

					groupService.addUser(groupId, userId, 2, jobId);
				}
			}
		}

	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setJobs(String userId, String... jobIds) {
		setJobs(new String[] { userId }, jobIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setJobs(String[] userIds, String[] jobIds) {
		if (null == jobIds || jobIds.length == 0) {
			throw new NullPointerException(JobMessages.getString("JOB.JOB_ID_ARRAY_IS_NULL"));
		}
		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(UserMessages.getString("USER.USER_ID_ARRAY_IS_NULL"));
		}
		for (String jobId : jobIds) {
			if (isBlank(jobId)) {
				throw new NullPointerException(JobMessages.getString("JOB.JOB_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		for (String userId : userIds) {
			if (isBlank(userId)) {
				throw new NullPointerException(UserMessages.getString("USER.USER_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		for (String userId : userIds) {
			// 获取用户所属岗位列表
			List<JobEO> jobEOList = jobDao.queryUserJob(new JobEO(), userId);
			List<String> userJobIdList = new ArrayList<String>();
			// 需要添加的用户
			List<String> addjobIdList = new ArrayList<String>();
			if (null != jobEOList && !jobEOList.isEmpty()) {
				for (JobEO jobEO : jobEOList) {
					userJobIdList.add(jobEO.getJobId());
				}
			}
			for (String jobId : jobIds) {
				// 判断用户是否已加入岗位
				if (userJobIdList.contains(jobId)) {
					userJobIdList.remove(jobId);
				} else {
					addjobIdList.add(jobId);
				}
			}
			// 删除用户与岗位关系
			if (!userJobIdList.isEmpty()) {
				removeJobs(new String[] { userId }, userJobIdList.toArray(new String[userJobIdList.size()]));
			}

			// 添加用户与岗位的关系
			if (!addjobIdList.isEmpty()) {
				addJobs(new String[] { userId }, addjobIdList.toArray(new String[addjobIdList.size()]));
			}
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeAllJobs(String... userIds) {
		if (null != userIds && userIds.length > 0) {
			// 查询用户的用户实例信息
			List<UserInstanceEO> userInstanceEOList = userInstanceDao.queryByScopeTypeUserIds("2", userIds);
			List<String> userInsIdList = new ArrayList<String>();
			if (null != userInstanceEOList && !userInstanceEOList.isEmpty()) {
				for (UserInstanceEO userInstanceEO : userInstanceEOList) {
					userInsIdList.add(userInstanceEO.getId());
				}
			}
			String[] userinstanceIds = userInsIdList.toArray(new String[userInsIdList.size()]);

			deleteUserInstancesByUserInstanceIds(userinstanceIds);
		} else {
			throw new NullPointerException(UserMessages.getString("USER.USERID_ARRAY_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeAllJobs(String orgId, String... userIds) {
		if (isBlank(orgId)) {
			throw new NullPointerException(OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(UserMessages.getString("USER.USERID_ARRAY_IS_NULL"));
		}
		for (String userId : userIds) {
			if (isBlank(userId)) {
				throw new NullPointerException(UserMessages.getString("USER.USERID_ARRAY_HAS_NULL_ITEMS"));
			}
		}

		JobEO jobEO = new JobEO();
		jobEO.setJobScope("1");
		List<JobEO> jobEOList = jobDao.queryOrgJob(jobEO, orgId);
		if (null != jobEOList && !jobEOList.isEmpty()) {
			List<String> jobIdList = new ArrayList<String>();
			for (JobEO jEO : jobEOList) {
				jobIdList.add(jEO.getJobId());
			}
			removeJobs(userIds, jobIdList.toArray(new String[jobIdList.size()]));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeJobs(String userId, String... jobIds) {
		removeJobs(new String[] { userId }, jobIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeJobs(String[] userIds, String[] jobIds) {
		if (null == jobIds || jobIds.length == 0) {
			throw new NullPointerException(JobMessages.getString("JOB.JOB_ID_ARRAY_IS_NULL"));
		}
		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(UserMessages.getString("USER.USER_ID_ARRAY_IS_NULL"));
		}
		for (String jobId : jobIds) {
			if (isBlank(jobId)) {
				throw new NullPointerException(JobMessages.getString("JOB.JOB_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		for (String userId : userIds) {
			if (isBlank(userId)) {
				throw new NullPointerException(UserMessages.getString("USER.USER_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		Set<String> userInstanceIds = new HashSet<String>();
		for (String jobId : jobIds) {
			String[] uiIds = userInstanceUtil.getUserInstanceIdByUserIdAndScope(userIds, 2, jobId);
			userInstanceIds.addAll(Arrays.asList(uiIds));
		}
		if (!userInstanceIds.isEmpty()) {
			deleteUserInstancesByUserInstanceIds(userInstanceIds.toArray(new String[userInstanceIds.size()]));
		}
	}

	public boolean belongsJob(String userId, String jobId) {
		if (isBlank(userId)) {
			throw new NullPointerException(UserMessages.getString("USER.USERID_IS_NULL"));
		}
		if (isBlank(jobId)) {
			throw new NullPointerException(JobMessages.getString("JOB.JOB_ID_IS_NULL"));
		}
		return jobDao.belongsToJob(userId, jobId) > 0;
	}

	public boolean belongsJob(String orgId, String userId, String jobId) {
		if (isBlank(userId)) {
			throw new NullPointerException(UserMessages.getString("USER.USERID_IS_NULL"));
		}
		if (isBlank(jobId)) {
			throw new NullPointerException(JobMessages.getString("JOB.JOB_ID_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		return jobDao.belongsToOrgJob(userId, orgId, jobId) > 0;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeFromAllOrgs(String... userIds) {
		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(UserMessages.getString("USER.USERID_ARRAY_IS_NULL"));
		}
		for (String userId : userIds) {
			if (isBlank(userId)) {
				throw new NullPointerException(UserMessages.getString("USER.USERID_IS_NULL"));
			}
			List<UserInstanceEO> userInstanceEOList = userInstanceDao.queryByScopeTypeUserIds("1", userIds);
			if (null != userInstanceEOList && !userInstanceEOList.isEmpty()) {
				for (UserInstanceEO userInstanceEO : userInstanceEOList) {
					removeAllJobs(userInstanceEO.getScopeId(), new String[] { userId });
				}
			}
		}

		super.removeFromAllOrgs(userIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeFromOrg(String userId, String orgId) {
		removeFromOrgs(new String[] { userId }, new String[] { orgId });
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeFromOrgs(String[] userIds, String[] orgIds) {
		if (null == userIds || null == orgIds || userIds.length == 0 || orgIds.length == 0) {
			throw new NullPointerException(UserMessages.getString("USER.USERID_ARRAY_OR_ORGID_ARRAY_IS_NULL"));
		}
		for (String userId : userIds) {
			if (isBlank(userId)) {
				throw new NullPointerException(UserMessages.getString("USER.USERID_ARRAY_HAS_NULL_ITEMS"));
			}
		}
		for (String orgId : orgIds) {
			if (isBlank(orgId)) {
				throw new NullPointerException(UserMessages.getString("USER.ORGID_ARRAY_HAS_NULL_ITEM"));
			}
			removeAllJobs(orgId, userIds);
		}
		super.removeFromOrgs(userIds, orgIds);
	}

	/**
	 * 判断字符串是否为空或空格或null
	 * 
	 * @param str
	 *            被判断的字符串
	 * @return 字符串是否为null或者""或者全是空格 是：true 否：false
	 */
	private boolean isBlank(String str) {
		return (null == str || "".equals(str.trim()));
	}

	private List<String> getUserIdsList(List<UserInstanceEO> userInstanceEOs) {
		List<String> arr = new ArrayList<String>();
		if (null != userInstanceEOs && !userInstanceEOs.isEmpty()) {
			for (UserInstanceEO userInstanceEO : userInstanceEOs) {
				if (!isBlank(userInstanceEO.getUserId())) {
					arr.add(userInstanceEO.getUserId());
				}
			}
		}
		return arr;
	}

	public List<UserDTO> queryByOrgJob(UserDTO userDto, String orgId, String jobId) {
		List<UserDTO> list = new ArrayList<UserDTO>();

		if (null == orgId || "".equals(orgId.trim()) || "0".equals(orgId.trim())) {
			orgId = null;
		}
		if (null == jobId || "".equals(jobId.trim())) {
			jobId = null;
		}

		if (null == orgId && null == jobId) {
			list = queryByUser(userDto);
		} else if (null != orgId && null == jobId) {
			list = queryByOrg(userDto, orgId);
		} else if (null == orgId && null != jobId) {
			list = queryByJob(userDto, jobId);
		} else {
			UserEO userEO = new UserEO();
			BeanCopierUtil.copy(userDto, userEO);
			List<UserEO> userEOList = userAdvancedDao.queryByOrgJob(userEO, orgId, jobId);
			BeanCopierUtil.copy(userEOList, list, UserEO.class, UserDTO.class);
		}

		return list;
	}

	public Page<UserDTO> queryByOrgJob(UserDTO userDto, String orgId, String jobId, Pageable pageable, Sortable sortable) {
		Page<UserDTO> userDTOPage = new Page<UserDTO>(pageable.getPageIndex(), pageable.getPageSize(), 0,
				new ArrayList<UserDTO>());

		if (null == orgId || "".equals(orgId.trim()) || "0".equals(orgId.trim())) {
			orgId = null;
		}
		if (null == jobId || "".equals(jobId.trim())) {
			jobId = null;
		}

		if (null == orgId && null == jobId) {
			userDTOPage = queryByUser(userDto, pageable, sortable);
		} else if (null != orgId && null == jobId) {
			userDTOPage = queryByOrg(userDto, orgId, pageable, sortable);
		} else if (null == orgId && null != jobId) {
			userDTOPage = queryByJob(userDto, jobId, pageable, sortable);
		} else {

			UserEO userEO = new UserEO();
			BeanCopierUtil.copy(userDto, userEO);
			Page<UserEO> userEOPage = userAdvancedDao.queryByOrgJob(userEO, orgId, jobId, pageable, sortable);

			if (null != userEOPage && userEOPage.getTotal() > 0) {
				userDTOPage = BeanCopierUtil.copyPage(userEOPage, UserEO.class, UserDTO.class);
			}
		}
		return userDTOPage;
	}

	public List<UserDTO> queryByJob(UserDTO userDto, String jobId) {
		if (isBlank(jobId)) {
			throw new NullPointerException(JobMessages.getString("JOB.JOB_ID_IS_NULL"));
		}
		List<UserDTO> list = new ArrayList<UserDTO>();

		UserEO userEO = new UserEO();
		BeanCopierUtil.copy(userDto, userEO);
		List<UserEO> eoList = userAdvancedDao.queryByJob(userEO, jobId);
		if (null != eoList && !eoList.isEmpty()) {
			BeanCopierUtil.copy(eoList, list, UserEO.class, UserDTO.class);
		}

		return list;
	}

	public Page<UserDTO> queryByJob(UserDTO userDto, String jobId, Pageable pageable, Sortable sortable) {
		if (isBlank(jobId)) {
			throw new NullPointerException(JobMessages.getString("JOB.JOB_ID_IS_NULL"));
		}
		Page<UserDTO> userDTOPage = new Page<UserDTO>(pageable.getPageIndex(), pageable.getPageSize(), 0,
				new ArrayList<UserDTO>());

		UserEO userEO = new UserEO();
		BeanCopierUtil.copy(userDto, userEO);
		Page<UserEO> userEOPage = userAdvancedDao.queryByJob(userEO, jobId, pageable, sortable);

		if (null != userEOPage && userEOPage.getTotal() > 0) {
			userDTOPage = BeanCopierUtil.copyPage(userEOPage, UserEO.class, UserDTO.class);
		}
		return userDTOPage;
	}

}
