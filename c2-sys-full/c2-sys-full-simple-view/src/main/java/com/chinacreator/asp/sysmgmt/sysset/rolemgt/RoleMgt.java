package com.chinacreator.asp.sysmgmt.sysset.rolemgt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.advanced.role.service.RoleService;
import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.RoleMessages;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.basic.privilege.service.PrivilegeService;
import com.chinacreator.asp.comp.sys.basic.role.dto.RoleTypeDTO;
import com.chinacreator.asp.comp.sys.basic.role.service.RoleTypeService;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;

@Component
public class RoleMgt {

	@Autowired
	private RoleTypeService roleTypeService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AccessControlService accessControlService;

	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.advanced.user.service.UserServiceImpl")
	private UserService userService;

	@Autowired
	private PrivilegeService privilegeService;

	public RoleTypeDTO getRoleTypeByPK(String roleTypeId) {
		RoleTypeDTO roleTypeDTO = new RoleTypeDTO();
		if (null != roleTypeId && !roleTypeId.trim().equals("")) {
			roleTypeDTO = roleTypeService.queryByPK(roleTypeId);
		} else {
			String userId = accessControlService.getUserID();
			roleTypeDTO.setCreatorUserId(userId);
			OrgDTO orgDTO = userService.queryMainOrg(userId);
			if (null != orgDTO) {
				roleTypeDTO.setCreatorOrgId(orgDTO.getOrgId());
			}
		}

		return roleTypeDTO;
	}

	public RoleDTO getRoleByPK(String roleId) {
		RoleDTO roleDTO = new RoleDTO();
		if (null != roleId && !roleId.trim().equals("")) {
			roleDTO = roleService.queryByPK(roleId);
		} else {
			roleDTO.setOwnerId(accessControlService.getUserID());
			roleDTO.setRoleUsage(true);
		}

		return roleDTO;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addRole(RoleDTO roleDTO, String presetId, String[] addResIds, String[] delResIds) {
		roleService.create(roleDTO);

		Set<String> addResIdSet = new HashSet<String>();
		Set<String> delResIdSet = new HashSet<String>();
		getAddAndDelResSet(addResIdSet, delResIdSet, presetId, addResIds, delResIds);

		if (!addResIdSet.isEmpty()) {
			roleService.assignPrivileges(new String[] { roleDTO.getRoleId() },
					addResIdSet.toArray(new String[addResIdSet.size()]));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void roleSetRes(String[] roleIds, String presetId, String[] addResIds, String[] delResIds) {
		roleIds = toRepeat(roleIds);
		if (roleIds.length > 0) {
			Set<String> addResIdSet = new HashSet<String>();
			Set<String> delResIdSet = new HashSet<String>();
			getAddAndDelResSet(addResIdSet, delResIdSet, presetId, addResIds, delResIds);

			if (!delResIdSet.isEmpty()) {
				if (roleIds.length == 1) {
					roleService.revokePrivileges(roleIds, delResIdSet.toArray(new String[delResIdSet.size()]));
				}
			}
			if (!addResIdSet.isEmpty()) {
				roleService.assignPrivileges(roleIds, addResIdSet.toArray(new String[addResIdSet.size()]));
			}
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void roleSetOrgs(String[] roleIds, String[] addOrgIds, String[] delOrgIds) {
		roleIds = toRepeat(roleIds);
		addOrgIds = toRepeat(addOrgIds);
		delOrgIds = toRepeat(delOrgIds);

		if (roleIds.length > 0) {
			if (delOrgIds.length > 0) {
				roleService.revokeFromOrgs(roleIds, delOrgIds);
			}
			if (addOrgIds.length > 0) {
				roleService.assignToOrgs(roleIds, addOrgIds);
			}
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void roleSetUsers(String[] roleIds, List<Map<String, String>> addUsers, List<Map<String, String>> delUsers) {
		roleIds = toRepeat(roleIds);

		if (roleIds.length > 0) {
			if (null != delUsers && !delUsers.isEmpty()) {
				for (Map<String, String> map : delUsers) {
					String orgId = map.get("orgId");
					String userId = map.get("userId");

					roleService.revokeFromUsers(roleIds, new String[] { userId }, orgId);
				}
			}

			if (null != addUsers && !addUsers.isEmpty()) {
				for (Map<String, String> map : addUsers) {
					String orgId = map.get("orgId");
					String userId = map.get("userId");

					roleService.assignToUsers(roleIds, new String[] { userId }, orgId);
				}
			}
		}
	}

	private void getAddAndDelResSet(Set<String> addResIdSet, Set<String> delResIdSet, String presetId,
			String[] addResIds, String[] delResIds) {
		addResIds = toRepeat(addResIds);
		delResIds = toRepeat(delResIds);

		List<PrivilegeDTO> privilegeDTOs = new ArrayList<PrivilegeDTO>();

		if (null != presetId && !presetId.trim().equals("")) {
			String[] presetIds = toRepeat(presetId.split(","));
			Set<String> presetIdSet = new HashSet<String>(Arrays.asList(presetIds));
			if (presetIdSet.contains(roleService.getAdministratorRoleId())) {
				privilegeDTOs = privilegeService.queryAll();
			} else {
				for (String roleId : presetIdSet) {
					privilegeDTOs.addAll(roleService.queryPrivileges(roleId));
				}
			}
		}

		for (PrivilegeDTO privilegeDTO : privilegeDTOs) {
			if (!privilegeDTO.getVirtual()) {
				addResIdSet.add(privilegeDTO.getPrivilegeId());
			}
		}
		if (null != addResIds && addResIds.length > 0) {
			addResIdSet.addAll(Arrays.asList(addResIds));
		}

		if (null != delResIds && delResIds.length > 0) {
			delResIdSet.addAll(Arrays.asList(delResIds));
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

	public Map<String, String> validateFormByRoleType(String elementId, String elementValue, String formType,
			String roleTypeId) {
		Map<String, String> map = new HashMap<String, String>();
		String validate = "success";
		String errmess = "";
		if (null != elementId && !elementId.trim().equals("") && null != elementValue
				&& !elementValue.trim().equals("") && null != formType && !formType.trim().equals("")) {
			elementId = elementId.trim();
			elementValue = elementValue.trim();
			if ("add".equals(formType)) {
				if ("typeName".equals(elementId)) {
					if (roleTypeService.existsByRoleTypeName(elementValue)) {
						validate = "error";
						errmess = RoleMessages.getString("ROLE.ROLETYPE_NAME_IS_EXIST");
					}
				}
			} else if ("edit".equals(formType)) {
				if ("typeName".equals(elementId)) {
					if (roleTypeService.existsByRoleTypeNameIgnoreRoleTypeId(elementValue, roleTypeId)) {
						validate = "error";
						errmess = RoleMessages.getString("ROLE.ROLETYPE_NAME_IS_EXIST");
					}
				}
			}
		}
		map.put("validate", validate);
		map.put("errmess", errmess);
		return map;
	}

	public Map<String, String> validateFormByRole(String elementId, String elementValue, String formType, String roleId) {
		Map<String, String> map = new HashMap<String, String>();
		String validate = "success";
		String errmess = "";
		if (null != elementId && !elementId.trim().equals("") && null != elementValue
				&& !elementValue.trim().equals("") && null != formType && !formType.trim().equals("")) {
			elementId = elementId.trim();
			elementValue = elementValue.trim();
			if ("add".equals(formType)) {
				if ("roleName".equals(elementId)) {
					if (roleService.existsByRoleName(elementValue)) {
						validate = "error";
						errmess = com.chinacreator.asp.comp.sys.core.RoleMessages.getString("ROLE.ROLENAME_IS_EXIST");
					}
				}
			} else if ("edit".equals(formType)) {
				if ("roleName".equals(elementId)) {
					if (roleService.existsByRoleNameIgnoreRoleID(elementValue, roleId)) {
						validate = "error";
						errmess = com.chinacreator.asp.comp.sys.core.RoleMessages.getString("ROLE.ROLENAME_IS_EXIST");
					}
				}
			}
		}
		map.put("validate", validate);
		map.put("errmess", errmess);
		return map;
	}
}
