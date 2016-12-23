package com.chinacreator.asp.sysmgmt.sysset.orgmgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chinacreator.asp.comp.sys.advanced.role.service.RoleService;
import com.chinacreator.asp.comp.sys.basic.role.dto.RoleTypeDTO;
import com.chinacreator.asp.comp.sys.basic.role.service.RoleTypeService;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.sysmgmt.common.CommonTreeNode;
import com.chinacreator.asp.sysmgmt.sysset.rolemgt.RoleMgtMessages;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;

public class OrgRoleTreeContentProviderImpl implements TreeContentProvider {

	private RoleTypeService roleTypeService = ApplicationContextManager.getContext().getBean(RoleTypeService.class);

	private RoleService roleService = ApplicationContextManager.getContext().getBean(RoleService.class);

	private static final String sfs_ROLETYPE_PREFIX = "RT_";

	@Override
	public TreeNode[] getElements(TreeContext context) {
		List<CommonTreeNode> list = new ArrayList<CommonTreeNode>();
		if (null != context) {
			Map<String, Object> map = context.getConditions();
			String roleTypeId = (String) map.get("id");
			String[] orgIds = (String[]) map.get("orgIds");
			if (null == roleTypeId || roleTypeId.trim().equals("")) {
				List<RoleTypeDTO> roleTypeDTOs = roleTypeService.queryAll();

				CommonTreeNode rootTreeNode = new CommonTreeNode();
				rootTreeNode.setId("root");
				rootTreeNode.setName(RoleMgtMessages.getString("ROLEMGT.ROLETYPE_ROOT_TREENAME"));
				rootTreeNode.setPid(null);
				rootTreeNode.setParent(!roleTypeDTOs.isEmpty());
				rootTreeNode.setHiddenName("roleType");
				rootTreeNode.setChkDisabled(true);
				rootTreeNode.setChecked(false);
				rootTreeNode.setNocheck(true);

				list.add(rootTreeNode);

				for (RoleTypeDTO roleTypeDTO : roleTypeDTOs) {
					if (!roleTypeDTO.getTypeId().equals(roleTypeService.getAnonymousRoleTypeId())) {
						String rtId = sfs_ROLETYPE_PREFIX + roleTypeDTO.getTypeId();
						CommonTreeNode node = new CommonTreeNode();
						node.setId(rtId);
						node.setName(roleTypeDTO.getTypeName());
						node.setPid("root");
						node.setParent(!getRole(rtId).isEmpty());
						node.setHiddenName("roleType");
						node.setChkDisabled(true);
						node.setChecked(false);
						node.setNocheck(true);

						list.add(node);
					}
				}
			} else if (!"root".equals(roleTypeId)) {
				String orgId = null;
				if (null != orgIds && orgIds.length == 1) {
					orgId = orgIds[0];
				}

				List<RoleDTO> roleList = getRole(roleTypeId);
				for (RoleDTO role : roleList) {
					if (role.getRoleId().equals(roleService.getRoleofeveryoneRoleId())) {
						continue;
					}
					CommonTreeNode node = new CommonTreeNode();
					node.setId(role.getRoleId());
					node.setName(role.getRoleName());
					node.setPid(roleTypeId);
					node.setHiddenName("role");
					node.setParent(false);
					if (!role.getRoleUsage()) {
						node.setChecked(false);
						node.setChkDisabled(true);
					} else if (null != orgId) {
						node.setChecked(roleService.isAssignedToOrg(role.getRoleId(), orgId));
					}

					list.add(node);
				}
			}
		}
		return list.toArray(new CommonTreeNode[list.size()]);
	}

	private List<RoleDTO> getRole(String roleTypeId) {
		List<RoleDTO> roleList = new ArrayList<RoleDTO>();
		if (null != roleTypeId && !roleTypeId.trim().equals("") && !roleTypeId.trim().equals("root")) {
			if (roleTypeId.startsWith(sfs_ROLETYPE_PREFIX)) {
				roleTypeId = roleTypeId.substring(sfs_ROLETYPE_PREFIX.length());
			}
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setRoleType(roleTypeId);
			roleList = roleService.queryByRole(roleDTO);
		}
		return roleList;
	}
}
