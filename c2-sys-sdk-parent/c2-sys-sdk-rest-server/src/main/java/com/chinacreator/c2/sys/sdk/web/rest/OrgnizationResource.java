package com.chinacreator.c2.sys.sdk.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
import org.springframework.stereotype.Service;

import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.service.query.OrgnizationService;
import com.chinacreator.c2.web.exception.ResourceNotFoundException;

@Service
@Path("/v1/remote/organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api("系统管理SDK - 机构")
@ApiResponses({
		@ApiResponse(code = 401, message = "用户没有登录", response = Error.class),
		@ApiResponse(code = 403, message = "没有权限访问", response = Error.class), })
public class OrgnizationResource {
	@Autowired
	OrgnizationService orgService;
	
	@GET
	@ApiOperation(value = "查询机构")
	public List<Organization> query(
			@ApiParam("机构ID") @QueryParam("code") String code,
			@QueryParam("name") String name, @QueryParam("pid") String pid,
			@QueryParam("creator") String creator,
			@QueryParam("level") String level) {
		if(code!=null){
			
		}else if(pid!=null){
			int cascadeLevel = level==null?-1:Integer.parseInt(level) ;
			List<Organization> allDept = orgService.getChildren(pid, true) ;
			Iterator<Organization> iterator = allDept.iterator();
			while(iterator.hasNext()){
				Organization next = iterator.next();
				//TODO 
				iterator.remove() ;
			}
			return 
		}else if(name!=null){
			
		}else if(creator!=null){
			
		}
		throw new UnsupportedOperationException();
	}

	@Path("/{id}")
	@GET
	@ApiOperation(value = "获取机构数据")
	@ApiResponses({
			@ApiResponse(code = 200, message = "获取成功", response = Organization.class),
			@ApiResponse(code = 404, message = "机构不存在", response = Error.class) })
	public Organization get(@ApiParam("机构ID") @PathParam("id") String orgId) {
		throw new UnsupportedOperationException();
	}

	@Path("/{id}/parents")
	@GET
	public List<Organization> getParents(@PathParam("id") String id) {
		throw new UnsupportedOperationException();
	}

	@Path("/{id}/children")
	@GET
	@ApiOperation(value = "查询子机构，不包含自身")
	public List<Organization> getChildren(
			@ApiParam("机构ID") @PathParam("id") String id,
			@ApiParam("是否递归查询所有子机构") @PathParam("cascade") boolean cascade) {
		
		throw new UnsupportedOperationException();
	}

	@Path("/{id}/users")
	@GET
	@ApiOperation(value = "获取机构下所有用户")
	@ApiResponses({
			@ApiResponse(code = 200, message = "获取成功", response = Organization.class),
			@ApiResponse(code = 404, message = "机构不存在", response = Error.class) })
	public Organization getUsers(
			@ApiParam("机构ID") @PathParam("id") String orgId,
			@PathParam("page") int page, @QueryParam("limit") int limit,
			@QueryParam("sort") String sort) throws ResourceNotFoundException {
		
		throw new UnsupportedOperationException();
	}

	@Path("/{id}/users")
	@GET
	@ApiOperation(value = "获取机构下所有用户")
	@ApiResponses({
			@ApiResponse(code = 200, message = "获取成功", response = Organization.class),
			@ApiResponse(code = 404, message = "机构不存在", response = Error.class) })
	public Organization getRoles(
			@ApiParam("机构ID") @PathParam("id") String orgId,
			@QueryParam("page") int page, @QueryParam("limit") int limit)
			throws ResourceNotFoundException {
		
		throw new UnsupportedOperationException();
	}
}
