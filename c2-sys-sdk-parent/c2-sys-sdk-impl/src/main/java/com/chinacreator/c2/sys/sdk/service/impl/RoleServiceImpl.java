package com.chinacreator.c2.sys.sdk.service.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.service.RoleService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;


@Service("sdkRoleService")
@Path("/api/v1/roles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api("角色接口")
public class RoleServiceImpl implements RoleService {
    @Autowired
    @Qualifier("com.chinacreator.asp.comp.sys.advanced.role.service.RoleServiceImpl")
    private com.chinacreator.asp.comp.sys.advanced.role.service.RoleService roleSevice;

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

    public List<Role> query(Role role) {
        List<RoleDTO> roleList;

        if (role != null) {
            RoleDTO roleDTO = toDto(role);
            roleList = roleSevice.queryByRole(roleDTO);
        } else {
            roleList = roleSevice.queryAll();
        }

        List<Role> retList = Lists.transform(roleList,
                new Function<RoleDTO, Role>() {
                    public Role apply(RoleDTO input) {
                        return toBean(input);
                    }
                });

        return retList;
    }

    @GET
    @ApiOperation(value = "查询角色列表")
    public List<Role> query(@ApiParam("角色名字") @QueryParam("name") String name, @ApiParam("是否使用") @QueryParam("usage") Boolean usage, @ApiParam("角色类型") @QueryParam("type") String type, @ApiParam("创建人") @QueryParam("creator") String creator) {
        Role role = new Role();
        role.setName(name);
        role.setType(type);
        role.setUsage(usage);
        role.setCreator(creator);

        List<Role> roleList = query(role);

        return roleList;
    }
}
