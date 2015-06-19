package com.chinacreator.asp.comp.sys.advanced.role.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.advanced.job.service.JobService;
import com.chinacreator.asp.comp.sys.basic.RoleMessages;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.core.group.dto.GroupDTO;
import com.chinacreator.asp.comp.sys.core.group.service.GroupService;

/**
 * 角色服务接口实现类
 * 
 * @author 杨祎程
 * 
 */
@Service
public class RoleServiceImpl extends
		com.chinacreator.asp.comp.sys.basic.role.service.RoleServiceImpl
		implements RoleService {

	@Autowired
	private JobService jobService;

	@Autowired
	private GroupService groupService;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignToJob(String roleId, String... jobIds) {
		assignToJob(new String[] { roleId }, jobIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignToJob(String[] roleIds, String[] jobIds) {
		jobService.assignRoles(jobIds, roleIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setToJob(String roleId, String... jobIds) {
		setToJob(new String[] { roleId }, jobIds);

	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setToJob(String[] roleIds, String[] jobIds) {
		jobService.setRoles(jobIds, roleIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeFromAllJobs(String... roleIds) {
		if (null == roleIds || roleIds.length == 0) {
			throw new NullPointerException(
					RoleMessages.getString("ROLE.ROLETYPE_ID_ARRAY_IS_NULL"));
		}
		for (String roleId : roleIds) {
			if (isBlank(roleId)) {
				throw new NullPointerException(
						RoleMessages
								.getString("ROLE.ROLEID_IS_NULL_EMPTY_BLANK"));
			}
		}
		// 查询所有的岗位用户组
		GroupDTO groupDTO = new GroupDTO();
		groupDTO.setType("2");
		List<GroupDTO> groupDTOList = groupService.queryByGroup(groupDTO);
		List<String> groupIdList = new ArrayList<String>();
		if (null != groupDTOList && !groupDTOList.isEmpty()) {
			for (GroupDTO dto : groupDTOList) {
				groupIdList.add(dto.getGroupId());
			}
		}
		String[] groupIds = groupIdList.toArray(new String[groupIdList.size()]);
		groupService.revokeRoles(groupIds, roleIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeFromJobs(String roleId, String... jobIds) {
		revokeFromJobs(new String[] { roleId }, jobIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeFromJobs(String[] roleIds, String[] jobIds) {
		jobService.revokeRoles(jobIds, roleIds);
	}

	public boolean isAssignedToJob(String roleId, String jobId) {
		return jobService.hasRole(jobId, roleId);
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

}
