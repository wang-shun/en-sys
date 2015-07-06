package com.chinacreator.asp.sysmgmt.sysset.jobmgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chinacreator.asp.comp.sys.advanced.job.dto.JobDTO;
import com.chinacreator.asp.comp.sys.advanced.job.service.JobService;
import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
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

			List<OrgDTO> orgDTOs = orgService.queryChildOrgs(orgId, false);
			List<JobDTO> jobDTOs = jobService.queryByOrg(new JobDTO(), orgId);

			for (JobDTO jobDTO : jobDTOs) {
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

			for (OrgDTO orgDTO : orgDTOs) {
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

		return list.toArray(new CommonTreeNode[list.size()]);
	}
}
