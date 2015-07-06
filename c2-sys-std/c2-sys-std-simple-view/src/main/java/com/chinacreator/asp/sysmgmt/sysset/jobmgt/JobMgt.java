package com.chinacreator.asp.sysmgmt.sysset.jobmgt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.basic.privilege.service.PrivilegeService;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.std.job.facade.JobFacade;
import com.chinacreator.asp.sysmgmt.sysset.resmgt.ResourceTreeNode;
import com.chinacreator.asp.sysmgmt.sysset.resmgt.ResourceTreeNodeBuilder;
import com.chinacreator.c2.web.ds.TreeNode;

@Component
public class JobMgt {

	@Autowired
	private JobService jobService;

	@Autowired
	private JobFacade jobFacade;

	@Autowired
	private PrivilegeService privilegeService;

	public JobDTO getJobByPK(String jobId) {
		JobDTO jobDTO = new JobDTO();
		if (null != jobId && !jobId.trim().equals("")) {
			jobDTO = jobService.queryByPK(jobId);
		}
		return jobDTO;
	}

	public String getOrgIdByJobId(String jobId) {
		String orgId = "";

		if (null != jobId && !jobId.trim().equals("")) {
			JobDTO jobDTO = jobService.queryByPK(jobId);
			if ("0".equals(jobDTO.getJobScope())) {
				orgId = "-1";
			} else {
				OrgDTO orgDTO = jobService.queryOrgByJobId(jobId);
				if (null != orgDTO) {
					orgId = orgDTO.getOrgId();
				}
			}
		}

		return orgId;
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

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void jobSetUsers(String[] jobIds, String[] addUserIds,
			String[] delUserIds) {
		jobIds = toRepeat(jobIds);
		addUserIds = toRepeat(addUserIds);
		delUserIds = toRepeat(delUserIds);

		if (jobIds.length > 0) {
			if (delUserIds.length > 0) {
				jobService.removeFromUsers(jobIds, delUserIds);
			}
			if (addUserIds.length > 0) {
				jobService.addToUsers(jobIds, addUserIds);
			}
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void jobRemoveUsers(String[] jobIds) {
		jobIds = toRepeat(jobIds);
		if (jobIds.length > 0) {
			jobService.removeFromAllUsers(jobIds);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addJob(JobDTO jobDTO, String orgId, String presetId,
			String[] addResIds, String[] delResIds) {
		jobFacade.create(jobDTO, orgId);

		Set<String> addResIdSet = new HashSet<String>();
		Set<String> delResIdSet = new HashSet<String>();
		getAddAndDelResSet(addResIdSet, delResIdSet, presetId, addResIds,
				delResIds);

		if (!addResIdSet.isEmpty()) {
			jobFacade.assignPrivilege(jobDTO.getJobId(),
					addResIdSet.toArray(new String[addResIdSet.size()]));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void jobSetRes(String[] jobIds, String presetId, String[] addResIds,
			String[] delResIds) {
		jobIds = toRepeat(jobIds);
		if (jobIds.length > 0) {
			Set<String> addResIdSet = new HashSet<String>();
			Set<String> delResIdSet = new HashSet<String>();
			getAddAndDelResSet(addResIdSet, delResIdSet, presetId, addResIds,
					delResIds);

			if (!delResIdSet.isEmpty()) {
				if (jobIds.length == 1) {
					jobFacade
							.revokePrivileges(jobIds, delResIdSet
									.toArray(new String[delResIdSet.size()]));
				}
			}
			if (!addResIdSet.isEmpty()) {
				jobFacade.assignPrivilege(jobIds,
						addResIdSet.toArray(new String[addResIdSet.size()]));
			}
		}
	}

	private void getAddAndDelResSet(Set<String> addResIdSet,
			Set<String> delResIdSet, String presetId, String[] addResIds,
			String[] delResIds) {
		addResIds = toRepeat(addResIds);
		delResIds = toRepeat(delResIds);

		List<PrivilegeDTO> privilegeDTOs = new ArrayList<PrivilegeDTO>();

		if (null != presetId && !presetId.trim().equals("")) {
			String[] presetIds = toRepeat(presetId.split(","));
			Set<String> presetIdSet = new HashSet<String>(
					Arrays.asList(presetIds));
			if (presetIdSet.contains(jobFacade.getAdministratorJobId())) {
				privilegeDTOs = privilegeService.queryAll();
			} else {
				privilegeDTOs = jobFacade.queryPrivilegeByJobs(presetIds);
			}
		}

		for (PrivilegeDTO privilegeDTO : privilegeDTOs) {
			addResIdSet.add(privilegeDTO.getPrivilegeId());
		}
		if (null != addResIds && addResIds.length > 0) {
			addResIdSet.addAll(Arrays.asList(addResIds));
		}

		if (delResIds.length > 0) {
			List<PrivilegeDTO> resList = new ArrayList<PrivilegeDTO>();
			List<PrivilegeDTO> menuList = new ArrayList<PrivilegeDTO>();
			Map<String, PrivilegeDTO> privilegeMap = new HashMap<String, PrivilegeDTO>();
			buildPrivilegeList(resList, menuList, privilegeMap);

			ResourceTreeNodeBuilder builder = new ResourceTreeNodeBuilder(
					resList);
			Collection<TreeNode> resourceTreeNode = builder.buileToCollection();

			for (String delId : delResIds) {
				delResIdSet.add(delId);
				PrivilegeDTO privilegeDTO = privilegeMap.get(delId);
				if ("4".equals(privilegeDTO.getType())) {
					getChildByMenu(delId, menuList, delResIdSet);
				} else {
					getChildByTreeNode(privilegeDTO.getPrivilegeCode(),
							resourceTreeNode, delResIdSet);
				}
			}
			if (!delResIdSet.isEmpty()) {
				addResIdSet.removeAll(delResIdSet);
			}
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
