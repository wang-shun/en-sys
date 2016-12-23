package com.chinacreator.asp.sysmgmt.sysset.resmgt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;

public class ResTreeContentProviderImpl implements TreeContentProvider {

	private static Map<String, List<ResourceTreeNode>> treeNodeMap;

	@Override
	public TreeNode[] getElements(TreeContext context) {
		List<ResourceTreeNode> list = new ArrayList<ResourceTreeNode>();
		if (null != context) {
			Map<String, Object> map = context.getConditions();
			String resId = (String) map.get("id");
			String isLoad = (String) map.get("isLoad");

			if (null == resId || resId.trim().equals("") || null == treeNodeMap || "true".equals(isLoad)) {
				ResTreeNodeBuilder resBuilder = new ResTreeNodeBuilder();
				treeNodeMap = resBuilder.build();
			}

			if (null == resId || resId.trim().equals("")) {
				ResourceTreeNode rootTreeNode = new ResourceTreeNode(null, "0", "资源树", "root", true);
				rootTreeNode.setChkDisabled(true);
				rootTreeNode.setNocheck(true);
				rootTreeNode.setNoteTitle("资源树");
				rootTreeNode.setSn(0);
				list.add(rootTreeNode);

				resId = "0";
			}

			if (null != treeNodeMap && !treeNodeMap.isEmpty()) {
				List<ResourceTreeNode> treeNodeList = treeNodeMap.get(resId);
				if(null!=treeNodeList && !treeNodeList.isEmpty()){
					list.addAll(treeNodeList);
				}
			}

			
		}

		if (!list.isEmpty()) {
			Collections.sort(list, new Comparator<ResourceTreeNode>() {
				@Override
				public int compare(ResourceTreeNode o1, ResourceTreeNode o2) {
					Integer i1 = o1.getSn();
					Integer i2 = o2.getSn();
					if (null == i1) {
						i1 = 0;
					}
					if (null == i2) {
						i2 = 0;
					}

					return i1.compareTo(i2);
				}
			});
		}

		return list.toArray(new TreeNode[list.size()]);
	}
}
