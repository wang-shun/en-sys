package com.chinacreator.c2.sys.sdk.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinacreator.c2.res.exception.ResourceNotFoundException;
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.service.query.RoleService;

@Service
@Path("/v1/roles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(tags="roles")
@ApiResponses({
		@ApiResponse(code = 401, message = "用户没有登录", response = Error.class),
		@ApiResponse(code = 403, message = "没有权限访问", response = Error.class), })
public class RoleResource {
	
	@Autowired
	RoleService roleService;
	
	@Path("/{id}")
	@GET
	@ApiOperation(value = "获取角色详情")
	@ApiResponses({
		@ApiResponse(code = 200, message = "机构信息获取成功", response = Role.class),
		@ApiResponse(code = 404, message = "机构不存在", response = Error.class) })
	public Role get(@ApiParam("角色ID")@PathParam("id")String id){
		Role role = roleService.get(id);
		if(role==null) throw new ResourceNotFoundException("角色id 【"+id+"】 不存在");
		
		return role;
	}

	@GET
	@ApiOperation(value = "通过角色名称获取角色详情")
	public Role getByName(@QueryParam("roleName")String roleName){
		if(roleName == null || roleName.length()==0) return null;
		Role role = roleService.getByName(roleName);
		if(role==null) throw new ResourceNotFoundException("角色名 【"+roleName+"】 不存在");
		
		return role;
	}
}
