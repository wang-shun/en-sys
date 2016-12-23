package com.chinacreator.asp.sysmgmt.sysset.orgmgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.sysmgmt.common.CommonTreeNode;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.sys.ui.ds.selecttree.cache.OrgUserTreeCache;
import com.chinacreator.c2.sys.ui.ds.selecttree.treenode.OrgUserTreeNode;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

public class CachedOrgTreeContentProviderImpl implements TreeContentProvider {
	private OrgService orgService = ApplicationContextManager.getContext().getBean(OrgService.class);

	@Override
	public TreeNode[] getElements(TreeContext context) {
		List<CommonTreeNode> list = new ArrayList<CommonTreeNode>();

		if (null != context) {
			Map<String, Object> map = context.getConditions();
			String orgId = (String) map.get("id");

			if ((null == orgId) || orgId.trim().equals("")) {
				CommonTreeNode rootOrgTreeNode = new CommonTreeNode();
				rootOrgTreeNode.setId("0");
				rootOrgTreeNode.setName(OrgMgtMessages.getString("ORGMGT.ORG_ROOT_TREENAME"));
				rootOrgTreeNode.setPid(null);
				rootOrgTreeNode.setParent(orgService.existsChildOrgs("0"));
				list.add(rootOrgTreeNode);

				orgId = "0";
			}

			OrgUserTreeCache cache = ApplicationContextManager.getContext().getBean(OrgUserTreeCache.class);
			List<OrgUserTreeNode> nodes = null;
			if ("0".equals(orgId)) {
				nodes = cache.getOrgTree().getTree(-1);
			} else {
				nodes = cache.getOrgTree().getChildren(-1, orgId);
			}

			list.addAll(Lists.newArrayList(FluentIterable.from(nodes).filter(new Predicate<OrgUserTreeNode>() {
				@Override
				public boolean apply(OrgUserTreeNode input) {
					return "org".equals(input.getType());
				}
			}).transform(new Function<OrgUserTreeNode, CommonTreeNode>() {
				public CommonTreeNode apply(OrgUserTreeNode input) {
					CommonTreeNode commonTreeNode = new CommonTreeNode();
					commonTreeNode.setId(input.getId());
					commonTreeNode.setName(input.getName());
					commonTreeNode.setPid(input.getPid() == null ? "0" : input.getPid());
					return commonTreeNode;
				}
			})));
		}

		return list.toArray(new CommonTreeNode[list.size()]);
	}
}
