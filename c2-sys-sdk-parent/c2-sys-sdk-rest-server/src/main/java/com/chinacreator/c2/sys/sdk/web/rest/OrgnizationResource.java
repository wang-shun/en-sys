package com.chinacreator.c2.sys.sdk.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.PageBuilder;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;
import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.service.query.OrgnizationService;
import com.chinacreator.c2.sys.sdk.util.BeanUtils;
import com.chinacreator.c2.sys.sdk.util.PageableUtils;
import com.chinacreator.c2.sysmgr.User;
import com.chinacreator.c2.web.exception.ResourceNotFoundException;
import com.chinacreator.c2.web.util.SortableUtil;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Service
@Path("/v1/organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api("系统管理SDK - 机构")
@ApiResponses({
		@ApiResponse(code = 401, message = "用户没有登录", response = Error.class),
		@ApiResponse(code = 403, message = "没有权限访问", response = Error.class), })
public class OrgnizationResource {
	@Autowired
	OrgnizationService organizationService;

	@Autowired
    @Qualifier("com.chinacreator.asp.comp.sys.advanced.org.service.OrgServiceImpl")
    private OrgService orgService;

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
	public List<Organization> getParents(@PathParam("id") String id) {
		if (id != null) {
			List<Organization> orgList = organizationService.getParents(id);
			if(orgList==null){
				throw new ResourceNotFoundException("该机构 【"+id+"】 的父类不存在");
			}
			return orgList;
		}
		
		throw new UnsupportedOperationException();
	}

	@Path("/{id}/children/{cascade}")
	@GET
	@ApiOperation(value = "查询子机构，不包含自身")
	public List<Organization> getChildren(
			@ApiParam("机构ID") @PathParam("id") String id,
			@ApiParam("是否递归查询所有子机构") @PathParam("cascade") boolean cascade) {
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

	@Path("/{oid}/user/{uid}")
	@GET
	@ApiOperation(value="判断机构下是否有指定用户")
	public boolean containUser(
			@ApiParam("机构ID")@PathParam("oid")String oid, 
			@ApiParam("用户ID")@PathParam("uid")String uid){
		if(oid != null){
			boolean hasUser = organizationService.containsUser(oid, uid);
			return hasUser;
		}
		throw new ResourceNotFoundException("机构 【"+oid+"】下的用户 【"+uid+"】 不存在");	
	}
			
}
