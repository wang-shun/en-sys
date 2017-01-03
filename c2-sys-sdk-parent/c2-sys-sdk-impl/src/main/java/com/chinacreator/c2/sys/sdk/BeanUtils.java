package com.chinacreator.c2.sys.sdk;

import java.sql.Timestamp;
import java.util.Date;

import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.util.UserPropertyInjector;
import com.chinacreator.c2.sysmgr.User;

public class BeanUtils {
	  public static OrgDTO toDto(Organization org) {
	        OrgDTO orgDTO = new OrgDTO();
	        orgDTO.setCreatingTime(org.getCreatAt());
	        orgDTO.setCreator(org.getCreator());
	        orgDTO.setOrgDesc(org.getDesc());
	        orgDTO.setOrgId(org.getId());
	        orgDTO.setOrgName(org.getName());
	        orgDTO.setOrgNumber(org.getCode());
	        orgDTO.setOrgShowName(org.getName());
	        orgDTO.setParentId(org.getPid());

	        return orgDTO;
	    }

	    public static Organization toBean(OrgDTO orgDTO) {
	        Organization organization = new Organization();
	        Date creatingTime = orgDTO.getCreatingTime();

	        if (creatingTime != null) {
	            organization.setCreatAt(new Timestamp(creatingTime.getTime()));
	        }

	        organization.setCreator(orgDTO.getCreator());
	        organization.setDesc(orgDTO.getOrgDesc());
	        organization.setId(orgDTO.getOrgId());
	        organization.setName(orgDTO.getOrgName());
	        organization.setCode(orgDTO.getOrgNumber());
	        organization.setPid(orgDTO.getParentId());

	        return organization;
	    }
	    
	    public static RoleDTO toDto(Role role) {
	        RoleDTO roleDTO = new RoleDTO();
	        roleDTO.setOwnerId(role.getCreator());
	        roleDTO.setRoleDesc(role.getDesc());
	        roleDTO.setRoleId(role.getId());
	        roleDTO.setRoleName(role.getName());
	        roleDTO.setRoleType(role.getType());
	        roleDTO.setRoleUsage(role.getUsage());

	        return roleDTO;
	    }

	    public static Role toBean(RoleDTO roleDTO) {
	        Role role = new Role();
	        role.setCreator(roleDTO.getOwnerId());
	        role.setDesc(roleDTO.getRoleDesc());
	        role.setId(roleDTO.getRoleId());
	        role.setName(roleDTO.getRoleName());
	        role.setType(roleDTO.getRoleType());
	        role.setUsage(roleDTO.getRoleUsage());

	        return role;
	    }
	    
	    /**
	     * 将UserDTO转成User对象，支持业务系统注入User的属性
	     * @param userDTO
	     * @return
	     */
	    public static User toBean(UserDTO userDTO) {
	        return UserPropertyInjector.inject(userDTO);
	    }
}
