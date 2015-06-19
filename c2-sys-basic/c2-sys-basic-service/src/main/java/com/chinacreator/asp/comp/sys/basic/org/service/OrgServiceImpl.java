package com.chinacreator.asp.comp.sys.basic.org.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.basic.OrgMessages;
import com.chinacreator.asp.comp.sys.basic.org.dao.GroupOrgDao;
import com.chinacreator.asp.comp.sys.basic.org.dao.OrgDao;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.basic.org.eo.GroupOrgEO;
import com.chinacreator.asp.comp.sys.basic.org.eo.OrgEO;
import com.chinacreator.asp.comp.sys.basic.role.service.RoleService;
import com.chinacreator.asp.comp.sys.basic.user.dao.UserInstanceOrgDao;
import com.chinacreator.asp.comp.sys.basic.user.service.UserService;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.CommonPropertiesUtil;
import com.chinacreator.asp.comp.sys.common.PKGenerator;
import com.chinacreator.asp.comp.sys.core.group.dao.GroupDao;
import com.chinacreator.asp.comp.sys.core.group.eo.GroupEO;
import com.chinacreator.asp.comp.sys.core.group.service.GroupService;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.role.eo.RoleEO;
import com.chinacreator.asp.comp.sys.core.user.dao.UserInstanceDao;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserEO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserInstanceEO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

@Service
public class OrgServiceImpl implements OrgService {

	@Autowired
	private OrgDao orgDao;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private GroupService groupService;

	@Autowired
	private GroupOrgDao groupOrgDao;

	@Autowired
	private UserInstanceDao userInstanceDao;

	@Autowired
	private UserInstanceOrgDao userInstanceOrgDao;

	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.basic.user.service.UserServiceImpl")
	private UserService userService;

	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.basic.role.service.RoleServiceImpl")
	private RoleService roleService;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void create(OrgDTO orgDTO) {
		validateCreate(orgDTO);
		orgDTO.setOrgId(PKGenerator.generate());

		// 先创建用户组
		GroupEO groupEO = new GroupEO();
		// 机构用户组，创建用户组时使用用户组ID定义名称
		String pkID = PKGenerator.generate();
		groupEO.setGroupId(pkID);
		groupEO.setGroupName(pkID);
		groupEO.setGroupDesc("机构【" + orgDTO.getOrgName() + "】对应的匿名用户组");
		groupEO.setOwnerId(orgDTO.getCreator());
		groupEO.setType("1");
		groupDao.create(groupEO);

		// 创建机构
		OrgEO orgEO = new OrgEO();
		BeanCopierUtil.copy(orgDTO, orgEO);
		orgDao.create(orgEO);

		// 建立机构与用户组关系
		GroupOrgEO groupOrgEO = new GroupOrgEO();
		groupOrgEO.setOrgId(orgEO.getOrgId());
		groupOrgEO.setGroupId(groupEO.getGroupId());
		groupOrgDao.create(groupOrgEO);
	}

	private void validateCreate(OrgDTO orgDTO) {
		if (null != orgDTO) {
			if (isBlank(orgDTO.getOrgName())) {
				throw new NullPointerException(
						OrgMessages.getString("ORG.ORG_NAME_IS_NULL"));
			}
			if (isBlank(orgDTO.getOrgShowName())) {
				throw new NullPointerException(
						OrgMessages.getString("ORG.ORG_SHOWNAME_IS_NULL"));
			}
			if (isBlank(orgDTO.getParentId())) {
				throw new NullPointerException(
						OrgMessages.getString("ORG.PARENT_ID_IS_NULL"));
			}
			if(isBlank(orgDTO.getOrgNumber())){
				throw new NullPointerException(
						OrgMessages.getString("ORG.ORG_NUMBER_IS_NULL"));
			}
			// 唯一：org_name(机构名称)、orgnumber(机构编号)
			if (CommonPropertiesUtil.isUniqueOrgName()
					&& orgDao.existsByOrgName(orgDTO.getOrgName()) > 0) {
				throw new IllegalArgumentException(
						OrgMessages.getString("ORG.ORG_NAME_EXISTED"));
			}
			if (orgDao.existsByOrgNumber(orgDTO.getOrgNumber()) > 0) {
				throw new IllegalArgumentException(
						OrgMessages.getString("ORG.ORG_NUMBER_EXISTED"));
			}
			if (CommonPropertiesUtil.isUniqueOrgShowName()
					&& orgDao.existsByOrgShowName(orgDTO.getParentId(),
							orgDTO.getOrgShowName()) > 0) {
				throw new IllegalArgumentException(
						OrgMessages
								.getString("ORG.ORG_SHOWNAME_EXISTED_IN_THE_SAME_LEVEL"));
			}

		} else {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORGDTO_IS_NULL"));
		}

	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void update(OrgDTO orgDTO) {
		validateUpdate(orgDTO);
		// 修改机构
		OrgEO orgEO = new OrgEO();
		BeanCopierUtil.copy(orgDTO, orgEO);
		orgDao.update(orgEO);
	}

	private void validateUpdate(OrgDTO orgDTO) {
		if (null != orgDTO) {
			if (isBlank(orgDTO.getOrgId())) {
				throw new NullPointerException(
						OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
			}
			// 如果机构名称和机构编号需要修改，则需要判断是否它与现有的其他冲突
			if (null != orgDTO.getOrgName()) {
				if ("".equals(orgDTO.getOrgName().trim())) {
					throw new NullPointerException(
							OrgMessages.getString("ORG.ORG_NAME_IS_NULL"));
				}
				if (CommonPropertiesUtil.isUniqueOrgName()
						&& orgDao.existsByOrgNameIgnoreOrgID(
								orgDTO.getOrgName(), orgDTO.getOrgId()) > 0) {
					throw new IllegalArgumentException(
							OrgMessages.getString("ORG.ORG_NAME_EXISTED"));
				}
			}
			if (null != orgDTO.getOrgNumber()) {
				if ("".equals(orgDTO.getOrgNumber().trim())) {
					throw new NullPointerException(
							OrgMessages.getString("ORG.ORG_NUMBER_IS_NULL"));
				}
				if (orgDao.existsByOrgNumberIgnoreOrgID(orgDTO.getOrgNumber(),
						orgDTO.getOrgId()) > 0) {
					throw new IllegalArgumentException(
							OrgMessages.getString("ORG.ORG_NUMBER_EXISTED"));
				}
			}
			// 机构显示名称
			String orgShowName = orgDTO.getOrgShowName();
			if (null != orgShowName) {
				if ("".equals(orgShowName.trim())) {
					throw new NullPointerException(
							OrgMessages.getString("ORG.ORG_SHOWNAME_IS_NULL"));
				}
				// 查询同层中机构显示名称的存在性
				if (CommonPropertiesUtil.isUniqueOrgShowName()
						&& orgDao.existsByOrgShowNameOnlyIgnoreOrgID(
								orgDTO.getOrgId(), orgShowName) > 0) {
					throw new IllegalArgumentException(
							OrgMessages
									.getString("ORG.ORG_SHOWNAME_EXISTED_IN_THE_SAME_LEVEL"));
				}
			}
		} else {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORGDTO_IS_NULL"));
		}

	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByPKs(String... orgIds) {

		// 验证机构ID数组
		if (null == orgIds || orgIds.length == 0) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_ARRAY_IS_NULL"));
		}
		Set<String> delOrgIdSet = new HashSet<String>();
		for (String orgId : orgIds) {
			if (isBlank(orgId)) {
				throw new NullPointerException(
						OrgMessages.getString("ORG.ORG_ID_ARRAY_HAS_NULL_ITEM"));
			}
			delOrgIdSet.add(orgId);
			List<OrgDTO> orgDTOList = queryChildOrgs(orgId, true);
			for (OrgDTO orgDTO : orgDTOList) {
				delOrgIdSet.add(orgDTO.getOrgId());
			}
		}

		// 删除用户信息
		userService.deleteAllByOrg(orgIds);

		String[] delOrgIds = delOrgIdSet
				.toArray(new String[delOrgIdSet.size()]);

		// 查询机构映射的用户组
		List<String> groupIdList = orgDao.queryGroupIdsByOrgIds(delOrgIds);
		// 删除机构与用户组关系
		groupOrgDao.deleteByOrgIds(delOrgIds);
		if (null != groupIdList && !groupIdList.isEmpty()) {
			// 删除机构映射的用户组
			groupService.deleteByPKs(groupIdList.toArray(new String[groupIdList
					.size()]));
		}

		// 删除机构
		orgDao.deleteByPKs(delOrgIds);
	}

	public List<OrgDTO> queryAll() {
		List<OrgEO> orgEOList = orgDao.queryAll();
		List<OrgDTO> orgDTOList = new ArrayList<OrgDTO>();
		if (null != orgEOList && !orgEOList.isEmpty()) {
			BeanCopierUtil.copy(orgEOList, orgDTOList, OrgEO.class,
					OrgDTO.class);
		}
		return orgDTOList;
	}

	public List<OrgDTO> queryAllByPermission() {
		// TODO 数据权限
		return null;
	}

	public Page<OrgDTO> queryAll(Pageable pageable, Sortable sortable) {
		Page<OrgEO> orgEOPage = orgDao.queryAll(pageable, sortable);
		Page<OrgDTO> orgDTOPage = new Page<OrgDTO>(pageable.getPageIndex(),
				pageable.getPageSize(), 0, new ArrayList<OrgDTO>());
		if (null != orgEOPage && orgEOPage.getTotal() > 0) {
			orgDTOPage = BeanCopierUtil.copyPage(orgEOPage, OrgEO.class,
					OrgDTO.class);
		}
		return orgDTOPage;
	}

	public Page<OrgDTO> queryAllByPermission(Pageable pageable,
			Sortable sortable) {
		// TODO 数据权限
		return null;
	}

	public List<OrgDTO> queryByOrg(OrgDTO orgDTO) {
		OrgEO orgEO = new OrgEO();
		BeanCopierUtil.copy(orgDTO, orgEO);

		List<OrgEO> orgEOList = orgDao.query(orgEO);
		List<OrgDTO> orgDTOList = new ArrayList<OrgDTO>();
		if (null != orgEOList && !orgEOList.isEmpty()) {
			BeanCopierUtil.copy(orgEOList, orgDTOList, OrgEO.class,
					OrgDTO.class);
		}
		return orgDTOList;
	}

	public List<OrgDTO> queryByOrgAndPermission(OrgDTO orgDTO) {
		// TODO 数据权限
		return null;
	}

	public Page<OrgDTO> queryByOrg(OrgDTO orgDTO, Pageable pageable,
			Sortable sortable) {
		OrgEO orgEO = new OrgEO();
		BeanCopierUtil.copy(orgDTO, orgEO);

		Page<OrgEO> orgEOPage = orgDao.query(orgEO, pageable, sortable);
		Page<OrgDTO> orgDTOPage = new Page<OrgDTO>(pageable.getPageIndex(),
				pageable.getPageSize(), 0, new ArrayList<OrgDTO>());

		if (null != orgEOPage && orgEOPage.getTotal() > 0) {
			orgDTOPage = BeanCopierUtil.copyPage(orgEOPage, OrgEO.class,
					OrgDTO.class);
		}
		return orgDTOPage;
	}

	public Page<OrgDTO> queryByOrgAndPermission(OrgDTO orgDTO,
			Pageable pageable, Sortable sortable) {
		// TODO 数据权限
		return null;
	}

	public OrgDTO queryByPK(String orgId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		OrgEO orgEO = orgDao.queryByPK(orgId);
		OrgDTO orgDTO = null;
		if (null != orgEO) {
			orgDTO = new OrgDTO();
			BeanCopierUtil.copy(orgEO, orgDTO);
		}
		return orgDTO;
	}

	public List<OrgDTO> queryFatherOrgs(String orgId, boolean isRecursion) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		List<OrgEO> orgEOList = null;
		if (isRecursion) {
			orgEOList = orgDao.queryFatherOrgs(orgId);
		} else {
			orgEOList = orgDao.queryFatherOrgsUnRecursive(orgId);
		}
		List<OrgDTO> orgDTOList = new ArrayList<OrgDTO>();
		if (null != orgEOList && !orgEOList.isEmpty()) {
			BeanCopierUtil.copy(orgEOList, orgDTOList, OrgEO.class,
					OrgDTO.class);
		}
		return orgDTOList;
	}

	public Page<OrgDTO> queryFatherOrgs(String orgId, boolean isRecursion,
			Pageable pageable, Sortable sortable) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		Page<OrgEO> orgEOPage = null;
		if (isRecursion) {
			orgEOPage = orgDao.queryFatherOrgs(orgId, pageable, sortable);
		} else {
			orgEOPage = orgDao.queryFatherOrgsUnRecursive(orgId, pageable,
					sortable);
		}
		Page<OrgDTO> orgDTOPage = new Page<OrgDTO>(pageable.getPageIndex(),
				pageable.getPageSize(), 0, new ArrayList<OrgDTO>());
		if (null != orgEOPage && orgEOPage.getTotal() > 0) {
			orgDTOPage = BeanCopierUtil.copyPage(orgEOPage, OrgEO.class,
					OrgDTO.class);
		}
		return orgDTOPage;
	}

	public List<OrgDTO> queryChildOrgs(String orgId, boolean isRecursion) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		List<OrgEO> orgEOList = null;
		if (isRecursion) {
			orgEOList = orgDao.queryChildOrgs(orgId);
		} else {
			orgEOList = orgDao.queryChildOrgsUnRecursive(orgId);
		}
		List<OrgDTO> orgDTOList = new ArrayList<OrgDTO>();
		if (null != orgEOList && !orgEOList.isEmpty()) {
			BeanCopierUtil.copy(orgEOList, orgDTOList, OrgEO.class,
					OrgDTO.class);
		}
		return orgDTOList;
	}

	public List<OrgDTO> queryChildOrgsByPermission(String orgId,
			boolean isRecursion) {
		// TODO 数据权限
		return null;
	}

	public Page<OrgDTO> queryChildOrgs(String orgId, boolean isRecursion,
			Pageable pageable, Sortable sortable) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		Page<OrgEO> orgEOPage = null;
		if (isRecursion) {
			orgEOPage = orgDao.queryChildOrgs(orgId, pageable, sortable);
		} else {
			orgEOPage = orgDao.queryChildOrgsUnRecursive(orgId, pageable,
					sortable);
		}
		Page<OrgDTO> orgDTOPage = new Page<OrgDTO>(pageable.getPageIndex(),
				pageable.getPageSize(), 0, new ArrayList<OrgDTO>());
		if (null != orgEOPage && orgEOPage.getTotal() > 0) {
			orgDTOPage = BeanCopierUtil.copyPage(orgEOPage, OrgEO.class,
					OrgDTO.class);
		}
		return orgDTOPage;
	}

	public Page<OrgDTO> queryChildOrgsByPermission(String orgId,
			boolean isRecursion, Pageable pageable, Sortable sortable) {
		// TODO 数据权限
		return null;
	}

	public List<UserDTO> queryUsers(String orgId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		String groupId = orgDao.queryGroupIdByOrgId(orgId);

		// 采用从用户组查下来定位到用户
		List<UserDTO> userDTOList = groupService.queryUsers(groupId, 1, orgId);
		return userDTOList;
	}

	public Page<UserDTO> queryUsers(String orgId, Pageable pageable,
			Sortable sortable) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		String groupId = orgDao.queryGroupIdByOrgId(orgId);
		// 采用从用户组查下来定位到用户
		Page<UserEO> userEOPage = groupDao.queryUsersByScope(groupId, "1",
				orgId, pageable, sortable);
		Page<UserDTO> userDTOPage = new Page<UserDTO>(pageable.getPageIndex(),
				pageable.getPageSize(), 0, new ArrayList<UserDTO>());
		if (null != userEOPage && userEOPage.getTotal() > 0) {
			userDTOPage = BeanCopierUtil.copyPage(userEOPage, UserEO.class,
					UserDTO.class);
		}
		return userDTOPage;
	}

	public List<RoleDTO> queryRoles(String orgId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		String groupId = orgDao.queryGroupIdByOrgId(orgId);
		List<RoleDTO> roleDTOList = groupService.queryRoles(groupId);
		return roleDTOList;
	}

	public Page<RoleDTO> queryRoles(String orgId, Pageable pageable,
			Sortable sortable) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		String groupId = orgDao.queryGroupIdByOrgId(orgId);

		Page<RoleEO> roleEOPage = groupDao.queryRoles(groupId, pageable,
				sortable);
		Page<RoleDTO> roleDTOPage = new Page<RoleDTO>(pageable.getPageIndex(),
				pageable.getPageSize(), 0, new ArrayList<RoleDTO>());
		if (null != roleEOPage && roleEOPage.getTotal() > 0) {
			roleDTOPage = BeanCopierUtil.copyPage(roleEOPage, RoleEO.class,
					RoleDTO.class);
		}
		return roleDTOPage;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addUser(String orgId, String userId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		if (isBlank(userId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.USER_ID_IS_NULL"));
		}
		addUsers(new String[] { orgId }, new String[] { userId });
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addUsers(String[] orgIds, String[] userIds) {
		if (null == orgIds || orgIds.length == 0) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_ARRAY_IS_NULL"));
		}
		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.USER_ID_ARRAY_IS_NULL"));
		}
		for (String orgId : orgIds) {
			if (isBlank(orgId)) {
				throw new NullPointerException(
						OrgMessages.getString("ORG.ORG_ID_ARRAY_HAS_NULL_ITEM"));
			}
			String groupId = orgDao.queryGroupIdByOrgId(orgId);
			for (String userId : userIds) {
				if (isBlank(userId)) {
					throw new NullPointerException(
							OrgMessages
									.getString("ORG.USER_ID_ARRAY_HAS_NULL_ITEM"));
				}
				if (containsUser(orgId, userId)) {
					throw new IllegalArgumentException(
							OrgMessages
									.getString("ORG.USER_EXISTED_IN_THE_ORG"));
				}

				// 先创建用户实例
				UserInstanceEO userInstanceEO = new UserInstanceEO();
				userInstanceEO.setId(PKGenerator.generate());
				userInstanceEO.setIsEnabled(true);
				userInstanceEO.setScopeId(orgId);
				userInstanceEO.setScopeType("1");
				userInstanceEO.setUserId(userId);
				userInstanceDao.create(userInstanceEO);

				// 插入用户与缺省角色关系
				userService.assignRole(userId,
						roleService.getRoleofeveryoneRoleId(), orgId);

				groupService.addUser(groupId, userId, 1, orgId);
			}
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setUser(String orgId, String userId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		if (isBlank(userId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.USER_ID_IS_NULL"));
		}

		setUsers(new String[] { orgId }, new String[] { userId });
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setUsers(String[] orgIds, String[] userIds) {
		if (null == orgIds || orgIds.length == 0) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_ARRAY_IS_NULL"));
		}
		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.USER_ID_ARRAY_IS_NULL"));
		}
		for (String orgId : orgIds) {
			if (isBlank(orgId)) {
				throw new NullPointerException(
						OrgMessages.getString("ORG.ORG_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		for (String userId : userIds) {
			if (isBlank(userId)) {
				throw new NullPointerException(
						OrgMessages
								.getString("ORG.USER_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		userService.setToOrgs(userIds, orgIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeAllUsers(String... orgIds) {
		// 通过机构查询用户组
		if (null == orgIds || orgIds.length == 0) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_ARRAY_IS_NULL"));
		}
		for (String orgId : orgIds) {
			if (isBlank(orgId)) {
				throw new NullPointerException(
						OrgMessages.getString("ORG.ORG_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}

		List<UserInstanceEO> uInsEOList = userInstanceDao
				.queryByScopeTypeScopeIds("1", orgIds);
		if (null != uInsEOList && !uInsEOList.isEmpty()) {
			List<String> userInsList = new ArrayList<String>();
			for (UserInstanceEO userInstanceEO : uInsEOList) {
				if (!isBlank(userInstanceEO.getId())) {
					userInsList.add(userInstanceEO.getId());
				}
			}
			userService.deleteUserInstancesByUserInstanceIds(userInsList
					.toArray(new String[userInsList.size()]));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeUser(String orgId, String userId) {
		userService.removeFromOrg(userId, orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeUsers(String[] orgIds, String[] userIds) {
		userService.removeFromOrgs(userIds, orgIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignRole(String orgId, String roleId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		if (isBlank(roleId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ROLE_ID_IS_NULL"));
		}
		String groupId = orgDao.queryGroupIdByOrgId(orgId);
		if (isBlank(groupId)) {
			throw new IllegalArgumentException(
					OrgMessages.getString("ORG.ORG_ILLEGAL"));
		}
		groupService.assignRole(groupId, roleId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignRoles(String[] orgIds, String[] roleIds) {
		if (null == orgIds || orgIds.length == 0) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_ARRAY_IS_NULL"));
		}
		if (null == roleIds || roleIds.length == 0) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ROLE_ID_ARRAY_IS_NULL"));
		}
		for (String roleId : roleIds) {
			if (isBlank(roleId)) {
				throw new NullPointerException(
						OrgMessages
								.getString("ORG.ROLE_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		List<String> groupIds = new ArrayList<String>();
		for (String orgId : orgIds) {
			if (isBlank(orgId)) {
				throw new NullPointerException(
						OrgMessages.getString("ORG.ORG_ID_ARRAY_HAS_NULL_ITEM"));
			}
			String groupId = orgDao.queryGroupIdByOrgId(orgId);
			if (!isBlank(groupId)) {
				groupIds.add(groupId);
			}
		}

		String[] groupArr = groupIds.toArray(new String[groupIds.size()]);
		groupService.assignRoles(groupArr, roleIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setRole(String orgId, String roleId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		if (isBlank(roleId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ROLE_ID_IS_NULL"));
		}
		setRoles(new String[] { orgId }, new String[] { roleId });

	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setRoles(String[] orgIds, String[] roleIds) {
		if (null == orgIds || orgIds.length == 0) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_ARRAY_IS_NULL"));
		}
		if (null == roleIds || roleIds.length == 0) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ROLE_ID_ARRAY_IS_NULL"));
		}
		for (String roleId : roleIds) {
			if (isBlank(roleId)) {
				throw new NullPointerException(
						OrgMessages
								.getString("ORG.ROLE_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		List<String> groupIds = new ArrayList<String>();
		for (String orgId : orgIds) {
			if (isBlank(orgId)) {
				throw new NullPointerException(
						OrgMessages.getString("ORG.ORG_ID_ARRAY_HAS_NULL_ITEM"));
			}
			String groupId = orgDao.queryGroupIdByOrgId(orgId);
			if (!isBlank(groupId)) {
				groupIds.add(groupId);
			}
		}
		String[] groupArr = groupIds.toArray(new String[groupIds.size()]);
		groupService.setRolesToGroups(groupArr, roleIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeAllRoles(String... orgIds) {
		if (null == orgIds || orgIds.length == 0) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_ARRAY_IS_NULL"));
		}
		for (String orgId : orgIds) {
			if (isBlank(orgId)) {
				throw new NullPointerException(
						OrgMessages.getString("ORG.ORG_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}

		List<String> groupIds = orgDao.queryGroupIdsByOrgIds(orgIds);
		if (null != groupIds && !groupIds.isEmpty()) {
			String[] groupArr = groupIds.toArray(new String[groupIds.size()]);
			groupService.revokeAllRoles(groupArr);
		} else {
			throw new IllegalArgumentException(
					OrgMessages.getString("ORG.ORG_ID_ILLEGAL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeRoles(String orgId, String... roleIds) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		if (null == roleIds || roleIds.length == 0) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ROLE_ID_ARRAY_IS_NULL"));
		}
		String groupId = orgDao.queryGroupIdByOrgId(orgId);
		for (String roleId : roleIds) {
			if (isBlank(roleId)) {
				throw new NullPointerException(
						OrgMessages
								.getString("ORG.ROLE_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		String[] groups = { groupId };
		groupService.revokeRoles(groups, roleIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addOrgMgrs(String orgId, String... userIds) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.USER_ID_ARRAY_IS_NULL"));
		}

		for (String userId : userIds) {
			if (isBlank(userId)) {
				throw new NullPointerException(
						OrgMessages
								.getString("ORG.USER_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}

		userService.assignRoles(userIds,
				new String[] { roleService.getOrgManagerRoleId() }, orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setOrgMgrs(String orgId, String... userIds) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.USER_ID_IS_NULL"));
		}
		for (String userId : userIds) {
			if (isBlank(userId)) {
				throw new NullPointerException(
						OrgMessages
								.getString("ORG.USER_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		removeOrgMgrs(orgId, userIds);
		addOrgMgrs(orgId, userIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeAllOrgMgrs(String... orgIds) {
		// 通过机构ID查询出所有的用户组ID
		if (null == orgIds || orgIds.length == 0) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_ARRAY_IS_NULL"));
		}
		String roleId = roleService.getOrgManagerRoleId();
		String[] roleIds = { roleId };
		for (String orgId : orgIds) {
			if (isBlank(orgId)) {
				throw new NullPointerException(
						OrgMessages.getString("ORG.ORG_ID_ARRAY_HAS_NULL_ITEM"));
			}
			List<UserDTO> userDTOList = roleService.queryUsers(roleId, orgId);

			List<String> userIds = new ArrayList<String>();
			String[] userIdArr = null;

			// 当机构下存在管理员
			if (null != userDTOList && !userDTOList.isEmpty()) {
				for (UserDTO userDto : userDTOList) {
					userIds.add(userDto.getUserId());
				}
				if (!userIds.isEmpty()) {
					userIdArr = userIds.toArray(new String[userIds.size()]);

					// 回收机构用户的管理员权限
					roleService.revokeFromUsers(roleIds, userIdArr, orgId);
				}
			}
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeOrgMgrs(String orgId, String... userIds) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		if (null == userIds || userIds.length == 0) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.USER_ID_IS_NULL"));
		}
		for (String userId : userIds) {
			if (isBlank(userId)) {
				throw new NullPointerException(
						OrgMessages
								.getString("ORG.USER_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		userService.revokeRoles(userIds,
				new String[] { roleService.getOrgManagerRoleId() }, orgId);
	}

	public boolean existsByOrgName(String orgName) {
		if (isBlank(orgName)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORGNAME_IS_NULL"));
		}
		return orgDao.existsByOrgName(orgName) > 0;
	}

	public boolean existsByOrgNameIgnoreOrgID(String orgName, String orgId) {
		if (isBlank(orgName)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORGNAME_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		return orgDao.existsByOrgNameIgnoreOrgID(orgName, orgId) > 0;
	}

	public boolean existsByOrgShowName(String parentId, String orgShowName) {
		if (isBlank(parentId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.PARENET_ID_IS_NULL"));
		}
		if (isBlank(orgShowName)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORGSHOWNAME_IS_NULL"));
		}
		return orgDao.existsByOrgShowName(parentId, orgShowName) > 0;
	}

	public boolean existsByOrgShowNameIgnoreOrgID(String orgShowName,
			String orgId) {
		if (isBlank(orgShowName)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORGSHOWNAME_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		return orgDao.existsByOrgShowNameOnlyIgnoreOrgID(orgId, orgShowName) > 0;
	}

	public boolean existsByOrgNumber(String orgNumber) {
		if (isBlank(orgNumber)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORGNUMBER_IS_NULL"));
		}
		return orgDao.existsByOrgNumber(orgNumber) > 0;
	}

	public boolean existsByOrgNumberIgnoreOrgID(String orgNumber, String orgId) {
		if (isBlank(orgNumber)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORGNUMBER_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		return orgDao.existsByOrgNumberIgnoreOrgID(orgNumber, orgId) > 0;
	}

	public boolean containsUser(String orgId, String userId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		if (isBlank(userId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.USER_ID_IS_NULL"));
		}

		String groupId = orgDao.queryGroupIdByOrgId(orgId);

		return groupDao.containsUserByScope(groupId, userId, "1", orgId) > 0;
	}

	public boolean hasRole(String orgId, String roleId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		if (isBlank(roleId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ROLE_ID_IS_NULL"));
		}
		return roleService.isAssignedToOrg(roleId, orgId);
	}

	public boolean isOrgMgr(String orgId, String userId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		if (isBlank(userId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.USER_ID_IS_NULL"));
		}

		return orgDao
				.isOrgMgr(userId, orgId, roleService.getOrgManagerRoleId()) > 0;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setOrder(List<OrgDTO> orgDTOList) {
		if (null != orgDTOList && orgDTOList.size() > 0) {
			for (OrgDTO orgDTO : orgDTOList) {
				if (null != orgDTO) {
					if (!isBlank(orgDTO.getOrgId())
							&& null != orgDTO.getOrgSn()) {
						OrgEO orgEO = new OrgEO();
						BeanCopierUtil.copy(orgDTO, orgEO);
						orgDao.setOrder(orgEO);
					} else {
						throw new NullPointerException(
								OrgMessages
										.getString("ORG.ORG_ID_OR_ORG_SN_IS_NULL"));
					}
				} else {
					throw new NullPointerException(
							OrgMessages.getString("ORG.ORG_DTO_IS_NULL"));
				}
			}
		} else {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_DTO_IS_NULL"));
		}
	}

	public boolean isMainOrg(String orgId, String userId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		if (isBlank(userId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.USER_ID_IS_NULL"));
		}
		return userInstanceOrgDao.isMainOrg(userId, orgId) > 0;
	}

	public boolean isAccessPermitted(String orgId, String userId) {
		// TODO 数据权限
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

	public boolean existsChildOrgs(String orgId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		return orgDao.existsChildOrgs(orgId) > 0;
	}
}
