package com.chinacreator.c2.sys.sdk.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.service.query.OrganizationService;
import com.chinacreator.c2.sys.sdk.service.query.UserService;
import com.chinacreator.c2.sysmgr.User;
import com.chinacreator.c2.web.exception.ResourceNotFoundException;

@Service
@Path("/v1/organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(tags="organizations")
@ApiResponses({
		@ApiResponse(code = 401, message = "用户没有登录", response = Error.class),
		@ApiResponse(code = 403, message = "没有权限访问", response = Error.class), })
public class OrganizationResource {
	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	UserService userService;

	@Path("/{id}")
	@GET
	@ApiOperation(value = "获取机构数据")
	@ApiResponses({
			@ApiResponse(code = 200, message = "获取成功", response = Organization.class),
			@ApiResponse(code = 404, message = "机构不存在", response = Error.class) })
	public Organization get(@ApiParam("机构ID") @PathParam("id") String orgId) {
		if (orgId != null) {
			Organization orgnization = organizationService.get(orgId);
			if(orgnization == null){
				throw new ResourceNotFoundException("机构【"+orgId+"】不存在");
			}
			return orgnization;
		}
		
		throw new UnsupportedOperationException();
	}

	@Path("/{id}/parents")
	@GET
	public List<Organization> getParents(@ApiParam("机构ID")@PathParam("id") String id) {
		if (id != null) {
			List<Organization> orgList = organizationService.getParents(id);
			if(orgList==null){
				throw new ResourceNotFoundException("该机构 【"+id+"】 的父类不存在");
			}
			return orgList;
		}
		
		throw new UnsupportedOperationException();
	}

	@Path("/{id}/children")
	@GET
	@ApiOperation(value = "查询子机构，不包含自身")
	public List<Organization> getChildren(
			@ApiParam("机构ID") @PathParam("id") String id,
			@ApiParam("是否递归查询所有子机构") @QueryParam("cascade") boolean cascade) {
		if (id != null) {
			List<Organization> orgList = organizationService.getChildren(id,
					cascade);
			if(orgList == null){
				throw new ResourceNotFoundException("机构 【"+id+"】 的父类不存在");
			}
			return orgList;
		}
		
		throw new UnsupportedOperationException();
	}

	@Path("/{oid}/users/{uid}")
	@HEAD
	@ApiOperation(value="判断机构下是否有指定用户")
	public boolean containUser(
			@ApiParam("机构ID")@PathParam("oid")String oid, 
			@ApiParam("用户ID")@PathParam("uid")String uid){
		boolean flag = organizationService.containsUser(oid, uid);
		
		if(!flag) throw new ResourceNotFoundException("机构 【"+oid+"】下的用户 【"+uid+"】 不存在");
		
		return flag;
		
	}
	
	@Path("/{oid}/users")
	@GET
	@ApiOperation(value = "获取指定机构的用户详情")
	public List<User> queryByRoleInOrg(
			@ApiParam("机构ID")@PathParam("oid")String oid, 
			@ApiParam("角色ID")@QueryParam("role")String role, 
			@ApiParam("是否递归查询所有的子机构")@QueryParam("cascade")String cascade){
		List<User> users = null;
		if(StringUtils.isNotEmpty(role)){
			users = userService.queryByRoleInOrg(oid, role, Boolean.valueOf(cascade));
		}else{
			users = userService.queryByOrg(oid, Boolean.valueOf(cascade));
		}
		
		if(users==null || users.size()==0){
			return Collections.emptyList();
		}
		return users;
	}
			
}
