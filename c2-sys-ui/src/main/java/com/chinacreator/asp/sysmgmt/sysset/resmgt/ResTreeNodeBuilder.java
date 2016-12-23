package com.chinacreator.asp.sysmgmt.sysset.resmgt;

import java.util.ArrayList;
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
import com.chinacreator.c2.ioc.ApplicationContextManager;

public class ResTreeNodeBuilder {

	private PrivilegeService privilegeService = ApplicationContextManager.getContext().getBean(PrivilegeService.class);
	private MenuService menuService = ApplicationContextManager.getContext().getBean(MenuService.class);
	private AccessControlService accessControlService = ApplicationContextManager.getContext().getBean(
			AccessControlService.class);

	private Map<String, PrivilegeDTO> resMap;
	private Map<String, List<PrivilegeInsiderelateDTO>> resRelateMap;
	private Map<String, PrivilegeDTO> resNotRelateMap;
	private Map<String, ResourceTreeNode> firstNodeMap;

	public ResTreeNodeBuilder() {
		List<PrivilegeDTO> privilegeDTOList = privilegeService.queryAll();
		List<PrivilegeInsiderelateDTO> privilegeInsiderelateDTOList = privilegeService.queryAllRelate();

		resMap = new HashMap<String, PrivilegeDTO>();
		resNotRelateMap = new HashMap<String, PrivilegeDTO>();
		resRelateMap = new HashMap<String, List<PrivilegeInsiderelateDTO>>();
		firstNodeMap = new HashMap<String, ResourceTreeNode>();

		if (null != privilegeDTOList && !privilegeDTOList.isEmpty()) {
			for (PrivilegeDTO privilegeDTO : privilegeDTOList) {
				if (!"4".equals(privilegeDTO.getType())
						&& (privilegeDTO.getVirtual() || accessControlService.isPermitted(privilegeDTO.getPermExpr()))) {
					resMap.put(privilegeDTO.getPrivilegeId(), privilegeDTO);
					resNotRelateMap.put(privilegeDTO.getPrivilegeId(), privilegeDTO);
				}
			}
		}

		if (null != privilegeInsiderelateDTOList && !privilegeInsiderelateDTOList.isEmpty()) {
			for (PrivilegeInsiderelateDTO privilegeInsiderelateDTO : privilegeInsiderelateDTOList) {
				String id = privilegeInsiderelateDTO.getId();
				String realteId = privilegeInsiderelateDTO.getRelateId();
				PrivilegeDTO idPrivilegeDTO = resMap.get(id);
				if (null != idPrivilegeDTO) {
					PrivilegeDTO realtePrivilegeDTO = resMap.get(realteId);
					if (null == realtePrivilegeDTO) {
						realteId = "0";
						privilegeInsiderelateDTO.setRelateId("0");
					}

					List<PrivilegeInsiderelateDTO> piList = resRelateMap.get(realteId);
					if (null == piList) {
						piList = new ArrayList<PrivilegeInsiderelateDTO>();
					}
					piList.add(privilegeInsiderelateDTO);

					resRelateMap.put(realteId, piList);
					resNotRelateMap.remove(id);
				}
			}
		}

		String[] nodeStr = new String[] { "menu", "form", "dom", "entity", "entity_op", "service", "rest", "custom" };
		for (int i = 0; i < nodeStr.length; i++) {
			String name = getTypeName(nodeStr[i]);

			ResourceTreeNode node = new ResourceTreeNode("0", nodeStr[i], name, nodeStr[i], true);
			node.setParent(true);
			node.setChkDisabled(true);
			node.setNocheck(true);
			node.setNoteTitle(name);
			node.setIcon("ext/resTreeIcons/" + nodeStr[i] + ".png");
			node.setSn(i + 1);
			firstNodeMap.put(nodeStr[i], node);
		}
	}

	public Map<String, List<ResourceTreeNode>> build() {
		Map<String, List<ResourceTreeNode>> treeNodeMap = new HashMap<String, List<ResourceTreeNode>>();

		Set<String> firstNodeSet = new HashSet<String>();

		List<MenuDTO> menuList = menuService.queryAll();
		if (null != menuList && !menuList.isEmpty()) {
			firstNodeSet.add("menu");
			for (MenuDTO menuDTO : menuList) {
				if (accessControlService.isPermitted(menuDTO.getPermExpr())) {
					String pid = "0".equals(menuDTO.getParentId()) ? "menu" : menuDTO.getParentId();

					ResourceTreeNode menuTreeNode = new ResourceTreeNode(pid, menuDTO.getMenuId(),
							menuDTO.getMenuName(), "menu", false);
					menuTreeNode.setParent(existsChildMenusByPerm(menuDTO.getMenuId()));
					menuTreeNode.setPrivilegeId(menuDTO.getMenuId());
					menuTreeNode.setNoteTitle("菜单：" + menuDTO.getMenuName());
					menuTreeNode.setIcon("ext/resTreeIcons/menu.png");
					menuTreeNode.setObj(menuDTO);
					menuTreeNode.setSn(menuDTO.getSn());

					List<ResourceTreeNode> menuTreeNodeList = treeNodeMap.get(pid);
					if (null == menuTreeNodeList) {
						menuTreeNodeList = new ArrayList<ResourceTreeNode>();
					}
					menuTreeNodeList.add(menuTreeNode);

					treeNodeMap.put(pid, menuTreeNodeList);
				}
			}
		}

		if (null != resRelateMap && !resRelateMap.isEmpty()) {
			for (String key : resRelateMap.keySet()) {
				List<PrivilegeInsiderelateDTO> piList = resRelateMap.get(key);
				for (PrivilegeInsiderelateDTO privilegeInsiderelateDTO : piList) {
					String id = privilegeInsiderelateDTO.getId();
					String realteId = privilegeInsiderelateDTO.getRelateId();

					PrivilegeDTO privilegeDTO = resMap.get(id);
					if (null != privilegeDTO) {
						String resType = getType(privilegeDTO.getType());
						if ("0".equals(realteId)) {
							firstNodeSet.add(resType);
						}

						String pid = "0".equals(realteId) ? resType : realteId;

						ResourceTreeNode treeNode = new ResourceTreeNode(pid, privilegeDTO.getPrivilegeId(),
								privilegeDTO.getPrivilegeName(), resType, privilegeDTO.getVirtual());
						treeNode.setParent(null != resRelateMap.get(privilegeDTO.getPrivilegeId())
								&& !resRelateMap.get(privilegeDTO.getPrivilegeId()).isEmpty());
						treeNode.setPrivilegeId(privilegeDTO.getPrivilegeId());
						treeNode.setNoteTitle(getTypeName(resType) + ":" + privilegeDTO.getPrivilegeName());
						treeNode.setIcon("ext/resTreeIcons/" + resType + ".png");
						privilegeDTO.setType(resType);
						treeNode.setObj(privilegeDTO);
						treeNode.setSn(privilegeInsiderelateDTO.getSn());

						List<ResourceTreeNode> treeNodeList = treeNodeMap.get(pid);
						if (null == treeNodeList) {
							treeNodeList = new ArrayList<ResourceTreeNode>();
						}
						treeNodeList.add(treeNode);

						treeNodeMap.put(pid, treeNodeList);
					}
				}
			}
		}

		if (null != resNotRelateMap && !resNotRelateMap.isEmpty()) {
			for (String key : resNotRelateMap.keySet()) {
				PrivilegeDTO privilegeDTO = resNotRelateMap.get(key);
				if (null != privilegeDTO) {
					String resType = getType(privilegeDTO.getType());

					firstNodeSet.add(resType);

					ResourceTreeNode treeNode = new ResourceTreeNode(resType, privilegeDTO.getPrivilegeId(),
							privilegeDTO.getPrivilegeName(), resType, privilegeDTO.getVirtual());
					treeNode.setParent(false);
					treeNode.setPrivilegeId(privilegeDTO.getPrivilegeId());
					treeNode.setNoteTitle(getTypeName(resType) + ":" + privilegeDTO.getPrivilegeName());
					treeNode.setIcon("ext/resTreeIcons/" + resType + ".png");
					privilegeDTO.setType(resType);
					treeNode.setObj(privilegeDTO);
					treeNode.setSn(privilegeDTO.getSn());
					treeNode.setSource(privilegeDTO.getSource());

					List<ResourceTreeNode> treeNodeList = treeNodeMap.get(resType);
					if (null == treeNodeList) {
						treeNodeList = new ArrayList<ResourceTreeNode>();
					}
					treeNodeList.add(treeNode);

					treeNodeMap.put(resType, treeNodeList);
				}
			}
		}

		boolean isCustom = true;
		for (String key : firstNodeSet) {
			if (firstNodeMap.containsKey(key)) {
				List<ResourceTreeNode> treeNodeList = treeNodeMap.get("0");
				if (null == treeNodeList) {
					treeNodeList = new ArrayList<ResourceTreeNode>();
				}
				treeNodeList.add(firstNodeMap.get(key));

				treeNodeMap.put("0", treeNodeList);

				if ("custom".equals(key)) {
					isCustom = false;
				}
			}
		}
		if (isCustom) {
			List<ResourceTreeNode> treeNodeList = treeNodeMap.get("0");
			if (null == treeNodeList) {
				treeNodeList = new ArrayList<ResourceTreeNode>();
			}
			ResourceTreeNode treeNode = firstNodeMap.get("custom");
			treeNode.setParent(false);
			treeNodeList.add(treeNode);

			treeNodeMap.put("0", treeNodeList);
		}

		return treeNodeMap;
	}

	private boolean existsChildMenusByPerm(String menuId) {
		boolean exists = false;
		List<MenuDTO> menuList = menuService.queryChildren(menuId, false);
		if (null != menuList && !menuList.isEmpty()) {
			for (MenuDTO menuDTO : menuList) {
				if (accessControlService.isPermitted(menuDTO.getPermExpr())) {
					exists = true;
					break;
				}
			}
		}
		return exists;
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
		} else if ("rest".equals(type)) {
			return "rest接口";
		} else if ("custom".equals(type)) {
			return "自定义";
		} else {
			return type;
		}
	}
}
