package com.chinacreator.asp.sysmgmt.sysset.jobmgt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chinacreator.asp.comp.sys.basic.menu.service.MenuService;
import com.chinacreator.asp.comp.sys.basic.privilege.service.PrivilegeService;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;
import com.chinacreator.asp.comp.sys.std.job.facade.JobFacade;
import com.chinacreator.asp.sysmgmt.sysset.resmgt.ResourceTreeNode;
import com.chinacreator.asp.sysmgmt.sysset.resmgt.ResourceTreeNodeBuilder;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;

public class JobResTreeContentProviderImpl implements TreeContentProvider {

	private PrivilegeService privilegeService = ApplicationContextManager
			.getContext().getBean(PrivilegeService.class);

	private AccessControlService accessControlService = ApplicationContextManager
			.getContext().getBean(AccessControlService.class);

	private MenuService menuService = ApplicationContextManager.getContext()
			.getBean(MenuService.class);

	private JobFacade jobFacade = ApplicationContextManager.getContext()
			.getBean(JobFacade.class);

	private static TreeNode[] resourceTreeNode;

	private static List<PrivilegeDTO> menuList;

	private static Map<String, Object> resMap;

	@Override
	public TreeNode[] getElements(TreeContext context) {
		List<TreeNode> list = new ArrayList<TreeNode>();
		if (null != context) {
			Map<String, Object> map = context.getConditions();
			String resId = (String) map.get("id");
			String type = (String) map.get("type");
			String isLoad = (String) map.get("isLoad");
			String[] jobIds = (String[]) map.get("jobIds");
			String presetId = (String) map.get("presetId");

			if (null == resId || resId.trim().equals("")
					|| resId.trim().equals("other") || null == resourceTreeNode
					|| null == menuList || "true".equals(isLoad)) {
				resMap = new HashMap<String, Object>();
				ResourceTreeNodeBuilder builder = new ResourceTreeNodeBuilder(
						allPrivilegeDTOList());
				resourceTreeNode = builder.build();
				menuList = getMenuList();
			}

			if (null == resId || resId.trim().equals("")) {
				ResourceTreeNode rootTreeNode = new ResourceTreeNode(null, "0",
						"资源树", "root", true);
				rootTreeNode.setChkDisabled(true);
				rootTreeNode.setNocheck(true);
				rootTreeNode.setNoteTitle("资源树");
				list.add(rootTreeNode);

				resId = "0";
			}

			if ("0".equals(resId)) {
				ResourceTreeNode menuTreeNode = new ResourceTreeNode("0",
						"menu", "菜单", "menu", true);
				menuTreeNode.setChkDisabled(true);
				menuTreeNode.setNocheck(true);
				menuTreeNode.setPrivilegeId("0");
				menuTreeNode.setNoteTitle("菜单");
				list.add(menuTreeNode);
			}

			if ("0".equals(resId) || "menu".equals(type)) {

				if (null != menuList && !menuList.isEmpty()) {
					String selResId = resId.equals("menu") ? "0" : resId;
					for (PrivilegeDTO privilegeDTO : menuList) {
						if (selResId.equals(privilegeDTO.getParentId())) {
							ResourceTreeNode menuTreeNode = new ResourceTreeNode(
									"0".equals(selResId) ? "menu"
											: privilegeDTO.getParentId(),
									privilegeDTO.getPrivilegeId(),
									privilegeDTO.getPrivilegeName(), "menu",
									false);
							menuTreeNode.setParent(isParentByMenu(
									privilegeDTO.getPrivilegeId(), menuList));
							menuTreeNode.setPrivilegeId(privilegeDTO
									.getPrivilegeId());
							menuTreeNode.setNoteTitle(getTitle(
									privilegeDTO.getPrivilegeName(), "menu"));
							menuTreeNode.setIcon("ext/resTreeIcons/menu.png");
							menuTreeNode.setObj(resMap.get(privilegeDTO
									.getPrivilegeId()));

							boolean checked = false;
							if (null != presetId && !presetId.trim().equals("")) {
								Set<String> presetIdSet = new HashSet<String>(
										Arrays.asList(presetId.split(",")));
								if (presetIdSet.contains(jobFacade
										.getAdministratorJobId())) {
									checked = true;
								} else {
									for (String id : presetIdSet) {
										if (jobFacade.hasPrivilege(id,
												privilegeDTO.getPrivilegeId())) {
											checked = true;
											break;
										}
									}
								}
							} 
							if (!checked && null != jobIds && jobIds.length == 1) {
								if (jobFacade.hasPrivilege(jobIds[0],
										privilegeDTO.getPrivilegeId())) {
									checked = true;
								}
							}
							menuTreeNode.setChecked(checked);
							list.add(menuTreeNode);
						}
					}
				}
			}

			if ("0".equals(resId) || !"menu".equals(type)) {
				if (null != resourceTreeNode && resourceTreeNode.length > 0) {
					for (TreeNode treeNode : resourceTreeNode) {
						ResourceTreeNode node = new ResourceTreeNode(
								treeNode.getId(), treeNode.getName());
						BeanCopierUtil
								.copy(((ResourceTreeNode) treeNode), node);
						if (resId.equals(node.getPid())) {
							if (node.isVirtual()) {
								node.setNoteTitle(node.getName());
								node.setChkDisabled(true);
								node.setNocheck(true);
							} else {
								node.setNoteTitle(getTitle(treeNode.getName(),
										node.getType()));
								node.setName(getNameByNodeName(treeNode
										.getName()));
								node.setIcon("ext/resTreeIcons/"
										+ node.getType() + ".png");
								node.setObj(resMap.get(node.getPrivilegeId()));
								
								boolean checked = false;
								if (null != presetId && !presetId.trim().equals("")) {
									Set<String> presetIdSet = new HashSet<String>(
											Arrays.asList(presetId.split(",")));
									if (presetIdSet.contains(jobFacade
											.getAdministratorJobId())) {
										checked = true;
									} else {
										for (String id : presetIdSet) {
											if (jobFacade.hasPrivilege(id,
													node.getPrivilegeId())) {
												checked = true;
												break;
											}
										}
									}
								} 
								if (!checked && null != jobIds && jobIds.length == 1) {
									if (jobFacade.hasPrivilege(jobIds[0],
											node.getPrivilegeId())) {
										checked = true;
									}
								}
								node.setChecked(checked);
							}
							
							list.add(node);
						}
					}
				}
			}
		}
		return list.toArray(new TreeNode[list.size()]);
	}

	private List<PrivilegeDTO> allPrivilegeDTOList() {
		List<PrivilegeDTO> allPrivilegeDTOList = new ArrayList<PrivilegeDTO>();
		List<PrivilegeDTO> privilegeDTOList = privilegeService.queryAll();
		if (null != privilegeDTOList && !privilegeDTOList.isEmpty()) {
			for (PrivilegeDTO privilegeDTO : privilegeDTOList) {
				if (accessControlService
						.isPermitted(privilegeDTO.getPermExpr())) {
					if (!"4".equals(privilegeDTO.getType())) {
						privilegeDTO.setType(getType(privilegeDTO.getType()));
						allPrivilegeDTOList.add(privilegeDTO);
						resMap.put(privilegeDTO.getPrivilegeId(), privilegeDTO);
					}
				}
			}
		}
		return allPrivilegeDTOList;
	}

	private List<PrivilegeDTO> getMenuList() {
		List<PrivilegeDTO> menuList = new ArrayList<PrivilegeDTO>();
		List<PrivilegeDTO> privilegeDTOList = privilegeService.queryAll();
		if (null != privilegeDTOList && !privilegeDTOList.isEmpty()) {
			for (PrivilegeDTO privilegeDTO : privilegeDTOList) {
				if (accessControlService
						.isPermitted(privilegeDTO.getPermExpr())) {
					if ("4".equals(privilegeDTO.getType())) {
						privilegeDTO.setType(getType(privilegeDTO.getType()));
						menuList.add(privilegeDTO);
						resMap.put(privilegeDTO.getPrivilegeId(), menuService
								.queryByPK(privilegeDTO.getPrivilegeId()));
					}
				}
			}
		}
		return menuList;
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
		} else if ("9".equals(type)) {
			return "custom";
		} else {
			return type;
		}
	}

	private String getNameByNodeName(String nodeName) {
		if (null != nodeName && !nodeName.trim().equals("")) {
			return nodeName.substring(nodeName.lastIndexOf("]") + 1);
		}
		return nodeName;
	}

	private String getTitle(String nodeName, String type) {
		if (null != nodeName && !nodeName.trim().equals("")) {
			return getTypeName(type) + nodeName;
		}
		return nodeName;
	}

	private String getTypeName(String type) {
		if ("menu".equals(type)) {
			return "[菜单]";
		} else if ("service".equals(type)) {
			return "[服务]";
		} else if ("form".equals(type)) {
			return "[表单]";
		} else if ("dom".equals(type)) {
			return "[表单元素]";
		} else if ("entity".equals(type)) {
			return "[实体]";
		} else if ("entity_op".equals(type)) {
			return "[实体操作]";
		} else if ("custom".equals(type)) {
			return "[自定义]";
		} else {
			return type;
		}
	}

	private boolean isParentByMenu(String menuId, List<PrivilegeDTO> menuList) {
		if (null != menuList && !menuList.isEmpty() && null != menuId
				&& !menuId.trim().equals("")) {
			for (PrivilegeDTO privilegeDTO : menuList) {
				if (menuId.equals(privilegeDTO.getParentId())) {
					return true;
				}
			}
		}
		return false;
	}
}
