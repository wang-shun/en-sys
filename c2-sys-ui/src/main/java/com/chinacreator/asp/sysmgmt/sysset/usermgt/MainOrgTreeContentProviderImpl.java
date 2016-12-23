package com.chinacreator.asp.sysmgmt.sysset.usermgt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.sysmgmt.common.CommonTreeNode;
import com.chinacreator.asp.sysmgmt.sysset.orgmgt.OrgMgtMessages;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;

public class MainOrgTreeContentProviderImpl implements TreeContentProvider {

	private OrgService orgService = ApplicationContextManager.getContext()
			.getBean(OrgService.class);

	private UserService userService = ApplicationContextManager.getContext()
			.getBean(UserService.class);

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
				CommonTreeNode rootOrgTreeNode = new CommonTreeNode();
				rootOrgTreeNode.setId("0");
				rootOrgTreeNode.setName(OrgMgtMessages
						.getString("ORGMGT.ORG_ROOT_TREENAME"));
				rootOrgTreeNode.setShowName(OrgMgtMessages
						.getString("ORGMGT.ORG_ROOT_TREENAME"));
				rootOrgTreeNode.setPid(null);
				rootOrgTreeNode.setParent(true);
				rootOrgTreeNode.setChkDisabled(true);
				rootOrgTreeNode.setNocheck(true);
				list.add(rootOrgTreeNode);
				
				orgId = "0";
			}

			if (null != userIds && userIds.length > 0) {
				Set<String> userOrgIdSet = new HashSet<String>();
				List<OrgDTO> orgDTOs = new ArrayList<OrgDTO>();

				if (isAdvancedSet) {
					orgDTOs = orgService.queryChildOrgs(orgId, false);
				} else {
					userOrgIdSet = getUserOrgIdSet(userIds);

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
									.format(UserMgtMessages
											.getString("USERMGT.TREE_MAINORGNAME_TEMPLATE"),
											orgDTO.getOrgShowName());
							orgTreeNode.setChecked(true);
						}
					}
					orgTreeNode.setName(orgShowName);
					orgTreeNode.setPid(orgDTO.getParentId());
					orgTreeNode.setParent(orgService.existsChildOrgs(orgDTO
							.getOrgId()));
					list.add(orgTreeNode);
				}
			}
		}
		return list.toArray(new CommonTreeNode[list.size()]);
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
