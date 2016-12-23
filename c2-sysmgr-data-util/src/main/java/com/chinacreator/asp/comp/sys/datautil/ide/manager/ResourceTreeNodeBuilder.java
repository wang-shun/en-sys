package com.chinacreator.asp.comp.sys.datautil.ide.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chinacreator.asp.comp.sys.core.privilege.dao.PrivilegeDao;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.asp.comp.sys.datautil.common.CommonTreeNode;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeNode;
import com.chinacreator.platform.mvc.perm.Resource;

public class ResourceTreeNodeBuilder {

	private IDEOperUtil ideOperUtil = ApplicationContextManager.getContext().getBean(IDEOperUtil.class);
	private PrivilegeDao privilegeDao = ApplicationContextManager.getContext().getBean(PrivilegeDao.class);

	private Map<String, List<Resource>> resMap;
	private Map<String, PrivilegeEO> dbResMap;
	private Set<String> dbResSet;
	private Map<String, CommonTreeNode> rootNodeMap;
	private Map<String, CommonTreeNode> auFirstNodeMap;
	private Map<String, CommonTreeNode> delFirstNodeMap;
	private Set<String> auFirstSet;
	private Set<String> delFirstSet;

	public ResourceTreeNodeBuilder() {
		List<Resource> ideRes = ideOperUtil.getIDEResourceList();
		List<PrivilegeEO> dbResList = privilegeDao.query(new PrivilegeEO());

		resMap = new HashMap<String, List<Resource>>();
		dbResMap = new HashMap<String, PrivilegeEO>();
		dbResSet = new HashSet<String>();
		rootNodeMap = new HashMap<String, CommonTreeNode>();
		auFirstNodeMap = new HashMap<String, CommonTreeNode>();
		delFirstNodeMap = new HashMap<String, CommonTreeNode>();
		auFirstSet = new HashSet<String>();
		delFirstSet = new HashSet<String>();

		ValidateIdeRes validateIdeRes = new ValidateIdeRes(ideRes);
		List<Resource> ideResList = validateIdeRes.getResList();

		if (null != ideResList && !ideResList.isEmpty()) {
			for (Resource resource : ideResList) {
				List<Resource> list = resMap.get(resource.getPid());
				if (null == list) {
					list = new ArrayList<Resource>();
				}
				list.add(resource);
				resMap.put(resource.getPid(), list);
			}
		}

		if (null != dbResList && !dbResList.isEmpty()) {
			for (PrivilegeEO privilegeEO : dbResList) {
				if (!"4".equals(privilegeEO.getType()) && "1".equals(privilegeEO.getSource())) {
					dbResMap.put(privilegeEO.getPermExpr(), privilegeEO);
					dbResSet.add(privilegeEO.getPermExpr());
				}
			}
		}

		CommonTreeNode auNode = new CommonTreeNode();
		auNode.setId("auNode");
		auNode.setName("待新增修改资源");
		auNode.setShowName("待新增修改资源");
		auNode.setSn(0);
		rootNodeMap.put("auNode", auNode);

		CommonTreeNode delNode = new CommonTreeNode();
		delNode.setId("delNode");
		delNode.setName("待删除资源");
		delNode.setShowName("待删除资源");
		delNode.setSn(1);
		rootNodeMap.put("delNode", delNode);

		String[] nodeStr = new String[] { "form", "dom", "entity", "entity_op", "service","rest", "custom" };
		for (int i = 0; i < nodeStr.length; i++) {
			String name = getTypeName(nodeStr[i]);

			CommonTreeNode auFirstNode = new CommonTreeNode();
			auFirstNode.setId("au_" + nodeStr[i]);
			auFirstNode.setName(name);
			auFirstNode.setShowName(name);
			auFirstNode.setIcon("ext/resTreeIcons/" + nodeStr[i] + ".png");
			auFirstNode.setSn(i);
			auFirstNodeMap.put("au_" + nodeStr[i], auFirstNode);

			CommonTreeNode delFirstNode = new CommonTreeNode();
			delFirstNode.setId("del_" + nodeStr[i]);
			delFirstNode.setName(name);
			delFirstNode.setShowName(name);
			delFirstNode.setIcon("ext/resTreeIcons/" + nodeStr[i] + ".png");
			delFirstNode.setSn(i);
			delFirstNodeMap.put("del_" + nodeStr[i], delFirstNode);
		}
	}

	public TreeNode[] build() {
		List<CommonTreeNode> nodeList = new ArrayList<CommonTreeNode>();

		List<Resource> list = resMap.get("0");
		if (null != list && !list.isEmpty()) {
			CommonTreeNode rootNode = rootNodeMap.get("auNode");
			List<CommonTreeNode> rootChildren = new ArrayList<CommonTreeNode>();
			rootNode.setChildren(rootChildren);
			nodeList.add(rootNode);
			reRes("0", rootChildren);
		}

		if (!dbResMap.isEmpty()) {
			CommonTreeNode rootNode = rootNodeMap.get("delNode");
			List<CommonTreeNode> rootChildren = new ArrayList<CommonTreeNode>();
			rootNode.setChildren(rootChildren);
			nodeList.add(rootNode);

			for (String key : dbResMap.keySet()) {
				PrivilegeEO privilegeEO = dbResMap.get(key);
				if (null != privilegeEO) {
					String id = privilegeEO.getPrivilegeId();
					String name = privilegeEO.getPrivilegeName() + "----待删除";
					String type = getType(privilegeEO.getType());
					String icon = "ext/resTreeIcons/" + type + ".png";

					if (delFirstSet.add("del_" + type)) {
						List<CommonTreeNode> firstChildren = new ArrayList<CommonTreeNode>();
						CommonTreeNode firstNode = delFirstNodeMap.get("del_" + type);
						firstNode.setChildren(firstChildren);
						delFirstNodeMap.put("del_" + type, firstNode);
						rootChildren.add(firstNode);
					}

					CommonTreeNode node = new CommonTreeNode();
					node.setId(id);
					node.setName(name);
					node.setShowName(getTypeName(type) + ":" + name);
					node.setIcon(icon);
					node.setSn(privilegeEO.getSn());
					delFirstNodeMap.get("del_" + type).getChildren().add(node);
				}
			}
		}

		sort(nodeList);

		return nodeList.toArray(new TreeNode[nodeList.size()]);
	}

	private void sort(List<CommonTreeNode> nodeList) {
		Collections.sort(nodeList, new Comparator<CommonTreeNode>() {
			@Override
			public int compare(CommonTreeNode o1, CommonTreeNode o2) {
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

		for (CommonTreeNode node : nodeList) {
			List<CommonTreeNode> list = node.getChildren();
			if (null != list && !list.isEmpty()) {
				sort(list);
			}
		}
	}

	private void reRes(String pid, List<CommonTreeNode> children) {
		List<Resource> list = resMap.get(pid);
		if (null != list && !list.isEmpty()) {
			for (Resource resource : list) {

				String id = resource.getId();
				String name = resource.getName();
				String type = resource.getType();
				String icon = "ext/resTreeIcons/" + type + ".png";

				if ("0".equals(pid)) {
					if (auFirstSet.add("au_" + type)) {
						List<CommonTreeNode> firstChildren = new ArrayList<CommonTreeNode>();
						CommonTreeNode firstNode = auFirstNodeMap.get("au_" + type);
						firstNode.setChildren(firstChildren);
						auFirstNodeMap.put("au" + type, firstNode);
						children.add(firstNode);
					}
				}

				if (dbResSet.contains(id)) {
					name += "----待修改";
					dbResMap.remove(id);
				} else {
					name += "----待新增";
				}

				CommonTreeNode node = new CommonTreeNode();
				node.setId(id);
				node.setName(name);
				node.setShowName(getTypeName(type) + ":" + name);
				node.setIcon(icon);
				node.setSn(resource.getSn());
				if ("0".equals(pid)) {
					auFirstNodeMap.get("au_" + type).getChildren().add(node);
				} else {
					children.add(node);
				}

				List<CommonTreeNode> _children = new ArrayList<CommonTreeNode>();
				node.setChildren(_children);

				reRes(id, _children);
			}
		}
	}

	private String getType(String type) {
		if ("1".equals(type)) {
			return "service";
		} else if ("2".equals(type)) {
			return "form";
		} else if ("3".equals(type)) {
			return "dom";
		} else if ("4".equals(type)) {
			return "menu";
		} else if ("5".equals(type)) {
			return "entity";
		} else if ("6".equals(type)) {
			return "entity_op";
		} else if ("7".equals(type)) {
			return "rest";
		} else if ("9".equals(type)) {
			return "custom";
		} else {
			throw new IllegalArgumentException("未知类型" + type);
		}
	}

	private String getTypeName(String type) {
		if ("service".equals(type)) {
			return "服务";
		} else if ("form".equals(type)) {
			return "表单";
		} else if ("dom".equals(type)) {
			return "表单元素";
		} else if ("entity".equals(type)) {
			return "实体";
		} else if ("entity_op".equals(type)) {
			return "实体操作";
		}else if("rest".equals(type)){
			return "rest接口";
		} else if ("custom".equals(type)) {
			return "自定义";
		} else {
			throw new IllegalArgumentException("未知类型" + type);
		}
	}
}
