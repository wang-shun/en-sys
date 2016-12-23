package com.chinacreator.asp.comp.sys.core.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.credential.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.CommonPropertiesUtil;
import com.chinacreator.asp.comp.sys.common.PKGenerator;
import com.chinacreator.asp.comp.sys.common.exception.SysException;
import com.chinacreator.asp.comp.sys.common.spiutil.CommonSortSpiUtil;
import com.chinacreator.asp.comp.sys.core.UserMessages;
import com.chinacreator.asp.comp.sys.core.common.UserInstanceUtil;
import com.chinacreator.asp.comp.sys.core.common.ValidatorUtil;
import com.chinacreator.asp.comp.sys.core.group.dao.GroupDao;
import com.chinacreator.asp.comp.sys.core.group.dto.GroupDTO;
import com.chinacreator.asp.comp.sys.core.group.eo.GroupEO;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.asp.comp.sys.core.role.dao.RoleDao;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.role.eo.RoleEO;
import com.chinacreator.asp.comp.sys.core.user.dao.UserDao;
import com.chinacreator.asp.comp.sys.core.user.dao.UserInstanceDao;
import com.chinacreator.asp.comp.sys.core.user.dao.UserInstanceGroupDao;
import com.chinacreator.asp.comp.sys.core.user.dao.UserInstanceRoleDao;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserEO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserInstanceEO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserInstanceGroupEO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserInstanceRoleEO;
import com.chinacreator.asp.comp.sys.core.user.spi.AfterUpdatePasswordSpi;
import com.chinacreator.asp.comp.sys.core.user.spi.AfterUpdateUserSpi;
import com.chinacreator.asp.comp.sys.core.user.spi.BeforeUpdatePasswordSpi;
import com.chinacreator.asp.comp.sys.core.user.spi.BeforeUpdateUserSpi;
import com.chinacreator.c2.ioc.ApplicationContextManager;

/**
 * 用户服务接口实现类
 * 
 * @author 彭盛
 * 
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserInstanceDao userInstanceDao;

	@Autowired
	private UserInstanceUtil userInstanceUtil;

	@Autowired
	private UserInstanceRoleDao userInstanceRoleDao;

	@Autowired
	private UserInstanceGroupDao userInstanceGroupDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private PasswordService passwordService;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void create(UserDTO userDto) {
		// 用户数据传输对象验证
		validateBeforeCreation(userDto);
		// 设置用户ID
		userDto.setUserId(PKGenerator.generate());

		UserEO userEO = new UserEO();
		// 对象转换
		BeanCopierUtil.copy(userDto, userEO);

		// 用户密码加密
		userEO.setUserPassword(passwordService.encryptPassword(userDto.getUserPassword()));

		// 新增用户
		userDao.create(userEO);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void create(UserDTO userDto, int scopeType, String scopeId) {
		// 用户活动范围验证
		ValidatorUtil.validateScope(scopeType, scopeId);

		if (null == userDto) {
			throw new NullPointerException(UserMessages.getString("USER.USERDTO_IS_NULL"));
		}

		// 用户是否存在
		boolean isExists = false;
		if (null != userDto.getUserId() && !userDto.getUserId().trim().equals("")) {
			isExists = existsByPK(userDto.getUserId());
		}

		if (!isExists) {
			// 如果用户不存在则新增用户
			create(userDto);
		} else {
			// 如果用户存在则判断用户实例是否存在
			if (userInstanceDao.existsByUserIdAndScope(userDto.getUserId(), scopeType + "", scopeId) > 0) {
				throw new IllegalArgumentException(UserMessages.getString("USER.USERINSTANCE_IS_EXISTS"));
			}
		}

		UserInstanceEO userInstanceEO = new UserInstanceEO();
		// 设置用户实例ID
		userInstanceEO.setId(PKGenerator.generate());
		userInstanceEO.setUserId(userDto.getUserId());
		userInstanceEO.setScopeId(scopeId);
		userInstanceEO.setScopeType(scopeType + "");
		userInstanceEO.setIsEnabled(true);

		// 新增用户实例
		userInstanceDao.create(userInstanceEO);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void update(UserDTO userDto) {
		try {
			// 编辑用户前操作
			beforeUpdate(userDto);

			// 系统管理编辑用户
			sysMgrUpdate(userDto);
		} catch (Exception e) {
			// 编辑用户前失败异常回调
			beforeUpdateExceptionCallback(userDto);
			throw new SysException(e.getMessage(), e);
		}

		try {
			// 编辑用户后操作
			afterUpdate(userDto);
		} catch (Exception e) {
			// 编辑用户前失败异常回调
			beforeUpdateExceptionCallback(userDto);
			// 编辑用户后失败异常回调
			afterUpdateExceptionCallback(userDto);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void beforeUpdate(UserDTO userDto) {
		Map<String, BeforeUpdateUserSpi> maps = null;
		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(BeforeUpdateUserSpi.class);
		} catch (Exception e) {
			logger.debug("无编辑用户前操作！");
		}
		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(UserMessages.getString("USER.SORTSPI_IS_ERROR"), e);
			}
			try {
				for (String key : maps.keySet()) {
					logger.debug("调用编辑用户前操作：" + key);
					maps.get(key).beforeUpdate(userDto);
				}
			} catch (Exception e) {
				throw new SysException(e.getMessage(), e);
			}
		} else {
			logger.debug("无编辑用户前操作！");
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void sysMgrUpdate(UserDTO userDto) {
		// 对象验证
		validateBeforeUpdation(userDto);

		UserEO userEO = new UserEO();
		// 对象转换
		BeanCopierUtil.copy(userDto, userEO);

		// 修改用户
		userDao.update(userEO);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void afterUpdate(UserDTO userDto) {
		Map<String, AfterUpdateUserSpi> maps = null;
		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(AfterUpdateUserSpi.class);
		} catch (Exception e) {
			logger.debug("无编辑用户后操作！");
		}
		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(UserMessages.getString("USER.SORTSPI_IS_ERROR"), e);
			}
			try {
				for (String key : maps.keySet()) {
					logger.debug("调用编辑用户后操作：" + key);
					maps.get(key).afterUpdate(userDto);
				}
			} catch (Exception e) {
				throw new SysException(e.getMessage(), e);
			}
		} else {
			logger.debug("无编辑用户后操作！");
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void beforeUpdateExceptionCallback(UserDTO userDto) {
		Map<String, BeforeUpdateUserSpi> maps = null;
		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(BeforeUpdateUserSpi.class);
		} catch (Exception e) {
			logger.debug("无编辑用户前失败异常回调！");
		}
		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(UserMessages.getString("USER.SORTSPI_IS_ERROR"), e);
			}
			for (String key : maps.keySet()) {
				try {
					logger.debug("调用编辑用户前失败异常回调：" + key);
					maps.get(key).updateExceptionCallback(userDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.debug("无编辑用户前失败异常回调！");
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void afterUpdateExceptionCallback(UserDTO userDto) {
		Map<String, AfterUpdateUserSpi> maps = null;
		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(AfterUpdateUserSpi.class);
		} catch (Exception e) {
			logger.debug("无编辑用户后失败异常回调！");
		}
		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(UserMessages.getString("USER.SORTSPI_IS_ERROR"), e);
			}
			for (String key : maps.keySet()) {
				try {
					logger.debug("调用编辑用户后失败异常回调：" + key);
					maps.get(key).updateExceptionCallback(userDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.debug("无编辑用户后失败异常回调！");
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setOrder(List<UserDTO> userDTOList) {
		if (null != userDTOList && !userDTOList.isEmpty()) {
			for (UserDTO userDTO : userDTOList) {
				if (null == userDTO) {
					throw new NullPointerException(UserMessages.getString("USER.USERDTO_IS_NULL"));
				}
				if (null == userDTO.getUserId() || userDTO.getUserId().trim().equals("")) {
					throw new NullPointerException(UserMessages.getString("USER.USERID_IS_NULL"));
				}
				if (null == userDTO.getUserSn()) {
					throw new NullPointerException(UserMessages.getString("USER.USERSN_IS_NULL"));
				}
				UserEO userEO = new UserEO();
				// 对象转换
				BeanCopierUtil.copy(userDTO, userEO);
				userDao.setOrder(userEO);
			}
		}

	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByPKs(String... userIds) {
		// 获取用户实例
		String[] userInstanceIds = userInstanceUtil.getUserInstanceIdByUserId(userIds);
		// 删除用户实例
		deleteUserInstancesByUserInstanceIds(userInstanceIds);
		// 删除用户
		userDao.deleteByPKs(userIds);
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
	public void deleteUserInstanceByScope(String userId, int scopeType, String scopeId) {
		// 获取用户实例
		String userInstanceId = userInstanceUtil.getUserInstanceIdByUserIdAndScope(userId, scopeType, scopeId);
		// 删除用户实例
		deleteUserInstancesByUserInstanceIds(userInstanceId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteUserInstancesByUserInstanceIds(String... userInstanceIds) {
		if (null != userInstanceIds && userInstanceIds.length > 0) {
			for (String uiId : userInstanceIds) {
				if (null == uiId || uiId.trim().equals("")) {
					throw new NullPointerException(UserMessages.getString("USER.USERINSTANCEID_IS_NULL"));
				}
			}

			// 删除用户实例与角色关系
			userInstanceRoleDao.deleteByUserInstanceIds(CommonPropertiesUtil.getAdminUserId(),
					CommonPropertiesUtil.getAdministratorRoleId(), userInstanceIds);
			// 删除用户实例与用户组关系
			userInstanceGroupDao.deleteByUserInstanceIds(userInstanceIds);
			// 删除用户实例
			userInstanceDao.deleteByPKs(userInstanceIds);
		}
	}

	public List<UserDTO> queryAll() {
		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		List<UserEO> userEoList = userDao.queryAll();

		BeanCopierUtil.copy(userEoList, userDtoList, UserEO.class, UserDTO.class);

		return userDtoList;
	}

	public List<UserDTO> queryByUser(UserDTO userDto) {
		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		if (null != userDto) {
			UserEO userEO = new UserEO();
			BeanCopierUtil.copy(userDto, userEO);

			List<UserEO> userEoList = userDao.queryByUser(userEO);
			BeanCopierUtil.copy(userEoList, userDtoList, UserEO.class, UserDTO.class);
		}
		return userDtoList;
	}

	public UserDTO queryByPK(String userId) {
		if (null != userId && !userId.trim().equals("")) {
			UserEO userEO = userDao.queryByPK(userId);
			UserDTO userDto = new UserDTO();
			BeanCopierUtil.copy(userEO, userDto);
			return userDto;
		}
		return null;
	}

	public UserDTO queryByUserName(String userName) {
		if (null != userName && !userName.trim().equals("")) {
			UserEO userEO = userDao.queryByUserName(userName);
			UserDTO userDto = new UserDTO();
			BeanCopierUtil.copy(userEO, userDto);
			return userDto;
		}
		return null;
	}

	public List<UserDTO> queryByUserRealName(String userRealname) {
		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		if (null != userRealname && !userRealname.trim().equals("")) {
			List<UserEO> userEoList = userDao.queryByUserRealName(userRealname);
			BeanCopierUtil.copy(userEoList, userDtoList, UserEO.class, UserDTO.class);
		}
		return userDtoList;
	}

	public List<UserDTO> queryByScope(int scopeType, String scopeId) {
		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		List<UserEO> userEoList = userDao.queryByScope(scopeType + "", scopeId);
		BeanCopierUtil.copy(userEoList, userDtoList, UserEO.class, UserDTO.class);

		return userDtoList;
	}

	public List<RoleDTO> queryRoles(String userId) {
		List<RoleDTO> roleDTOList = new ArrayList<RoleDTO>();
		if (null != userId && !userId.trim().equals("")) {
			List<RoleEO> roleEoList = userInstanceDao.queryRolesByUserId(userId);
			BeanCopierUtil.copy(roleEoList, roleDTOList, RoleEO.class, RoleDTO.class);
		}
		return roleDTOList;
	}

	public List<RoleDTO> queryRoles(String userId, int scopeType, String scopeId) {
		List<RoleDTO> roleDTOList = new ArrayList<RoleDTO>();
		if (null != userId && !userId.trim().equals("")) {
			List<RoleEO> roleEoList = userInstanceDao.queryRolesByScope(userId, scopeType + "", scopeId);
			BeanCopierUtil.copy(roleEoList, roleDTOList, RoleEO.class, RoleDTO.class);
		}
		return roleDTOList;
	}

	public List<GroupDTO> queryGroups(String userId) {
		List<GroupDTO> groupDTOList = new ArrayList<GroupDTO>();
		if (null != userId && !userId.trim().equals("")) {
			List<GroupEO> groupEOList = userInstanceDao.queryGroupsByUserId(userId);
			BeanCopierUtil.copy(groupEOList, groupDTOList, GroupEO.class, GroupDTO.class);
		}
		return groupDTOList;
	}

	public List<GroupDTO> queryGroups(String userId, int scopeType, String scopeId) {
		List<GroupDTO> groupDTOList = new ArrayList<GroupDTO>();
		if (null != userId && !userId.trim().equals("")) {
			List<GroupEO> groupEOList = userInstanceDao.queryGroupsByScope(userId, scopeType + "", scopeId);
			BeanCopierUtil.copy(groupEOList, groupDTOList, GroupEO.class, GroupDTO.class);
		}
		return groupDTOList;
	}

	public List<PrivilegeDTO> queryPrivileges(String userId) {
		List<PrivilegeDTO> privilegeDTOList = new ArrayList<PrivilegeDTO>();
		if (null != userId && !userId.trim().equals("")) {
			List<PrivilegeEO> privilegeEOList = userInstanceDao.queryPrivilegesByUserId(userId);
			BeanCopierUtil.copy(privilegeEOList, privilegeDTOList, PrivilegeEO.class, PrivilegeDTO.class);
		}
		return privilegeDTOList;
	}

	public List<PrivilegeDTO> queryPrivileges(String userId, int scopeType, String scopeId) {
		List<PrivilegeDTO> privilegeDTOList = new ArrayList<PrivilegeDTO>();
		if (null != userId && !userId.trim().equals("")) {
			List<PrivilegeEO> privilegeEOList = userInstanceDao.queryPrivilegesByScope(userId, scopeType + "", scopeId);
			BeanCopierUtil.copy(privilegeEOList, privilegeDTOList, PrivilegeEO.class, PrivilegeDTO.class);
		}
		return privilegeDTOList;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignRole(String userId, String roleId, int scopeType, String scopeId) {
		assignRoles(new String[] { userId }, new String[] { roleId }, scopeType, scopeId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignRoles(String[] userIds, String[] roleIds, int scopeType, String scopeId) {
		// 角色ID验证，并去重复与空值
		String[] rIds = ValidatorUtil.validateRoleId(roleIds);

		// 获取用户实例
		String[] userInstanceIds = userInstanceUtil.getUserInstanceIdByUserIdAndScope(userIds, scopeType, scopeId);

		List<UserInstanceRoleEO> userInstanceRoleEOList = new ArrayList<UserInstanceRoleEO>();
		if (userInstanceIds.length > 0) {
			for (String userInstanceId : userInstanceIds) {
				for (String roleId : rIds) {
					// 判断角色是否已经授予给用户实例
					if (roleDao.isAssingedToUserInstance(roleId, userInstanceId) <= 0) {
						UserInstanceRoleEO userInstanceRoleEO = new UserInstanceRoleEO();
						userInstanceRoleEO.setRoleId(roleId);
						userInstanceRoleEO.setUserInstanceId(userInstanceId);
						userInstanceRoleEOList.add(userInstanceRoleEO);
					}
				}
			}
		} else {
			throw new NullPointerException(UserMessages.getString("USER.USERINSTANCEID_IS_NULL"));

		}

		if (!userInstanceRoleEOList.isEmpty()) {
			userInstanceRoleDao.createBatch(userInstanceRoleEOList);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setRoles(String userId, String[] roleIds, int scopeType, String scopeId) {
		setRoles(new String[] { userId }, roleIds, scopeType, scopeId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setRoles(String[] userIds, String[] roleIds, int scopeType, String scopeId) {
		// 角色ID验证，并去重复与空值
		String[] rIds = ValidatorUtil.validateRoleId(roleIds);

		// 获取用户实例
		String[] userInstanceIds = userInstanceUtil.getUserInstanceIdByUserIdAndScope(userIds, scopeType, scopeId);

		if (userInstanceIds.length > 0) {
			List<UserInstanceRoleEO> userInstanceRoleEOList = new ArrayList<UserInstanceRoleEO>();
			for (String userInstanceId : userInstanceIds) {
				for (String roleId : rIds) {
					UserInstanceRoleEO userInstanceRoleEO = new UserInstanceRoleEO();
					userInstanceRoleEO.setRoleId(roleId);
					userInstanceRoleEO.setUserInstanceId(userInstanceId);
					userInstanceRoleEOList.add(userInstanceRoleEO);
				}
			}
			// 批量删除用户实例与角色关系
			userInstanceRoleDao.deleteByUserInstanceIds(CommonPropertiesUtil.getAdminUserId(),
					CommonPropertiesUtil.getAdministratorRoleId(), userInstanceIds);
			// 批量新增用户实例与角色关系
			userInstanceRoleDao.createBatch(userInstanceRoleEOList);
		} else {
			throw new NullPointerException(UserMessages.getString("USER.USERINSTANCEID_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeAllRoles(String... userIds) {
		// 获取用户实例
		String[] userInstanceIds = userInstanceUtil.getUserInstanceIdByUserId(userIds);
		if (userInstanceIds.length > 0) {
			// 删除用户实例与角色关系
			userInstanceRoleDao.deleteByUserInstanceIds(CommonPropertiesUtil.getAdminUserId(),
					CommonPropertiesUtil.getAdministratorRoleId(), userInstanceIds);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeRole(String userId, String roleId, int scopeType, String scopeId) {
		revokeRoles(new String[] { userId }, new String[] { roleId }, scopeType, scopeId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeRoles(String[] userIds, String[] roleIds, int scopeType, String scopeId) {
		// 角色ID验证，并去重复与空值
		String[] rIds = ValidatorUtil.validateRoleId(roleIds);

		// 获取用户实例
		String[] userInstanceIds = userInstanceUtil.getUserInstanceIdByUserIdAndScope(userIds, scopeType, scopeId);

		if (userInstanceIds.length > 0) {
			for (String userInstanceId : userInstanceIds) {
				for (String roleId : rIds) {
					// 删除用户实例与角色关系
					userInstanceRoleDao.deleteByUserInstanceIdAndRoleId(CommonPropertiesUtil.getAdminUserId(),
							CommonPropertiesUtil.getAdministratorRoleId(), userInstanceId, roleId);
				}
			}
		} else {
			throw new NullPointerException(UserMessages.getString("USER.USERINSTANCEID_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addToGroup(String userId, String groupId, int scopeType, String scopeId) {
		addToGroups(new String[] { userId }, new String[] { groupId }, scopeType, scopeId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addToGroups(String[] userIds, String[] groupIds, int scopeType, String scopeId) {
		// 用户组ID验证，并去重复与空值
		String[] gIds = ValidatorUtil.validateGroupId(groupIds);

		// 获取用户实例
		String[] userInstanceIds = userInstanceUtil.getUserInstanceIdByUserIdAndScope(userIds, scopeType, scopeId);

		if (userInstanceIds.length > 0) {
			List<UserInstanceGroupEO> userInstanceGroupEOList = new ArrayList<UserInstanceGroupEO>();
			for (String userInstanceId : userInstanceIds) {
				for (String groupId : gIds) {
					// 判断角色是否已经授予给用户实例
					if (userInstanceDao.belongsToGroup(userInstanceId, groupId) <= 0) {
						UserInstanceGroupEO userInstanceGroupEO = new UserInstanceGroupEO();
						userInstanceGroupEO.setUserInstanceId(userInstanceId);
						userInstanceGroupEO.setGroupId(groupId);
						userInstanceGroupEO.setSn(9999);
						userInstanceGroupEOList.add(userInstanceGroupEO);
					}
				}
			}
			if (userInstanceGroupEOList.size() > 0) {
				userInstanceGroupDao.createBatch(userInstanceGroupEOList);
			}
		} else {
			throw new NullPointerException(UserMessages.getString("USER.USERINSTANCEID_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setGroup(String userId, String groupId, int scopeType, String scopeId) {
		setGroups(new String[] { userId }, new String[] { groupId }, scopeType, scopeId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setGroups(String[] userIds, String[] groupIds, int scopeType, String scopeId) {
		// 用户组ID验证，并去重复与空值
		String[] gIds = ValidatorUtil.validateGroupId(groupIds);

		// 获取用户实例
		String[] userInstanceIds = userInstanceUtil.getUserInstanceIdByUserIdAndScope(userIds, scopeType, scopeId);

		if (userInstanceIds.length > 0) {
			List<UserInstanceGroupEO> userInstanceGroupEOList = new ArrayList<UserInstanceGroupEO>();
			for (String userInstanceId : userInstanceIds) {
				for (String groupId : gIds) {
					UserInstanceGroupEO userInstanceGroupEO = new UserInstanceGroupEO();
					userInstanceGroupEO.setUserInstanceId(userInstanceId);
					userInstanceGroupEO.setGroupId(groupId);
					userInstanceGroupEO.setSn(9999);
					userInstanceGroupEOList.add(userInstanceGroupEO);
				}
			}

			// 删除用户实例与用户组关系
			userInstanceGroupDao.deleteByUserInstanceIds(userInstanceIds);

			// 批量新增用户实例与用户组关系
			userInstanceGroupDao.createBatch(userInstanceGroupEOList);
		} else {
			throw new NullPointerException(UserMessages.getString("USER.USERINSTANCEID_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeFromAllGroups(String... userIds) {
		// 获取用户实例
		String[] userInstanceIds = userInstanceUtil.getUserInstanceIdByUserId(userIds);

		if (userInstanceIds.length > 0) {
			// 删除用户实例与用户组关系
			userInstanceGroupDao.deleteByUserInstanceIds(userInstanceIds);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeFromGroup(String userId, String groupId, int scopeType, String scopeId) {
		removeFromGroups(new String[] { userId }, new String[] { groupId }, scopeType, scopeId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeFromGroups(String[] userIds, String[] groupIds, int scopeType, String scopeId) {
		// 用户组ID验证，并去重复与空值
		String[] gIds = ValidatorUtil.validateGroupId(groupIds);

		// 获取用户实例
		String[] userInstanceIds = userInstanceUtil.getUserInstanceIdByUserIdAndScope(userIds, scopeType, scopeId);

		if (userInstanceIds.length > 0) {
			for (String userInstanceId : userInstanceIds) {
				for (String groupId : gIds) {
					userInstanceGroupDao.deleteByUserInstanceIdAndGroupId(userInstanceId, groupId);
				}
			}
		} else {
			throw new NullPointerException(UserMessages.getString("USER.USERINSTANCEID_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setEnabledByScope(String userId, int scopeType, String scopeId, boolean enabled) {
		if (null != userId && !userId.trim().equals("")) {
			ValidatorUtil.validateScope(scopeType, scopeId);

			userInstanceDao.setEnabledByScope(userId, scopeType + "", scopeId, enabled ? "1" : "0");
		} else {
			throw new NullPointerException(UserMessages.getString("USER.USERID_IS_NULL"));
		}
	}

	public boolean hasDirectRole(String userId, String roleId) {
		if (null != userId && !userId.trim().equals("") && null != roleId && !roleId.trim().equals("")) {

			return userInstanceDao.hasRole(userId, roleId) > 0;
		}
		return false;
	}

	public boolean hasDirectRole(String userId, String roleId, int scopeType, String scopeId) {
		if (null != userId && !userId.trim().equals("") && null != roleId && !roleId.trim().equals("")) {
			return userInstanceDao.hasRoleByScope(userId, roleId, scopeType + "", scopeId) > 0;
		}
		return false;
	}

	public boolean hasRole(String userId, String roleId) {
		if (null != userId && !userId.trim().equals("") && null != roleId && !roleId.trim().equals("")) {
			return userInstanceDao.hasAllRole(userId, roleId) > 0;
		}
		return false;
	}

	public boolean hasRole(String userId, String roleId, int scopeType, String scopeId) {
		if (null != userId && !userId.trim().equals("") && null != roleId && !roleId.trim().equals("")) {
			return userInstanceDao.hasAllRoleByScope(userId, roleId, scopeType + "", scopeId) > 0;
		}
		return false;
	}

	public boolean belongsToGroup(String userId, String groupId) {
		if (null != userId && !userId.trim().equals("") && null != groupId && !groupId.trim().equals("")) {
			return groupDao.containsUser(groupId, userId) > 0;
		}
		return false;
	}

	public boolean belongsToGroup(String userId, String groupId, int scopeType, String scopeId) {
		if (null != userId && !userId.trim().equals("") && null != groupId && !groupId.trim().equals("")) {
			return groupDao.containsUserByScope(groupId, userId, scopeType + "", scopeId) > 0;
		}
		return false;
	}

	public boolean hasPrivilege(String userId, String privilegeId) {
		if (null != userId && !userId.trim().equals("") && null != privilegeId && !privilegeId.trim().equals("")) {
			return userInstanceDao.hasPrivilege(userId, privilegeId) > 0;
		}
		return false;
	}

	public boolean hasPrivilege(String userId, String privilegeId, int scopeType, String scopeId) {
		if (null != userId && !userId.trim().equals("") && null != privilegeId && !privilegeId.trim().equals("")) {
			return userInstanceDao.hasPrivilegeByScope(userId, privilegeId, scopeType + "", scopeId) > 0;
		}
		return false;
	}

	public boolean isEnabledByScope(String userId, int scopeType, String scopeId) {
		if (null != userId && !userId.trim().equals("")) {
			int result = userInstanceDao.isEnabledByScope(userId, scopeType + "", scopeId) != null ? userInstanceDao
					.isEnabledByScope(userId, scopeType + "", scopeId) : 0;
			return result > 0;
		}
		return false;
	}

	public boolean existsByPK(String userId) {
		if (null != userId && !userId.trim().equals("")) {
			return userDao.existsByPK(userId) > 0;
		} else {
			throw new NullPointerException(UserMessages.getString("USER.USERID_IS_NULL"));
		}
	}

	public boolean existsByUserName(String userName) {
		if (null != userName && !userName.trim().equals("")) {
			return userDao.existsByUserName(userName) > 0;
		} else {
			throw new NullPointerException(UserMessages.getString("USER.USERNAME_IS_NULL"));
		}
	}

	/**
	 * 新增用户前用户数据传输对象验证
	 * 
	 * @param userDto
	 *            用户数据传输对象
	 */
	private void validateBeforeCreation(UserDTO userDto) {
		if (null != userDto) {
			if (null == userDto.getUserName() || userDto.getUserName().trim().equals("")) {
				throw new NullPointerException(UserMessages.getString("USER.USERNAME_IS_NULL"));
			} else {
				userDto.setUserName(userDto.getUserName().toLowerCase());
				// 判断用户是否存在
				if (userDao.existsByUserName(userDto.getUserName()) > 0) {
					throw new IllegalArgumentException(UserMessages.getString("USER.USERNAME_IS_EXISTS"));
				}
			}
			if (null == userDto.getUserPassword() || userDto.getUserPassword().trim().equals("")) {
				throw new NullPointerException(UserMessages.getString("USER.USERPASSWORD_IS_NULL"));
			}
			if (null == userDto.getUserRealname() || userDto.getUserRealname().trim().equals("")) {
				throw new NullPointerException(UserMessages.getString("USER.USERREALNAME_IS_NULL"));
			}
		} else {
			throw new NullPointerException(UserMessages.getString("USER.USERDTO_IS_NULL"));
		}
	}

	/**
	 * 修改用户前用户数据传输对象验证
	 * 
	 * @param userDto
	 *            用户数据传输对象
	 */
	private void validateBeforeUpdation(UserDTO userDto) {
		if (null != userDto) {
			if (null == userDto.getUserId() || userDto.getUserId().trim().equals("")) {
				throw new NullPointerException(UserMessages.getString("USER.USERID_IS_NULL"));
			}
		} else {
			throw new NullPointerException(UserMessages.getString("USER.USERDTO_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void updatePassword(String userId, String password) {
		try {
			// 修改用户密码前操作
			beforeUpdatePassword(userId, password);

			// 系统管理修改用户密码
			sysMgrUpdatePassword(userId, password);
		} catch (Exception e) {
			// 修改用户密码前失败异常回调
			beforeUpdatePasswordExceptionCallback(userId, password);
			throw new SysException(e.getMessage(), e);
		}

		try {
			// 修改用户密码后操作
			afterUpdatePassword(userId, password);
		} catch (Exception e) {
			// 修改用户密码前失败异常回调
			beforeUpdatePasswordExceptionCallback(userId, password);
			// 修改用户密码后失败异常回调
			afterUpdatePasswordExceptionCallback(userId, password);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void beforeUpdatePassword(String userId, String password) {
		Map<String, BeforeUpdatePasswordSpi> maps = null;
		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(BeforeUpdatePasswordSpi.class);
		} catch (Exception e) {
			logger.debug("无修改用户密码前操作！");
		}
		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(UserMessages.getString("USER.SORTSPI_IS_ERROR"), e);
			}
			try {
				for (String key : maps.keySet()) {
					logger.debug("调用修改用户密码前操作：" + key);
					maps.get(key).beforeUpdatePassword(userId, password);
				}
			} catch (Exception e) {
				throw new SysException(e.getMessage(), e);
			}
		} else {
			logger.debug("无修改用户密码前操作！");
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void sysMgrUpdatePassword(String userId, String password) {
		ValidatorUtil.validateUserId(userId);

		if (null != password && !password.trim().equals("")) {
			userDao.updatePasswordByUserId(userId, passwordService.encryptPassword(password));
		} else {
			throw new NullPointerException(UserMessages.getString("USER.USERPASSWORD_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void afterUpdatePassword(String userId, String password) {
		Map<String, AfterUpdatePasswordSpi> maps = null;
		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(AfterUpdatePasswordSpi.class);
		} catch (Exception e) {
			logger.debug("无修改用户密码后操作！");
		}
		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(UserMessages.getString("USER.SORTSPI_IS_ERROR"), e);
			}
			try {
				for (String key : maps.keySet()) {
					logger.debug("调用修改用户密码后操作：" + key);
					maps.get(key).afterUpdatePassword(userId, password);
				}
			} catch (Exception e) {
				throw new SysException(e.getMessage(), e);
			}
		} else {
			logger.debug("无修改用户密码后操作！");
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void beforeUpdatePasswordExceptionCallback(String userId, String password) {
		Map<String, BeforeUpdatePasswordSpi> maps = null;
		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(BeforeUpdatePasswordSpi.class);
		} catch (Exception e) {
			logger.debug("无修改用户密码前失败异常回调！");
		}
		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(UserMessages.getString("USER.SORTSPI_IS_ERROR"), e);
			}
			for (String key : maps.keySet()) {
				try {
					logger.debug("调用修改用户密码前失败异常回调：" + key);
					maps.get(key).updatePasswordExceptionCallback(userId, password);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.debug("无修改用户密码前失败异常回调！");
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void afterUpdatePasswordExceptionCallback(String userId, String password) {
		Map<String, AfterUpdatePasswordSpi> maps = null;
		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(AfterUpdatePasswordSpi.class);
		} catch (Exception e) {
			logger.debug("无修改用户密码后失败异常回调！");
		}
		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(UserMessages.getString("USER.SORTSPI_IS_ERROR"), e);
			}
			for (String key : maps.keySet()) {
				try {
					logger.debug("调用修改用户密码后失败异常回调：" + key);
					maps.get(key).updatePasswordExceptionCallback(userId, password);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.debug("无修改用户密码后失败异常回调！");
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void updatePassword(String userName, String oldPassword, String newPassword) {
		try {
			// 修改用户密码前操作
			beforeUpdatePassword(userName, oldPassword, newPassword);

			// 系统管理修改用户密码
			sysMgrUpdatePassword(userName, oldPassword, newPassword);
		} catch (Exception e) {
			// 修改用户密码前失败异常回调
			beforeUpdatePasswordExceptionCallback(userName, oldPassword, newPassword);
			throw new SysException(e.getMessage(), e);
		}

		try {
			// 修改用户密码后操作
			afterUpdatePassword(userName, oldPassword, newPassword);
		} catch (Exception e) {
			// 修改用户密码前失败异常回调
			beforeUpdatePasswordExceptionCallback(userName, oldPassword, newPassword);
			// 修改用户密码后失败异常回调
			afterUpdatePasswordExceptionCallback(userName, oldPassword, newPassword);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void beforeUpdatePassword(String userName, String oldPassword, String newPassword) {
		Map<String, BeforeUpdatePasswordSpi> maps = null;
		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(BeforeUpdatePasswordSpi.class);
		} catch (Exception e) {
			logger.debug("无修改用户密码前操作！");
		}
		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(UserMessages.getString("USER.SORTSPI_IS_ERROR"), e);
			}
			try {
				for (String key : maps.keySet()) {
					logger.debug("调用修改用户密码前操作：" + key);
					maps.get(key).beforeUpdatePassword(userName, oldPassword, newPassword);
				}
			} catch (Exception e) {
				throw new SysException(e.getMessage(), e);
			}
		} else {
			logger.debug("无修改用户密码前操作！");
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void sysMgrUpdatePassword(String userName, String oldPassword, String newPassword) {
		if (null == userName || userName.trim().equals("")) {
			throw new NullPointerException(UserMessages.getString("USER.USERNAME_IS_NULL"));
		}
		if (null == oldPassword || oldPassword.trim().equals("")) {
			throw new NullPointerException(UserMessages.getString("USER.USEROLDPASSWORD_IS_NULL"));
		}
		if (null == newPassword || newPassword.trim().equals("")) {
			throw new NullPointerException(UserMessages.getString("USER.USERNEWPASSWORD_IS_NULL"));
		}

		// 判断用户是否存在
		if (existsByUserName(userName)) {
			UserEO userEO = userDao.queryByUserName(userName);
			if (null != userEO) {
				// 判断原密码是否一致
				if (passwordService.passwordsMatch(oldPassword, userEO.getUserPassword())) {
					userDao.updatePasswordByUserName(userName, passwordService.encryptPassword(newPassword));
				} else {
					throw new IllegalArgumentException(UserMessages.getString("USER.USEROLDPASSWORD_IS_ERROR"));
				}
			} else {
				throw new NullPointerException(UserMessages.getString("USER.USER_IS_NOT_EXISTS"));
			}
		} else {
			throw new NullPointerException(UserMessages.getString("USER.USER_IS_NOT_EXISTS"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void afterUpdatePassword(String userName, String oldPassword, String newPassword) {
		Map<String, AfterUpdatePasswordSpi> maps = null;
		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(AfterUpdatePasswordSpi.class);
		} catch (Exception e) {
			logger.debug("无修改用户密码后操作！");
		}
		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(UserMessages.getString("USER.SORTSPI_IS_ERROR"), e);
			}
			try {
				for (String key : maps.keySet()) {
					logger.debug("调用修改用户密码后操作：" + key);
					maps.get(key).afterUpdatePassword(userName, oldPassword, newPassword);
				}
			} catch (Exception e) {
				throw new SysException(e.getMessage(), e);
			}
		} else {
			logger.debug("无修改用户密码后操作！");
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void beforeUpdatePasswordExceptionCallback(String userName, String oldPassword, String newPassword) {
		Map<String, BeforeUpdatePasswordSpi> maps = null;
		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(BeforeUpdatePasswordSpi.class);
		} catch (Exception e) {
			logger.debug("无修改用户密码前失败异常回调！");
		}
		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(UserMessages.getString("USER.SORTSPI_IS_ERROR"), e);
			}
			for (String key : maps.keySet()) {
				try {
					logger.debug("调用修改用户密码前失败异常回调：" + key);
					maps.get(key).updatePasswordExceptionCallback(userName, oldPassword, newPassword);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.debug("无修改用户密码前失败异常回调！");
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	private void afterUpdatePasswordExceptionCallback(String userName, String oldPassword, String newPassword) {
		Map<String, AfterUpdatePasswordSpi> maps = null;
		try {
			maps = ApplicationContextManager.getContext().getBeansOfType(AfterUpdatePasswordSpi.class);
		} catch (Exception e) {
			logger.debug("无修改用户密码后失败异常回调！");
		}
		if (null != maps && !maps.isEmpty()) {
			try {
				maps = CommonSortSpiUtil.sortSpi(maps);
			} catch (Exception e) {
				throw new SysException(UserMessages.getString("USER.SORTSPI_IS_ERROR"), e);
			}
			for (String key : maps.keySet()) {
				try {
					logger.debug("调用修改用户密码后失败异常回调：" + key);
					maps.get(key).updatePasswordExceptionCallback(userName, oldPassword, newPassword);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.debug("无修改用户密码后失败异常回调！");
		}
	}

	public List<RoleDTO> queryDirectRoles(String userId) {
		List<RoleDTO> roleDTOList = new ArrayList<RoleDTO>();
		if (null != userId && !userId.trim().equals("")) {
			List<RoleEO> roleEoList = userInstanceDao.queryDirectRolesByUserId(userId);
			BeanCopierUtil.copy(roleEoList, roleDTOList, RoleEO.class, RoleDTO.class);
		}
		return roleDTOList;
	}

	public List<RoleDTO> queryDirectRoles(String userId, int scopeType, String scopeId) {
		List<RoleDTO> roleDTOList = new ArrayList<RoleDTO>();
		if (null != userId && !userId.trim().equals("")) {
			List<RoleEO> roleEoList = userInstanceDao.queryDirectRolesByScope(userId, scopeType + "", scopeId);
			BeanCopierUtil.copy(roleEoList, roleDTOList, RoleEO.class, RoleDTO.class);
		}
		return roleDTOList;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeAllRoles(String[] userIds, int scopeType, String scopeId) {
		// 获取用户实例
		String[] userInstanceIds = userInstanceUtil.getUserInstanceIdByUserIdAndScope(userIds, scopeType, scopeId);

		if (userInstanceIds.length > 0) {
			// 删除用户实例与角色关系
			userInstanceRoleDao.deleteByUserInstanceIds(CommonPropertiesUtil.getAdminUserId(),
					CommonPropertiesUtil.getAdministratorRoleId(), userInstanceIds);
		} else {
			throw new NullPointerException(UserMessages.getString("USER.USERINSTANCEID_IS_NULL"));
		}
	}

	public boolean existsByUserRealname(String userRealname) {
		if (null != userRealname && !userRealname.trim().equals("")) {
			return userDao.existsByUserRealname(userRealname) > 0;
		} else {
			throw new NullPointerException(UserMessages.getString("USER.USERREALNAME_IS_NULL"));
		}
	}
}
