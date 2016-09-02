package com.chinacreator.asp.sysmgmt.sysset.jobresmgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.basic.privilege.service.PrivilegeService;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;
import com.chinacreator.asp.comp.sys.std.job.facade.JobFacade;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;
@Service("restreecontent")
public class ResTreeContentProviderImpl implements TreeContentProvider {

//	private PrivilegeService privilegeService = ApplicationContextManager
//			.getContext().getBean(PrivilegeService.class);
//
//	private JobFacade jobFacade = ApplicationContextManager.getContext()
//			.getBean(JobFacade.class);
//
//	private AccessControlService accessControlService = ApplicationContextManager
//			.getContext().getBean(AccessControlService.class);

	private static TreeNode[] resourceTreeNode;
	@Autowired
	private PrivilegeService privilegeService;
	@Autowired
	private JobFacade jobFacade;
	@Autowired
	private AccessControlService accessControlService;
	
	@Override
	public TreeNode[] getElements(TreeContext context) {
		List<TreeNode> list = new ArrayList<TreeNode>();
		if (null != context) {
			Map<String, Object> map = context.getConditions();
			String resId = (String) map.get("id");
			String type = (String) map.get("type");
			String jobId = (String) map.get("jobId");
			String checked = (String) map.get("checked");
			String virtual = (String) map.get("virtual");
			String isLoad = (String) map.get("isLoad");

			boolean isChecked = true;
			if (!"true".equals(virtual) && "false".equals(checked)) {
				isChecked = false;
			}

			if (null == resId || resId.trim().equals("")
					|| resId.trim().equals("other") || null == resourceTreeNode
					|| "true".equals(isLoad)) {
				ResourceTreeNodeBuilder builder = new ResourceTreeNodeBuilder(
						allPrivilegeDTOList());
				resourceTreeNode = builder.build();
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
				List<PrivilegeDTO> menuList = getMenuList();
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
//							menuTreeNode.setIcon("ext/resTreeIcons/menu.png");
							if (null != jobId && !jobId.trim().equals("")) {
								if (jobId.equals(jobFacade
										.getAdministratorJobId())) {
									menuTreeNode.setChecked(true);
									menuTreeNode.setChkDisabled(true);
								} else if (isChecked
										&& jobFacade.hasPrivilege(jobId,
												privilegeDTO.getPrivilegeId())) {
									menuTreeNode.setChecked(true);
								}
							}
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
								if (null != jobId && !jobId.trim().equals("")) {
									if (jobId.equals(jobFacade
											.getAdministratorJobId())) {
										node.setChecked(true);
										node.setChkDisabled(true);
									} else if (isChecked
											&& jobFacade.hasPrivilege(jobId,
													node.getPrivilegeId())) {
										node.setChecked(true);
									}
								}
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
