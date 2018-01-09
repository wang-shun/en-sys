package com.chinacreator.asp.sysmgmt.sysset.usermgt;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.common.CommonPropertiesUtil;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.sysmgmt.mypanel.MyPanelMessages;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

public class UserDataArrayContentProviderImpl implements ArrayContentProvider {

	private UserService userService = ApplicationContextManager.getContext().getBean(UserService.class);

	@Override
	public Page<UserDTO> getElements(ArrayContext context) {
		Page<UserDTO> page = new Page<UserDTO>(new Pageable(), new ArrayList<UserDTO>());
		if (null != context) {
			Map<String, Object> map = context.getCondition();
			if (null != map && !map.isEmpty()) {
				String orgId = (String) map.get("orgId");
				String roleId = (String) map.get("roleId");
				UserDTO userDto = new UserDTO();
				try {
					BeanUtils.populate(userDto, map);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					return page;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					return page;
				} catch (Exception e) {
					e.printStackTrace();
					return page;
				}

				Page<UserDTO> userPage = userService.queryByOrgRole(userDto, orgId, roleId, context.getPageable(),
						context.getSortable());
				if (null != userPage) {
					List<UserDTO> userDTOs = userPage.getContents();
					for (UserDTO dto : userDTOs) {
						dto.setExtFields(getExtFields(dto.getUserId(), orgId));
					}
					page = new Page<UserDTO>(context.getPageable(), userDTOs);
				}
			}

		}
		return page;
	}

	private Map<String, Object> getExtFields(String userId, String orgId) {
		Map<String, Object> extFields = new HashMap<String, Object>();
		if (null != userId && !userId.trim().equals("")) {
			List<OrgDTO> orgList = userService.queryOrgs(userId);
			OrgDTO mainOrg = userService.queryMainOrg(userId);
			String mainOrgId = null;
			if (null != mainOrg) {
				mainOrgId = mainOrg.getOrgId();
				extFields.put("mainOrgShowName", mainOrg.getOrgShowName());
			}
			StringBuffer str = new StringBuffer();
			StringBuffer strOrgIds = new StringBuffer();
			for (OrgDTO orgDTO : orgList) {
				if (null != orgDTO) {
					if (null != mainOrgId && !mainOrgId.trim().equals("") && mainOrgId.equals(orgDTO.getOrgId())) {
						str.insert(0, ",");
						str.insert(0, orgDTO.getOrgShowName());
						str.insert(0, MyPanelMessages.getString("MYPANEL.MAINORG"));
						strOrgIds.insert(0, ",");
						strOrgIds.insert(0, orgDTO.getOrgId());
					} else {
						str.append(orgDTO.getOrgShowName());
						str.append(",");
						
						strOrgIds.append(orgDTO.getOrgId());
						strOrgIds.append(",");
					}
				}
			}

			extFields.put("orgShowName", (str.length() > 0 ? str.substring(0, str.length() - 1) : ""));
			extFields.put("orgIds", (strOrgIds.length()>0?strOrgIds.substring(0, strOrgIds.length()-1):""));

			List<RoleDTO> roleList = new ArrayList<RoleDTO>();
			if (null != orgId && !orgId.trim().equals("") && !orgId.trim().equals("0")) {
				roleList = userService.queryDirectRoles(userId, orgId);
			} else {
				roleList = userService.queryDirectRoles(userId);
			}

			StringBuffer roleNames = new StringBuffer();
			for (RoleDTO roleDTO : roleList) {
				if (null != roleDTO && !roleDTO.getRoleId().equals(CommonPropertiesUtil.getRoleofeveryoneRoleId())) {
					roleNames.append(roleDTO.getRoleName());
					roleNames.append(",");
				}
			}

			extFields.put("roleNames", (roleNames.length() > 0 ? roleNames.substring(0, roleNames.length() - 1) : ""));
		}
		return extFields;
	}
}
