package com.chinacreator.asp.sysmgmt.sysset.jobresmgt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.advanced.JobMessages;
import com.chinacreator.asp.comp.sys.advanced.job.dto.JobDTO;
import com.chinacreator.asp.comp.sys.advanced.job.service.JobService;
import com.chinacreator.asp.comp.sys.basic.MenuMessages;
import com.chinacreator.asp.comp.sys.basic.menu.dto.MenuDTO;
import com.chinacreator.asp.comp.sys.basic.menu.service.MenuService;
import com.chinacreator.asp.comp.sys.basic.privilege.service.PrivilegeService;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.core.PrivilegeMessages;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.std.job.facade.JobFacade;
import com.chinacreator.c2.web.ds.TreeNode;

@Component
public class JobResMgt {

	@Autowired
	private JobService jobService;

	@Autowired
	private JobFacade jobFacade;

	@Autowired
	private MenuService menuService;

	@Autowired
	private PrivilegeService privilegeService;

	public JobDTO getJobByPK(String jobId) {
		JobDTO jobDTO = new JobDTO();
		if (null != jobId && !jobId.trim().equals("")) {
			jobDTO = jobService.queryByPK(jobId);
		}
		return jobDTO;
	}

	public MenuDTO getMenuByPK(String menuId) {
		MenuDTO menuDTO = new MenuDTO();
		if (null != menuId && !menuId.trim().equals("")) {
			menuDTO = menuService.queryByPK(menuId);
		}
		return menuDTO;
	}

	public PrivilegeDTO getPrivilegeByPK(String privilegeId) {
		PrivilegeDTO privilegeDTO = new PrivilegeDTO();
		if (null != privilegeId && !privilegeId.trim().equals("")) {
			privilegeDTO = privilegeService.queryByPK(privilegeId);
		}
		return privilegeDTO;
	}

	public PrivilegeDTO getPrivilege(String privilegeId, String type,
			String parentId, String parentType) {
		PrivilegeDTO privilegeDTO = new PrivilegeDTO();
		if ("add".equals(type)) {
			if ("custom".equals(parentType)) {
				privilegeDTO.setCreatorTime(new Date());
				privilegeDTO.setParentId("other/");
				privilegeDTO.setSource("0");
				privilegeDTO.setType("9");
			}
		} else {
			if (null != privilegeId && !privilegeId.trim().equals("")) {
				privilegeDTO = privilegeService.queryByPK(privilegeId);
			}
		}
		return privilegeDTO;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void sortPrivilege(String... privilegeIds) {
		if (null != privilegeIds && privilegeIds.length > 0) {
			for (int i = 0; i < privilegeIds.length; i++) {
				PrivilegeDTO privilegeDTO = new PrivilegeDTO();
				privilegeDTO.setPrivilegeId(privilegeIds[i]);
				privilegeDTO.setSn(i);
				privilegeService.update(privilegeDTO);
			}
		}
	}

	public Map<String, String> validateFormByJob(String elementId,
			String elementValue, String formType, String orgId, String jobId) {
		Map<String, String> map = new HashMap<String, String>();
		String validate = "success";
		String errmess = "";
		if (null != elementId && !elementId.trim().equals("")
				&& null != elementValue && !elementValue.trim().equals("")
				&& null != formType && !formType.trim().equals("")) {
			elementId = elementId.trim();
			elementValue = elementValue.trim();
			if ("add".equals(formType)) {
				if ("jobName".equals(elementId)) {
					if ("-1".equals(orgId)) {
						if (jobFacade.existsJobNameByCommon(elementValue)) {
							validate = "error";
							errmess = JobMessages
									.getString("JOB.JOB_NAME_IS_EXISTS");
						}
					} else {
						if (jobFacade.existsJobNameByOrg(elementValue, orgId)) {
							validate = "error";
							errmess = JobMessages
									.getString("JOB.JOB_NAME_IS_EXISTS");
						}
					}
				}
			} else if ("edit".equals(formType)) {
				if ("jobName".equals(elementId)) {
					if ("-1".equals(orgId)) {
						if (jobFacade.existsJobNameByCommonIgnoreJobID(
								elementValue, jobId)) {
							validate = "error";
							errmess = JobMessages
									.getString("JOB.JOB_NAME_IS_EXISTS");
						}
					} else {
						if (jobFacade.existsJobNameByOrgIgnoreJobID(
								elementValue, orgId, jobId)) {
							validate = "error";
							errmess = JobMessages
									.getString("JOB.JOB_NAME_IS_EXISTS");
						}
					}
				}
			}
		}
		map.put("validate", validate);
		map.put("errmess", errmess);
		return map;
	}

	public Map<String, String> validateFormByMenu(String elementId,
			String elementValue, String formType, MenuDTO menuDTO) {
		Map<String, String> map = new HashMap<String, String>();
		String validate = "success";
		String errmess = "";
		if (null != elementId && !elementId.trim().equals("")
				&& null != elementValue && !elementValue.trim().equals("")
				&& null != formType && !formType.trim().equals("")) {
			elementId = elementId.trim();
			elementValue = elementValue.trim();
			if ("add".equals(formType)) {
				if ("menuName".equals(elementId)) {
					if (menuService.existsByMenuName(elementValue,
							menuDTO.getParentId())) {
						validate = "error";
						errmess = MenuMessages
								.getString("MENU.MENUNAME_IS_EXIST");
					}
				} else if ("menuCode".equals(elementId)) {
					if (menuService.existsByMenuCode(elementValue)) {
						validate = "error";
						errmess = MenuMessages
								.getString("MENU.MENUCODE_IS_EXIST");
					}
				}
			} else if ("edit".equals(formType)) {
				if ("menuName".equals(elementId)) {
					if (menuService.existsByMenuNameIgnoreMenuID(elementValue,
							menuDTO.getMenuId())) {
						validate = "error";
						errmess = MenuMessages
								.getString("MENU.MENUNAME_IS_EXIST");
					}
				}
			}
		}
		map.put("validate", validate);
		map.put("errmess", errmess);
		return map;
	}

	public Map<String, String> validateFormByRes(String elementId,
			String elementValue, String formType, PrivilegeDTO privilegeDTO) {
		Map<String, String> map = new HashMap<String, String>();
		String validate = "success";
		String errmess = "";
		if (null != elementId && !elementId.trim().equals("")
				&& null != elementValue && !elementValue.trim().equals("")
				&& null != formType && !formType.trim().equals("")) {
			elementId = elementId.trim();
			elementValue = elementValue.trim();
			if ("add".equals(formType)) {
				if ("privilegeCode".equals(elementId)) {
					if (privilegeService.existsByPrivilegeCode(privilegeDTO
							.getPrivilegeCode())) {
						validate = "error";
						errmess = PrivilegeMessages
								.getString("PRIVILEGE.PRIVILEGECODE_IS_EXISTS");
					}
				}
			} else if ("edit".equals(formType)) {
				if ("privilegeCode".equals(elementId)) {
					if (privilegeService
							.existsByPrivilegeCodeIgnorePrivilegeId(
									privilegeDTO.getPrivilegeCode(),
									privilegeDTO.getParentId())) {
						validate = "error";
						errmess = PrivilegeMessages
								.getString("PRIVILEGE.PRIVILEGECODE_IS_EXISTS");
					}
				}
			}
		}
		map.put("validate", validate);
		map.put("errmess", errmess);
		return map;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void jobSetPrivileges(String[] jobIds, String[] addPrivileges,
			String[] delPrivileges) {
		jobIds = toRepeat(jobIds);
		addPrivileges = toRepeat(addPrivileges);
		delPrivileges = toRepeat(delPrivileges);

		if (jobIds.length > 0) {
			Set<String> delIdSet = new HashSet<String>();
			if (delPrivileges.length > 0) {
				List<PrivilegeDTO> resList = new ArrayList<PrivilegeDTO>();
				List<PrivilegeDTO> menuList = new ArrayList<PrivilegeDTO>();
				Map<String, PrivilegeDTO> privilegeMap = new HashMap<String, PrivilegeDTO>();
				buildPrivilegeList(resList, menuList, privilegeMap);

				ResourceTreeNodeBuilder builder = new ResourceTreeNodeBuilder(
						resList);
				Collection<TreeNode> resourceTreeNode = builder
						.buileToCollection();

				for (String delId : delPrivileges) {
					delIdSet.add(delId);
					PrivilegeDTO privilegeDTO = privilegeMap.get(delId);
					if ("4".equals(privilegeDTO.getPrivilegeCode())) {
						getChildByMenu(delId, menuList, delIdSet);
					} else {
						getChildByTreeNode(privilegeDTO.getPrivilegeCode(),
								resourceTreeNode, delIdSet);
					}
				}
			}
			if (!delIdSet.isEmpty()) {
				jobFacade.revokePrivileges(jobIds,
						delIdSet.toArray(new String[delIdSet.size()]));
			}
			if (addPrivileges.length > 0) {
				jobFacade.assignPrivilege(jobIds, addPrivileges);
			}
		}
	}
	
	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deletePrivileges(String[] customIds,String[] menuIds){
			if(null!=customIds && customIds.length>0){
				privilegeService.deleteByPKs(customIds);
			}
			if(null!=menuIds && menuIds.length>0){
				menuService.deleteByPKs(menuIds);
			}
	}

	private String[] toRepeat(String[] array) {
		Set<String> set = new HashSet<String>();
		if (null != array && array.length > 0) {
			for (String id : array) {
				if (null != id && !id.trim().equals("")) {
					set.add(id);
				}
			}
		}
		return set.toArray(new String[set.size()]);
	}

	private void buildPrivilegeList(List<PrivilegeDTO> resList,
			List<PrivilegeDTO> menuList, Map<String, PrivilegeDTO> map) {
		List<PrivilegeDTO> privilegeDTOList = privilegeService.queryAll();
		if (null != privilegeDTOList && !privilegeDTOList.isEmpty()) {
			for (PrivilegeDTO privilegeDTO : privilegeDTOList) {
				map.put(privilegeDTO.getPrivilegeId(), privilegeDTO);
				if (!"4".equals(privilegeDTO.getType())) {
					resList.add(privilegeDTO);
				} else {
					menuList.add(privilegeDTO);
				}
			}
		}
	}

	private void getChildByMenu(String menuId, List<PrivilegeDTO> menuList,
			Set<String> set) {
		for (PrivilegeDTO privilegeDTO : menuList) {
			if (menuId.equals(privilegeDTO.getParentId())) {
				set.add(privilegeDTO.getPrivilegeId());
				getChildByMenu(privilegeDTO.getPrivilegeId(), menuList, set);
			}
		}
	}

	private void getChildByTreeNode(String code,
			Collection<TreeNode> resourceTreeNode, Set<String> set) {
		for (TreeNode treeNode : resourceTreeNode) {
			ResourceTreeNode node = (ResourceTreeNode) treeNode;
			if (code.equals(node.getPid())) {
				set.add(node.getPrivilegeId());
				getChildByTreeNode(node.getId(), resourceTreeNode, set);
			}
		}
	}
}
