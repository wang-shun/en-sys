package com.chinacreator.asp.sysmgmt.sysset.usermgt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.sysmgmt.common.CommonTreeNode;
import com.chinacreator.asp.sysmgmt.sysset.orgmgt.OrgMgtMessages;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;

public class OrgSelectTreeContentProviderImpl implements TreeContentProvider {

	private OrgService orgService = ApplicationContextManager.getContext()
			.getBean(OrgService.class);

	@Override
	public TreeNode[] getElements(TreeContext context) {
		List<CommonTreeNode> list = new ArrayList<CommonTreeNode>();
		if (null != context) {
			Map<String, Object> map = context.getConditions();
			String orgId = (String) map.get("id");
			String searchOrgId = (String) map.get("searchOrgId");
			if (null == orgId || orgId.trim().equals("")) {
				CommonTreeNode rootOrgTreeNode = new CommonTreeNode();
				rootOrgTreeNode.setId("0");
				rootOrgTreeNode.setName(OrgMgtMessages
						.getString("ORGMGT.ORG_ROOT_TREENAME"));
				rootOrgTreeNode.setShowName(OrgMgtMessages
						.getString("ORGMGT.ORG_ROOT_TREENAME"));
				rootOrgTreeNode.setPid(null);
				rootOrgTreeNode.setParent(orgService.existsChildOrgs("0"));
				rootOrgTreeNode.setChkDisabled(true);
				rootOrgTreeNode.setChecked(false);
				rootOrgTreeNode.setNocheck(true);
				list.add(rootOrgTreeNode);

				orgId = "0";
			}

			Set<String> orgIdSet = new HashSet<String>();
			if ("0".equals(orgId) && null != searchOrgId
					&& !searchOrgId.trim().equals("")
					&& !searchOrgId.trim().equals("0")) {
				List<OrgDTO> parentOrgs = orgService.queryFatherOrgs(
						searchOrgId, true);
				for (OrgDTO orgDTO : parentOrgs) {
					String parentOrgId = orgDTO.getParentId();
					if (!"0".equals(parentOrgId)) {
						List<OrgDTO> orgDTOs = orgService.queryChildOrgs(
								parentOrgId, false);
						for (OrgDTO org : orgDTOs) {
							CommonTreeNode orgTreeNode = new CommonTreeNode();
							orgTreeNode.setId(org.getOrgId());
							orgTreeNode.setShowName(org.getOrgShowName());
							orgTreeNode.setName(org.getOrgShowName());
							orgTreeNode.setPid(org.getParentId());
							orgTreeNode.setParent(orgService
									.existsChildOrgs(org.getOrgId()));
							list.add(orgTreeNode);
							orgIdSet.add(org.getOrgId());
						}
					}
				}
			}

			List<OrgDTO> orgDTOs = orgService.queryChildOrgs(orgId, false);

			for (OrgDTO orgDTO : orgDTOs) {
				if (!orgIdSet.contains(orgDTO.getOrgId())) {
					CommonTreeNode orgTreeNode = new CommonTreeNode();
					orgTreeNode.setId(orgDTO.getOrgId());
					orgTreeNode.setShowName(orgDTO.getOrgShowName());
					orgTreeNode.setName(orgDTO.getOrgShowName());
					orgTreeNode.setPid(orgDTO.getParentId());
					orgTreeNode.setParent(orgService.existsChildOrgs(orgDTO
							.getOrgId()));
					list.add(orgTreeNode);
				}
			}

		}
		return list.toArray(new CommonTreeNode[list.size()]);
	}
}
