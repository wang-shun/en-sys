package com.chinacreator.asp.comp.sys.core.role.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.CommonPropertiesUtil;
import com.chinacreator.asp.comp.sys.common.PKGenerator;
import com.chinacreator.asp.comp.sys.core.RoleMessages;
import com.chinacreator.asp.comp.sys.core.UserMessages;
import com.chinacreator.asp.comp.sys.core.common.UserInstanceUtil;
import com.chinacreator.asp.comp.sys.core.common.ValidatorUtil;
import com.chinacreator.asp.comp.sys.core.group.dao.GroupRoleDao;
import com.chinacreator.asp.comp.sys.core.group.dto.GroupDTO;
import com.chinacreator.asp.comp.sys.core.group.eo.GroupEO;
import com.chinacreator.asp.comp.sys.core.group.eo.GroupRoleEO;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.asp.comp.sys.core.role.dao.RoleDao;
import com.chinacreator.asp.comp.sys.core.role.dao.RolePrivilegeDao;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.role.eo.RoleEO;
import com.chinacreator.asp.comp.sys.core.role.eo.RolePrivilegeEO;
import com.chinacreator.asp.comp.sys.core.user.dao.UserInstanceDao;
import com.chinacreator.asp.comp.sys.core.user.dao.UserInstanceRoleDao;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserEO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserInstanceRoleEO;

/**
 * 角色服务接口实现类
 * 
 * @author 蒋海杰
 * 
 */
@Service
public class RoleServiceImpl implements RoleService {

	/** 角色数据访问接口 */
	@Autowired
	private RoleDao roleDao;
	/** 用户实例与角色关系数据访问接口 */
	@Autowired
	private UserInstanceRoleDao userInstanceRoleDao;
	/** 用户组与角色关系数据访问接口 */
	@Autowired
	private GroupRoleDao groupRoleDao;
	/** 用户实例数据访问接口 */
	@Autowired
	private UserInstanceDao userInstanceDao;
	/** 权限与角色关系数据访问接口 */
	@Autowired
	private RolePrivilegeDao rolePrivilegeDao;
	/** 用户实例工具类 */
	@Autowired
	private UserInstanceUtil userInstanceUtil;

	private static final String sfs_NOUPDATE_KEY = "noUpdate";
	private static final String sfs_NODELETE_KEY = "noDelete";

	private static final Set<String> sfset_INBUILT_ROLEID = new HashSet<String>() {
		private static final long serialVersionUID = 1L;

		{
			add(CommonPropertiesUtil.getAdministratorRoleId());
			add(CommonPropertiesUtil.getOrgManagerRoleId());
			add(CommonPropertiesUtil.getRoleofeveryoneRoleId());
		}
	};

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void create(RoleDTO roleDTO) {
		validataCreateRoleDTO(roleDTO); // 数据对象验证
		roleDTO.setRoleId(PKGenerator.generate()); // 设置角色ID
		/* 对象转换 */
		RoleEO roleEO = new RoleEO();
		BeanCopierUtil.copy(roleDTO, roleEO);
		roleDao.create(roleEO); // 新增角色
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void update(RoleDTO roleDTO) {
		validataUpdateRoleDTO(roleDTO); // 数据对象验证
		/* 对象转换 */
		RoleEO roleEO = new RoleEO();
		BeanCopierUtil.copy(roleDTO, roleEO);
		roleDao.update(roleEO); // 更新角色
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByPKs(String... roleIds) {
		String[] rIds = ValidatorUtil.validateRoleId(roleIds); // 数据为空验证
		validataNoUpdateOrNoDeleteRole(sfs_NODELETE_KEY, rIds);
		rolePrivilegeDao.deleteByRoles(rIds); // 删除角色与权限关系表
		groupRoleDao.deleteByRoleIds(rIds); // 删除角色与用户组关系表
		userInstanceRoleDao.deleteByRoleIds(CommonPropertiesUtil.getAdminUserId(),
				CommonPropertiesUtil.getAdministratorRoleId(), rIds); // 删除角色与用户实例关系表
		roleDao.deleteByPKs(rIds); // 删除角色表
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByRoleName(String roleName) {
		validataRoleName(roleName); // 数据为空验证
		RoleEO roleEO = roleDao.queryByRoleName(roleName); // 通过角色名获取角色对象
		if (null != roleEO) {
			String roleId = roleEO.getRoleId(); // 获取角色ID
			validataNoUpdateOrNoDeleteRole(sfs_NODELETE_KEY, roleId);
			deleteByPKs(roleId);
		}
	}

	public List<RoleDTO> queryAll() {
		List<RoleEO> listEO = roleDao.queryAll();
		/* 对象转换 */
		List<RoleDTO> listDTO = new ArrayList<RoleDTO>();
		BeanCopierUtil.copy(listEO, listDTO, RoleEO.class, RoleDTO.class);
		return listDTO;
	}

	public List<RoleDTO> queryByRole(RoleDTO roleDTO) {
		validataQueryByRole(roleDTO); // 验证查询角色对象
		/* 对象转换 */
		RoleEO roleEO = new RoleEO();
		BeanCopierUtil.copy(roleDTO, roleEO);
		List<RoleEO> listEO = roleDao.queryByRole(roleEO);
		List<RoleDTO> listDTO = new ArrayList<RoleDTO>();
		BeanCopierUtil.copy(listEO, listDTO, RoleEO.class, RoleDTO.class);
		return listDTO;
	}

	public RoleDTO queryByPK(String roleId) {
		ValidatorUtil.validateRoleId(roleId); // 数据为空验证
		RoleEO roleEO = roleDao.queryByPK(roleId);
		if (null != roleEO) {
			/* 对象转换 */
			RoleDTO roleDTO = new RoleDTO();
			BeanCopierUtil.copy(roleEO, roleDTO);
			return roleDTO;
		}
		return null;
	}

	public RoleDTO queryByRoleName(String roleName) {
		validataRoleName(roleName); // 数据为空验证
		RoleEO roleEO = roleDao.queryByRoleName(roleName);
		if (null != roleEO) {
			/* 对象转换 */
			RoleDTO roleDTO = new RoleDTO();
			BeanCopierUtil.copy(roleEO, roleDTO);
			return roleDTO;
		}
		return null;
	}

	public List<UserDTO> queryUsers(String roleId) {
		ValidatorUtil.validateRoleId(roleId); // 数据为空验证
		List<UserEO> listUserEO = roleDao.queryUsers(roleId);
		/* 对象转换 */
		List<UserDTO> ListUserDTO = new ArrayList<UserDTO>();
		BeanCopierUtil.copy(listUserEO, ListUserDTO, UserEO.class, UserDTO.class);
		return ListUserDTO;
	}

	public List<UserDTO> queryUsers(String roleId, int scopeType, String scopeId) {
		/* 数据为空验证 */
		ValidatorUtil.validateRoleId(roleId);
		// 用户活动范围验证
		ValidatorUtil.validateScope(scopeType, scopeId);
		List<UserEO> listUserEO = roleDao.queryUsersByScope(roleId, scopeType + "", scopeId);
		/* 对象转换 */
		List<UserDTO> listUserDTO = new ArrayList<UserDTO>();
		BeanCopierUtil.copy(listUserEO, listUserDTO, UserEO.class, UserDTO.class);
		return listUserDTO;
	}

	public List<GroupDTO> queryGroups(String roleId) {
		ValidatorUtil.validateRoleId(roleId); // 数据为空验证
		List<GroupEO> listGroupEO = roleDao.queryGroups(roleId);
		/* 对象转换 */
		List<GroupDTO> listGroupDTO = new ArrayList<GroupDTO>();
		BeanCopierUtil.copy(listGroupEO, listGroupDTO, GroupEO.class, GroupDTO.class);
		return listGroupDTO;
	}

	public List<PrivilegeDTO> queryPrivileges(String roleId) {
		ValidatorUtil.validateRoleId(roleId); // 数据为空验证
		List<PrivilegeEO> listPrivilegeEO = roleDao.queryPrivileges(roleId);
		/* 对象转换 */
		List<PrivilegeDTO> listPrivilegeDTO = new ArrayList<PrivilegeDTO>();
		BeanCopierUtil.copy(listPrivilegeEO, listPrivilegeDTO, PrivilegeEO.class, PrivilegeDTO.class);
		return listPrivilegeDTO;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignToUser(String roleId, String userId, int scopeType, String scopeId) {
		assignToUsers(new String[] { roleId }, new String[] { userId }, scopeType, scopeId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignToUsers(String[] roleIds, String[] userIds, int scopeType, String scopeId) {
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
	public void setToUser(String roleId, String[] userIds, int scopeType, String scopeId) {
		setToUsers(new String[] { roleId }, userIds, scopeType, scopeId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setToUsers(String[] roleIds, String[] userIds, int scopeType, String scopeId) {
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
			userInstanceRoleDao.deleteByRoleIds(CommonPropertiesUtil.getAdminUserId(),
					CommonPropertiesUtil.getAdministratorRoleId(), roleIds);
			// 批量新增用户实例与角色关系
			userInstanceRoleDao.createBatch(userInstanceRoleEOList);
		} else {
			throw new NullPointerException(UserMessages.getString("USER.USERINSTANCEID_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeFromAllUsers(String... roleIds) {
		String[] rIds = ValidatorUtil.validateRoleId(roleIds); // 数据为空验证
		userInstanceRoleDao.deleteByRoleIds(CommonPropertiesUtil.getAdminUserId(),
				CommonPropertiesUtil.getAdministratorRoleId(), rIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeFromUser(String roleId, String userId, int scopeType, String scopeId) {
		revokeFromUsers(new String[] { roleId }, new String[] { userId }, scopeType, scopeId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeFromUsers(String[] roleIds, String[] userIds, int scopeType, String scopeId) {
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
	public void assingToGroup(String roleId, String groupId) {
		assingToGroups(new String[] { roleId }, new String[] { groupId });
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assingToGroups(String[] roleIds, String[] groupIds) {
		String[] rIds = ValidatorUtil.validateRoleId(roleIds);
		String[] gIds = ValidatorUtil.validateGroupId(groupIds);
		List<GroupRoleEO> groupRoleEOList = new ArrayList<GroupRoleEO>();
		for (String roleId : rIds) {
			for (String groupId : gIds) {
				/* 判断用户组是否拥有该角色 */
				if (roleDao.isAssignedToGroup(roleId, groupId) <= 0) {
					GroupRoleEO groupRoleEO = new GroupRoleEO();
					groupRoleEO.setGroupId(groupId);
					groupRoleEO.setRoleId(roleId);
					groupRoleEOList.add(groupRoleEO);
				}
			}
		}
		if (!groupRoleEOList.isEmpty()) {
			groupRoleDao.createBatch(groupRoleEOList);
		}

	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setToGroups(String roleId, String[] groupIds) {
		setRolesToGroups(new String[] { roleId }, groupIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setRolesToGroups(String[] roleIds, String[] groupIds) {
		String[] rIds = ValidatorUtil.validateRoleId(roleIds);
		String[] gIds = ValidatorUtil.validateGroupId(groupIds);
		List<GroupRoleEO> groupRoleEOList = new ArrayList<GroupRoleEO>();
		for (String roleId : rIds) {
			for (String groupId : gIds) {
				/* 判断用户组是否拥有该角色 */
				if (roleDao.isAssignedToGroup(roleId, groupId) <= 0) {
					GroupRoleEO groupRoleEO = new GroupRoleEO();
					groupRoleEO.setGroupId(groupId);
					groupRoleEO.setRoleId(roleId);
					groupRoleEOList.add(groupRoleEO);
				}
			}
		}
		groupRoleDao.deleteByRoleIds(roleIds); // 回收角色所授予的所有用户组

		if (!groupRoleEOList.isEmpty()) {
			groupRoleDao.createBatch(groupRoleEOList);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeFromAllGroups(String... roleIds) {
		String[] rIds = ValidatorUtil.validateRoleId(roleIds); // 数据为空验证
		groupRoleDao.deleteByRoleIds(rIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeFromGroup(String roleId, String groupId) {
		revokeFromGroups(new String[] { roleId }, new String[] { groupId });
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeFromGroups(String[] roleIds, String[] groupIds) {
		String[] rIds = ValidatorUtil.validateRoleId(roleIds);
		String[] gIds = ValidatorUtil.validateGroupId(groupIds);
		for (String roleId : rIds) {
			for (String groupId : gIds) {
				groupRoleDao.deleteByGroupIdAndRoleId(groupId, roleId);
			}
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignPrivilege(String roleId, String privilegeId) {
		assignPrivileges(new String[] { roleId }, new String[] { privilegeId });
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignPrivileges(String[] roleIds, String[] privilegeIds) {
		String[] rIds = ValidatorUtil.validateRoleId(roleIds);
		String[] pIds = ValidatorUtil.validatePrivilegeId(privilegeIds);
		List<RolePrivilegeEO> rolePrivilegeEOList = new ArrayList<RolePrivilegeEO>();
		for (String roleId : rIds) {
			for (String privilegeId : pIds) {
				/* 判断角色是否拥有该权限 */
				if (roleDao.hasPrivilege(roleId, privilegeId) <= 0) {
					RolePrivilegeEO rolePrivilegeEO = new RolePrivilegeEO();
					rolePrivilegeEO.setRoleId(roleId);
					rolePrivilegeEO.setPrivilegeId(privilegeId);
					rolePrivilegeEOList.add(rolePrivilegeEO);
				}
			}
		}
		if (!rolePrivilegeEOList.isEmpty()) {
			rolePrivilegeDao.createBatch(rolePrivilegeEOList);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setPrivileges(String roleId, String[] privilegeIds) {
		setPrivilegesToRoles(new String[] { roleId }, privilegeIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setPrivilegesToRoles(String[] roleIds, String[] privilegeIds) {
		String[] rIds = ValidatorUtil.validateRoleId(roleIds);
		String[] pIds = ValidatorUtil.validatePrivilegeId(privilegeIds);
		List<RolePrivilegeEO> rolePrivilegeEOList = new ArrayList<RolePrivilegeEO>();
		for (String roleId : rIds) {
			for (String privilegeId : pIds) {
				RolePrivilegeEO rolePrivilegeEO = new RolePrivilegeEO();
				rolePrivilegeEO.setRoleId(roleId);
				rolePrivilegeEO.setPrivilegeId(privilegeId);
				rolePrivilegeEOList.add(rolePrivilegeEO);
			}
		}
		rolePrivilegeDao.deleteByRoles(rIds);
		if (!rolePrivilegeEOList.isEmpty()) {
			rolePrivilegeDao.createBatch(rolePrivilegeEOList);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeAllPrivileges(String... roleIds) {
		String[] rIds = ValidatorUtil.validateRoleId(roleIds); // 数据为空验证
		rolePrivilegeDao.deleteByRoles(rIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokePrivilege(String roleId, String privilegeId) {
		revokePrivileges(new String[] { roleId }, new String[] { privilegeId });
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokePrivileges(String[] roleIds, String[] privilegeIds) {
		String[] rIds = ValidatorUtil.validateRoleId(roleIds);
		String[] pIds = ValidatorUtil.validatePrivilegeId(privilegeIds);
		for (String roleId : rIds) {
			for (String privilegeId : pIds) {
				rolePrivilegeDao.deleteByPrivilegeIdAndRoleId(privilegeId, roleId);
			}
		}
	}

	public boolean isEnabledByPK(String roleId) {
		ValidatorUtil.validateRoleId(roleId); // 数据为空验证
		Integer result = roleDao.isEnabledByPK(roleId); // 查询不到数据时会返回null

		return (null != result && result > 0);
	}

	public boolean isEnabledByRoleName(String roleName) {
		validataRoleName(roleName); // 数据为空验证
		Integer result = roleDao.isEnabledByRoleName(roleName); // 查询不到数据时会返回null
		return (null != result && result > 0);
	}

	public boolean existsByRoleName(String roleName) {
		validataRoleName(roleName);
		return roleDao.existsByRoleName(roleName) > 0;
	}

	public boolean existsByRoleNameIgnoreRoleID(String roleName, String roleId) {
		validataRoleName(roleName);
		if (null == roleId || roleId.trim().equals("")) {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLEID_IS_NULL"));
		}

		return roleDao.existsByRoleNameIgnoreRoleID(roleName, roleId) > 0;
	}

	public boolean isAssingedToUser(String roleId, String userId) {
		ValidatorUtil.validateRoleId(roleId);
		ValidatorUtil.validateUserId(userId);

		return userInstanceDao.hasRole(userId, roleId) > 0;

	}

	public boolean isAssingedToUser(String roleId, String userId, int scopeType, String scopeId) {
		ValidatorUtil.validateRoleId(roleId);
		ValidatorUtil.validateUserId(userId);
		ValidatorUtil.validateScope(scopeType, scopeId);

		return userInstanceDao.hasRoleByScope(userId, roleId, scopeType + "", scopeId) > 0;

	}

	public boolean isAssignedToGroup(String roleId, String groupId) {
		/* 数据为空验证 */
		ValidatorUtil.validateRoleId(roleId);
		ValidatorUtil.validateGroupId(groupId);
		return roleDao.isAssignedToGroup(roleId, groupId) > 0;
	}

	public boolean hasPrivilege(String roleId, String privilegeId) {
		/* 数据为空验证 */
		ValidatorUtil.validateRoleId(roleId);
		ValidatorUtil.validatePrivilegeId(privilegeId);
		return roleDao.hasPrivilege(roleId, privilegeId) > 0;
	}

	/**
	 * 验证角色对象
	 * 
	 * @param roleDTO
	 *            角色数据传输对象
	 */
	private void validataCreateRoleDTO(RoleDTO roleDTO) {
		if (null != roleDTO) {
			if (null == roleDTO.getRoleName() || roleDTO.getRoleName().trim().equals("")) {
				throw new NullPointerException(RoleMessages.getString("ROLE.ROLENAME_IS_NULL"));
			} else {
				if (existsByRoleName(roleDTO.getRoleName())) {
					throw new IllegalArgumentException(RoleMessages.getString("ROLE.ROLENAME_IS_EXIST"));
				}
			}
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLEOBJECT_IS_NULL"));
		}
	}

	/**
	 * 验证更新角色对象
	 * 
	 * @param roleDTO
	 *            角色数据传输对象
	 */
	private void validataUpdateRoleDTO(RoleDTO roleDTO) {
		if (null != roleDTO) {
			if (null == roleDTO.getRoleId() || roleDTO.getRoleId().trim().equals("")) {
				throw new NullPointerException(RoleMessages.getString("ROLE.ROLEID_IS_NULL"));
			} else {
				validataNoUpdateOrNoDeleteRole(sfs_NOUPDATE_KEY, roleDTO.getRoleId());
			}
			if (null != roleDTO.getRoleName() && !roleDTO.getRoleName().trim().equals("")) {
				if (roleDao.existsByRoleNameIgnoreRoleID(roleDTO.getRoleName(), roleDTO.getRoleId()) > 0) {
					throw new IllegalArgumentException(RoleMessages.getString("ROLE.ROLENAME_IS_EXIST"));
				}
			}
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLEOBJECT_IS_NULL"));
		}
	}

	/**
	 * 验证角色名称是否为空
	 * 
	 * @param roleName
	 *            角色名称
	 */
	private void validataRoleName(String roleName) {
		if (null == roleName || roleName.trim().equals("")) {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLENAME_IS_NULL"));
		}
	}

	/**
	 * 验证查询角色对象
	 * 
	 * @param roleDTO
	 *            角色数据传输对象
	 */
	private void validataQueryByRole(RoleDTO roleDTO) {
		if (null == roleDTO) {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLEOBJECT_IS_NULL"));
		}
	}

	/**
	 * 验证禁止编辑删除角色
	 * 
	 * @param type
	 *            类型(update:编辑，delete:删除)
	 * @param roleIds
	 *            角色ID
	 */
	private void validataNoUpdateOrNoDeleteRole(String type, String... roleIds) {
		String[] rIds = ValidatorUtil.validateRoleId(roleIds);
		for (String rId : rIds) {
			if (sfset_INBUILT_ROLEID.contains(rId)) {
				RoleDTO roleDTO = queryByPK(rId);
				String mess = "%s角色不能被操作";
				if (sfs_NOUPDATE_KEY.equals(type)) {
					mess = RoleMessages.getString("ROLE.NOUPDATE");
				} else if (sfs_NODELETE_KEY.equals(type)) {
					mess = RoleMessages.getString("ROLE.NODELETE");
				}
				throw new IllegalArgumentException(String.format(mess, roleDTO.getRoleName()));
			}
		}
	}
}
