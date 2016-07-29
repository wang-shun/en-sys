package com.chinacreator.c2.sys.selecttree;

import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.sys.selecttree.cache.OrgUserTreeCache;
import com.chinacreator.c2.web.ds.TreeNode;
import com.chinacreator.c2.web.ds.impl.DynamicTreeNode;

public class OrgUserNodeService {

	private static final String sfs_USER = "user:";

	private OrgService orgService;
	private UserService userService;

	public TreeNode getOrgUserNode(String nodeId) {
		DynamicTreeNode node = new DynamicTreeNode();
		if (null != nodeId && !nodeId.trim().equals("")) {
			if (nodeId.startsWith(sfs_USER)) {
				UserDTO userDTO = getUserService().queryByPK(nodeId.substring(sfs_USER.length()));
				if (null != userDTO) {
					node.put(DynamicTreeNode.NAME, userDTO.getUserRealname());
				}
			} else {
				OrgDTO orgDTO = getOrgService().queryByPK(nodeId);
				if (null != orgDTO) {
					node.put(DynamicTreeNode.NAME, orgDTO.getOrgShowName());
				}
			}
		}
		return node;
	}

	public void resetOrgUserTreeCache() {
		OrgUserTreeCache orgUserTreeCache = ApplicationContextManager.getContext().getBean(OrgUserTreeCache.class);
		orgUserTreeCache.init();
	}

	private OrgService getOrgService() {
		if (null == orgService) {
			orgService = ApplicationContextManager.getContext().getBean(OrgService.class);
		}
		return orgService;
	}

	private UserService getUserService() {
		if (null == userService) {
			userService = ApplicationContextManager.getContext().getBean(UserService.class);
		}
		return userService;
	}
}
