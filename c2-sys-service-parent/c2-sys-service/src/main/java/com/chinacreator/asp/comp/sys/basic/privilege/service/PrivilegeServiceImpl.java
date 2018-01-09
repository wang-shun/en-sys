package com.chinacreator.asp.comp.sys.basic.privilege.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.basic.user.service.UserService;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.CommonPropertiesUtil;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;

/**
 * 权限服务接口实现
 * 
 * @author 彭盛
 * 
 */
@Service
public class PrivilegeServiceImpl
		extends
		com.chinacreator.asp.comp.sys.core.privilege.service.PrivilegeServiceImpl
		implements PrivilegeService {

	@Autowired
	private AccessControlService accessControlService;

	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.basic.user.service.UserServiceImpl")
	private UserService userService;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void create(PrivilegeDTO privilegeDTO) {
		String userId = null;
		if (null == privilegeDTO.getCreator()) {
			try {
				userId = accessControlService.getUserID();
			} catch (Exception e) {
			}
			if (null != userId && !userId.trim().equals("")) {
				privilegeDTO.setCreator(userId);
			}
		} else {
			userId = privilegeDTO.getCreator();
		}

		if (null == privilegeDTO.getPermExpr()
				|| privilegeDTO.getPermExpr().trim().equals("")) {
			privilegeDTO.setPermExpr(privilegeDTO.getPrivilegeCode());
		}
		if (null == privilegeDTO.getSource()
				|| privilegeDTO.getSource().trim().equals("")) {
			privilegeDTO.setSource("0");
		}

		super.create(privilegeDTO);

		// 如果不是超级管理员，添加该用户资源权限
		if (null != userId && !userId.trim().equals("")
				&& !userId.trim().equals(CommonPropertiesUtil.getAdminUserId())) {
			List<OrgDTO> orgDTOList = userService.queryOrgs(userId);
			for (OrgDTO orgDTO : orgDTOList) {
				userService.assignPrivilege(userId,
						privilegeDTO.getPrivilegeId(), orgDTO.getOrgId());
			}
		}
	}
}
