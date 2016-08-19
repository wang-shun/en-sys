package com.chinacreator.c2.sys.sdk.service.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.service.AuthorizationService;
import com.chinacreator.c2.web.exception.ResourceNotFoundException;
import com.chinacreator.platform.mvc.perm.Resource;

@Service
@Path("/v1/authorizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api("系统管理 - 权限")
@ApiResponses({ @ApiResponse(code = 401, message = "没有权限访问", response = Error.class), })
public class AuthorizationServiceImpl implements AuthorizationService {

	@GET
	@Path("/resources")
	@ApiOperation("获取所有权限资源")
	public List<Resource> getResources() {
		return null;
	}

	@GET
	@Path("/resources/{id}")
	@ApiOperation("获取权限资源")
	@ApiResponses({ @ApiResponse(code = 404, message = "权限资源不存在", response = Error.class), })
	public Resource getResource(@PathParam("id") String id)
			throws ResourceNotFoundException {
		return null;
	}

	@GET
	@Path("/resources/{id}/roles")
	@ApiOperation("有权访问该资源的角色列表")
	@ApiResponses({ @ApiResponse(code = 404, message = "权限资源不存在", response = Error.class), })
	public List<Role> getAuthorizedRoles(String resource)
			throws ResourceNotFoundException {
		return null;
	}

	@PUT
	@ApiOperation("授权")
	@ApiResponses({ @ApiResponse(code = 404, message = "权限资源不存在", response = Error.class), })
	public void grant(@QueryParam("role") String role,
			@QueryParam("resources") Set<String> resources)
			throws ResourceNotFoundException {

	}

	@DELETE
	@ApiOperation("取消授权")
	public void cancelGranted(@QueryParam("role") String role,
			@QueryParam("resources") Set<String> resources) {

	}
}
