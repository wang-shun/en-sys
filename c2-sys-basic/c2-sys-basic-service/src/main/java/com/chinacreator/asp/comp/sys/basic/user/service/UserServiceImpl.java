package com.chinacreator.asp.comp.sys.basic.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.basic.UserMessages;
import com.chinacreator.asp.comp.sys.basic.menu.dao.MenuDao;
import com.chinacreator.asp.comp.sys.basic.menu.dto.MenuDTO;
import com.chinacreator.asp.comp.sys.basic.menu.eo.MenuAllInfoEO;
import com.chinacreator.asp.comp.sys.basic.org.dao.OrgDao;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.basic.org.eo.OrgEO;
import com.chinacreator.asp.comp.sys.basic.role.service.RoleTypeService;
import com.chinacreator.asp.comp.sys.basic.user.dao.UserInstanceOrgDao;
import com.chinacreator.asp.comp.sys.basic.user.eo.UserInstanceOrgEO;
import com.chinacreator.asp.comp.sys.basic.userpreferences.service.UserPreferencesService;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.CommonPropertiesUtil;
import com.chinacreator.asp.comp.sys.common.PKGenerator;
import com.chinacreator.asp.comp.sys.core.common.UserInstanceUtil;
import com.chinacreator.asp.comp.sys.core.group.dto.GroupDTO;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.asp.comp.sys.core.role.dao.RoleDao;
import com.chinacreator.asp.comp.sys.core.role.dao.RolePrivilegeDao;
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
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

@Service
public class UserServiceImpl extends
		com.chinacreator.asp.comp.sys.core.user.service.UserServiceImpl
		implements UserService {

	@Autowired
	private UserInstanceUtil userInstanceUtil;

	@Autowired
	private UserInstanceDao userInstanceDao;

	@Autowired
	private UserInstanceRoleDao userInstanceRoleDao;

	@Autowired
	private UserInstanceGroupDao userInstanceGroupDao;

	@Autowired
	private UserInstanceOrgDao userInstanceOrgDao;

	@Autowired
	private UserDao userCoreDao;

	@Autowired
	private com.chinacreator.asp.comp.sys.basic.user.dao.UserDao userBasicDao;

	@Autowired
	private OrgDao orgDao;

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private RoleTypeService roleTypeService;

	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.basic.role.service.RoleServiceImpl")
	private com.chinacreator.asp.comp.sys.basic.role.service.RoleService roleBasicService;

	@Autowired
	private RoleDao roleCoreDao;

	@Autowired
	private com.chinacreator.asp.comp.sys.basic.role.dao.RoleDao roleBasicDao;

	@Autowired
	private RolePrivilegeDao rolePrivilegeDao;

	@Autowired
	private PasswordService passwordService;

	@Autowired
	private UserPreferencesService userPreferencesService;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void create(UserDTO userDto, String orgId, int sn) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USEROEG_ID_IS_NULL"));
		}

		// 调用核心的创建指定用户范围的用户的接口
		super.create(userDto, 1, orgId);

		// 通过机构ID和用户ID查出用户实例ID
		UserInstanceEO userInstanceEO = userInstanceDao.queryByUserAndScope(
				userDto.getUserId(), "1", orgId);

		// 进行用户在机构下排序号等信息的插入
		if (null != userInstanceEO) {

			// 插入用户实例机构扩展信息表
			UserInstanceOrgEO userInstanceOrgEO = new UserInstanceOrgEO();
			userInstanceOrgEO.setUserInstanceId(userInstanceEO.getId());
			userInstanceOrgEO.setIsMain(true);
			userInstanceOrgEO.setSn(sn);
			userInstanceOrgDao.create(userInstanceOrgEO);

			// 插入用户与机构所在用户组的关系
			String groupId = orgDao.queryGroupIdByOrgId(orgId);
			String uId = userInstanceEO.getId();

			UserInstanceGroupEO userInstanceGroupEO = new UserInstanceGroupEO();
			userInstanceGroupEO.setGroupId(groupId);
			userInstanceGroupEO.setUserInstanceId(uId);
			userInstanceGroupDao.create(userInstanceGroupEO);

			// 插入用户与缺省角色关系
			assignRole(userDto.getUserId(),
					roleBasicService.getRoleofeveryoneRoleId(), orgId);

		} else {
			throw new IllegalArgumentException(
					UserMessages.getString("USER.USERINSTANCE_IS_NOT_EXISTS"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setOrderInOrg(List<Map<String, Object>> sortUserList) {
		if (null != sortUserList && !sortUserList.isEmpty()) {
			for (Map<String, Object> map : sortUserList) {
				if (map != null) {
					String userId = (String) map.get("userId");
					String orgId = (String) map.get("orgId");
					Integer sn = (Integer) map.get("sn");
					if (isBlank((String) map.get("userId"))
							|| isBlank((String) map.get("orgId")) || null == sn) {
						throw new NullPointerException(
								UserMessages
										.getString("USER.USER_ORDER_LIST_ITEM_ILLEGAL"));
					}
					userInstanceOrgDao.setOrderInOrg(userId, orgId, sn);
				}
			}
		} else {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ORDER_LIST_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setMainOrg(String userId, String orgId) {
		if (isBlank(userId) || isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_OR_ORGID_IS_NULL"));
		}
		// 先通过用户ID将所有用户实例的主机构设置都置为否，即解除所有主机构设置
		userInstanceOrgDao.updateMainOrgFalse(userId);

		// 然后将orgId所在机构的用户实例设置为主机构
		userInstanceOrgDao.setMainOrg(userId, orgId, "1");
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByPKs(String... userIds) {
		// 获取用户实例
		String[] userInstanceIds = userInstanceUtil
				.getUserInstanceIdByUserId(userIds);
		// 删除用户实例
		deleteUserInstancesByUserInstanceIds(userInstanceIds);
		// 删除用户
		super.deleteByPKs(userIds);
		// 删除用户偏好设置
		userPreferencesService.deleteByUserIDs(userIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteUserInstanceByScope(String userId, int scopeType,
			String scopeId) {
		// 获取用户实例
		String userInstanceId = userInstanceUtil
				.getUserInstanceIdByUserIdAndScope(userId, scopeType, scopeId);
		// 删除用户实例
		deleteUserInstancesByUserInstanceIds(userInstanceId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteUserInstancesByUserInstanceIds(String... userInstanceIds) {
		if (null != userInstanceIds && userInstanceIds.length > 0) {
			Set<String> uiIds = new HashSet<String>();
			for (String uiId : userInstanceIds) {
				if (null != uiId && !uiId.trim().equals("")) {
					uiIds.add(uiId);
				}
			}
			if (!uiIds.isEmpty()) {
				userInstanceIds = uiIds.toArray(new String[uiIds.size()]);

				// 查询是否有与匿名角色的关联，有，则查询出角色ID
				List<String> anonymousRoleIdList = roleBasicDao
						.queryRoleIdsByUserInstance(userInstanceIds,
								roleTypeService.getAnonymousRoleTypeId());

				// 删除用户实例与机构扩展信息的关系
				userInstanceOrgDao.deleteByUserInstanceIds(userInstanceIds);

				// 调用父类删除方法
				super.deleteUserInstancesByUserInstanceIds(userInstanceIds);

				// 删除匿名角色
				if (null != anonymousRoleIdList
						&& !anonymousRoleIdList.isEmpty()) {
					String[] anonymousRoleIds = anonymousRoleIdList
							.toArray(new String[anonymousRoleIdList.size()]);
					roleBasicService.deleteByPKs(anonymousRoleIds);
				}
			}
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteAllByOrg(String... orgIds) {
		Set<String> delOrgIdSet = new HashSet<String>();
		if (null == orgIds || orgIds.length == 0) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORG_ID_ARRAY_IS_NULL"));
		} else {
			for (String orgId : orgIds) {
				if (isBlank(orgId)) {
					throw new NullPointerException(
							UserMessages
									.getString("USER.ORG_ID_ARRAY_HAS_NULL_ITEM"));
				}
				delOrgIdSet.add(orgId);
				List<OrgEO> orgEOList = orgDao.queryChildOrgs(orgId);
				for (OrgEO orgEO : orgEOList) {
					delOrgIdSet.add(orgEO.getOrgId());
				}
			}
		}

		String[] delOrgIds = delOrgIdSet
				.toArray(new String[delOrgIdSet.size()]);

		List<UserInstanceEO> userInstanceEOs = userInstanceDao
				.queryByScopeTypeScopeIds("1", delOrgIds);

		if (null != userInstanceEOs && !userInstanceEOs.isEmpty()) {
			// 获取用户ID
			List<String> userIdsList = getUserIdsList(userInstanceEOs);
			for (String orgId : delOrgIds) {
				deleteByOrg(orgId,
						userIdsList.toArray(new String[userIdsList.size()]));
			}
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByOrg(String orgId, String... userIds) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_ARRAY_IS_NULL"));
		} else {
			for (String userId : userIds) {
				if (isBlank(userId)) {
					throw new NullPointerException(
							UserMessages
									.getString("USER.USERID_ARRAY_HAS_NULL_ITEM"));
				} else {
					if ("all".equals(CommonPropertiesUtil
							.getDelMainOrgUserMode())) {
						// 查询用户所属主机构
						OrgDTO orgDTO = queryMainOrg(userId);
						// 如果当前机构为用户所属主机构，则删除用户，反之仅删除该机构下用户
						if (null != orgDTO && orgId.equals(orgDTO.getOrgId())) {
							deleteByPKs(userId);
						} else {
							deleteUserInstanceByScope(userId, 1, orgId);
						}

					} else if ("only".equals(CommonPropertiesUtil
							.getDelMainOrgUserMode())) {
						// 查询用户所属机构
						List<OrgDTO> orgDTOs = queryOrgs(userId);
						// 如果用户所属机构仅有1个，则删除用户，反之仅删除该机构下用户
						if (orgDTOs.size() == 1) {
							deleteByPKs(userId);
						} else if (orgDTOs.size() > 1) {
							deleteUserInstanceByScope(userId, 1, orgId);
						}
					} else {
						throw new IllegalArgumentException(
								UserMessages
										.getString("USER.DELMAINORGUSERMODE_IS_ERROR"));
					}
				}
			}
		}
	}

	public Page<UserDTO> queryAll(Pageable pageable, Sortable sortable) {
		Page<UserDTO> userDTOPage = new Page<UserDTO>(pageable.getPageIndex(),
				pageable.getPageSize(), 0, new ArrayList<UserDTO>());
		Page<UserEO> userEOPage = userCoreDao.queryAll(pageable, sortable);
		if (null != userEOPage && userEOPage.getTotal() > 0) {
			userDTOPage = BeanCopierUtil.copyPage(userEOPage, UserEO.class,
					UserDTO.class);
		}
		return userDTOPage;
	}

	public Page<UserDTO> queryByUser(UserDTO userDto, Pageable pageable,
			Sortable sortable) {
		Page<UserDTO> userDTOPage = new Page<UserDTO>(pageable.getPageIndex(),
				pageable.getPageSize(), 0, new ArrayList<UserDTO>());

		UserEO userEO = new UserEO();
		BeanCopierUtil.copy(userDto, userEO);
		Page<UserEO> userEOPage = userBasicDao.queryByUser(userEO, pageable,
				sortable);

		if (null != userEOPage && userEOPage.getTotal() > 0) {
			userDTOPage = BeanCopierUtil.copyPage(userEOPage, UserEO.class,
					UserDTO.class);
		}
		return userDTOPage;
	}

	public List<UserDTO> queryByOrg(UserDTO userDto, String orgId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		List<UserDTO> list = new ArrayList<UserDTO>();
		UserEO userEO = new UserEO();
		BeanCopierUtil.copy(userDto, userEO);
		List<UserEO> eoList = userBasicDao.queryByOrg(userEO, orgId);
		if (null != eoList && !eoList.isEmpty()) {
			BeanCopierUtil.copy(eoList, list, UserEO.class, UserDTO.class);
		}

		return list;
	}

	public Page<UserDTO> queryByOrg(UserDTO userDto, String orgId,
			Pageable pageable, Sortable sortable) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		Page<UserDTO> userDTOPage = new Page<UserDTO>(pageable.getPageIndex(),
				pageable.getPageSize(), 0, new ArrayList<UserDTO>());

		UserEO userEO = new UserEO();
		BeanCopierUtil.copy(userDto, userEO);
		Page<UserEO> userEOPage = userBasicDao.queryByOrg(userEO, orgId,
				pageable, sortable);

		if (null != userEOPage && userEOPage.getTotal() > 0) {
			userDTOPage = BeanCopierUtil.copyPage(userEOPage, UserEO.class,
					UserDTO.class);
		}
		return userDTOPage;

	}

	public List<OrgDTO> queryOrgs(String userId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_IS_NULL"));
		}
		List<GroupDTO> groupList = super.queryGroups(userId);
		List<String> groupIdList = getGroupIdList(groupList);

		List<OrgEO> orgEOList = new ArrayList<OrgEO>();
		if (!groupIdList.isEmpty()) {
			orgEOList = orgDao.queryByGroupIds(groupIdList);
		}
		List<OrgDTO> orgDTOList = new ArrayList<OrgDTO>();
		if (null != orgEOList && !orgEOList.isEmpty()) {
			BeanCopierUtil.copy(orgEOList, orgDTOList, OrgEO.class,
					OrgDTO.class);
		}
		return orgDTOList;
	}

	public OrgDTO queryMainOrg(String userId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_IS_NULL"));
		}
		OrgEO orgEO = orgDao.queryMainOrg(userId);
		OrgDTO orgDTO = null;
		if (null != orgEO) {
			orgDTO = new OrgDTO();
			BeanCopierUtil.copy(orgEO, orgDTO);
		}
		return orgDTO;

	}

	public List<OrgDTO> queryCreatedOrgs(String userId) {
		OrgEO orgEO = new OrgEO();
		orgEO.setCreator(userId);
		List<OrgEO> orgEOList = orgDao.query(orgEO);
		List<OrgDTO> orgDTOList = new ArrayList<OrgDTO>();
		if (null != orgEOList && !orgEOList.isEmpty()) {
			BeanCopierUtil.copy(orgEOList, orgDTOList, OrgEO.class,
					OrgDTO.class);
		}
		return orgDTOList;
	}

	public List<OrgDTO> queryOrgsPermmitedToAccess(String userId) {
		// TODO 数据权限，暂时不实现
		return null;
	}

	public List<OrgDTO> queryOrgsPermmitedToManage(String userId) {
		// TODO 数据权限，暂时不实现
		return null;
	}

	public Page<RoleDTO> queryDirectRoles(String userId, Pageable pageable,
			Sortable sortable) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_IS_NULL"));
		}
		Page<RoleEO> roleEOPage = userInstanceDao.queryDirectRolesByUserId(
				userId, pageable, sortable);
		Page<RoleDTO> roleDTOPage = new Page<RoleDTO>(pageable.getPageIndex(),
				pageable.getPageSize(), 0, new ArrayList<RoleDTO>());
		if (null != roleEOPage && roleEOPage.getTotal() > 0) {
			roleDTOPage = BeanCopierUtil.copyPage(roleEOPage, RoleEO.class,
					RoleDTO.class);
		}
		return roleDTOPage;
	}

	public List<RoleDTO> queryDirectRoles(String userId, String orgId) {
		if (isBlank(userId) || isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_OR_ORGID_IS_NULL"));
		}
		List<RoleEO> roleEOList = userInstanceDao.queryDirectRolesByScope(
				userId, "1", orgId);
		List<RoleDTO> roleDTOList = new ArrayList<RoleDTO>();
		if (null != roleEOList && !roleEOList.isEmpty()) {
			BeanCopierUtil.copy(roleEOList, roleDTOList, RoleEO.class,
					RoleDTO.class);
		}
		return roleDTOList;
	}

	public Page<RoleDTO> queryDirectRoles(String userId, String orgId,
			Pageable pageable, Sortable sortable) {
		if (isBlank(userId) || isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_OR_ORGID_IS_NULL"));
		}
		Page<RoleEO> roleEOPage = userInstanceDao.queryDirectRolesByScope(
				userId, "1", orgId, pageable, sortable);
		Page<RoleDTO> roleDTOPage = new Page<RoleDTO>(pageable.getPageIndex(),
				pageable.getPageSize(), 0, new ArrayList<RoleDTO>());
		if (null != roleEOPage && roleEOPage.getTotal() > 0) {
			roleDTOPage = BeanCopierUtil.copyPage(roleEOPage, RoleEO.class,
					RoleDTO.class);
		}
		return roleDTOPage;
	}

	public Page<RoleDTO> queryRoles(String userId, Pageable pageable,
			Sortable sortable) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_IS_NULL"));
		}
		Page<RoleEO> roleEOPage = userInstanceDao.queryRolesByUserId(userId,
				pageable, sortable);
		Page<RoleDTO> roleDTOPage = new Page<RoleDTO>(pageable.getPageIndex(),
				pageable.getPageSize(), 0, new ArrayList<RoleDTO>());
		if (null != roleEOPage && roleEOPage.getTotal() > 0) {
			roleDTOPage = BeanCopierUtil.copyPage(roleEOPage, RoleEO.class,
					RoleDTO.class);
		}
		return roleDTOPage;
	}

	public List<RoleDTO> queryRoles(String userId, String orgId) {
		if (isBlank(userId) || isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_OR_ORGID_IS_NULL"));
		}
		List<RoleEO> roleEOList = userInstanceDao.queryRolesByScope(userId,
				"1", orgId);
		List<RoleDTO> roleDTOList = new ArrayList<RoleDTO>();
		if (null != roleEOList && !roleEOList.isEmpty()) {
			BeanCopierUtil.copy(roleEOList, roleDTOList, RoleEO.class,
					RoleDTO.class);
		}
		return roleDTOList;
	}

	public Page<RoleDTO> queryRoles(String userId, String orgId,
			Pageable pageable, Sortable sortable) {
		if (isBlank(userId) || isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_OR_ORGID_IS_NULL"));
		}
		Page<RoleEO> roleEOPage = userInstanceDao.queryRolesByScope(userId,
				"1", orgId, pageable, sortable);
		Page<RoleDTO> roleDTOPage = new Page<RoleDTO>(pageable.getPageIndex(),
				pageable.getPageSize(), 0, new ArrayList<RoleDTO>());
		if (null != roleEOPage && roleEOPage.getTotal() > 0) {
			roleDTOPage = BeanCopierUtil.copyPage(roleEOPage, RoleEO.class,
					RoleDTO.class);
		}
		return roleDTOPage;
	}

	public List<RoleDTO> queryCreatedRoles(String userId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_IS_NULL"));
		}
		RoleEO roleEO = new RoleEO();
		roleEO.setOwnerId(userId);
		roleEO.setRoleUsage(true);
		List<RoleEO> roleEOList = roleCoreDao.queryByRole(roleEO);
		List<RoleDTO> roleDTOList = new ArrayList<RoleDTO>();
		if (null != roleEOList && !roleEOList.isEmpty()) {
			BeanCopierUtil.copy(roleEOList, roleDTOList, RoleEO.class,
					RoleDTO.class);
		}
		return roleDTOList;
	}

	public List<RoleDTO> queryRolesPermittedToAccess(String userId) {
		// TODO 数据权限
		return null;
	}

	public List<RoleDTO> queryRolesPermittedToManage(String userId) {
		// TODO 数据权限
		return null;
	}

	public List<PrivilegeDTO> queryDirectPrivileges(String userId) {

		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_IS_NULL"));
		}
		List<PrivilegeEO> privilegeEOList = userBasicDao.queryDirectPrivileges(
				roleTypeService.getAnonymousRoleTypeId(), userId);
		List<PrivilegeDTO> privilegeDTOList = new ArrayList<PrivilegeDTO>();
		if (null != privilegeEOList && !privilegeEOList.isEmpty()) {
			BeanCopierUtil.copy(privilegeEOList, privilegeDTOList,
					PrivilegeEO.class, PrivilegeDTO.class);
		}
		return privilegeDTOList;
	}

	public Page<PrivilegeDTO> queryDirectPrivileges(String userId,
			Pageable pageable, Sortable sortable) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_IS_NULL"));
		}
		Page<PrivilegeEO> privilegeEOPage = userBasicDao.queryDirectPrivileges(
				roleTypeService.getAnonymousRoleTypeId(), userId, pageable,
				sortable);
		Page<PrivilegeDTO> privilegeDTOPage = new Page<PrivilegeDTO>(
				pageable.getPageIndex(), pageable.getPageSize(), 0,
				new ArrayList<PrivilegeDTO>());
		if (null != privilegeEOPage && privilegeEOPage.getTotal() > 0) {
			privilegeDTOPage = BeanCopierUtil.copyPage(privilegeEOPage,
					PrivilegeEO.class, PrivilegeDTO.class);
		}
		return privilegeDTOPage;
	}

	public List<PrivilegeDTO> queryDirectPrivileges(String userId, String orgId) {
		// 先查询出直接角色，然后通过查询角色查询所有的权限
		if (isBlank(userId) || isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_OR_ORGID_IS_NULL"));
		}
		List<PrivilegeEO> privilegeEOList = userBasicDao
				.queryDirectOrgUserPrivileges(
						roleTypeService.getAnonymousRoleTypeId(), orgId, userId);
		List<PrivilegeDTO> privilegeDTOList = new ArrayList<PrivilegeDTO>();
		if (null != privilegeEOList && !privilegeEOList.isEmpty()) {
			BeanCopierUtil.copy(privilegeEOList, privilegeDTOList,
					PrivilegeEO.class, PrivilegeDTO.class);
		}
		return privilegeDTOList;
	}

	public Page<PrivilegeDTO> queryDirectPrivileges(String userId,
			String orgId, Pageable pageable, Sortable sortable) {
		// 先查询出直接角色，然后通过查询角色查询所有的权限
		if (isBlank(userId) || isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_OR_ORGID_IS_NULL"));
		}
		Page<PrivilegeEO> privilegeEOPage = userBasicDao
				.queryDirectOrgUserPrivileges(
						roleTypeService.getAnonymousRoleTypeId(), orgId,
						userId, pageable, sortable);
		Page<PrivilegeDTO> privilegeDTOPage = new Page<PrivilegeDTO>(
				pageable.getPageIndex(), pageable.getPageSize(), 0,
				new ArrayList<PrivilegeDTO>());
		if (null != privilegeEOPage && privilegeEOPage.getTotal() > 0) {
			privilegeDTOPage = BeanCopierUtil.copyPage(privilegeEOPage,
					PrivilegeEO.class, PrivilegeDTO.class);
		}
		return privilegeDTOPage;
	}

	public Page<PrivilegeDTO> queryPrivileges(String userId, Pageable pageable,
			Sortable sortable) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_IS_NULL"));
		}
		Page<PrivilegeEO> privilegeEOPage = userInstanceDao
				.queryPrivilegesByUserId(userId, pageable, sortable);
		Page<PrivilegeDTO> privilegeDTOPage = new Page<PrivilegeDTO>(
				pageable.getPageIndex(), pageable.getPageSize(), 0,
				new ArrayList<PrivilegeDTO>());
		if (null != privilegeEOPage && privilegeEOPage.getTotal() > 0) {
			privilegeDTOPage = BeanCopierUtil.copyPage(privilegeEOPage,
					PrivilegeEO.class, PrivilegeDTO.class);
		}
		return privilegeDTOPage;
	}

	public List<PrivilegeDTO> queryPrivileges(String userId, String orgId) {
		if (isBlank(userId) || isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_OR_ORGID_IS_NULL"));
		}
		List<PrivilegeEO> privilegeEOList = userInstanceDao
				.queryPrivilegesByScope(userId, "1", orgId);
		List<PrivilegeDTO> privilegeDTOList = new ArrayList<PrivilegeDTO>();
		if (null != privilegeEOList && !privilegeEOList.isEmpty()) {
			BeanCopierUtil.copy(privilegeEOList, privilegeDTOList,
					PrivilegeEO.class, PrivilegeDTO.class);
		}
		return privilegeDTOList;
	}

	public Page<PrivilegeDTO> queryPrivileges(String userId, String orgId,
			Pageable pageable, Sortable sortable) {
		if (isBlank(userId) || isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_OR_ORGID_IS_NULL"));
		}
		Page<PrivilegeEO> privilegeEOPage = userInstanceDao
				.queryPrivilegesByScope(userId, "1", orgId, pageable, sortable);
		Page<PrivilegeDTO> privilegeDTOPage = new Page<PrivilegeDTO>(
				pageable.getPageIndex(), pageable.getPageSize(), 0,
				new ArrayList<PrivilegeDTO>());
		if (null != privilegeEOPage && privilegeEOPage.getTotal() > 0) {
			privilegeDTOPage = BeanCopierUtil.copyPage(privilegeEOPage,
					PrivilegeEO.class, PrivilegeDTO.class);
		}
		return privilegeDTOPage;
	}

	public List<MenuDTO> queryMenus(String userId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_IS_NULL"));
		}

		List<MenuAllInfoEO> menuAllInfoEOList = userBasicDao.queryMenus(userId);
		List<MenuDTO> menuDTOList = new ArrayList<MenuDTO>();
		if (null != menuAllInfoEOList && !menuAllInfoEOList.isEmpty()) {
			BeanCopierUtil.copy(menuAllInfoEOList, menuDTOList,
					MenuAllInfoEO.class, MenuDTO.class);
		}
		return menuDTOList;
	}

	public List<MenuDTO> queryMenus(String userId, String orgId) {
		if (isBlank(userId) || isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_OR_ORGID_IS_NULL"));
		}

		List<MenuAllInfoEO> menuAllInfoEOList = userBasicDao.queryOrgUserMenus(
				orgId, userId);
		List<MenuDTO> menuDTOList = new ArrayList<MenuDTO>();
		if (null != menuAllInfoEOList && !menuAllInfoEOList.isEmpty()) {
			BeanCopierUtil.copy(menuAllInfoEOList, menuDTOList,
					MenuAllInfoEO.class, MenuDTO.class);
		}
		return menuDTOList;
	}

	public List<MenuDTO> queryCreatedMenus(String userId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_IS_NULL"));
		}
		MenuAllInfoEO menuAllInfoEO = new MenuAllInfoEO();
		menuAllInfoEO.setCreator(userId);
		menuAllInfoEO.setIsEnabled(true);
		List<MenuAllInfoEO> menuAllInfoEOList = menuDao
				.queryByMenu(menuAllInfoEO);
		List<MenuDTO> menuDTOList = new ArrayList<MenuDTO>();
		if (null != menuAllInfoEOList && !menuAllInfoEOList.isEmpty()) {
			BeanCopierUtil.copy(menuAllInfoEOList, menuDTOList,
					MenuAllInfoEO.class, MenuDTO.class);
		}
		return menuDTOList;
	}

	public List<MenuDTO> queryMenusPermittedToAccess(String userId) {
		// TODO 数据权限
		return null;
	}

	public List<MenuDTO> queryMenusPermittedToManage(String userId) {
		// TODO 数据权限
		return null;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addToOrg(String userId, String orgId) {
		if (isBlank(userId) || isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_OR_ORGID_IS_NULL"));
		}

		// 先查询用户在机构下是否已经存在实例，存在则报错

		if (userInstanceDao.existsByUserIdAndScope(userId, "1", orgId) > 0) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ALREADY_EXISTED_IN_ORG"));
		}
		// 创建用户实例
		UserInstanceEO userInstanceEO = new UserInstanceEO();
		userInstanceEO.setId(PKGenerator.generate());
		userInstanceEO.setUserId(userId);
		userInstanceEO.setScopeType("1");
		userInstanceEO.setScopeId(orgId);
		userInstanceEO.setIsEnabled(true);
		userInstanceDao.create(userInstanceEO);

		// 插入用户与缺省角色关系
		assignRole(userId, roleBasicService.getRoleofeveryoneRoleId(), orgId);

		// 创建用户实例的机构扩展信息
		UserInstanceOrgEO userInstanceOrgEO = new UserInstanceOrgEO();

		OrgDTO orgDTO = queryMainOrg(userId);

		userInstanceOrgEO.setIsMain(null == orgDTO);

		userInstanceOrgEO.setUserInstanceId(userInstanceEO.getId());
		userInstanceOrgDao.create(userInstanceOrgEO);

		// 创建用户组与用户实例的关系
		String groupId = orgDao.queryGroupIdByOrgId(orgId);
		UserInstanceGroupEO userInstanceGroupEO = new UserInstanceGroupEO();
		userInstanceGroupEO.setUserInstanceId(userInstanceEO.getId());
		userInstanceGroupEO.setGroupId(groupId);
		userInstanceGroupDao.create(userInstanceGroupEO);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addToOrgs(String[] userIds, String[] orgIds) {
		if (null == userIds || null == orgIds || userIds.length == 0
				|| orgIds.length == 0) {
			throw new NullPointerException(
					UserMessages
							.getString("USER.USERID_ARRAY_OR_ORGID_ARRAY_IS_NULL"));
		}
		for (String userId : userIds) {
			if (isBlank(userId)) {
				throw new NullPointerException(
						UserMessages
								.getString("USER.USERID_ARRAY_HAS_NULL_ITEMS"));
			}
		}
		for (String orgId : orgIds) {
			if (isBlank(orgId)) {
				throw new NullPointerException(
						UserMessages
								.getString("USER.ORGID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		for (String orgId : orgIds) {
			for (String userId : userIds) {
				addToOrg(userId, orgId);
			}
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setToOrg(String userId, String orgId) {
		if (isBlank(userId) || isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_OR_ORGID_IS_NULL"));
		}
		setToOrgs(new String[] { userId }, new String[] { orgId });
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setToOrgs(String[] userIds, String[] orgIds) {
		if (null == userIds || null == orgIds || userIds.length == 0
				|| orgIds.length == 0) {
			throw new NullPointerException(
					UserMessages
							.getString("USER.USERID_ARRAY_OR_ORGID_ARRAY_IS_NULL"));
		}
		for (String userId : userIds) {
			if (isBlank(userId)) {
				throw new NullPointerException(
						UserMessages
								.getString("USER.USERID_ARRAY_HAS_NULL_ITEMS"));
			}
		}
		for (String orgId : orgIds) {
			if (isBlank(orgId)) {
				throw new NullPointerException(
						UserMessages
								.getString("USER.ORGID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		for (String userId : userIds) {
			// 获取用户所属机构列表
			List<String> userOrgIdList = userBasicDao
					.queryOrgIdsByUserId(userId);
			// 需要添加的用户
			List<String> addOrgIdList = new ArrayList<String>();

			for (String orgId : orgIds) {
				// 判断用户是否已加入机构
				if (userOrgIdList.contains(orgId)) {
					userOrgIdList.remove(orgId);
				} else {
					addOrgIdList.add(orgId);
				}
			}

			// 删除用户与机构关系
			removeFromOrgs(new String[] { userId },
					userOrgIdList.toArray(new String[userOrgIdList.size()]));

			// 添加用户与机构的关系
			addToOrgs(new String[] { userId },
					addOrgIdList.toArray(new String[addOrgIdList.size()]));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeFromAllOrgs(String... userIds) {
		// 查询所有机构的用户实例
		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_ARRAY_IS_NULL"));
		}
		List<UserInstanceEO> userInstanceEOList = userInstanceDao
				.queryByScopeTypeUserIds("1", userIds);
		// 回收用户实例
		if (null != userInstanceEOList && !userInstanceEOList.isEmpty()) {
			// 筛选出用户实例ID数组
			List<String> userInsList = getUserInsList(userInstanceEOList);
			// 删除用户实例
			deleteUserInstancesByUserInstanceIds(userInsList
					.toArray(new String[userInsList.size()]));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeFromOrg(String userId, String orgId) {
		if (isBlank(userId) || isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_OR_ORGID_IS_NULL"));
		}
		removeFromOrgs(new String[] { userId }, new String[] { orgId });
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeFromOrgs(String[] userIds, String[] orgIds) {
		if (null == userIds || null == orgIds || userIds.length == 0
				|| orgIds.length == 0) {
			throw new NullPointerException(
					UserMessages
							.getString("USER.USERID_ARRAY_OR_ORGID_ARRAY_IS_NULL"));
		}
		for (String userId : userIds) {
			if (isBlank(userId)) {
				throw new NullPointerException(
						UserMessages
								.getString("USER.USERID_ARRAY_HAS_NULL_ITEMS"));
			}
		}
		List<String> userInstanceIds = new ArrayList<String>();
		for (String orgId : orgIds) {
			if (isBlank(orgId)) {
				throw new NullPointerException(
						UserMessages
								.getString("USER.ORGID_ARRAY_HAS_NULL_ITEM"));
			}
			String[] userInstances = userInstanceUtil
					.getUserInstanceIdByUserIdAndScope(userIds, 1, orgId);
			userInstanceIds.addAll(Arrays.asList(userInstances));
		}

		if (!userInstanceIds.isEmpty()) {
			deleteUserInstancesByUserInstanceIds(userInstanceIds
					.toArray(new String[userInstanceIds.size()]));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignRole(String userId, String roleId, String orgId) {
		assignRoles(new String[] { userId }, new String[] { roleId }, orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignRoles(String[] userIds, String[] roleIds, String orgId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		assignRoles(userIds, roleIds, 1, orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setRoles(String userId, String[] roleIds, String orgId) {
		setRoles(new String[] { userId }, roleIds, orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setRoles(String[] userIds, String[] roleIds, String orgId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		setRoles(userIds, roleIds, 1, orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeAllRoles(String[] userIds, String orgId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		revokeAllRoles(userIds, 1, orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeRole(String userId, String roleId, String orgId) {
		revokeRoles(new String[] { userId }, new String[] { roleId }, orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeRoles(String[] userIds, String[] roleIds, String orgId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		revokeRoles(userIds, roleIds, 1, orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignPrivilege(String userId, String privilegeId, String orgId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_IS_NULL"));
		}
		if (isBlank(privilegeId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.PRIVILEGEID_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		assignPrivileges(new String[] { userId }, new String[] { privilegeId },
				orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignPrivileges(String[] userIds, String[] privilegeIds,
			String orgId) {
		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_ARRAY_IS_NULL"));
		} else {
			for (String userId : userIds) {
				if (isBlank(userId)) {
					throw new NullPointerException(
							UserMessages
									.getString("USER.USERID_ARRAY_HAS_NULL_ITEMS"));
				}
			}
		}
		if (null == privilegeIds || privilegeIds.length == 0) {
			throw new NullPointerException(
					UserMessages.getString("USER.PRIVILEGEID_ARRAY_IS_NULL"));
		} else {
			for (String privilegeId : privilegeIds) {
				if (isBlank(privilegeId)) {
					throw new NullPointerException(
							UserMessages
									.getString("USER.PRIVILEGEID_ARRAY_HAS_NULL_ITEM"));
				}
			}
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		// 匿名角色类型的ID
		String anonymousRoleTypeId = roleTypeService.getAnonymousRoleTypeId();

		// 获取机构用户的用户实例ID
		List<UserInstanceEO> userInstanceEOList = userInstanceDao
				.queryByScopeTypeScopeIdUserIds("1", orgId, userIds);
		for (UserInstanceEO ueo : userInstanceEOList) {

			// 查询用户实例是否有匿名角色，有则将匿名角色ID查出，否则新建匿名角色
			String anonymousRoleId = userBasicDao.queryAnonymousRoleId(
					ueo.getId(), anonymousRoleTypeId);
			if (isBlank(anonymousRoleId)) {
				UserEO userEO = userCoreDao.queryByPK(ueo.getUserId());
				// 创建匿名角色
				RoleDTO roleDTO = new RoleDTO();
				roleDTO.setOwnerId(ueo.getUserId());
				roleDTO.setRoleDesc("用户【" + userEO.getUserName() + "】对应的匿名角色");
				roleDTO.setRoleType(anonymousRoleTypeId);
				roleDTO.setRoleUsage(true);
				roleBasicService.createAnonymousRole(roleDTO);
				anonymousRoleId = roleDTO.getRoleId();

				// 将用户与匿名角色建立关系
				UserInstanceRoleEO userInstanceRoleEO = new UserInstanceRoleEO();
				userInstanceRoleEO.setRoleId(anonymousRoleId);
				userInstanceRoleEO.setUserInstanceId(ueo.getId());
				userInstanceRoleDao.create(userInstanceRoleEO);
			}

			// 将匿名角色与权限建立关系
			roleBasicService.assignPrivileges(new String[] { anonymousRoleId },
					privilegeIds);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setPrivileges(String userId, String[] privilegeIds, String orgId) {
		// 验证数据合法性
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		if (null == privilegeIds || privilegeIds.length == 0) {
			throw new NullPointerException(
					UserMessages.getString("USER.PRIVILEGEID_ARRAY_IS_NULL"));
		}
		for (String privilegeId : privilegeIds) {
			if (isBlank(privilegeId)) {
				throw new NullPointerException(
						UserMessages
								.getString("USER.PRIVILEGEID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		setPrivilegesToUsers(new String[] { userId }, privilegeIds, orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setPrivilegesToUsers(String[] userIds, String[] privilegeIds,
			String orgId) {
		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_ARRAY_IS_NULL"));
		} else {
			for (String userId : userIds) {
				if (isBlank(userId)) {
					throw new NullPointerException(
							UserMessages
									.getString("USER.USERID_ARRAY_HAS_NULL_ITEMS"));
				}
			}
		}
		if (null == privilegeIds || privilegeIds.length == 0) {
			throw new NullPointerException(
					UserMessages.getString("USER.PRIVILEGEID_ARRAY_IS_NULL"));
		} else {
			for (String privilegeId : privilegeIds) {
				if (isBlank(privilegeId)) {
					throw new NullPointerException(
							UserMessages
									.getString("USER.PRIVILEGEID_ARRAY_HAS_NULL_ITEM"));
				}
			}
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		// 匿名角色类型的ID
		String anonymousRoleTypeId = roleTypeService.getAnonymousRoleTypeId();

		// 获取机构用户的用户实例ID
		List<UserInstanceEO> userInstanceEOList = userInstanceDao
				.queryByScopeTypeScopeIdUserIds("1", orgId, userIds);
		for (UserInstanceEO ueo : userInstanceEOList) {

			// 查询用户实例是否有匿名角色，有则将匿名角色ID查出，否则新建匿名角色
			String anonymousRoleId = userBasicDao.queryAnonymousRoleId(
					ueo.getId(), anonymousRoleTypeId);
			if (isBlank(anonymousRoleId)) {
				UserEO userEO = userCoreDao.queryByPK(ueo.getId());
				// 创建匿名角色
				RoleDTO roleDTO = new RoleDTO();
				roleDTO.setOwnerId(ueo.getUserId());
				roleDTO.setRoleDesc("用户【" + userEO.getUserName() + "】对应的匿名角色");
				roleDTO.setRoleType(anonymousRoleTypeId);
				roleDTO.setRoleUsage(true);
				roleBasicService.createAnonymousRole(roleDTO);
				anonymousRoleId = roleDTO.getRoleId();

				// 将用户与匿名角色建立关系
				UserInstanceRoleEO userInstanceRoleEO = new UserInstanceRoleEO();
				userInstanceRoleEO.setRoleId(anonymousRoleId);
				userInstanceRoleEO.setUserInstanceId(ueo.getId());
				userInstanceRoleDao.create(userInstanceRoleEO);
			}

			// 将匿名角色与权限建立关系
			roleBasicService.setPrivileges(anonymousRoleId, privilegeIds);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeAllPrivileges(String... userIds) {
		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_ARRAY_IS_NULL"));
		}

		for (String userId : userIds) {
			if (isBlank(userId)) {
				throw new NullPointerException(
						UserMessages
								.getString("USER.USERID_ARRAY_HAS_NULL_ITEMS"));
			}
		}

		List<String> roleIdList = userBasicDao.queryAnonymousRoleIdsByUserIds(
				roleTypeService.getAnonymousRoleTypeId(), userIds);
		if (null != roleIdList && !roleIdList.isEmpty()) {
			rolePrivilegeDao.deleteByRoles(roleIdList
					.toArray(new String[roleIdList.size()]));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeAllPrivileges(String[] userIds, String orgId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}

		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_ARRAY_IS_NULL"));
		}
		for (String userId : userIds) {
			if (isBlank(userId)) {
				throw new NullPointerException(
						UserMessages
								.getString("USER.USERID_ARRAY_HAS_NULL_ITEMS"));
			}
		}
		List<String> roleIdList = userBasicDao
				.queryAnonymousRoleIdsByUserIdsAndScope(
						roleTypeService.getAnonymousRoleTypeId(), orgId, "1",
						userIds);
		if (null != roleIdList && !roleIdList.isEmpty()) {
			rolePrivilegeDao.deleteByRoles(roleIdList
					.toArray(new String[roleIdList.size()]));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokePrivilege(String userId, String privilegeId, String orgId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_IS_NULL"));
		}
		if (isBlank(privilegeId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.PRIVILEGEID_IS_NULL"));
		}
		revokePrivileges(new String[] { userId }, new String[] { privilegeId },
				orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokePrivileges(String[] userIds, String[] privilegeIds,
			String orgId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_ARRAY_IS_NULL"));
		}
		if (null == privilegeIds || privilegeIds.length == 0) {
			throw new NullPointerException(
					UserMessages.getString("USER.PRIVILEGEID_ARRAY_IS_NULL"));
		}
		for (String userId : userIds) {
			if (isBlank(userId)) {
				throw new NullPointerException(
						UserMessages
								.getString("USER.USERID_ARRAY_HAS_NULL_ITEMS"));
			}
		}
		for (String privilegeId : privilegeIds) {
			if (isBlank(privilegeId)) {
				throw new NullPointerException(
						UserMessages
								.getString("USER.PRIVILEGEID_ARRAY_HAS_NULL_ITEMS"));
			}
		}
		for (String userId : userIds) {
			for (String privilegeId : privilegeIds) {
				String anonymousRoleId = userBasicDao
						.queryAnonymousRoleIdByScope(userId,
								roleTypeService.getAnonymousRoleTypeId(), "1",
								orgId);
				if (!isBlank(anonymousRoleId)) {
					rolePrivilegeDao.deleteByPrivilegeIdAndRoleId(privilegeId,
							anonymousRoleId);
				}
			}
		}
	}

	public boolean belongsToOrg(String userId, String orgId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ID_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		return userInstanceDao.existsByUserIdAndScope(userId, "1", orgId) > 0;
	}

	public boolean hasDirectRole(String userId, String roleId, String orgId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ID_IS_NULL"));
		}
		if (isBlank(roleId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ROLEID_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		return hasDirectRole(userId, roleId, 1, orgId);
	}

	public boolean hasRole(String userId, String roleId, String orgId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ID_IS_NULL"));
		}
		if (isBlank(roleId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ROLEID_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		return hasRole(userId, roleId, 1, orgId);
	}

	public boolean hasDirectPrivilege(String userId, String privilegeId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ID_IS_NULL"));
		}
		if (isBlank(privilegeId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.PRIVILEGEID_IS_NULL"));
		}

		return userBasicDao.hasDirectPrivilege(userId,
				roleTypeService.getAnonymousRoleTypeId(), privilegeId) > 0;

	}

	public boolean hasDirectPrivilege(String userId, String privilegeId,
			String orgId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ID_IS_NULL"));
		}
		if (isBlank(privilegeId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.PRIVILEGEID_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		return userBasicDao.hasDirectOrgUserPrivilege(userId, orgId,
				roleTypeService.getAnonymousRoleTypeId(), privilegeId) > 0;
	}

	public boolean hasPrivilege(String userId, String privilegeId, String orgId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ID_IS_NULL"));
		}
		if (isBlank(privilegeId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.PRIVILEGEID_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}

		return hasPrivilege(userId, privilegeId, 1, orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setEnabledByOrg(String userId, String orgId, boolean enabled) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ID_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		setEnabledByScope(userId, 1, orgId, enabled);
	}

	public boolean isEnabledByOrg(String userId, String orgId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ID_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		return isEnabledByScope(userId, 1, orgId);
	}

	public boolean isAdmin(String userId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ID_IS_NULL"));
		}
		return hasRole(userId, roleBasicService.getAdministratorRoleId());
	}

	public boolean isMainOrg(String userId, String orgId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ID_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		return userInstanceOrgDao.isMainOrg(userId, orgId) > 0;
	}

	public boolean isOrgManager(String userId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ID_IS_NULL"));
		}
		return hasRole(userId, roleBasicService.getOrgManagerRoleId());

	}

	public boolean isOrgManager(String userId, String orgId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ID_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		return hasRole(userId, roleBasicService.getOrgManagerRoleId(), 1, orgId);

	}

	public boolean isOrgSCreator(String userId, String orgId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ID_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ORGID_IS_NULL"));
		}
		return orgDao.isOrgSCreator(userId, orgId) > 0;
	}

	public boolean isMgtPermitted(String userId, String orgId) {
		// TODO 数据权限
		return false;
	}

	public boolean isRoleSCreator(String userId, String roleId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ID_IS_NULL"));
		}
		if (isBlank(roleId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.ROLEID_IS_NULL"));
		}
		return roleBasicDao.isRoleSCreator(userId, roleId) > 0;
	}

	public boolean hasMenu(String userId, String menuId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ID_IS_NULL"));
		}
		if (isBlank(menuId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.MENUID_IS_NULL"));
		}
		return hasPrivilege(userId, menuId);
	}

	public boolean isMenuSCreator(String userId, String menuId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ID_IS_NULL"));
		}
		if (isBlank(menuId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.MENUID_IS_NULL"));
		}
		return menuDao.isMenuSCreator(userId, menuId) > 0;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void resetPasswords(String... userIds) {
		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(
					UserMessages.getString("USER.USER_ID_ARRAY_IS_NULL"));
		}
		for (String userId : userIds) {
			if (isBlank(userId)) {
				throw new NullPointerException(
						UserMessages
								.getString("USER.USER_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}

		userCoreDao.updatePasswordByUserIds(userIds,
				passwordService.encryptPassword(getDefaultPwd()));
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

	private List<String> getUserInsList(List<UserInstanceEO> userInstanceEOs) {
		List<String> arr = new ArrayList<String>();
		if (null != userInstanceEOs && !userInstanceEOs.isEmpty()) {
			for (UserInstanceEO userInstanceEO : userInstanceEOs) {
				if (!isBlank(userInstanceEO.getId())) {
					arr.add(userInstanceEO.getId());
				}
			}
		}
		return arr;
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

	private List<String> getGroupIdList(List<GroupDTO> groupList) {
		List<String> groupIdList = new ArrayList<String>();
		if (null != groupList && !groupList.isEmpty()) {
			for (GroupDTO groupDTO : groupList) {
				groupIdList.add(groupDTO.getGroupId());
			}
		}
		return groupIdList;
	}

	public String getDefaultPwd() {
		return CommonPropertiesUtil.getDefaultPwd();
	}
}
