package com.chinacreator.asp.sysmgmt.sysset.rolemgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chinacreator.asp.comp.sys.basic.role.dto.RoleTypeDTO;
import com.chinacreator.asp.comp.sys.basic.role.service.RoleTypeService;
import com.chinacreator.asp.sysmgmt.common.CommonTreeNode;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;

public class RoleTypeTreeContentProviderImpl implements TreeContentProvider {

	private RoleTypeService roleTypeService = ApplicationContextManager.getContext().getBean(RoleTypeService.class);

	@Override
	public TreeNode[] getElements(TreeContext context) {
		List<CommonTreeNode> list = new ArrayList<CommonTreeNode>();
		if (null != context) {
			Map<String, Object> map = context.getConditions();
			String roleTypeId = (String) map.get("id");
			if (null == roleTypeId || roleTypeId.trim().equals("")) {
				List<RoleTypeDTO> roleTypeDTOs = roleTypeService.queryAll();

				CommonTreeNode rootOrgTreeNode = new CommonTreeNode();
				rootOrgTreeNode.setId("0");
				rootOrgTreeNode.setName(RoleMgtMessages.getString("ROLEMGT.ROLETYPE_ROOT_TREENAME"));
				rootOrgTreeNode.setPid(null);
				rootOrgTreeNode.setParent(!roleTypeDTOs.isEmpty());

				list.add(rootOrgTreeNode);

				for (RoleTypeDTO roleTypeDTO : roleTypeDTOs) {
					if (!roleTypeDTO.getTypeId().equals(roleTypeService.getAnonymousRoleTypeId())) {
						CommonTreeNode node = new CommonTreeNode();
						node.setId(roleTypeDTO.getTypeId());
						node.setName(roleTypeDTO.getTypeName());
						node.setPid("0");
						node.setParent(false);

						list.add(node);
					}
				}
			}
		}
		return list.toArray(new CommonTreeNode[list.size()]);
	}

}
