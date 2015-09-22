package com.chinacreator.asp.comp.sys.basic.role.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.basic.RoleMessages;
import com.chinacreator.asp.comp.sys.basic.org.dao.OrgDao;
import com.chinacreator.asp.comp.sys.basic.role.dao.RoleDao;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.CommonPropertiesUtil;
import com.chinacreator.asp.comp.sys.common.PKGenerator;
import com.chinacreator.asp.comp.sys.core.common.ValidatorUtil;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.role.eo.RoleEO;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserEO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 角色服务接口实现类
 * 
 * @author 杨祎程
 * 
 */
@Service
public class RoleServiceImpl extends com.chinacreator.asp.comp.sys.core.role.service.RoleServiceImpl
		implements RoleService {
	@Autowired
	private com.chinacreator.asp.comp.sys.core.role.dao.RoleDao roleCoreDao;
	@Autowired
	private RoleDao roleBasicDao;
	@Autowired
	private OrgDao orgDao;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void createAnonymousRole(RoleDTO roleDTO) {
		if (null != roleDTO) {
			String pk = PKGenerator.generate();
			roleDTO.setRoleId(pk); // 设置角色ID
			roleDTO.setRoleName(pk); // 设置角色名称（匿名角色就设置为和roleId一样）
			/* 对象转换 */
			RoleEO roleEO = new RoleEO();
			BeanCopierUtil.copy(roleDTO, roleEO);
			roleCoreDao.create(roleEO); // 新增角色
		}
	}

	public Page<RoleDTO> queryAll(Pageable pageable, Sortable sortable) {
		Page<RoleEO> roleEOPage = roleCoreDao.queryAll(pageable, sortable);
		Page<RoleDTO> roleDTOPage = new Page<RoleDTO>(pageable.getPageIndex(), pageable.getPageSize(), 0,
				new ArrayList<RoleDTO>());
		if (null != roleEOPage && roleEOPage.getTotal() > 0) {
			roleDTOPage = BeanCopierUtil.copyPage(roleEOPage, RoleEO.class, RoleDTO.class);
		}
		return roleDTOPage;
	}

	public List<RoleDTO> queryByRoleIgnoreAnonymous(RoleDTO roleDTO) {
		if (null != roleDTO) {
			RoleEO roleEO = new RoleEO();
			BeanCopierUtil.copy(roleDTO, roleEO);
			List<RoleEO> listEO = roleBasicDao.queryByRoleIgnoreAnonymous(roleEO,
					CommonPropertiesUtil.getAnonymousRoleTypeId());
			List<RoleDTO> listDTO = new ArrayList<RoleDTO>();
			BeanCopierUtil.copy(listEO, listDTO, RoleEO.class, RoleDTO.class);
			return listDTO;
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLEDTO_IS_NULL"));
		}
	}

	public Page<RoleDTO> queryByRole(RoleDTO roleDTO, Pageable pageable, Sortable sortable) {
		if (null != roleDTO) {
			RoleEO roleEO = new RoleEO();
			BeanCopierUtil.copy(roleDTO, roleEO);

			Page<RoleEO> roleEOPage = roleCoreDao.queryByRole(roleEO, pageable, sortable);
			Page<RoleDTO> roleDTOPage = new Page<RoleDTO>(pageable.getPageIndex(), pageable.getPageSize(), 0,
					new ArrayList<RoleDTO>());
			if (null != roleEOPage && roleEOPage.getTotal() > 0) {
				roleDTOPage = BeanCopierUtil.copyPage(roleEOPage, RoleEO.class, RoleDTO.class);
			}
			return roleDTOPage;
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLEDTO_IS_NULL"));
		}
	}

	public Page<RoleDTO> queryByRoleIgnoreAnonymous(RoleDTO roleDTO, Pageable pageable, Sortable sortable) {
		if (null != roleDTO) {
			RoleEO roleEO = new RoleEO();
			BeanCopierUtil.copy(roleDTO, roleEO);

			Page<RoleEO> roleEOPage = roleBasicDao.queryByRoleIgnoreAnonymous(roleEO,
					CommonPropertiesUtil.getAnonymousRoleTypeId(), pageable, sortable);
			Page<RoleDTO> roleDTOPage = new Page<RoleDTO>(pageable.getPageIndex(), pageable.getPageSize(), 0,
					new ArrayList<RoleDTO>());
			if (null != roleEOPage && roleEOPage.getTotal() > 0) {
				roleDTOPage = BeanCopierUtil.copyPage(roleEOPage, RoleEO.class, RoleDTO.class);
			}
			return roleDTOPage;
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLEDTO_IS_NULL"));
		}
	}

	public Page<UserDTO> queryUsers(String roleId, Pageable pageable, Sortable sortable) {
		if (!isBlank(roleId)) {
			Page<UserEO> userEOPage = roleCoreDao.queryUsers(roleId, pageable, sortable);
			Page<UserDTO> userDTOPage = new Page<UserDTO>(pageable.getPageIndex(), pageable.getPageSize(), 0,
					new ArrayList<UserDTO>());

			if (null != userEOPage && userEOPage.getTotal() > 0) {
				userDTOPage = BeanCopierUtil.copyPage(userEOPage, UserEO.class, UserDTO.class);
			}
			return userDTOPage;
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLEID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public List<UserDTO> queryUsers(String roleId, String orgId) {
		if (!isBlank(roleId) && !isBlank(orgId)) {
			List<UserDTO> userDTOList = super.queryUsers(roleId, 1, orgId);
			return userDTOList;
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLEID_OR_ORGID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public Page<UserDTO> queryUsers(String roleId, String orgId, Pageable pageable, Sortable sortable) {
		if (!isBlank(roleId)) {
			/* 数据为空验证 */
			ValidatorUtil.validateRoleId(roleId);
			// 用户活动范围验证
			if (isBlank(orgId)) {
				throw new IllegalArgumentException(RoleMessages.getString("ROLE.ORGID_IS_NULL_EMPTY_BLANK"));
			}
			Page<UserEO> listUserEOPage = roleCoreDao.queryUsersByScope(roleId, "" + 1, orgId, pageable, sortable);
			/* 对象转换 */
			Page<UserDTO> listUserDTOPage = new Page<UserDTO>(pageable.getPageIndex(), pageable.getPageSize(), 0,
					new ArrayList<UserDTO>());
			if (null != listUserEOPage && listUserEOPage.getTotal() > 0) {
				listUserDTOPage = BeanCopierUtil.copyPage(listUserEOPage, UserEO.class, UserDTO.class);
			}
			return listUserDTOPage;
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLEID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public Page<PrivilegeDTO> queryPrivileges(String roleId, Pageable pageable, Sortable sortable) {
		ValidatorUtil.validateRoleId(roleId); // 数据为空验证
		Page<PrivilegeEO> privilegeEOPage = roleCoreDao.queryPrivileges(roleId, pageable, sortable);
		Page<PrivilegeDTO> privilegeDTOPage = new Page<PrivilegeDTO>(pageable.getPageIndex(), pageable.getPageSize(), 0,
				new ArrayList<PrivilegeDTO>());
		if (null != privilegeEOPage && privilegeEOPage.getTotal() > 0) {
			privilegeDTOPage = BeanCopierUtil.copyPage(privilegeEOPage, PrivilegeEO.class, PrivilegeDTO.class);
		}
		return privilegeDTOPage;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignToUser(String roleId, String userId, String orgId) {
		// 机构ID验证
		if (isBlank(orgId)) {
			throw new IllegalArgumentException(RoleMessages.getString("ROLE.ORGID_IS_NULL_EMPTY_BLANK"));
		}
		// 调用核心的实现即可
		super.assignToUser(roleId, userId, 1, orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignToUsers(String[] roleIds, String[] userIds, String orgId) {
		// 机构ID验证
		if (isBlank(orgId)) {
			throw new IllegalArgumentException(RoleMessages.getString("ROLE.ORGID_IS_NULL_EMPTY_BLANK"));
		}
		// 调用核心的实现即可
		super.assignToUsers(roleIds, userIds, 1, orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setToUser(String roleId, String[] userIds, String orgId) {
		// 机构ID验证
		if (isBlank(orgId)) {
			throw new IllegalArgumentException(RoleMessages.getString("ROLE.ORGID_IS_NULL_EMPTY_BLANK"));
		}
		// 调用核心的实现即可
		super.setToUser(roleId, userIds, 1, orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setToUsers(String[] roleIds, String[] userIds, String orgId) {
		// 机构ID验证
		if (isBlank(orgId)) {
			throw new IllegalArgumentException(RoleMessages.getString("ROLE.ORGID_IS_NULL_EMPTY_BLANK"));
		}
		// 调用核心的实现即可
		super.setToUsers(roleIds, userIds, 1, orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignToOrg(String roleId, String orgId) {
		if (!isBlank(roleId) && !isBlank(orgId)) {
			String groupId = orgDao.queryGroupIdByOrgId(orgId);
			if (null != groupId) {
				// 进行角色和用户组之间的授予
				super.assingToGroup(roleId, groupId);
			} else {
				throw new IllegalArgumentException(RoleMessages.getString("ROLE.GROUPID_OF_ORG_NOT_EXIST"));
			}
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLEID_OR_ORGID_CANT_BE_NULL_EMPTY_BLANK"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignToOrgs(String[] roleIds, String[] orgIds) {
		if (null != orgIds) {
			if (orgIds.length > 0) {
				List<String> groupIdList = new ArrayList<String>();
				for (String orgId : orgIds) {
					if (isBlank(orgId)) {
						throw new NullPointerException(
								RoleMessages.getString("ROLE.ORGID_ARRAY_CONTAINS_NULL_EMPTY_BLANK_ITEM"));
					} else {
						String groupId = orgDao.queryGroupIdByOrgId(orgId);
						groupIdList.add(groupId);
					}
				}
				if (groupIdList.size() > 0) {
					String[] groupIds = groupIdList.toArray(new String[groupIdList.size()]);
					super.assingToGroups(roleIds, groupIds);
				} else {
					throw new IllegalArgumentException(
							RoleMessages.getString("ROLE.ORGID_ARRAY_DIDNT_MATCH_WITH_GROUPID"));
				}
			} else {
				throw new NullPointerException(RoleMessages.getString("ROLE.ORGID_ARRAY_CONTAINS_NO_ITEM"));
			}
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ORGID_ARRAY_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setToOrgs(String roleId, String[] orgIds) {
		if (null != orgIds) {
			if (orgIds.length > 0) {
				List<String> groupIdList = new ArrayList<String>();
				for (String orgId : orgIds) {
					if (isBlank(orgId)) {
						throw new NullPointerException(
								RoleMessages.getString("ROLE.ORGID_ARRAY_CONTAINS_NULL_EMPTY_BLANK_ITEM"));
					} else {
						String groupId = orgDao.queryGroupIdByOrgId(orgId);
						groupIdList.add(groupId);
					}
				}
				if (groupIdList.size() > 0) {
					String[] groupIds = groupIdList.toArray(new String[groupIdList.size()]);
					super.setToGroups(roleId, groupIds);
				} else {
					throw new IllegalArgumentException(
							RoleMessages.getString("ROLE.ORGID_ARRAY_DIDNT_MATCH_WITH_GROUPID"));
				}
			} else {
				throw new NullPointerException(RoleMessages.getString("ROLE.ORGID_ARRAY_CONTAINS_NO_ITEM"));
			}
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ORGID_ARRAY_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setRolesToOrgs(String[] roleIds, String[] orgIds) {
		if (null != orgIds) {
			if (orgIds.length > 0) {
				List<String> groupIdList = new ArrayList<String>();
				for (String orgId : orgIds) {
					if (isBlank(orgId)) {
						throw new NullPointerException(
								RoleMessages.getString("ROLE.ORGID_ARRAY_CONTAINS_NULL_EMPTY_BLANK_ITEM"));
					} else {
						String groupId = orgDao.queryGroupIdByOrgId(orgId);
						groupIdList.add(groupId);
					}
				}
				if (groupIdList.size() > 0) {
					String[] groupIds = groupIdList.toArray(new String[groupIdList.size()]);
					super.setRolesToGroups(roleIds, groupIds);
				} else {
					throw new IllegalArgumentException(
							RoleMessages.getString("ROLE.ORGID_ARRAY_DIDNT_MATCH_WITH_GROUPID"));
				}
			} else {
				throw new NullPointerException(RoleMessages.getString("ROLE.ORGID_ARRAY_CONTAINS_NO_ITEM"));
			}
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ORGID_ARRAY_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeFromAllOrgs(String... roleIds) {
		String[] rIds = ValidatorUtil.validateRoleId(roleIds); // 数据为空验证
		roleBasicDao.deleteOrgRoleRelationByRoleIds(rIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeFromOrg(String roleId, String orgId) {
		if (!isBlank(roleId) && !isBlank(orgId)) {
			String groupId = orgDao.queryGroupIdByOrgId(orgId);
			if (null != groupId) {
				// 回收角色和用户组之间的授予
				super.revokeFromGroup(roleId, groupId);
			} else {
				throw new IllegalArgumentException(RoleMessages.getString("ROLE.GROUPID_OF_ORG_NOT_EXIST"));
			}
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLEID_OR_ORGID_CANT_BE_NULL_EMPTY_BLANK"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeFromOrgs(String[] roleIds, String[] orgIds) {
		if (null != orgIds) {
			if (orgIds.length > 0) {
				List<String> groupIdList = new ArrayList<String>();
				for (String orgId : orgIds) {
					if (isBlank(orgId)) {
						throw new NullPointerException(
								RoleMessages.getString("ROLE.ORGID_ARRAY_CONTAINS_NULL_EMPTY_BLANK_ITEM"));
					} else {
						String groupId = orgDao.queryGroupIdByOrgId(orgId);
						groupIdList.add(groupId);
					}
				}
				if (groupIdList.size() > 0) {
					String[] groupIds = groupIdList.toArray(new String[groupIdList.size()]);
					super.revokeFromGroups(roleIds, groupIds);
				} else {
					throw new IllegalArgumentException(
							RoleMessages.getString("ROLE.ORGID_ARRAY_DIDNT_MATCH_WITH_GROUPID"));
				}
			} else {
				throw new NullPointerException(RoleMessages.getString("ROLE.ORGID_ARRAY_CONTAINS_NO_ITEM"));
			}
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ORGID_ARRAY_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeFromUser(String roleId, String userId, String orgId) {
		// 机构ID验证
		if (isBlank(orgId)) {
			throw new IllegalArgumentException(RoleMessages.getString("ROLE.ORGID_IS_NULL_EMPTY_BLANK"));
		}
		super.revokeFromUser(roleId, userId, 1, orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeFromUsers(String[] roleIds, String[] userIds, String orgId) {
		// 机构ID验证
		if (isBlank(orgId)) {
			throw new IllegalArgumentException(RoleMessages.getString("ROLE.ORGID_IS_NULL_EMPTY_BLANK"));
		}
		super.revokeFromUsers(roleIds, userIds, 1, orgId);
	}

	public boolean isAssignedToUser(String roleId, String userId, String orgId) {
		// 机构ID验证
		if (isBlank(orgId)) {
			throw new IllegalArgumentException(RoleMessages.getString("ROLE.ORGID_IS_NULL_EMPTY_BLANK"));
		}
		return super.isAssingedToUser(roleId, userId, 1, orgId);
	}

	public boolean isAssignedToOrg(String roleId, String orgId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(RoleMessages.getString("ROLE.ORGID_IS_NULL_EMPTY_BLANK"));
		}
		String groupId = orgDao.queryGroupIdByOrgId(orgId);
		return super.isAssignedToGroup(roleId, groupId);
	}

	public boolean isMgtPermitted(String roleId, String userId) {
		// TODO 数据管理权限，后期做
		return false;
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

	public String getAdministratorRoleId() {
		return CommonPropertiesUtil.getAdministratorRoleId();
	}

	public String getAdministratorRoleName() {
		return CommonPropertiesUtil.getAdministratorRoleName();
	}

	public String getRoleofeveryoneRoleId() {
		return CommonPropertiesUtil.getRoleofeveryoneRoleId();
	}

	public String getRoleofeveryoneRoleName() {
		return CommonPropertiesUtil.getRoleofeveryoneRoleName();
	}

	public String getOrgManagerRoleId() {
		return CommonPropertiesUtil.getOrgManagerRoleId();
	}

	public String getOrgManagerRoleName() {
		return CommonPropertiesUtil.getOrgManagerRoleName();
	}
}
