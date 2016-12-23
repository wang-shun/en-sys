package com.chinacreator.asp.sysmgmt.sysset.jobresmgt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.std.job.facade.JobFacade;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;
import com.chinacreator.c2.web.ds.impl.DefaultTreeNode;

public class ResTreeContentProviderImpl implements TreeContentProvider {

	private JobFacade jobFacade = ApplicationContextManager.getContext().getBean(JobFacade.class);

	private static List<ResourceTreeNode> resList = null;

	@Override
	public TreeNode[] getElements(TreeContext context) {
		List<ResourceTreeNode> list = new ArrayList<ResourceTreeNode>();

		if (null != context) {
			Map<String, Object> map = context.getConditions();
			String resId = (String) map.get("id");
			String type = (String) map.get("type");
			String jobId = (String) map.get("jobId");
			String checked = (String) map.get("checked");
			String virtual = (String) map.get("virtual");
			String isLoad = (String) map.get("isLoad");

			ResourceTreeNode rootTreeNode = new ResourceTreeNode(null, "0", "资源树", "root", true);
			rootTreeNode.setNoteTitle("资源树");
			list.add(rootTreeNode);

			if (null == resList || resList.isEmpty() || "true".equals(isLoad)) {
				JobResTreeNodeBuilder jobResTreeNodeBuilder = new JobResTreeNodeBuilder(true);
				resList = jobResTreeNodeBuilder.build();
			}

			if (null != resList && !resList.isEmpty()) {
				boolean isAdmin = false;
				Set<String> privilegeIdSet = new HashSet<String>();
				if (null != jobId && !jobId.trim().equals("")) {
					if (jobId.equals(jobFacade.getAdministratorJobId())) {
						isAdmin = true;
					} else {
						List<PrivilegeDTO> privilegeDTOs = jobFacade.queryPrivilegeByJobs(jobId);
						if (null != privilegeDTOs && !privilegeDTOs.isEmpty()) {
							for (PrivilegeDTO privilegeDTO : privilegeDTOs) {
								privilegeIdSet.add(privilegeDTO.getPrivilegeId());
							}
						}
					}
				}
				for (ResourceTreeNode resTreeNode : list) {
					if (isAdmin) {
						resTreeNode.setChecked(true);
						resTreeNode.setChkDisabled(true);
					} else if (privilegeIdSet.contains(resTreeNode.getId())) {
						resTreeNode.setChecked(true);
					}
					setChedked(resTreeNode.getChildren(), isAdmin, privilegeIdSet);
				}
				
				list.addAll(resList);
			}

		}
		return list.toArray(new TreeNode[list.size()]);
	}

	private void setChedked(List<DefaultTreeNode> nodeList, boolean isAdmin, Set<String> privilegeIdSet) {
		if (null != nodeList && !nodeList.isEmpty()) {
			for (DefaultTreeNode defaultTreeNode : nodeList) {
				if (isAdmin) {
					defaultTreeNode.setChecked(true);
					defaultTreeNode.setChkDisabled(true);
				} else if (privilegeIdSet.contains(defaultTreeNode.getId())) {
					defaultTreeNode.setChecked(true);
				}
				setChedked(defaultTreeNode.getChildren(), isAdmin, privilegeIdSet);
			}
		}
	}
}
