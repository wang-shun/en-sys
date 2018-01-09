package com.chinacreator.c2.sys.sdk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.c2.sys.sdk.BeanUtils;
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.service.query.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.advanced.role.service.RoleServiceImpl")
	private com.chinacreator.asp.comp.sys.advanced.role.service.RoleService roleService;

	@Override
	public Role get(String id) {
		RoleDTO roleDTO = roleService.queryByPK(id);
		if(roleDTO==null) return null;
		Role role = BeanUtils.toBean(roleDTO);
		return role;
	}

	@Override
	public Role getByName(String roleName) {
		RoleDTO roleDTO = roleService.queryByRoleName(roleName);
		if(roleDTO==null)	return null;
		Role role = BeanUtils.toBean(roleDTO);
		return role;
	}

}
