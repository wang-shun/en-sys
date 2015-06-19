package com.chinacreator.asp.sysmgmt.sysset.usermgt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chinacreator.asp.comp.sys.advanced.job.dto.JobDTO;
import com.chinacreator.asp.comp.sys.advanced.job.service.JobService;
import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.std.job.facade.JobFacade;
import com.chinacreator.asp.sysmgmt.common.CommonTreeNode;
import com.chinacreator.asp.sysmgmt.sysset.jobmenumgt.JobMenuMgtMessages;
import com.chinacreator.asp.sysmgmt.sysset.orgusermgt.OrgUserMgtMessages;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;

public class OrgJobTreeContentProviderImpl implements TreeContentProvider {

	private OrgService orgService = ApplicationContextManager.getContext()
			.getBean(OrgService.class);

	private JobService jobService = ApplicationContextManager.getContext()
			.getBean(JobService.class);

	private UserService userService = ApplicationContextManager.getContext()
			.getBean(UserService.class);

	private JobFacade jobFacade = ApplicationContextManager.getContext()
			.getBean(JobFacade.class);

	@Override
	public TreeNode[] getElements(TreeContext context) {
		List<CommonTreeNode> list = new ArrayList<CommonTreeNode>();
		if (null != context) {
			Map<String, Object> map = context.getConditions();
			String orgId = (String) map.get("id");
			boolean isAdvancedSet = "true".equals((String) map
					.get("isAdvancedSet"));
			String[] userIds = (String[]) map.get("userIds");
			if (null == orgId || orgId.trim().equals("")) {
				generateRootTree(list, userIds);
				orgId = "0";
			}

			if (null != userIds && userIds.length > 0) {
				List<JobDTO> jobDTOs = new ArrayList<JobDTO>();
				Set<String> userOrgIdSet = new HashSet<String>();
				List<OrgDTO> orgDTOs = new ArrayList<OrgDTO>();

				if (isAdvancedSet) {
					jobDTOs = jobService.queryByOrg(new JobDTO(), orgId);
					orgDTOs = orgService.queryChildOrgs(orgId, false);
				} else {
					userOrgIdSet = getUserOrgIdSet(userIds);
					if (userOrgIdSet.contains(orgId)) {
						jobDTOs = jobService.queryByOrg(new JobDTO(), orgId);
					}

					List<OrgDTO> allOrgDTOs = orgService.queryChildOrgs(orgId,
							false);
					if (!allOrgDTOs.isEmpty()) {
						Set<String> fatherOrgIdSet = getFatherOrgIdSet(userOrgIdSet);
						for (String fatherOrgId : fatherOrgIdSet) {
							for (OrgDTO orgDTO : allOrgDTOs) {
								if (fatherOrgId.equals(orgDTO.getOrgId())) {
									orgDTOs.add(orgDTO);
								}
							}
						}
					}
				}

				for (JobDTO jobDTO : jobDTOs) {
					CommonTreeNode orgJobTreeNode = new CommonTreeNode();
					orgJobTreeNode.setId(jobDTO.getJobId());
					orgJobTreeNode.setName(jobDTO.getJobName());
					orgJobTreeNode.setShowName(jobDTO.getJobName());
					orgJobTreeNode.setPid(orgId);
					orgJobTreeNode.setParent(false);
					orgJobTreeNode.setChkDisabled(false);
					if (userIds.length == 1) {
						orgJobTreeNode.setChecked(jobService.containsUser(
								jobDTO.getJobId(), userIds[0]));
					}

					OrgDTO orgDTO = orgService.queryByPK(orgId);
					if (null != orgDTO) {
						orgJobTreeNode
								.setHiddenName(String.format(
										OrgUserMgtMessages
												.getString("ORGUSERMGT.ORGJOBTREE_HIDDENNAME_TEMPLATE"),
										orgDTO.getOrgShowName(), jobDTO
												.getJobName()));
					}

					list.add(orgJobTreeNode);
				}

				String orgShowName = null;
				OrgDTO mainOrg = null;
				if (userIds.length == 1) {
					mainOrg = userService.queryMainOrg(userIds[0]);
				}

				for (OrgDTO orgDTO : orgDTOs) {
					CommonTreeNode orgTreeNode = new CommonTreeNode();
					orgTreeNode.setId(orgDTO.getOrgId());
					orgTreeNode.setShowName(orgDTO.getOrgShowName());
					orgShowName = orgDTO.getOrgShowName();
					if (userIds.length == 1) {
						if (null != mainOrg
								&& mainOrg.getOrgId().equals(orgDTO.getOrgId())) {
							orgShowName = String
									.format(OrgUserMgtMessages
											.getString("ORGUSERMGT.ORGJOBTREE_MAINORGNAME_TEMPLATE"),
											orgDTO.getOrgShowName());
						}
					}
					orgTreeNode.setName(orgShowName);
					orgTreeNode.setPid(orgDTO.getParentId());
					orgTreeNode.setParent(orgService.existsChildOrgs(orgDTO
							.getOrgId())
							|| !jobService.queryByOrg(new JobDTO(),
									orgDTO.getOrgId()).isEmpty());
					orgTreeNode.setChkDisabled(true);
					orgTreeNode.setChecked(false);
					orgTreeNode.setNocheck(true);
					list.add(orgTreeNode);
				}
			}
		}
		return list.toArray(new CommonTreeNode[list.size()]);
	}

	private void generateRootTree(List<CommonTreeNode> list, String[] userIds) {
		List<JobDTO> jobDTOs = new ArrayList<JobDTO>();
		if (null != userIds && userIds.length > 0) {
			jobDTOs = jobService.queryByOrg(new JobDTO(), null);
		}
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
		list.add(jobOrgTreeNode);

		String roleofeveryoneJobId = jobFacade.getRoleofeveryoneJobId();

		for (JobDTO jobDTO : jobDTOs) {
			CommonTreeNode jobOrgJobTreeNode = new CommonTreeNode();
			jobOrgJobTreeNode.setId(jobDTO.getJobId());
			jobOrgJobTreeNode.setName(jobDTO.getJobName());
			jobOrgJobTreeNode.setShowName(jobDTO.getJobName());
			jobOrgJobTreeNode.setHiddenName(String.format(OrgUserMgtMessages
					.getString("ORGUSERMGT.ORGJOBTREE_HIDDENNAME_TEMPLATE"),
					JobMenuMgtMessages
							.getString("JOBMENUMGT.JOBORG_ROOT_TREENAME"),
					jobDTO.getJobName()));
			jobOrgJobTreeNode.setPid("-1");
			jobOrgJobTreeNode.setParent(false);
			if (jobDTO.getJobId().equals(roleofeveryoneJobId)) {
				jobOrgJobTreeNode.setChkDisabled(true);
				jobOrgJobTreeNode.setChecked(true);
			} else {
				jobOrgJobTreeNode.setChkDisabled(false);
				if (userIds.length == 1) {
					jobOrgJobTreeNode.setChecked(jobService.containsUser(
							jobDTO.getJobId(), userIds[0]));
				}
			}

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
		list.add(rootOrgTreeNode);
	}

	private Set<String> getUserOrgIdSet(String[] userIds) {
		Set<String> userOrgIdSet = new HashSet<String>();
		Set<String> userOrgIDs = new HashSet<String>();
		boolean isRetain = false;
		for (String userId : userIds) {
			List<OrgDTO> orgDTOs = userService.queryOrgs(userId);
			userOrgIDs.clear();
			for (OrgDTO orgDTO : orgDTOs) {
				userOrgIDs.add(orgDTO.getOrgId());
			}
			if (isRetain) {
				userOrgIdSet.retainAll(userOrgIDs);
			} else {
				userOrgIdSet.addAll(userOrgIDs);
				isRetain = true;
			}
		}
		return userOrgIdSet;
	}

	public Set<String> getFatherOrgIdSet(Set<String> userOrgIdSet) {
		Set<String> fatherOrgIdSet = new HashSet<String>();
		for (String userOrgId : userOrgIdSet) {
			List<OrgDTO> fatherOrgs = orgService.queryFatherOrgs(userOrgId,
					true);
			for (OrgDTO orgDTO : fatherOrgs) {
				fatherOrgIdSet.add(orgDTO.getOrgId());
			}
		}
		return fatherOrgIdSet;
	}
}
