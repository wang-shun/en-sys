package com.chinacreator.asp.sysmgmt.sysset.rolemgt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chinacreator.asp.comp.sys.basic.menu.dto.MenuDTO;
import com.chinacreator.asp.comp.sys.basic.menu.service.MenuService;
import com.chinacreator.asp.comp.sys.basic.privilege.service.PrivilegeService;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeInsiderelateDTO;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;
import com.chinacreator.asp.sysmgmt.sysset.resmgt.ResourceTreeNode;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.impl.DefaultTreeNode;

public class RoleResTreeNodeBuilder {

	private PrivilegeService privilegeService = ApplicationContextManager.getContext().getBean(PrivilegeService.class);
	private MenuService menuService = ApplicationContextManager.getContext().getBean(MenuService.class);
	private AccessControlService accessControlService = ApplicationContextManager.getContext().getBean(
			AccessControlService.class);

	private boolean isRelate;
	private Map<String, List<MenuDTO>> menuMap;
	private Map<String, String> menuHrefMap;
	private Map<String, List<PrivilegeDTO>> resMap;
	private Map<String, ResourceTreeNode> firstNodeMap;
	private Map<String, List<DefaultTreeNode>> menuChildrenMap;
	private Set<String> firstNodeSet;

	public RoleResTreeNodeBuilder(boolean isRelate) {
		this.isRelate = isRelate;

		menuMap = new HashMap<String, List<MenuDTO>>();
		menuHrefMap = new HashMap<String, String>();
		resMap = new HashMap<String, List<PrivilegeDTO>>();
		firstNodeMap = new HashMap<String, ResourceTreeNode>();
		menuChildrenMap = new HashMap<String, List<DefaultTreeNode>>();
		firstNodeSet = new HashSet<String>();

		buildMenuMap();
		buildResMap();

		String[] nodeStr = new String[] { "menu", "form", "dom", "entity", "entity_op", "service","rest", "custom" };
		for (int i = 0; i < nodeStr.length; i++) {
			String name = getTypeName(nodeStr[i]);

			ResourceTreeNode node = new ResourceTreeNode("0", nodeStr[i], name, nodeStr[i], true);
			node.setParent(true);
			node.setNoteTitle(name);
			node.setIcon("ext/resTreeIcons/" + nodeStr[i] + ".png");
			node.setSn(i + 1);
			firstNodeMap.put(nodeStr[i], node);
		}
	}

	private void buildMenuMap() {
		List<MenuDTO> menuList = menuService.queryAll();
		if (null != menuList && !menuList.isEmpty()) {
			for (MenuDTO menuDTO : menuList) {
				if (null != menuDTO && accessControlService.isPermitted(menuDTO.getPermExpr())) {
					String pid = menuDTO.getParentId();
					if (null == pid || pid.trim().equals("")) {
						pid = "0";
						menuDTO.setParentId("0");
					}

					List<MenuDTO> list = menuMap.get(pid);
					if (null == list) {
						list = new ArrayList<MenuDTO>();
					}
					list.add(menuDTO);
					menuMap.put(pid, list);

					String href = menuDTO.getHref();
					if (null != href && !href.trim().equals("")) {
						href = href.substring(href.startsWith("#/") ? 2 : 0,
								href.lastIndexOf("?") > 0 ? href.lastIndexOf("?") : href.length());
						menuHrefMap.put(href, menuDTO.getMenuId());
					}
				}
			}
		}
	}

	private void buildResMap() {

		Map<String, PrivilegeDTO> resDBMap = new HashMap<String, PrivilegeDTO>();
		Map<String, PrivilegeDTO> resNotRelateMap = new HashMap<String, PrivilegeDTO>();

		List<PrivilegeDTO> privilegeDTOList = privilegeService.queryAll();
		List<PrivilegeInsiderelateDTO> privilegeInsiderelateDTOList = null;
		if (isRelate) {
			privilegeInsiderelateDTOList = privilegeService.queryAllRelate();
		}

		if (null != privilegeDTOList && !privilegeDTOList.isEmpty()) {
			for (PrivilegeDTO privilegeDTO : privilegeDTOList) {
				if (!"4".equals(privilegeDTO.getType())
						&& (privilegeDTO.getVirtual() || accessControlService.isPermitted(privilegeDTO.getPermExpr()))) {
					resDBMap.put(privilegeDTO.getPrivilegeId(), privilegeDTO);
					resNotRelateMap.put(privilegeDTO.getPrivilegeId(), privilegeDTO);
				}
			}
		}

		if (null != privilegeInsiderelateDTOList && !privilegeInsiderelateDTOList.isEmpty()) {
			for (PrivilegeInsiderelateDTO privilegeInsiderelateDTO : privilegeInsiderelateDTOList) {
				String id = privilegeInsiderelateDTO.getId();
				String realteId = privilegeInsiderelateDTO.getRelateId();

				if (resDBMap.containsKey(id)) {
					PrivilegeDTO idPrivilegeDTO = resDBMap.get(id);
					if (!resDBMap.containsKey(realteId)) {
						realteId = "0";
					}
					List<PrivilegeDTO> dtoList = resMap.get(realteId);
					if (null == dtoList) {
						dtoList = new ArrayList<PrivilegeDTO>();
					}
					dtoList.add(idPrivilegeDTO);
					resMap.put(realteId, dtoList);

					resNotRelateMap.remove(id);
				}
			}
		}

		if (null != resNotRelateMap && !resNotRelateMap.isEmpty()) {
			for (String key : resNotRelateMap.keySet()) {
				List<PrivilegeDTO> dtoList = resMap.get("0");
				if (null == dtoList) {
					dtoList = new ArrayList<PrivilegeDTO>();
				}
				dtoList.add(resNotRelateMap.get(key));
				resMap.put("0", dtoList);
			}
		}
	}

	public List<ResourceTreeNode> build() {
		List<ResourceTreeNode> list = new ArrayList<ResourceTreeNode>();

		List<MenuDTO> menuList = menuMap.get("0");
		if (null != menuList && !menuList.isEmpty()) {
			ResourceTreeNode firstNode = firstNodeMap.get("menu");
			List<DefaultTreeNode> children = new ArrayList<DefaultTreeNode>();
			firstNode.setChildren(children);
			list.add(firstNode);
			firstNodeMap.put("menu", firstNode);
			firstNodeSet.add("menu");

			reMenu("0", children);
		}

		reRes(list, "0", null);
		
		sort(list);

		return list;
	}

	private void reRes(List<ResourceTreeNode> list, String pid, List<DefaultTreeNode> children) {
		List<PrivilegeDTO> privilegeDTOList = resMap.get(pid);
		if (null != privilegeDTOList && !privilegeDTOList.isEmpty()) {
			for (PrivilegeDTO privilegeDTO : privilegeDTOList) {
				if (null != privilegeDTO) {
					String resType = getType(privilegeDTO.getType());

					ResourceTreeNode treeNode = new ResourceTreeNode(privilegeDTO.getPrivilegeId(),
							privilegeDTO.getPrivilegeName());
					treeNode.setPrivilegeId(privilegeDTO.getPrivilegeId());
					treeNode.setNoteTitle(getTypeName(resType) + ":" + privilegeDTO.getPrivilegeName());
					treeNode.setIcon("ext/resTreeIcons/" + resType + ".png");
					privilegeDTO.setType(resType);
					treeNode.setObj(privilegeDTO);
					treeNode.setSn(privilegeDTO.getSn());
					treeNode.setSource(privilegeDTO.getSource());
					treeNode.setVirtual(privilegeDTO.getVirtual());

					if ("0".equals(pid)) {
						if (isRelate && "form".equals(resType) && menuHrefMap.containsKey(privilegeDTO.getParentId())) {
							String menuId = menuHrefMap.get(privilegeDTO.getParentId());
							List<DefaultTreeNode> menuChildren = menuChildrenMap.get(menuId);
							menuChildren.add(treeNode);
						} else {
							ResourceTreeNode firstNode = firstNodeMap.get(resType);
							if (firstNodeSet.add(resType)) {
								List<DefaultTreeNode> resChildren = new ArrayList<DefaultTreeNode>();
								firstNode.setChildren(resChildren);
								list.add(firstNode);
								firstNodeMap.put(resType, firstNode);
								resChildren.add(treeNode);
							}else{
								List<DefaultTreeNode> resChildren = firstNode.getChildren();
								resChildren.add(treeNode);
							}
						}
					} else {
						children.add(treeNode);
					}

					List<DefaultTreeNode> resChildren = new ArrayList<DefaultTreeNode>();
					treeNode.setChildren(resChildren);
					reRes(list, privilegeDTO.getPrivilegeId(), resChildren);
				}
			}
		}
	}

	private void reMenu(String pid, List<DefaultTreeNode> children) {
		List<MenuDTO> list = menuMap.get(pid);
		if (null != list && !list.isEmpty()) {
			for (MenuDTO menuDTO : list) {
				if (null != menuDTO) {
					ResourceTreeNode menuTreeNode = new ResourceTreeNode(menuDTO.getMenuId(), menuDTO.getMenuName());
					menuTreeNode.setPrivilegeId(menuDTO.getMenuId());
					menuTreeNode.setNoteTitle("菜单：" + menuDTO.getMenuName());
					menuTreeNode.setIcon("ext/resTreeIcons/menu.png");
					menuTreeNode.setType("menu");
					menuTreeNode.setObj(menuDTO);
					menuTreeNode.setSn(menuDTO.getSn());
					menuTreeNode.setSource(menuDTO.getSource());

					List<DefaultTreeNode> menuChildren = new ArrayList<DefaultTreeNode>();
					menuTreeNode.setChildren(menuChildren);

					children.add(menuTreeNode);

					String href = menuDTO.getHref();
					if (null != href && !href.trim().equals("")) {
						menuChildrenMap.put(menuDTO.getMenuId(), menuChildren);
					}

					reMenu(menuDTO.getMenuId(), menuChildren);
				}
			}
		}
	}
	
	private void sort(List<ResourceTreeNode> nodeList) {
		Collections.sort(nodeList, new Comparator<ResourceTreeNode>() {
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

		for (ResourceTreeNode node : nodeList) {
			List<DefaultTreeNode> list = node.getChildren();
			if (null != list && !list.isEmpty()) {	
				sortChildren(list);
			}
		}
	}
	
	private void sortChildren(List<DefaultTreeNode> nodeList) {
		Collections.sort(nodeList, new Comparator<DefaultTreeNode>() {
			@Override
			public int compare(DefaultTreeNode o1, DefaultTreeNode o2) {
				Integer i1 = ((ResourceTreeNode)o1).getSn();
				Integer i2 = ((ResourceTreeNode)o2).getSn();
				if (null == i1) {
					i1 = 0;
				}
				if (null == i2) {
					i2 = 0;
				}

				return i1.compareTo(i2);
			}
		});

		for (DefaultTreeNode node : nodeList) {
			List<DefaultTreeNode> list = node.getChildren();
			if (null != list && !list.isEmpty()) {	
				sortChildren(list);
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
		}else if("7".equals(type)){
			return "rest";
		} else if ("9".equals(type)) {
			return "custom";
		} else {
			return type;
		}
	}

	private String getTypeName(String type) {
		if ("menu".equals(type)) {
			return "菜单";
		} else if ("service".equals(type)) {
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
			return type;
		}
	}
}
