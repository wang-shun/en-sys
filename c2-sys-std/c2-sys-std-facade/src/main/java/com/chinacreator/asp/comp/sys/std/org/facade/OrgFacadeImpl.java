package com.chinacreator.asp.comp.sys.std.org.facade;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.advanced.job.dto.JobDTO;
import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.basic.OrgMessages;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.std.job.facade.JobFacade;
import com.chinacreator.asp.comp.sys.std.user.facade.UserFacade;

@Component
public class OrgFacadeImpl implements OrgFacade {

	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.advanced.org.service.OrgServiceImpl")
	private OrgService orgService;

	@Autowired
	private JobFacade jobFacade;

	@Autowired
	private UserFacade userFacade;

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
			List<OrgDTO> orgDTOList = orgService.queryChildOrgs(orgId, true);
			for (OrgDTO orgDTO : orgDTOList) {
				delOrgIdSet.add(orgDTO.getOrgId());
			}
		}

		Set<String> jobIdSet = new HashSet<String>();
		for (String orgId : delOrgIdSet) {
			List<JobDTO> jobDTOList = orgService.queryJobByOrgId(orgId);
			for (JobDTO jobDTO : jobDTOList) {
				jobIdSet.add(jobDTO.getJobId());
			}
		}

		if (!jobIdSet.isEmpty()) {
			jobFacade
					.deleteByPKs(jobIdSet.toArray(new String[jobIdSet.size()]));
		}

		orgService.deleteByPKs(orgIds);
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
