package com.chinacreator.asp.comp.sys.datautil.ide.web;

import com.chinacreator.asp.comp.sys.common.exception.SysException;
import com.chinacreator.asp.comp.sys.datautil.ide.manager.ResourceTreeNodeBuilder;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;

public class ResTreeContentProviderImpl implements TreeContentProvider {

	@Override
	public TreeNode[] getElements(TreeContext context) {
		TreeNode[] node = null;

		try {
			ResourceTreeNodeBuilder bulider = new ResourceTreeNodeBuilder();

			node = bulider.build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysException(e);
		}

		return node;
	}
}
