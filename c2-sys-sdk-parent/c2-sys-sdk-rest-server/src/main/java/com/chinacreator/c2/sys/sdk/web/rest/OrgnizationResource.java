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
@Path("/v1/remote/organizations")
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

	@GET
	@ApiOperation(value = "查询机构")
	public List<Organization> query(
			@ApiParam("机构ID") @QueryParam("code") String code,
			@QueryParam("name") String name, @QueryParam("pid") String pid,
			@QueryParam("creator") String creator,
			@QueryParam("level") String level) {

		if (code != null) {
			List<Organization> orgList = organizationService.getParents(code);
			return orgList;

		} else if (pid != null) {
			// int cascadeLevel = level==null?-1:Integer.parseInt(level) ;
			List<Organization> orgList = organizationService.getChildren(pid,
					true);
			Iterator<Organization> iterator = orgList.iterator();
			while (iterator.hasNext()) {
				Organization next = iterator.next();
				if (!next.getPid().equals(pid)) {
					iterator.remove();
				}
			}
			return orgList;

		} else if (name != null) {
			List<Organization> orgList = organizationService.getChildren(null,
					true);
			Iterator<Organization> iterator = orgList.iterator();
			while (iterator.hasNext()) {
				Organization next = iterator.next();
				if (!next.getName().equals(name)) {
					iterator.remove();
				}
			}
			return orgList;

		} else if (creator != null) {
			List<Organization> orgList = organizationService.getChildren(null,
					true);
			Iterator<Organization> iterator = orgList.iterator();
			while (iterator.hasNext()) {
				Organization next = iterator.next();
				if (!next.getCreator().equals(creator)) {
					iterator.remove();
				}
			}
			return orgList;
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
		if (orgId != null) {
			Organization orgnization = organizationService.get(orgId);
			if(orgnization == null){
				throw new ResourceNotFoundException("机构["+orgId+"]不存在");
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
			return orgList;
		}
		
		throw new UnsupportedOperationException();
	}

	@Path("/{id}/children")
	@GET
	@ApiOperation(value = "查询子机构，不包含自身")
	public List<Organization> getChildren(
			@ApiParam("机构ID") @PathParam("id") String id,
			@ApiParam("是否递归查询所有子机构") @PathParam("cascade") boolean cascade) {
		if (id != null) {
			List<Organization> orgList = organizationService.getChildren(id,
					cascade);
			return orgList;
		}
		
		throw new UnsupportedOperationException();
	}

	@Path("/{id}/users")
	@GET
	@ApiOperation(value = "获取机构下所有用户")
	@ApiResponses({
			@ApiResponse(code = 200, message = "获取成功", response = Organization.class),
			@ApiResponse(code = 404, message = "机构不存在", response = Error.class) })
	public List<User> getUsers(
			@ApiParam("机构ID") @PathParam("id") String orgId,
			@PathParam("page") int page, @QueryParam("limit") int limit,
			@QueryParam("sort") String sort) throws ResourceNotFoundException {
		
			Pageable pageable = PageableUtils.getPageable(page, limit) ;
			Sortable sortable = SortableUtil.getSortable(sort, null);
			List<UserDTO> userDtos = (List<UserDTO>) orgService.queryUsers(orgId, pageable, sortable) ;
			if(userDtos == null){
				return new ArrayList<User>() ;
			}
			List<User> users =  Lists.transform(userDtos, new Function<UserDTO, User>() {
				@Override
				public User apply(UserDTO input) {
					return BeanUtils.toBean(input);
				}
				
			});
			if(users!=null && users.size()>0){
				return users;
			}
			throw new UnsupportedOperationException();
		
	}

	@Path("/{id}/users")
	@GET
	@ApiOperation(value = "获取机构下所有角色")
	@ApiResponses({
			@ApiResponse(code = 200, message = "获取成功", response = Organization.class),
			@ApiResponse(code = 404, message = "机构不存在", response = Error.class) })
	public List<Role> getRoles(
			@ApiParam("机构ID") @PathParam("id") String orgId,
			@QueryParam("page") int page, @QueryParam("limit") int limit)
			throws ResourceNotFoundException {
		
		Pageable pageable = PageableUtils.getPageable(page, limit);
		List<RoleDTO> roleDtos = (List<RoleDTO>) orgService.queryRoles(orgId, pageable, null);
		if(roleDtos==null){
			return new ArrayList<Role>();
		}
		List<Role> roles = Lists.transform(roleDtos, new Function<RoleDTO, Role>() {

			@Override
			public Role apply(RoleDTO input) {
				return BeanUtils.toBean(input);
			}
			
		});
		if(roles!=null && roles.size()>0){
			return roles;
		}
		
		throw new UnsupportedOperationException();
	}
			
}
