package com.chinacreator.asp.sysmgmt.sysset.jobmgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chinacreator.asp.comp.sys.advanced.job.dto.JobDTO;
import com.chinacreator.asp.comp.sys.advanced.job.service.JobService;
import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.sysmgmt.common.CommonTreeNode;
import com.chinacreator.asp.sysmgmt.sysset.jobmenumgt.JobMenuMgtMessages;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;

public class OrgUserTreeContentProviderImpl implements TreeContentProvider {

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
			String[] jobIds = (String[]) map.get("jobIds");
			if (null == orgId || orgId.trim().equals("")) {
				CommonTreeNode rootOrgTreeNode = new CommonTreeNode();
				rootOrgTreeNode.setId("0");
				rootOrgTreeNode.setName(JobMenuMgtMessages
						.getString("JOBMENUMGT.ORG_ROOT_TREENAME"));
				rootOrgTreeNode.setShowName(JobMenuMgtMessages
						.getString("JOBMENUMGT.ORG_ROOT_TREENAME"));
				rootOrgTreeNode.setPid(null);
				rootOrgTreeNode.setParent(orgService.existsChildOrgs("0"));
				rootOrgTreeNode.setChkDisabled(true);
				rootOrgTreeNode.setChecked(false);
				rootOrgTreeNode.setNocheck(true);
				list.add(rootOrgTreeNode);

				orgId = "0";
			}

			String searchOrgId = null;
			String showOrgType = "all";
			if (null != jobIds && jobIds.length > 0) {
				for (String jobId : jobIds) {
					JobDTO jobDTO = jobService.queryByPK(jobId);
					if (null != jobDTO && jobDTO.getJobScope().equals("1")) {
						OrgDTO orgDTO = jobService.queryOrgByJobId(jobId);
						if (null != orgDTO) {
							if ("all".equals(showOrgType)) {
								showOrgType = "org";
							}
							if (null == searchOrgId) {
								searchOrgId = orgDTO.getOrgId();
							} else {
								if (!searchOrgId.equals(orgDTO.getOrgId())) {
									showOrgType = "none";
								}
							}
						}
					}
				}
			}

			String jobId = null;
			if (null != jobIds && jobIds.length == 1) {
				jobId = jobIds[0];
			}

			if ("org".equals(showOrgType)) {
				if ("0".equals(orgId) && null != searchOrgId
						&& !searchOrgId.trim().equals("")
						&& !searchOrgId.trim().equals("0")) {
					List<OrgDTO> parentOrgs = orgService.queryFatherOrgs(
							searchOrgId, true);
					for (OrgDTO orgDTO : parentOrgs) {
						CommonTreeNode orgTreeNode = new CommonTreeNode();
						orgTreeNode.setId(orgDTO.getOrgId());
						orgTreeNode.setShowName(orgDTO.getOrgShowName());
						orgTreeNode.setName(orgDTO.getOrgShowName());
						orgTreeNode.setPid(orgDTO.getParentId());
						orgTreeNode.setChkDisabled(true);
						orgTreeNode.setChecked(false);
						orgTreeNode.setNocheck(true);
						list.add(orgTreeNode);
					}

					List<UserDTO> userList = orgService.queryUsers(searchOrgId);
					for (UserDTO userDTO : userList) {
						CommonTreeNode userTreeNode = new CommonTreeNode();
						userTreeNode.setId(userDTO.getUserId());
						userTreeNode.setShowName(userDTO.getUserRealname());
						userTreeNode.setName(userDTO.getUserRealname());
						userTreeNode.setPid(searchOrgId);
						userTreeNode.setParent(false);
						if (null != jobId && !jobId.trim().equals("")) {
							userTreeNode.setChecked(jobService.containsUser(
									jobId, userDTO.getUserId()));
						}
						list.add(userTreeNode);
					}
				}
			} else if ("all".equals(showOrgType)) {
				List<OrgDTO> orgDTOs = orgService.queryChildOrgs(orgId, false);

				for (OrgDTO orgDTO : orgDTOs) {
					CommonTreeNode orgTreeNode = new CommonTreeNode();
					orgTreeNode.setId(orgDTO.getOrgId());
					orgTreeNode.setShowName(orgDTO.getOrgShowName());
					orgTreeNode.setName(orgDTO.getOrgShowName());
					orgTreeNode.setPid(orgDTO.getParentId());
					orgTreeNode.setParent(orgService.existsChildOrgs(orgDTO
							.getOrgId())
							|| !orgService.queryUsers(orgDTO.getOrgId())
									.isEmpty());
					orgTreeNode.setChkDisabled(true);
					orgTreeNode.setChecked(false);
					orgTreeNode.setNocheck(true);
					list.add(orgTreeNode);
				}

				if (!"0".equals(orgId)) {
					List<UserDTO> userList = orgService.queryUsers(orgId);
					for (UserDTO userDTO : userList) {
						CommonTreeNode userTreeNode = new CommonTreeNode();
						userTreeNode.setId(userDTO.getUserId());
						userTreeNode.setShowName(userDTO.getUserRealname());
						userTreeNode.setName(userDTO.getUserRealname());
						userTreeNode.setPid(orgId);
						userTreeNode.setParent(false);
						if (null != jobId && !jobId.trim().equals("")) {
							userTreeNode.setChecked(jobService.containsUser(
									jobId, userDTO.getUserId()));
						}
						list.add(userTreeNode);
					}
				}
			}
		}

		return list.toArray(new CommonTreeNode[list.size()]);
	}

}
