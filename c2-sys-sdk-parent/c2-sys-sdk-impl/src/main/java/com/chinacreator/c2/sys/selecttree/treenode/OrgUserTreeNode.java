package com.chinacreator.c2.sys.selecttree.treenode;

import com.chinacreator.c2.tree.GenericTreeNode;

public interface OrgUserTreeNode extends GenericTreeNode{

	public String getType();

	public int getDepth();
	
	public Object getDTO();
}
