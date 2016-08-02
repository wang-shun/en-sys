package com.chinacreator.c2.sys.sdk.service.impl;

import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;

import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("sdkRoleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    @Qualifier("com.chinacreator.asp.comp.sys.advanced.role.service.RoleServiceImpl")
    private com.chinacreator.asp.comp.sys.advanced.role.service.RoleService roleSevice;

    @Override
    public List<Role> query(Role role) {
        RoleDTO roleDTO=new RoleDTO();
        BeanCopierUtil.copy(role, roleDTO);
        List<RoleDTO> roleList = roleSevice.queryByRole(roleDTO);
        List<Role> retList = new ArrayList<Role>();
        BeanCopierUtil.copy(roleList, retList, RoleDTO.class, Role.class);

        return retList;
    }
}
