package com.chinacreator.asp.sysmgmt.sysset.rolemgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.sysmgmt.common.CommonTreeNode;
import com.chinacreator.asp.sysmgmt.sysset.orgmgt.OrgMgtMessages;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;

public class RoleOrgTreeContentProviderImpl implements TreeContentProvider {

	private OrgService orgService = ApplicationContextManager.getContext().getBean(OrgService.class);

	@Override
	public TreeNode[] getElements(TreeContext context) {
		List<CommonTreeNode> list = new ArrayList<CommonTreeNode>();
		if (null != context) {
			Map<String, Object> map = context.getConditions();
			String orgId = (String) map.get("id");
			String[] roleIds = (String[]) map.get("roleIds");
			if (null == orgId || orgId.trim().equals("")) {
				CommonTreeNode rootOrgTreeNode = new CommonTreeNode();
				rootOrgTreeNode.setId("0");
				rootOrgTreeNode.setName(OrgMgtMessages.getString("ORGMGT.ORG_ROOT_TREENAME"));
				rootOrgTreeNode.setPid(null);
				rootOrgTreeNode.setParent(orgService.existsChildOrgs("0"));
				rootOrgTreeNode.setChkDisabled(true);
				rootOrgTreeNode.setChecked(false);
				rootOrgTreeNode.setNocheck(true);
				list.add(rootOrgTreeNode);

				orgId = "0";
			}
			
			String roleId = null;
			if (null != roleIds && roleIds.length == 1) {
				roleId = roleIds[0];
			}
			
			List<OrgDTO> orgDTOs = orgService.queryChildOrgs(orgId, false);
			for (OrgDTO orgDTO : orgDTOs) {
				CommonTreeNode orgTreeNode = new CommonTreeNode();
				orgTreeNode.setId(orgDTO.getOrgId());
				orgTreeNode.setName(orgDTO.getOrgShowName());
				orgTreeNode.setPid(orgDTO.getParentId());
				orgTreeNode.setParent(orgService.existsChildOrgs(orgDTO.getOrgId()));
				if(null!=roleId && !roleId.trim().equals("")){
					orgTreeNode.setChecked(orgService.hasRole(orgDTO.getOrgId(), roleId));
				}			
				
				list.add(orgTreeNode);
			}
		}
		return list.toArray(new CommonTreeNode[list.size()]);
	}
}
