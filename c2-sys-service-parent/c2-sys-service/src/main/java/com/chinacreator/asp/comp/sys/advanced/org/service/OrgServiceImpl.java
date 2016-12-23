package com.chinacreator.asp.comp.sys.advanced.org.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.advanced.job.dto.JobDTO;
import com.chinacreator.asp.comp.sys.advanced.job.service.JobService;
import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.OrgMessages;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.common.CommonConstants;

/**
 * 机构服务接口实现类
 * 
 * @author 杨祎程
 * 
 */
@Service
public class OrgServiceImpl extends
		com.chinacreator.asp.comp.sys.basic.org.service.OrgServiceImpl
		implements OrgService {

	@Autowired
	private JobService jobService;

	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.advanced.user.service.UserServiceImpl")
	private UserService userService;

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
		Set<String> jobIdSet = new HashSet<String>();
		for (String orgId : delOrgIdSet) {
			List<JobDTO> jobDTOList = queryJobByOrgId(orgId);
			for (JobDTO jobDTO : jobDTOList) {
				jobIdSet.add(jobDTO.getJobId());
			}
		}

		if (!jobIdSet.isEmpty()) {
			jobService
					.deleteByPKs(jobIdSet.toArray(new String[jobIdSet.size()]));
		}

		// 删除用户信息
		userService.deleteAllByOrg(orgIds);

		super.deleteByPKs(orgIds);
	}

	public OrgDTO queryByJobId(String jobId) {
		return jobService.queryOrgByJobId(jobId);
	}

	public List<JobDTO> queryJobByOrgId(String orgId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		return jobService.queryByOrg(new JobDTO(), orgId);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addJobs(String orgId, String... jobIds) {
		addJobs(new String[] { orgId }, jobIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addJobs(String[] orgIds, String[] jobIds) {
		jobService.addToOrgs(jobIds, orgIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setJobs(String orgId, String... jobIds) {
		setJobs(new String[] { orgId }, jobIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setJobs(String[] orgIds, String[] jobIds) {
		jobService.setToOrgs(jobIds, orgIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeAllJobs(String... orgIds) {
		if (null == orgIds || orgIds.length == 0) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_ARRAY_IS_NULL"));
		}
		for (String orgId : orgIds) {
			if (isBlank(orgId)) {
				throw new NullPointerException(
						OrgMessages.getString("ORG.ORG_ID_ARRAY_HAS_NULL_ITEM"));
			} else {
				Set<String> jobIdSet = new HashSet<String>();
				List<JobDTO> jobDTOs = jobService.queryByOrg(new JobDTO(),
						orgId);
				if (null != jobDTOs && !jobDTOs.isEmpty()) {
					for (JobDTO jobDTO : jobDTOs) {
						jobIdSet.add(jobDTO.getJobId());
					}
					jobService.deleteByPKs(jobIdSet.toArray(new String[jobIdSet
							.size()]));
				}
			}
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeJobs(String orgId, String... jobIds) {
		removeJobs(new String[] { orgId }, jobIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeJobs(String[] orgIds, String[] jobIds) {
		jobService.removeFromOrgs(jobIds, orgIds);
	}

	public boolean containsJob(String orgId, String jobId) {
		return jobService.belongsToOrg(jobId, orgId);
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
