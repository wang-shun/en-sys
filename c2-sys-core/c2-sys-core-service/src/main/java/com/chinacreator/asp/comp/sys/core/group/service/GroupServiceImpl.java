package com.chinacreator.asp.comp.sys.core.group.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.PKGenerator;
import com.chinacreator.asp.comp.sys.core.GroupMessages;
import com.chinacreator.asp.comp.sys.core.UserMessages;
import com.chinacreator.asp.comp.sys.core.common.ValidatorUtil;
import com.chinacreator.asp.comp.sys.core.group.dao.GroupDao;
import com.chinacreator.asp.comp.sys.core.group.dao.GroupRoleDao;
import com.chinacreator.asp.comp.sys.core.group.dto.GroupDTO;
import com.chinacreator.asp.comp.sys.core.group.eo.GroupEO;
import com.chinacreator.asp.comp.sys.core.group.eo.GroupRoleEO;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.asp.comp.sys.core.role.dao.RoleDao;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.role.eo.RoleEO;
import com.chinacreator.asp.comp.sys.core.user.dao.UserInstanceDao;
import com.chinacreator.asp.comp.sys.core.user.dao.UserInstanceGroupDao;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserEO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserInstanceEO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserInstanceGroupEO;

/**
 * 用户组服务接口实现
 * 
 * @author 林伏山
 * 
 */
@Service
public class GroupServiceImpl implements GroupService {

	/** 用户组数据访问接口 */
	@Autowired
	private GroupDao groupDao;

	/** 用户实例与用户组关系数据访问接口 */
	@Autowired
	private UserInstanceGroupDao userInstanceGroupDao;

	/** 用户组与角色关系数据访问接口 */
	@Autowired
	private GroupRoleDao groupRoleDao;

	/** 角色数据访问接口 */
	@Autowired
	private RoleDao roleDao;

	/** 用户实例数据访问接口 */
	@Autowired
	private UserInstanceDao userInstanceDao;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void create(GroupDTO groupDTO) {
		validateGroupBeforeCreation(groupDTO);// 验证用户组数据传输对象
		groupDTO.setGroupId(PKGenerator.generate());
		GroupEO groupEO = new GroupEO();
		BeanCopierUtil.copy(groupDTO, groupEO);
		groupDao.create(groupEO);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void update(GroupDTO groupDTO) {
		validateGroupBeforeUpdate(groupDTO);// 验证用户组数据传输对象
		GroupEO groupEO = new GroupEO();
		BeanCopierUtil.copy(groupDTO, groupEO);
		groupDao.update(groupEO);

	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByPKs(String... groupIds) {
		String[] gIds = ValidatorUtil.validateGroupId(groupIds);// 验证用户组id
		groupRoleDao.deleteByGroupIds(gIds);
		userInstanceGroupDao.deleteByGroupIds(gIds);
		groupDao.deleteByPKs(gIds);
	}

	public List<GroupDTO> queryAll() {

		List<GroupEO> groupEOs = groupDao.queryAll();
		List<GroupDTO> groupDTOs = new ArrayList<GroupDTO>();
		BeanCopierUtil.copy(groupEOs, groupDTOs, GroupEO.class, GroupDTO.class);
		return groupDTOs;
	}

	public List<GroupDTO> queryByGroup(GroupDTO groupDTO) {
		GroupEO groupEO = new GroupEO();
		BeanCopierUtil.copy(groupDTO, groupEO);
		List<GroupEO> groupEOs = groupDao.queryByGroup(groupEO);
		List<GroupDTO> groupDTOs = new ArrayList<GroupDTO>();
		BeanCopierUtil.copy(groupEOs, groupDTOs, GroupEO.class, GroupDTO.class);
		return groupDTOs;
	}

	public GroupDTO queryByPK(String groupId) {
		GroupEO groupEO = groupDao.queryByPK(groupId);
		if (groupEO == null) {
			return null;
		}
		GroupDTO groupDTO = new GroupDTO();
		BeanCopierUtil.copy(groupEO, groupDTO);
		return groupDTO;
	}

	public List<UserDTO> queryUsers(String groupId) {
		List<UserEO> userEOs = groupDao.queryUsers(groupId);
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		BeanCopierUtil.copy(userEOs, userDTOs, UserEO.class, UserDTO.class);
		return userDTOs;
	}

	public List<UserDTO> queryUsers(String groupId, int scopeType,
			String scopeId) {
		List<UserEO> userEOs = groupDao.queryUsersByScope(groupId,
				String.valueOf(scopeType), scopeId);
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		BeanCopierUtil.copy(userEOs, userDTOs, UserEO.class, UserDTO.class);
		return userDTOs;
	}

	public List<RoleDTO> queryRoles(String groupId) {
		List<RoleEO> roleEOs = groupDao.queryRoles(groupId);
		List<RoleDTO> roleDTOs = new ArrayList<RoleDTO>();
		BeanCopierUtil.copy(roleEOs, roleDTOs, RoleEO.class, RoleDTO.class);
		return roleDTOs;
	}

	public List<PrivilegeDTO> queryPrivileges(String groupId) {
		List<PrivilegeEO> privilegeEOs = groupDao.queryPrivileges(groupId);
		List<PrivilegeDTO> privilegeDTOs = new ArrayList<PrivilegeDTO>();
		BeanCopierUtil.copy(privilegeEOs, privilegeDTOs, PrivilegeEO.class,
				PrivilegeDTO.class);
		return privilegeDTOs;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addUser(String groupId, String userId, int scopeType,
			String scopeId) {
		String[] groupIds = { groupId };
		String[] userIds = { userId };
		addUsers(groupIds, userIds, scopeType, scopeId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addUsers(String[] groupIds, String[] userIds, int scopeType,
			String scopeId) {
		String[] gIds = ValidatorUtil.validateGroupId(groupIds);// 验证用户组id数组
		String[] uIds = ValidatorUtil.validateUserId(userIds);// 验证用户id数组
		// 用户活动范围验证
		ValidatorUtil.validateScope(scopeType, scopeId);
		List<UserInstanceGroupEO> userInstanceGroupList = new ArrayList<UserInstanceGroupEO>();// 用户实例和用户组关系集合
		for (int i = 0; i < gIds.length; i++) {
			/* 查询用户组下已经存在的用户，用户已存在则不添加该用户 */
			List<UserEO> existentUsers = groupDao.queryUsersByScope(gIds[i],
					String.valueOf(scopeType), scopeId);
			Set<String> existentUserIdSet = new HashSet<String>();
			for (UserEO user : existentUsers) {
				existentUserIdSet.add(user.getUserId());
			}
			for (int j = 0; j < uIds.length; j++) {
				if (existentUserIdSet.contains(uIds[j])) {// 用户已经存在则不添加
					continue;
				}
				/* 查询用户实例id */
				UserInstanceEO userInstanceEO = new UserInstanceEO();
				userInstanceEO.setIsEnabled(true);
				userInstanceEO.setScopeId(scopeId);
				userInstanceEO.setScopeType(String.valueOf(scopeType));
				userInstanceEO.setUserId(uIds[j]);
				List<UserInstanceEO> userInstanceEOs = queryUserInstances(userInstanceEO);
				/* 构造用户实例和用户组关系对象 */
				UserInstanceGroupEO userInstanceGroupEO = new UserInstanceGroupEO();
				userInstanceGroupEO.setGroupId(gIds[i]);
				userInstanceGroupEO.setUserInstanceId(userInstanceEOs.get(0)
						.getId());
				userInstanceGroupList.add(userInstanceGroupEO);
			}
		}
		/* 批量创建用户实例与用户组关系 */
		if (userInstanceGroupList.size() > 0) {
			userInstanceGroupDao.createBatch(userInstanceGroupList);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setUser(String groupId, String[] userIds, int scopeType,
			String scopeId) {

		String[] groupIds = { groupId };
		setUsers(groupIds, userIds, scopeType, scopeId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setUsers(String[] groupIds, String[] userIds, int scopeType,
			String scopeId) {
		/*
		 * 删除用户组与其他所有用户实例的关系 根据用户id查找对应用户实例id 按顺序创建用户实例与用户组关系
		 */
		String[] gIds = ValidatorUtil.validateGroupId(groupIds);// 验证用户组id数组
		String[] uIds = ValidatorUtil.validateUserId(userIds);// 验证用户id数组
		// 用户活动范围验证
		ValidatorUtil.validateScope(scopeType, scopeId);

		/* 批量删除用户组与其他所有用户实例的关系 */
		userInstanceGroupDao.deleteByGroupIds(gIds);

		List<UserInstanceGroupEO> userInstanceGroupList = new ArrayList<UserInstanceGroupEO>();// 用户实例和用户组关系集合
		for (int i = 0; i < gIds.length; i++) {
			for (int j = 0; j < uIds.length; j++) {
				/* 查询用户实例id */
				UserInstanceEO userInstanceEO = new UserInstanceEO();
				userInstanceEO.setIsEnabled(true);
				userInstanceEO.setScopeId(scopeId);
				userInstanceEO.setScopeType(String.valueOf(scopeType));
				userInstanceEO.setUserId(uIds[j]);
				List<UserInstanceEO> userInstanceEOs = queryUserInstances(userInstanceEO);
				/* 构造用户实例与用户组关系对象 */
				UserInstanceGroupEO userInstanceGroupEO = new UserInstanceGroupEO();
				userInstanceGroupEO.setGroupId(gIds[i]);
				userInstanceGroupEO.setUserInstanceId(userInstanceEOs.get(0)
						.getId());
				userInstanceGroupList.add(userInstanceGroupEO);
			}

		}

		/* 批量创建用户实例与用户组关系 */
		if (userInstanceGroupList.size() > 0) {
			userInstanceGroupDao.createBatch(userInstanceGroupList);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeAllUsers(String... groupIds) {
		String[] gIds = ValidatorUtil.validateGroupId(groupIds);// 验证用户组id数组
		userInstanceGroupDao.deleteByGroupIds(gIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeUser(String groupId, String userId, int scopeType,
			String scopeId) {
		String[] groupIds = { groupId };
		String[] userIds = { userId };
		removeUsers(groupIds, userIds, scopeType, scopeId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeUsers(String[] groupIds, String[] userIds, int scopeType,
			String scopeId) {
		/*
		 * 查找用户实例 根据用户组id和用户实例id删除用户组与用户实例关系
		 */
		String[] gIds = ValidatorUtil.validateGroupId(groupIds);// 验证用户组id数组
		String[] uIds = ValidatorUtil.validateUserId(userIds);// 验证用户id数组
		// 用户活动范围验证
		ValidatorUtil.validateScope(scopeType, scopeId);
		String[] userInstanceIds = new String[uIds.length];
		for (int i = 0; i < uIds.length; i++) {
			/* 查询用户实例id */
			UserInstanceEO userInstanceEO = new UserInstanceEO();
			userInstanceEO.setIsEnabled(true);
			userInstanceEO.setScopeId(scopeId);
			userInstanceEO.setScopeType(String.valueOf(scopeType));
			userInstanceEO.setUserId(uIds[i]);
			List<UserInstanceEO> userInstanceEOs = queryUserInstances(userInstanceEO);
			userInstanceIds[i] = userInstanceEOs.get(0).getId();
		}
		/* 根据用户组id和用户实例id删除用户组与用户实例关系 */
		for (int i = 0; i < gIds.length; i++) {
			for (int j = 0; j < userInstanceIds.length; j++) {
				userInstanceGroupDao.deleteByUserInstanceIdAndGroupId(
						userInstanceIds[j], gIds[i]);
			}
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignRole(String groupId, String roleId) {
		String[] groupIds = { groupId };
		String[] roleIds = { roleId };
		assignRoles(groupIds, roleIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignRoles(String[] groupIds, String[] roleIds) {
		String[] gIds = ValidatorUtil.validateGroupId(groupIds);// 验证用户组id数组
		String[] rIds = ValidatorUtil.validateRoleId(roleIds);// 验证角色id数组
		List<GroupRoleEO> groupRoleList = new ArrayList<GroupRoleEO>();
		for (int i = 0; i < gIds.length; i++) {
			/* 已经存在的角色则不添加 */
			List<RoleEO> existentRoles = groupDao.queryRoles(gIds[i]);
			Set<String> existentRoleIdSet = new HashSet<String>();
			for (RoleEO existentRole : existentRoles) {
				existentRoleIdSet.add(existentRole.getRoleId());
			}
			for (int j = 0; j < rIds.length; j++) {
				if (existentRoleIdSet.contains(rIds[j])) {
					continue;// 已经存在的角色则不添加
				}
				GroupRoleEO groupRoleEO = new GroupRoleEO();
				groupRoleEO.setGroupId(gIds[i]);
				groupRoleEO.setRoleId(rIds[j]);
				groupRoleList.add(groupRoleEO);
			}
		}
		if (groupRoleList.size() > 0) {
			groupRoleDao.createBatch(groupRoleList);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setRoles(String groupId, String[] roleIds) {
		String[] groupIds = { groupId };
		setRolesToGroups(groupIds, roleIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setRolesToGroups(String[] groupIds, String[] roleIds) {
		/*
		 * 删除用户组和其他所有角色的关系 新建用户组和对应角色的关系
		 */
		String[] gIds = ValidatorUtil.validateGroupId(groupIds);// 验证用户组id数组
		String[] rIds = ValidatorUtil.validateRoleId(roleIds);// 验证角色id数组
		groupRoleDao.deleteByGroupIds(groupIds);
		List<GroupRoleEO> groupRoleList = new ArrayList<GroupRoleEO>();
		for (int i = 0; i < gIds.length; i++) {
			for (int j = 0; j < rIds.length; j++) {
				GroupRoleEO groupRoleEO = new GroupRoleEO();
				groupRoleEO.setGroupId(gIds[i]);
				groupRoleEO.setRoleId(rIds[j]);
				groupRoleList.add(groupRoleEO);
			}
		}
		if (groupRoleList.size() > 0) {
			groupRoleDao.createBatch(groupRoleList);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeAllRoles(String... groupIds) {
		String[] gIds = ValidatorUtil.validateGroupId(groupIds);// 验证用户组id数组
		groupRoleDao.deleteByGroupIds(gIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeRole(String groupId, String roleId) {
		String[] groupIds = { groupId };
		String[] roleIds = { roleId };
		revokeRoles(groupIds, roleIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeRoles(String[] groupIds, String[] roleIds) {
		String[] gIds = ValidatorUtil.validateGroupId(groupIds);// 验证用户组id数组
		String[] rIds = ValidatorUtil.validateRoleId(roleIds);// 验证角色id数组
		for (int i = 0; i < gIds.length; i++) {
			for (int j = 0; j < rIds.length; j++) {
				groupRoleDao.deleteByGroupIdAndRoleId(gIds[i], rIds[j]);
			}
		}
	}

	public boolean containsUser(String groupId, String userId) {
		ValidatorUtil.validateGroupId(groupId);// 验证用户组id
		ValidatorUtil.validateUserId(userId);// 验证用户id
		return groupDao.containsUser(groupId, userId) > 0;
	}

	public boolean containsUser(String groupId, String userId, int scopeType,
			String scopeId) {
		ValidatorUtil.validateGroupId(groupId);// 验证用户组id
		ValidatorUtil.validateUserId(userId);// 验证用户id
		// 用户活动范围验证
		ValidatorUtil.validateScope(scopeType, scopeId);
		return groupDao.containsUserByScope(groupId, userId,
				String.valueOf(scopeType), scopeId) > 0;
	}

	public boolean hasRole(String groupId, String roleId) {
		ValidatorUtil.validateGroupId(groupId);// 验证用户组id
		ValidatorUtil.validateRoleId(roleId);// 验证角色id
		return roleDao.isAssignedToGroup(roleId, groupId) > 0;
	}

	/**
	 * 创建用户组前验证
	 * 
	 * @param groupDTO
	 * @exception NullPointerException
	 *                用户组数据传输对象为空或者用户组名称为空
	 */
	private void validateGroupBeforeCreation(GroupDTO groupDTO) {
		if (null != groupDTO) {
			if (groupDTO.getGroupName() == null
					|| groupDTO.getGroupName().trim().equals("")) {
				throw new NullPointerException(
						GroupMessages.getString("GROUP.GROUPNAME_IS_NULL"));
			}
		} else {
			throw new NullPointerException(
					GroupMessages.getString("GROUP.GROUPDTO_IS_NULL"));
		}
	}

	/**
	 * 更新用户组前验证
	 * 
	 * @param groupDTO
	 * @exception NullPointerException
	 *                用户组数据传输对象为空或者用户组id为空
	 */
	private void validateGroupBeforeUpdate(GroupDTO groupDTO) {
		if (null != groupDTO) {
			ValidatorUtil.validateGroupId(groupDTO.getGroupId());// 验证groupId
		} else {
			throw new NullPointerException(
					GroupMessages.getString("GROUP.GROUPDTO_IS_NULL"));
		}
	}

	/**
	 * 查询用户实例是否存在
	 * 
	 * @param userInstanceEO
	 * @return 用户实例集合
	 * @exception IllegalArgumentException
	 *                用户实例不存在
	 */
	private List<UserInstanceEO> queryUserInstances(
			UserInstanceEO userInstanceEO) {
		List<UserInstanceEO> userInstanceEOs = userInstanceDao
				.queryByUserInstance(userInstanceEO);
		/* 用户实例不存在则抛出异常 */
		if (userInstanceEOs == null || userInstanceEOs.size() == 0) {
			throw new IllegalArgumentException(
					UserMessages.getString("USER.USERINSTANCE_IS_NOT_EXISTS")
							+ ",userId=" + userInstanceEO.getUserId()
							+ ",scopeType=" + userInstanceEO.getScopeType()
							+ ",scopeId=" + userInstanceEO.getScopeId());
		}
		return userInstanceEOs;
	}
}
