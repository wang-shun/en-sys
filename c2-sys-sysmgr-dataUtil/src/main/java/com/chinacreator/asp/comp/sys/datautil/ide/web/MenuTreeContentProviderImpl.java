package com.chinacreator.asp.comp.sys.datautil.ide.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinacreator.asp.comp.sys.basic.menu.eo.MenuAllInfoEO;
import com.chinacreator.asp.comp.sys.datautil.common.CommonTreeNode;
import com.chinacreator.asp.comp.sys.datautil.ide.manager.SynchroSysMgtUtil;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;

public class MenuTreeContentProviderImpl implements TreeContentProvider {

	@Autowired
	private SynchroSysMgtUtil synchroSysMgtUtil = ApplicationContextManager
			.getContext().getBean(SynchroSysMgtUtil.class);

	@Override
	public TreeNode[] getElements(TreeContext context) {
		List<CommonTreeNode> list = new ArrayList<CommonTreeNode>();
		if (null != context) {
			Map<String, Object> map = context.getConditions();
			String id = (String) map.get("id");
			if (null == id || id.trim().equals("")) {
				List<MenuAllInfoEO> moduleList = new ArrayList<MenuAllInfoEO>();
				List<MenuAllInfoEO> addMenuList = new ArrayList<MenuAllInfoEO>();
				List<MenuAllInfoEO> updateMenuList = new ArrayList<MenuAllInfoEO>();
				List<MenuAllInfoEO> delMenuList = new ArrayList<MenuAllInfoEO>();
				synchroSysMgtUtil.getSynchroMenuInfo(addMenuList,
						updateMenuList, delMenuList);

				for (MenuAllInfoEO menuAllInfoEO : addMenuList) {
					menuAllInfoEO.setMenuName(menuAllInfoEO.getMenuName()
							+ "_(新增)");
					moduleList.add(menuAllInfoEO);
				}
				for (MenuAllInfoEO menuAllInfoEO : updateMenuList) {
					menuAllInfoEO.setMenuName(menuAllInfoEO.getMenuName()
							+ "_(修改)");
					moduleList.add(menuAllInfoEO);
				}
				for (MenuAllInfoEO menuAllInfoEO : delMenuList) {
					menuAllInfoEO.setMenuName(menuAllInfoEO.getMenuName()
							+ "_(删除)");
					moduleList.add(menuAllInfoEO);
				}
				for (MenuAllInfoEO menuAllInfoEO : moduleList) {
					CommonTreeNode treeNode = new CommonTreeNode();
					treeNode.setId(menuAllInfoEO.getMenuId());
					treeNode.setName(menuAllInfoEO.getMenuName());
					treeNode.setPid(menuAllInfoEO.getParentId());
					treeNode.setParent(isParent(menuAllInfoEO.getMenuId(),
							moduleList));
					treeNode.setHiddenName(menuAllInfoEO.getMenuCode());
					list.add(treeNode);
				}

				if (null != moduleList && !moduleList.isEmpty()) {
					generateRootTree(list, !moduleList.isEmpty());
				} else {
					generateRootTree(list, false);
				}
			}
		}

		return list.toArray(new CommonTreeNode[list.size()]);
	}

	private void generateRootTree(List<CommonTreeNode> list, boolean isParent) {
		CommonTreeNode rootTreeNode = new CommonTreeNode();
		rootTreeNode.setId("0");
		rootTreeNode.setName("菜单树");
		rootTreeNode.setPid(null);
		rootTreeNode.setParent(isParent);
		list.add(rootTreeNode);
	}

	private boolean isParent(String menuId, List<MenuAllInfoEO> moduleList) {
		for (MenuAllInfoEO menuAllInfoEO : moduleList) {
			if (menuId.equals(menuAllInfoEO.getParentId())) {
				return true;
			}
		}
		return false;
	}

}
