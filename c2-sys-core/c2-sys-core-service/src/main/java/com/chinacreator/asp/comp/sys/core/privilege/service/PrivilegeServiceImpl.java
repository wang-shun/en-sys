package com.chinacreator.asp.comp.sys.core.privilege.service;

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
import com.chinacreator.asp.comp.sys.core.PrivilegeMessages;
import com.chinacreator.asp.comp.sys.core.RoleMessages;
import com.chinacreator.asp.comp.sys.core.common.ValidatorUtil;
import com.chinacreator.asp.comp.sys.core.group.dto.GroupDTO;
import com.chinacreator.asp.comp.sys.core.group.eo.GroupEO;
import com.chinacreator.asp.comp.sys.core.privilege.dao.PrivilegeDao;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.asp.comp.sys.core.role.dao.RoleDao;
import com.chinacreator.asp.comp.sys.core.role.dao.RolePrivilegeDao;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.role.eo.RoleEO;
import com.chinacreator.asp.comp.sys.core.role.eo.RolePrivilegeEO;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserEO;

/**
 * 权限服务业务实现
 * 
 * @author 杨祎程
 * 
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

	@Autowired
	private PrivilegeDao privilegeDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private RolePrivilegeDao rolePrivilegeDao;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void create(PrivilegeDTO privilegeDTO) {

		// 使用domain验证参数合法性和唯一值的存在性
		createValidata(privilegeDTO);

		// 设置主键
		privilegeDTO.setPrivilegeId(PKGenerator.generate());

		// 创建EO，将DTO赋值给EO
		PrivilegeEO privilegeEO = new PrivilegeEO();
		BeanCopierUtil.copy(privilegeDTO, privilegeEO);

		// 创建权限
		privilegeDao.create(privilegeEO);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void update(PrivilegeDTO privilegeDTO) {

		// 使用domain验证参数合法性和唯一值的存在性
		updateValidata(privilegeDTO);

		// 创建EO，将DTO赋值给EO
		PrivilegeEO privilegeEO = new PrivilegeEO();
		BeanCopierUtil.copy(privilegeDTO, privilegeEO);

		// 修改权限
		privilegeDao.update(privilegeEO);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByPKs(String... privilegeIds) {
		// 验证参数
		if (null != privilegeIds && privilegeIds.length > 0) {
			// 去除重复与空值
			Set<String> set = new HashSet<String>();
			for (String privilegeId : privilegeIds) {
				if (null != privilegeId && !privilegeId.trim().equals("")) {
					set.add(privilegeId);
				}
			}
			if (!set.isEmpty()) {
				String[] ids = new String[set.size()];
				set.toArray(ids);
				// 删除权限与角色的关系
				rolePrivilegeDao.deleteByPrivileges(ids);
				// 删除权限
				privilegeDao.deleteByPKs(ids);
			} else {
				throw new NullPointerException(
						PrivilegeMessages
								.getString("PRIVILEGE.PRIVILEGEID_IS_NULL"));
			}
		} else {
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_IS_NULL"));
		}
	}

	public List<PrivilegeDTO> queryAll() {

		// 查询全部的权限,new一个空对象传dao即可
		List<PrivilegeEO> privilegeEoList = privilegeDao
				.query(new PrivilegeEO());

		List<PrivilegeDTO> privilegeDtoList = new ArrayList<PrivilegeDTO>();
		// 权限列表非空，进行Eo到Dto的转换
		if (!listIsNullOrEmpty(privilegeEoList)) {
			privilegeDtoList = new ArrayList<PrivilegeDTO>();
			BeanCopierUtil.copy(privilegeEoList, privilegeDtoList,
					PrivilegeEO.class, PrivilegeDTO.class);
		}
		return privilegeDtoList;
	}

	public List<PrivilegeDTO> queryByPrivilege(PrivilegeDTO privilegeDTO) {
		if (null != privilegeDTO) {
			PrivilegeEO privilegeEO = new PrivilegeEO();
			BeanCopierUtil.copy(privilegeDTO, privilegeEO);
			List<PrivilegeEO> privilegeEoList = privilegeDao.query(privilegeEO);

			List<PrivilegeDTO> privilegeDtoList = new ArrayList<PrivilegeDTO>();
			// 权限列表非空，进行Eo到Dto的转换
			if (!listIsNullOrEmpty(privilegeEoList)) {
				privilegeDtoList = new ArrayList<PrivilegeDTO>();
				BeanCopierUtil.copy(privilegeEoList, privilegeDtoList,
						PrivilegeEO.class, PrivilegeDTO.class);
			}
			return privilegeDtoList;
		} else {// 传值非法
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEDTO_IS_NULL"));
		}
	}

	public PrivilegeDTO queryByPK(String privilegeId) {
		if (!isBlank(privilegeId)) {
			PrivilegeEO privilegeEO = new PrivilegeEO();
			privilegeEO.setPrivilegeId(privilegeId);
			List<PrivilegeEO> privilegeEoList = privilegeDao.query(privilegeEO);

			List<PrivilegeDTO> privilegeDtoList = null;
			// 权限列表非空，进行Eo到Dto的转换
			if (!listIsNullOrEmpty(privilegeEoList)) {
				privilegeDtoList = new ArrayList<PrivilegeDTO>();
				BeanCopierUtil.copy(privilegeEoList, privilegeDtoList,
						PrivilegeEO.class, PrivilegeDTO.class);
				return privilegeDtoList.get(0);
			}
			return null;
		} else {// 传值非法
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_IS_NULL"));
		}
	}

	public List<UserDTO> queryUsers(String privilegeId) {
		if (!isBlank(privilegeId)) {
			// 查询拥有权限的所有用户
			List<UserEO> userEoList = privilegeDao.queryUsers(privilegeId);
			List<UserDTO> userDtoList = new ArrayList<UserDTO>();
			// 用户列表非空，进行Eo到Dto的转换
			if (!listIsNullOrEmpty(userEoList)) {
				userDtoList = new ArrayList<UserDTO>();
				BeanCopierUtil.copy(userEoList, userDtoList, UserEO.class,
						UserDTO.class);
			}
			return userDtoList;
		} else {// 传值非法
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_IS_NULL"));
		}
	}

	public List<UserDTO> queryUsers(String privilegeId, int scopeType,
			String scopeId) {
		if (!isBlank(privilegeId)) {
			// 用户活动范围和用户范围ID的传参校验
			ValidatorUtil.validateScope(scopeType, scopeId);

			// 查询拥有权限并满足用户范围类型和用户范围ID下的所有用户
			List<UserEO> userEoList = privilegeDao.queryUsersByScope(
					privilegeId, scopeType + "", scopeId);
			List<UserDTO> userDtoList = new ArrayList<UserDTO>();
			// 用户列表非空，进行Eo到Dto的转换
			if (!listIsNullOrEmpty(userEoList)) {
				userDtoList = new ArrayList<UserDTO>();
				BeanCopierUtil.copy(userEoList, userDtoList, UserEO.class,
						UserDTO.class);
			}
			return userDtoList;
		} else {// 传值非法
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_IS_NULL"));
		}
	}

	public List<RoleDTO> queryRoles(String privilegeId) {
		if (!isBlank(privilegeId)) {
			PrivilegeEO privilegeEO = new PrivilegeEO();
			privilegeEO.setPrivilegeId(privilegeId);

			List<RoleEO> roleEoList = privilegeDao.queryRoles(privilegeEO);
			List<RoleDTO> roleDtoList = new ArrayList<RoleDTO>();
			// 角色列表非空，进行Eo到Dto的转换
			if (!listIsNullOrEmpty(roleEoList)) {
				roleDtoList = new ArrayList<RoleDTO>();
				BeanCopierUtil.copy(roleEoList, roleDtoList, RoleEO.class,
						RoleDTO.class);
			}
			return roleDtoList;
		} else {// 传值非法
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_IS_NULL"));
		}
	}

	public List<GroupDTO> queryGroups(String privilegeId) {
		if (!isBlank(privilegeId)) {
			PrivilegeEO privilegeEO = new PrivilegeEO();
			privilegeEO.setPrivilegeId(privilegeId);

			List<GroupEO> groupEoList = privilegeDao.queryGroups(privilegeEO);
			List<GroupDTO> groupDtoList = new ArrayList<GroupDTO>();
			// 用户组列表非空，进行Eo到Dto的转换
			if (!listIsNullOrEmpty(groupEoList)) {
				groupDtoList = new ArrayList<GroupDTO>();
				BeanCopierUtil.copy(groupEoList, groupDtoList, GroupEO.class,
						GroupDTO.class);
			}
			return groupDtoList;
		} else {// 传值非法
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignToRole(String privilegeId, String roleId) {

		// 验证角色授权参数与角色、权限的存在性
		assignToRoleValidata(privilegeId, roleId);

		RolePrivilegeEO rolePrivilegeEO = new RolePrivilegeEO();
		rolePrivilegeEO.setPrivilegeId(privilegeId);
		rolePrivilegeEO.setRoleId(roleId);
		// 为角色权限建立新的关联关系
		if (roleDao.hasPrivilege(roleId, privilegeId) == 0) {
			rolePrivilegeDao.create(rolePrivilegeEO);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignToRoles(String[] privilegeIds, String[] roleIds) {
		if (null != privilegeIds && null != roleIds) {
			int pCount = privilegeIds.length;
			int rCount = roleIds.length;
			List<RolePrivilegeEO> rolePrivilegeEOs = null;
			if (rCount > 0 && pCount > 0) {// 数组里的元素个数要都大于0
				// 逐个校验，循环调用domain方法验证每一对关系的合法性,并组装批量建立关系的对象
				rolePrivilegeEOs = new ArrayList<RolePrivilegeEO>();
				for (int i = 0; i < rCount; i++) {
					for (int j = 0; j < pCount; j++) {
						assignToRoleValidata(privilegeIds[j], roleIds[i]);
						RolePrivilegeEO rpeo = new RolePrivilegeEO();
						rpeo.setRoleId(roleIds[i]);
						rpeo.setPrivilegeId(privilegeIds[j]);
						// 做容错处理，只将未建立关联关系的角色权限对进行批量添加
						if (roleDao.hasPrivilege(roleIds[i], privilegeIds[j]) == 0) {
							rolePrivilegeEOs.add(rpeo);
						}
					}
				}

				// 进行批量关系的建立
				if (!listIsNullOrEmpty(rolePrivilegeEOs)) {
					rolePrivilegeDao.createBatch(rolePrivilegeEOs);
				}
			} else {// 传值非法
					// 修改错误信息
				throw new IllegalArgumentException(
						PrivilegeMessages
								.getString("PRIVILEGE.PRIVILEGEIDNUMBER_OR_ROLEIDNUMBER_IS_ZERO"));
			}
		} else {// 传值非法
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_OR_ROLEID_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setToRole(String privilegeId, String[] roleIds) {
		if (!isBlank(privilegeId) && null != roleIds) {
			List<RolePrivilegeEO> rolePrivilegeEOs = null;
			if (roleIds.length > 0) {
				rolePrivilegeEOs = new ArrayList<RolePrivilegeEO>();
				for (int i = 0; i < roleIds.length; i++) {
					assignToRoleValidata(privilegeId, roleIds[i]);
					RolePrivilegeEO rpeo = new RolePrivilegeEO();
					rpeo.setRoleId(roleIds[i]);
					rpeo.setPrivilegeId(privilegeId);
					rolePrivilegeEOs.add(rpeo);
				}
				// 进行批量关系的建立

				// 先删除原有关系
				rolePrivilegeDao.deleteByPrivilege(privilegeId);
				// 批量建立角色与权限的关系
				rolePrivilegeDao.createBatch(rolePrivilegeEOs);
			} else {// 参数非法
				throw new IllegalArgumentException(
						PrivilegeMessages
								.getString("PRIVILEGE.NO_VALUE_IN_ROLEID_ARRAY"));
			}
		} else {// 参数非法
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_OR_ROLEID_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setToRoles(String[] privilegeIds, String[] roleIds) {
		if (null != privilegeIds && null != roleIds) {
			int pCount = privilegeIds.length;
			int rCount = roleIds.length;
			List<RolePrivilegeEO> rolePrivilegeEOs = null;
			if (rCount > 0 && pCount > 0) {// 数组里的元素个数要都大于0
				// 逐个校验，循环调用domain方法验证每一对关系的合法性,并组装批量建立关系的对象
				rolePrivilegeEOs = new ArrayList<RolePrivilegeEO>();
				for (int i = 0; i < pCount; i++) {
					for (int j = 0; j < rCount; j++) {
						assignToRoleValidata(privilegeIds[i], roleIds[j]);

						RolePrivilegeEO rpeo = new RolePrivilegeEO();
						rpeo.setRoleId(roleIds[j]);
						rpeo.setPrivilegeId(privilegeIds[i]);
						rolePrivilegeEOs.add(rpeo);
					}
				}
				// 先删除原有关系
				rolePrivilegeDao.deleteByPrivileges(privilegeIds);

				// 批量建立角色与权限的关系
				rolePrivilegeDao.createBatch(rolePrivilegeEOs);
			} else {// 传值非法
				throw new IllegalArgumentException(
						PrivilegeMessages
								.getString("PRIVILEGE.PRIVILEGEIDNUMBER_OR_ROLEIDNUMBER_IS_ZERO"));
			}
		} else {// 传值非法
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_OR_ROLEID_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeFromAllRoles(String... privilegeIds) {
		if (null != privilegeIds && privilegeIds.length > 0) {
			for (int i = 0; i < privilegeIds.length; i++) {
				if (isBlank(privilegeIds[i])) {
					throw new NullPointerException(
							PrivilegeMessages
									.getString("PRIVILEGE.PRIVILEGEID_IS_NULL"));
				}
			}
			rolePrivilegeDao.deleteByPrivileges(privilegeIds);
		} else {// 传值非法
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeFromRole(String privilegeId, String roleId) {
		if (!isBlank(privilegeId) && !isBlank(roleId)) {
			rolePrivilegeDao.deleteByPrivilegeIdAndRoleId(privilegeId, roleId);
		} else {// 传值非法
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_OR_ROLEID_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeFromRoles(String[] privilegeIds, String[] roleIds) {
		if (null != privilegeIds && null != roleIds) {
			int pCount = privilegeIds.length;
			int rCount = roleIds.length;
			if (rCount > 0 && pCount > 0) {// 数组里的元素个数要相等且都大于0
				// 逐个校验，循环调用domain方法验证每一对关系的合法性,然后删除关联关系
				for (int i = 0; i < pCount; i++) {
					for (int j = 0; j < rCount; j++) {
						assignToRoleValidata(privilegeIds[i], roleIds[j]);
						// 删除角色权限对应关系
						rolePrivilegeDao.deleteByPrivilegeIdAndRoleId(
								privilegeIds[i], roleIds[j]);
					}
				}

			} else {// 传值非法
				throw new IllegalArgumentException(
						PrivilegeMessages
								.getString("PRIVILEGE.PRIVILEGEIDNUMBER_OR_ROLEIDNUMBER_IS_ZERO"));
			}
		} else {// 传值非法
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_OR_ROLEID_IS_NULL"));
		}
	}

	public boolean isAssignedToRole(String privilegeId, String roleId) {
		if (!isBlank(privilegeId) && !isBlank(roleId)) {
			return roleDao.hasPrivilege(roleId, privilegeId) > 0;
		} else {// 传值非法
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_OR_ROLEID_IS_NULL"));
		}
	}

	public boolean existsByPrivilegeCode(String privilegeCode) {
		if (isBlank(privilegeCode)) {
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGECODE_IS_NULL"));
		}
		return privilegeDao.existsByCode(privilegeCode) > 0;
	}

	public boolean existsByPrivilegeCodeIgnorePrivilegeId(String privilegeCode,
			String privilegeId) {
		if (isBlank(privilegeCode)) {
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGECODE_IS_NULL"));
		}
		if (isBlank(privilegeId)) {
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_IS_NULL"));
		}
		return privilegeDao.existsByCodeIgnoreId(privilegeCode, privilegeId) > 0;
	}

	/**
	 * 验证权限创建时权限各参数的合法性
	 * 
	 * @param privilegeDto
	 *            权限传输对象
	 */
	private void createValidata(PrivilegeDTO privilegeDto) {
		if (null != privilegeDto) {
			// 权限编码不能为空
			if (isBlank(privilegeDto.getPrivilegeCode())) {
				throw new NullPointerException(
						PrivilegeMessages
								.getString("PRIVILEGE.PRIVILEGECODE_IS_NULL"));
			}
			if (privilegeDao.existsByCode(privilegeDto.getPrivilegeCode()) > 0) {
				// 权限编码已经存在
				throw new IllegalArgumentException(
						PrivilegeMessages
								.getString("PRIVILEGE.PRIVILEGECODE_IS_EXISTS"));
			}
			if (isBlank(privilegeDto.getPrivilegeName())) {
				throw new NullPointerException(
						PrivilegeMessages
								.getString("PRIVILEGE.PRIVILEGENAME_IS_NULL"));
			}
			if (isBlank(privilegeDto.getParentId())) {
				throw new NullPointerException(
						PrivilegeMessages
								.getString("PRIVILEGE.PARENTID_IS_NULL"));
			}
			if (isBlank(privilegeDto.getType())) {
				throw new NullPointerException(
						PrivilegeMessages.getString("PRIVILEGE.TYPE_IS_NULL"));
			} else if (!"1".equals(privilegeDto.getType())
					&& !"2".equals(privilegeDto.getType())
					&& !"3".equals(privilegeDto.getType())
					&& !"4".equals(privilegeDto.getType())
					&& !"5".equals(privilegeDto.getType())
					&& !"6".equals(privilegeDto.getType())
					&& !"9".equals(privilegeDto.getType())) {
				throw new IllegalArgumentException(
						PrivilegeMessages
								.getString("PRIVILEGE.TYPE_IS_NOT_EXISTS"));
			}
		} else {// 参数非法
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEDTO_IS_NULL"));
		}
	}

	/**
	 * 验证权限更新时权限各参数的合法性
	 * 
	 * @param privilegeDto
	 *            权限传输对象
	 */
	private void updateValidata(PrivilegeDTO privilegeDto) {
		if (null != privilegeDto) {
			// 权限ID不能为空
			if (isBlank(privilegeDto.getPrivilegeId())) {
				throw new NullPointerException(
						PrivilegeMessages
								.getString("PRIVILEGE.PRIVILEGEID_IS_NULL"));
			}
			if (isBlank(privilegeDto.getPrivilegeName())) {
				privilegeDto.setPrivilegeName(null);
			}
		} else {// 权限编码不能为空
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEDTO_IS_NULL"));
		}
	}

	/**
	 * 验证授权时角色和权限的合法性
	 * 
	 * @param privilegeId
	 *            权限ID
	 * @param roleId
	 *            角色ID
	 */
	private void assignToRoleValidata(String privilegeId, String roleId) {
		if (!isBlank(privilegeId) && !isBlank(roleId)) {
			// 判断权限ID是否存在，存在才算验证通过，让建立关联关系
			PrivilegeEO eo = new PrivilegeEO();
			eo.setPrivilegeId(privilegeId);
			List<PrivilegeEO> privilegeList = privilegeDao.query(eo);
			if (listIsNullOrEmpty(privilegeList)) {
				throw new IllegalArgumentException(
						PrivilegeMessages
								.getString("PRIVILEGE.PRIVILEGEID_IS_NOT_EXISTS"));
			}

			// 判断角色ID是否存在，存在才算验证通过，让建立关联关系
			RoleEO roleEo = roleDao.queryByPK(roleId);
			if (null == roleEo) {
				throw new IllegalArgumentException(
						RoleMessages.getString("ROLE.ROLEID_IS_NOT_EXISTS"));
			}
		} else {
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_OR_ROLEID_IS_NULL"));
		}

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

	/**
	 * 判断List是否为null或size=0
	 * 
	 * @param collect
	 *            被判断的list
	 * @return List是否为null或者empty 是：true 否：false
	 */
	private boolean listIsNullOrEmpty(List collect) {
		return (null == collect || collect.isEmpty());
	}
}
