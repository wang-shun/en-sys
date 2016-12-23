package com.chinacreator.asp.sysmgmt.sysset.rolemgt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chinacreator.asp.comp.sys.advanced.role.service.RoleService;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.sysmgmt.sysset.resmgt.ResourceTreeNode;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;
import com.chinacreator.c2.web.ds.impl.DefaultTreeNode;

public class RoleResTreeContentProviderImpl implements TreeContentProvider {

	private static List<ResourceTreeNode> resList = null;

	private RoleService roleService = ApplicationContextManager.getContext().getBean(RoleService.class);

	@Override
	public TreeNode[] getElements(TreeContext context) {
		List<ResourceTreeNode> list = new ArrayList<ResourceTreeNode>();

		if (null != context) {
			Map<String, Object> map = context.getConditions();
			// String resId = (String) map.get("id");
			// String type = (String) map.get("type");
			String isLoad = (String) map.get("isLoad");
			String[] roleIds = (String[]) map.get("roleIds");
			String presetId = (String) map.get("presetId");
			String isAdvanced = (String) map.get("isAdvanced");

			ResourceTreeNode rootTreeNode = new ResourceTreeNode(null, "0", "资源树", "root", true);
			rootTreeNode.setNoteTitle("资源树");
			list.add(rootTreeNode);

			if (null == resList || resList.isEmpty() || "true".equals(isLoad)) {
				RoleResTreeNodeBuilder roleResTreeNodeBuilder = new RoleResTreeNodeBuilder(!"true".equals(isAdvanced));
				resList = roleResTreeNodeBuilder.build();
			}

			if (null != resList && !resList.isEmpty()) {

				boolean isAdmin = false;
				Set<String> privilegeIdSet = new HashSet<String>();
				if (null != presetId && !presetId.trim().equals("")) {
					Set<String> presetIdSet = new HashSet<String>(Arrays.asList(presetId.split(",")));
					if (presetIdSet.contains(roleService.getAdministratorRoleId())) {
						isAdmin = true;
					} else {
						for (String id : presetIdSet) {
							List<PrivilegeDTO> privilegeDTOs = roleService.queryPrivileges(id);
							if (null != privilegeDTOs && !privilegeDTOs.isEmpty()) {
								for (PrivilegeDTO privilegeDTO : privilegeDTOs) {
									privilegeIdSet.add(privilegeDTO.getPrivilegeId());
								}
							}
						}
					}
				}
				if (!isAdmin && null != roleIds && roleIds.length == 1) {
					List<PrivilegeDTO> privilegeDTOs = roleService.queryPrivileges(roleIds[0]);
					if (null != privilegeDTOs && !privilegeDTOs.isEmpty()) {
						for (PrivilegeDTO privilegeDTO : privilegeDTOs) {
							privilegeIdSet.add(privilegeDTO.getPrivilegeId());
						}
					}
				}

				for (ResourceTreeNode resourceTreeNode : resList) {
					resourceTreeNode.setChecked(isAdmin || privilegeIdSet.contains(resourceTreeNode.getId()));
					setChedked(resourceTreeNode.getChildren(), isAdmin, privilegeIdSet);
				}

				list.addAll(resList);
			}
		}
		return list.toArray(new TreeNode[list.size()]);
	}

	private void setChedked(List<DefaultTreeNode> nodeList, boolean isAdmin, Set<String> privilegeIdSet) {
		if (null != nodeList && !nodeList.isEmpty()) {
			for (DefaultTreeNode defaultTreeNode : nodeList) {
				defaultTreeNode.setChecked(isAdmin || privilegeIdSet.contains(defaultTreeNode.getId()));
				setChedked(defaultTreeNode.getChildren(), isAdmin, privilegeIdSet);
			}
		}
	}
}
