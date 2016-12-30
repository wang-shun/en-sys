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
			String jobId = (String) map.get("jobId");
			String isLoad = (String) map.get("isLoad");

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

			if (null == resId) {
				ResourceTreeNode rootTreeNode = new ResourceTreeNode(null, "0", "资源树", "root", true);
				rootTreeNode.setNoteTitle("资源树");
				if (isAdmin) {
					rootTreeNode.setChecked(true);
					rootTreeNode.setChkDisabled(true);
				}
				list.add(rootTreeNode);
			}

			if (null == resList || resList.isEmpty() || "true".equals(isLoad)) {
				JobResTreeNodeBuilder jobResTreeNodeBuilder = new JobResTreeNodeBuilder(true);
				resList = jobResTreeNodeBuilder.build();
			}

			if (null != resList && !resList.isEmpty()) {

				List<ResourceTreeNode> selResList = getResList(resId);

				if(null!=selResList){
				for (ResourceTreeNode resTreeNode : selResList) {
					if (isAdmin) {
						resTreeNode.setChecked(true);
						resTreeNode.setChkDisabled(true);
					} else if (privilegeIdSet.contains(resTreeNode.getId())) {
						resTreeNode.setChecked(true);
						resTreeNode.setChkDisabled(false);
					} else {
						resTreeNode.setChecked(false);
						resTreeNode.setChkDisabled(false);
					}
					setChedked(resTreeNode.getChildren(), isAdmin, privilegeIdSet);
				}

				list.addAll(selResList);
				}
			}

		}
		return list.toArray(new TreeNode[list.size()]);
	}

	private List getResList(String resId) {
		if (null == resId || "0".equals(resId)) {
			return resList;
		} else {
			for (ResourceTreeNode resTreeNode : resList) {
				if (resId.equals(resTreeNode.getId())) {
					return resTreeNode.getChildren();
				} else {
					List list = reResList(resTreeNode.getChildren(), resId);
					if(null!=list){
						return  list;
					}
				}
			}
		}
		return null;
	}

	private List reResList(List<DefaultTreeNode> children, String resId) {
		if (null != children && !children.isEmpty()) {
			for (DefaultTreeNode defaultTreeNode : children) {
				if (resId.equals(defaultTreeNode.getId())) {
					return defaultTreeNode.getChildren();
				} else {
					return reResList(defaultTreeNode.getChildren(), resId);
				}
			}
		}
		return null;
	}

	private void setChedked(List<DefaultTreeNode> nodeList, boolean isAdmin, Set<String> privilegeIdSet) {
		if (null != nodeList && !nodeList.isEmpty()) {
			for (DefaultTreeNode defaultTreeNode : nodeList) {
				if (isAdmin) {
					defaultTreeNode.setChecked(true);
					defaultTreeNode.setChkDisabled(true);
				} else if (privilegeIdSet.contains(defaultTreeNode.getId())) {
					defaultTreeNode.setChecked(true);
					defaultTreeNode.setChkDisabled(false);
				} else {
					defaultTreeNode.setChecked(false);
					defaultTreeNode.setChkDisabled(false);
				}
				setChedked(defaultTreeNode.getChildren(), isAdmin, privilegeIdSet);
			}
		}
	}
}
