package com.chinacreator.asp.sysmgmt.sysset.usermgt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chinacreator.asp.comp.sys.advanced.job.dto.JobDTO;
import com.chinacreator.asp.comp.sys.advanced.job.service.JobService;
import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.common.CommonPropertiesUtil;
import com.chinacreator.asp.sysmgmt.common.CommonTreeNode;
import com.chinacreator.asp.sysmgmt.sysset.jobmenumgt.JobMenuMgtMessages;
import com.chinacreator.asp.sysmgmt.sysset.orgusermgt.OrgUserMgtMessages;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;

public class JobSelectTreeContentProviderImpl implements TreeContentProvider {

	private OrgService orgService = ApplicationContextManager.getContext()
			.getBean(OrgService.class);

	private JobService jobService = ApplicationContextManager.getContext()
			.getBean(JobService.class);

	@Override
	public TreeNode[] getElements(TreeContext context) {
		List<CommonTreeNode> list = new ArrayList<CommonTreeNode>();
		if (null != context) {
			Map<String, Object> map = context.getConditions();
			String orgId = (String) map.get("id");
			String searchOrgId = (String) map.get("searchOrgId");
			String searchJobId = (String) map.get("searchJobId");
			if (null == orgId || orgId.trim().equals("")) {
				List<JobDTO> jobDTOs = jobService
						.queryByOrg(new JobDTO(), null);
				CommonTreeNode jobOrgTreeNode = new CommonTreeNode();
				jobOrgTreeNode.setId("-1");
				jobOrgTreeNode.setName(JobMenuMgtMessages
						.getString("JOBMENUMGT.JOBORG_ROOT_TREENAME"));
				jobOrgTreeNode.setShowName(JobMenuMgtMessages
						.getString("JOBMENUMGT.JOBORG_ROOT_TREENAME"));
				jobOrgTreeNode.setPid(null);
				jobOrgTreeNode.setParent(!jobDTOs.isEmpty());
				jobOrgTreeNode.setChkDisabled(true);
				jobOrgTreeNode.setNocheck(true);
				jobOrgTreeNode.setHiddenName("org");
				list.add(jobOrgTreeNode);

				for (JobDTO jobDTO : jobDTOs) {
					if (CommonPropertiesUtil.getRoleofeveryoneJobId().equals(
							jobDTO.getJobId())) {
						continue;
					}
					CommonTreeNode jobOrgJobTreeNode = new CommonTreeNode();
					jobOrgJobTreeNode.setId(jobDTO.getJobId());
					jobOrgJobTreeNode.setName(jobDTO.getJobName());
					jobOrgJobTreeNode.setShowName(jobDTO.getJobName());
					jobOrgJobTreeNode
							.setHiddenName(String.format(
									OrgUserMgtMessages
											.getString("ORGUSERMGT.ORGJOBTREE_HIDDENNAME_TEMPLATE"),
									JobMenuMgtMessages
											.getString("JOBMENUMGT.JOBORG_ROOT_TREENAME"),
									jobDTO.getJobName()));
					jobOrgJobTreeNode.setPid("-1");
					jobOrgJobTreeNode.setParent(false);
					jobOrgJobTreeNode.setHiddenName("job");

					list.add(jobOrgJobTreeNode);
				}

				CommonTreeNode rootOrgTreeNode = new CommonTreeNode();
				rootOrgTreeNode.setId("0");
				rootOrgTreeNode.setName(JobMenuMgtMessages
						.getString("JOBMENUMGT.ORG_ROOT_TREENAME"));
				rootOrgTreeNode.setShowName(JobMenuMgtMessages
						.getString("JOBMENUMGT.ORG_ROOT_TREENAME"));
				rootOrgTreeNode.setPid(null);
				rootOrgTreeNode.setParent(orgService.existsChildOrgs("0"));
				rootOrgTreeNode.setChkDisabled(true);
				rootOrgTreeNode.setNocheck(true);
				rootOrgTreeNode.setHiddenName("org");
				list.add(rootOrgTreeNode);

				orgId = "0";
			}

			Set<String> orgIdSet = new HashSet<String>();
			Set<String> jobIdSet = new HashSet<String>();
			if ("0".equals(orgId) && null != searchJobId
					&& !searchJobId.trim().equals("")
					&& !searchJobId.trim().equals("0")) {
				JobDTO job = jobService.queryByPK(searchJobId);
				if (null != job && job.getJobScope().equals("1")) {
					OrgDTO orgJobDTO = jobService.queryOrgByJobId(searchJobId);
					if (null != orgJobDTO) {

						List<JobDTO> jobDTOs = jobService.queryByOrg(
								new JobDTO(), orgJobDTO.getOrgId());
						for (JobDTO jobDTO : jobDTOs) {
							CommonTreeNode orgJobTreeNode = new CommonTreeNode();
							orgJobTreeNode.setId(jobDTO.getJobId());
							orgJobTreeNode.setName(jobDTO.getJobName());
							orgJobTreeNode.setShowName(jobDTO.getJobName());
							orgJobTreeNode.setPid(orgJobDTO.getOrgId());
							orgJobTreeNode.setParent(false);
							orgJobTreeNode.setChkDisabled(false);
							orgJobTreeNode.setHiddenName("job");

							list.add(orgJobTreeNode);
							jobIdSet.add(jobDTO.getJobId());
						}

						List<OrgDTO> parentOrgs = orgService.queryFatherOrgs(
								orgJobDTO.getOrgId(), true);
						for (OrgDTO orgDTO : parentOrgs) {
							CommonTreeNode orgTreeNode = new CommonTreeNode();
							orgTreeNode.setId(orgDTO.getOrgId());
							orgTreeNode.setShowName(orgDTO.getOrgShowName());
							orgTreeNode.setName(orgDTO.getOrgShowName());
							orgTreeNode.setPid(orgDTO.getParentId());
							orgTreeNode.setParent(orgService
									.existsChildOrgs(orgDTO.getOrgId())
									|| !jobService.queryByOrg(new JobDTO(),
											orgDTO.getOrgId()).isEmpty());
							orgTreeNode.setChkDisabled(true);
							orgTreeNode.setChecked(false);
							orgTreeNode.setNocheck(true);
							orgTreeNode.setHiddenName("org");

							list.add(orgTreeNode);
							orgIdSet.add(orgDTO.getOrgId());
						}

					}
				}
			}

			List<OrgDTO> orgDTOs = orgService.queryChildOrgs(orgId, false);
			List<JobDTO> jobDTOs = new ArrayList<JobDTO>();
			Set<String> fatherOrgIdSet = null;
			if (null == searchOrgId || searchOrgId.trim().equals("")
					|| searchOrgId.trim().equals("0")) {
				jobDTOs = jobService.queryByOrg(new JobDTO(), orgId);
			} else {
				fatherOrgIdSet = getFatherOrgIdSet(searchOrgId);

				if (searchOrgId.equals(orgId)) {
					jobDTOs = jobService.queryByOrg(new JobDTO(), orgId);
				}
			}

			for (JobDTO jobDTO : jobDTOs) {
				if (!jobIdSet.contains(jobDTO.getJobId())) {
					CommonTreeNode orgJobTreeNode = new CommonTreeNode();
					orgJobTreeNode.setId(jobDTO.getJobId());
					orgJobTreeNode.setName(jobDTO.getJobName());
					orgJobTreeNode.setShowName(jobDTO.getJobName());
					orgJobTreeNode.setPid(orgId);
					orgJobTreeNode.setParent(false);
					orgJobTreeNode.setChkDisabled(false);
					orgJobTreeNode.setHiddenName("job");

					list.add(orgJobTreeNode);
				}
			}

			for (OrgDTO orgDTO : orgDTOs) {
				if (null != fatherOrgIdSet
						&& !fatherOrgIdSet.contains(orgDTO.getOrgId())) {
					continue;
				}
				if (!orgIdSet.contains(orgDTO.getOrgId())) {
					CommonTreeNode orgTreeNode = new CommonTreeNode();
					orgTreeNode.setId(orgDTO.getOrgId());
					orgTreeNode.setShowName(orgDTO.getOrgShowName());
					orgTreeNode.setName(orgDTO.getOrgShowName());
					orgTreeNode.setPid(orgDTO.getParentId());
					orgTreeNode.setParent(orgService.existsChildOrgs(orgDTO
							.getOrgId())
							|| !jobService.queryByOrg(new JobDTO(),
									orgDTO.getOrgId()).isEmpty());
					orgTreeNode.setChkDisabled(true);
					orgTreeNode.setChecked(false);
					orgTreeNode.setNocheck(true);
					orgTreeNode.setHiddenName("org");
					list.add(orgTreeNode);
				}
			}

		}
		
		return list.toArray(new CommonTreeNode[list.size()]);
	}

	private Set<String> getFatherOrgIdSet(String searchOrgId) {
		Set<String> set = new HashSet<String>();
		List<OrgDTO> parentOrgs = orgService.queryFatherOrgs(searchOrgId, true);
		for (OrgDTO orgDTO : parentOrgs) {
			set.add(orgDTO.getOrgId());
		}
		return set;
	}
}
