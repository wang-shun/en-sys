package com.chinacreator.asp.comp.sys.std.job.facade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.advanced.JobMessages;
import com.chinacreator.asp.comp.sys.advanced.job.dao.JobDao;
import com.chinacreator.asp.comp.sys.advanced.job.dto.JobDTO;
import com.chinacreator.asp.comp.sys.advanced.job.service.JobService;
import com.chinacreator.asp.comp.sys.advanced.role.service.RoleService;
import com.chinacreator.asp.comp.sys.basic.OrgMessages;
import com.chinacreator.asp.comp.sys.basic.org.eo.OrgEO;
import com.chinacreator.asp.comp.sys.basic.role.service.RoleTypeService;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.CommonPropertiesUtil;
import com.chinacreator.asp.comp.sys.core.PrivilegeMessages;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.role.dao.RoleDao;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.role.eo.RoleEO;

@Component
public class JobFacadeImpl implements JobFacade {

	@Autowired
	private JobService jobService;

	@Autowired
	private JobDao jobDao;

	@Autowired
	private RoleDao roleCoreDao;

	@Autowired
	private RoleTypeService roleTypeService;

	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.advanced.role.service.RoleServiceImpl")
	private RoleService roleService;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void create(JobDTO jobDTO, String orgId) {
		// 验证数据的合法性
		if (null == jobDTO) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_DTO_IS_NULL"));
		}
		if (null == jobDTO.getJobScope()) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_SCOPE_IS_NULL"));
		} else {
			if ("1".equals(jobDTO.getJobScope()) && isBlank(orgId)) {
				throw new NullPointerException(
						OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
			}
		}
		if (isBlank(jobDTO.getJobName())) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_NAME_IS_NULL"));
		}
		if ("0".equals(jobDTO.getJobScope())
				&& jobDao.existsJobNameByCommon(jobDTO.getJobName()) > 0) {
			throw new IllegalArgumentException(
					JobMessages.getString("JOB.JOB_NAME_IS_EXISTS"));
		} else if ("1".equals(jobDTO.getJobScope())
				&& jobDao.existsJobNameByOrg(jobDTO.getJobName(), orgId) > 0) {
			throw new IllegalArgumentException(
					JobMessages.getString("JOB.JOB_NAME_IS_EXISTS"));
		}

		// 创建好岗位、与用户组关联、用户组
		jobService.create(jobDTO);

		if ("1".equals(jobDTO.getJobScope())) {
			// 建立岗位与机构之间的关联关系
			jobService.addToOrgs(jobDTO.getJobId(), orgId);
		}

		// 创建匿名角色
		RoleDTO roleDTO = new RoleDTO();

		roleDTO.setOwnerId(jobDTO.getOwnerId());
		roleDTO.setRoleDesc("岗位【" + jobDTO.getJobName() + "】对应的匿名角色");
		roleDTO.setRoleType(getAnonymousRoleTypeId());
		roleDTO.setRoleUsage(true);
		roleService.createAnonymousRole(roleDTO);

		// 得到匿名角色的角色ID
		String anonymousRoleId = roleDTO.getRoleId();

		// 将岗位与匿名角色建立关系
		jobService.assignRoles(jobDTO.getJobId(),
				new String[] { anonymousRoleId });
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void update(JobDTO jobDTO) {
		// 验证数据合法性
		if (null == jobDTO) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_DTO_IS_NULL"));
		}
		if (isBlank(jobDTO.getJobId())) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_IS_NULL"));
		}
		if (isBlank(jobDTO.getJobName())) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_NAME_IS_NULL"));
		}
		if ("0".equals(jobDTO.getJobScope())
				&& jobDao.existsJobNameByCommonIgnoreJobID(jobDTO.getJobName(),
						jobDTO.getJobId()) > 0) {
			throw new IllegalArgumentException(
					JobMessages.getString("JOB.JOB_NAME_IS_EXISTS"));
		} else if ("1".equals(jobDTO.getJobScope())) {
			OrgEO orgEO = jobDao.queryOrgByJobId(jobDTO.getJobId());
			if (jobDao.existsJobNameByOrgIgnoreJobID(jobDTO.getJobName(),
					orgEO.getOrgId(), jobDTO.getJobId()) > 0) {
				throw new IllegalArgumentException(
						JobMessages.getString("JOB.JOB_NAME_IS_EXISTS"));
			}
		}
		jobService.update(jobDTO);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByPKs(String... jobIds) {
		// 验证数据的合法性
		if (null == jobIds || jobIds.length == 0) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_ARRAY_IS_NULL"));
		}
		List<String> anonymousRoleIdList = new ArrayList<String>();
		for (String jobId : jobIds) {
			if (isBlank(jobId)) {
				throw new NullPointerException(
						JobMessages.getString("JOB.JOB_ID_ARRAY_HAS_NULL_ITEM"));
			}

			// 查询岗位所在用户组对应的匿名角色，在角色表中删掉
			RoleEO roleEO = jobDao.queryAnonymousRoles(jobId,
					getAnonymousRoleTypeId());

			if (null != roleEO) {
				anonymousRoleIdList.add(roleEO.getRoleId());
			}
		}

		jobService.deleteByPKs(jobIds);

		// 根据匿名角色ID删除匿名角色
		if (!anonymousRoleIdList.isEmpty()) {
			roleCoreDao.deleteByPKs(anonymousRoleIdList
					.toArray(new String[anonymousRoleIdList.size()]));
		}
	}

	public List<PrivilegeDTO> queryPrivilegeByJobs(String... jobIds) {
		if (null == jobIds || jobIds.length == 0) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_ARRAY_IS_NULL"));
		}
		for (String jobId : jobIds) {
			if (isBlank(jobId)) {
				throw new NullPointerException(
						JobMessages.getString("JOB.JOB_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		Set<String> roleIdSet = new HashSet<String>();
		for (String jobId : jobIds) {
			if (getRoleofeveryoneJobId().equals(jobId)) {
				roleIdSet.add(roleService.getRoleofeveryoneRoleId());
			} else {
				// 查询岗位的匿名角色ID
				RoleEO roleEO = jobDao.queryAnonymousRoles(jobId,
						getAnonymousRoleTypeId());
				if (null != roleEO) {
					roleIdSet.add(roleEO.getRoleId());
				}
			}
		}
		List<PrivilegeDTO> privilegeDTOList = new ArrayList<PrivilegeDTO>();
		Set<String> privilegeIdSet = new HashSet<String>();
		for (String roleId : roleIdSet) {
			List<PrivilegeDTO> privilegeDTOs = roleService
					.queryPrivileges(roleId);
			for (PrivilegeDTO privilegeDTO : privilegeDTOs) {
				if (privilegeIdSet.add(privilegeDTO.getPrivilegeId())) {
					privilegeDTOList.add(privilegeDTO);
				}
			}
		}

		return privilegeDTOList;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignPrivilege(String jobId, String... privilegeIds) {
		if (isBlank(jobId)) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_IS_NULL"));
		}
		assignPrivilege(new String[] { jobId }, privilegeIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignPrivilege(String[] jobIds, String[] privilegeIds) {
		if (null == jobIds || jobIds.length == 0) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_ARRAY_IS_NULL"));
		}
		if (null == privilegeIds || privilegeIds.length == 0) {
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_IS_NULL"));
		}
		for (String jobId : jobIds) {
			if (isBlank(jobId)) {
				throw new NullPointerException(
						JobMessages.getString("JOB.JOB_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		for (String privilegeId : privilegeIds) {
			if (isBlank(privilegeId)) {
				throw new NullPointerException(
						PrivilegeMessages
								.getString("PRIVILEGE.PRIVILEGEID_IS_NULL"));
			}
		}
		String roleId = null;
		for (String jobId : jobIds) {
			if (getRoleofeveryoneJobId().equals(jobId)) {
				roleId = roleService.getRoleofeveryoneRoleId();
			} else {
				// 查询岗位的匿名角色ID，并将匿名角色与权限关联
				RoleEO roleEO = jobDao.queryAnonymousRoles(jobId,
						getAnonymousRoleTypeId());
				if (null != roleEO) {
					roleId = roleEO.getRoleId();
				} else {
					roleId = null;
				}
			}
			// 获取到一个匿名角色，将权限与匿名角色关联
			if (null != roleId) {
				roleService.assignPrivileges(new String[] { roleId },
						privilegeIds);
			}
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setPrivileges(String jobId, String... privilegeIds) {
		if (isBlank(jobId)) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_IS_NULL"));
		}
		setPrivileges(new String[] { jobId }, privilegeIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setPrivileges(String[] jobIds, String[] privilegeIds) {
		if (null == jobIds || jobIds.length == 0) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_ARRAY_IS_NULL"));
		}
		if (null == privilegeIds || privilegeIds.length == 0) {
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_IS_NULL"));
		}
		for (String jobId : jobIds) {
			if (isBlank(jobId)) {
				throw new NullPointerException(
						JobMessages.getString("JOB.JOB_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		String roleId = null;
		for (String jobId : jobIds) {
			if (getRoleofeveryoneJobId().equals(jobId)) {
				roleId = roleService.getRoleofeveryoneRoleId();
			} else {
				// 查询岗位的匿名角色ID，并将匿名角色与权限关联
				RoleEO roleEO = jobDao.queryAnonymousRoles(jobId,
						getAnonymousRoleTypeId());
				if (null != roleEO) {
					roleId = roleEO.getRoleId();
				} else {
					roleId = null;
				}
			}
			// 获取到一个匿名角色，将权限与匿名角色关联
			if (null != roleId) {
				roleService.setPrivileges(roleId, privilegeIds);
			}
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeAllPrivileges(String... jobIds) {
		if (null == jobIds || jobIds.length == 0) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_ARRAY_IS_NULL"));
		}
		Set<String> roleIdSet = new HashSet<String>();
		for (String jobId : jobIds) {
			if (isBlank(jobId)) {
				throw new NullPointerException(
						JobMessages.getString("JOB.JOB_ID_ARRAY_HAS_NULL_ITEM"));
			}

			if (getRoleofeveryoneJobId().equals(jobId)) {
				roleIdSet.add(roleService.getRoleofeveryoneRoleId());
			} else {
				// 查询岗位的匿名角色ID，并将匿名角色与菜单关联
				RoleEO roleEO = jobDao.queryAnonymousRoles(jobId,
						getAnonymousRoleTypeId());
				if (null != roleEO) {
					roleIdSet.add(roleEO.getRoleId());
				}
			}
		}
		if (!roleIdSet.isEmpty()) {
			roleService.revokeAllPrivileges(roleIdSet
					.toArray(new String[roleIdSet.size()]));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokePrivileges(String jobId, String... privilegeIds) {
		if (isBlank(jobId)) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_IS_NULL"));
		}
		revokePrivileges(new String[] { jobId }, privilegeIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokePrivileges(String[] jobIds, String[] privilegeIds) {
		if (null == jobIds || jobIds.length == 0) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_ARRAY_IS_NULL"));
		}
		if (null == privilegeIds || privilegeIds.length == 0) {
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_IS_NULL"));
		}
		for (String jobId : jobIds) {
			if (isBlank(jobId)) {
				throw new NullPointerException(
						JobMessages.getString("JOB.JOB_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		String roleId = null;
		for (String jobId : jobIds) {
			if (getRoleofeveryoneJobId().equals(jobId)) {
				roleId = roleService.getRoleofeveryoneRoleId();
			} else {
				// 查询岗位的匿名角色ID，并将匿名角色与权限关联
				RoleEO roleEO = jobDao.queryAnonymousRoles(jobId,
						getAnonymousRoleTypeId());
				if (null != roleEO) {
					roleId = roleEO.getRoleId();
				} else {
					roleId = null;
				}
			}
			if (null != roleId) {
				roleService.revokePrivileges(new String[] { roleId },
						privilegeIds);
			}
		}

	}

	public boolean hasPrivilege(String jobId, String privilegeId) {
		if (isBlank(jobId)) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_IS_NULL"));
		}
		if (isBlank(privilegeId)) {
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEID_IS_NULL"));
		}
		String roleId = null;
		if (getRoleofeveryoneJobId().equals(jobId)) {
			roleId = roleService.getRoleofeveryoneRoleId();
		} else {
			// 查询岗位的匿名角色ID
			RoleEO roleEO = jobDao.queryAnonymousRoles(jobId,
					getAnonymousRoleTypeId());
			if (null != roleEO) {
				roleId = roleEO.getRoleId();
			}
		}
		if (null != roleId && !roleId.trim().equals("")) {
			return roleService.hasPrivilege(roleId, privilegeId);
		}
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

	/**
	 * 获取匿名角色类型ID
	 * 
	 * @return 匿名角色类型ID
	 */
	private String getAnonymousRoleTypeId() {
		return roleTypeService.getAnonymousRoleTypeId();
	}

	public String getAdministratorJobId() {
		return CommonPropertiesUtil.getAdministratorJobId();
	}

	public String getRoleofeveryoneJobId() {
		return CommonPropertiesUtil.getRoleofeveryoneJobId();
	}

	public boolean existsJobNameByCommon(String jobName) {
		if (isBlank(jobName)) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_NAME_IS_NULL"));
		}
		return jobDao.existsJobNameByCommon(jobName) > 0;
	}

	public boolean existsJobNameByCommonIgnoreJobID(String jobName, String jobId) {
		if (isBlank(jobName)) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_NAME_IS_NULL"));
		}
		if (isBlank(jobId)) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_IS_NULL"));
		}
		return jobDao.existsJobNameByCommonIgnoreJobID(jobName, jobId) > 0;
	}

	public boolean existsJobNameByOrg(String jobName, String orgId) {
		if (isBlank(jobName)) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_NAME_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		return jobDao.existsJobNameByOrg(jobName, orgId) > 0;
	}

	public boolean existsJobNameByOrgIgnoreJobID(String jobName, String orgId,
			String jobId) {
		if (isBlank(jobName)) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_NAME_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		if (isBlank(jobId)) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_IS_NULL"));
		}
		return jobDao.existsJobNameByOrgIgnoreJobID(jobName, orgId, jobId) > 0;
	}
}
