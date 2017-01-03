package com.chinacreator.c2.sys.sdk.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

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
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.service.query.UserService;
import com.chinacreator.c2.sysmgr.User;
import com.chinacreator.c2.web.exception.ResourceNotFoundException;

@Service
@Path("/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(tags="users")
@SwaggerDefinition(tags={@Tag(name="users",description="用户管理"),@Tag(name="organizations",description="机构管理")})
@ApiResponses({
	@ApiResponse(code = 401, message = "用户没有登录", response = Error.class),
	@ApiResponse(code = 403, message = "没有权限访问", response = Error.class),
	})
public class UserResource {
	@Autowired
	UserService userService;
	
	@Path("/{id}")
	@GET
	@ApiOperation(value = "获取用户详情")
	@ApiResponses({
		@ApiResponse(code = 200, message = "用户信息获取成功", response = User.class),
		@ApiResponse(code = 404, message = "用户不存在", response = Error.class) })
	public User get(@ApiParam("用户ID")@PathParam("id")String id){
			User user = userService.get(id);
			if(user == null){
				throw new ResourceNotFoundException("用户 【"+id+"】 不存在");
			}
			return user;
	}
	
	@GET
	@ApiOperation(value = "根据用户ID或用户名获取用户详情，支持批量",notes="一次只有一个查询条件生效，优先级id>username>role。如果查询参数是数组，则返回的结果为集合")
	@ApiResponses({
		@ApiResponse(code = 200, message = "用户信息获取成功", response = User.class),
		@ApiResponse(code = 404, message = "用户不存在", response = Error.class) })
	public Object queryMulti(@ApiParam("用户ID") @QueryParam("id") String[] ids,
			@ApiParam("用户名")@QueryParam("username")String[] usernames,
			@ApiParam("角色id") @QueryParam("role") String role){
		List<User> users =null;
		boolean getOne=false;
		if(ids !=null && ids.length>0){
			if(ids.length == 1) getOne=true;
			users = userService.queryMulti(ids);
		}else if(usernames != null && usernames.length > 0){
			if(usernames.length == 1) getOne=true;
			users = userService.queryMultiByUsername(usernames);
		}else if(role !=null && StringUtils.isNotEmpty(role)){
			users = userService.queryByRole(role);
		}
		if(users==null || users.size()==0){
			throw new ResourceNotFoundException("用户 【"+usernames+"】 不存在");
		}
		if(getOne){
			return users.get(0);
		}
		return users;
	}
	
	@Path("/{id}/orgs")
	@GET
	@ApiOperation(value="获取用户所属机构")
	public List<Organization> getOrgs(@ApiParam("用户ID")@PathParam("id")String id){
		List<Organization> users = userService.getOrgs(id);
		if(users==null || users.size()==0){
			throw new ResourceNotFoundException("用户【"+id+"】 不存在");
		}
		
		return users;
	}
	
	@Path("/{id}/mainorg")
	@GET
	@ApiOperation(value="获取用户所属主机构")
	public Organization getMainOrg(@ApiParam("用户ID")@PathParam("id")String id){
		Organization organization = userService.getMainOrg(id);
		if(organization==null){
			throw new ResourceNotFoundException("用户【"+id+"】 不存在");
		}
		
		return organization;
	}
	
	@Path("/{id}/roles")
	@GET
	@ApiOperation(value = "获取用户所拥有的角色")
	public List<Role> getRoles(@ApiParam("用户ID")@PathParam("id")String id){
		List<Role> roles = userService.getRoles(id);
		if(roles==null || roles.size()==0){
			throw new ResourceNotFoundException("用户【"+id+"】 不存在");
		}
		
		return roles;
	}
	
	@Path("/{id}/roles/{rid}")
	@HEAD
	@ApiOperation(value = "判断用户是否拥有指定角色")
	public boolean hasRole(@ApiParam("用户ID")@PathParam("id")String id, @ApiParam("角色ID")@PathParam("rid")String rid){
		boolean flag = userService.hasRole(id, rid);
		if(!flag){
			throw new ResourceNotFoundException("机构 id或用户id 【"+id+"】 不存在");
		}
		return flag;
	}
	
	
}
