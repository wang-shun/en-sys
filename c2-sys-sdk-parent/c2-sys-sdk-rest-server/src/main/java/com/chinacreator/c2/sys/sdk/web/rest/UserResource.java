package com.chinacreator.c2.sys.sdk.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
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

import com.chinacreator.c2.sys.sdk.service.query.UserService;
import com.chinacreator.c2.sysmgr.User;
import com.chinacreator.c2.web.exception.ResourceNotFoundException;

@Service
@Path("/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api("系统管理SDK - 用户")
@ApiResponses({
	@ApiResponse(code = 401, message = "用户没有登录", response = Error.class),
	@ApiResponse(code = 403, message = "没有权限访问", response = Error.class),
	})
public class UserResource {
	@Autowired
	UserService userService;
	
	@Path("/{id}")
	@GET
	@ApiOperation(value="获取用户详情")
	@ApiResponses({
		@ApiResponse(code = 200, message = "用户信息获取成功", response = User.class),
		@ApiResponse(code = 404, message = "用户不存在", response = Error.class) })
	public User get(@PathParam("id")String id){
		if(id != null){
			User user = userService.get(id);
			if(user == null){
				throw new ResourceNotFoundException("用户 【"+id+"】 不存在");
			}
			return user;
		}
		throw new ResourceNotFoundException("未定义用户id");
	}
	
	@GET
	@ApiOperation(value="根据ID获取多个用户详情")
	@ApiResponses({
		@ApiResponse(code = 200, message = "用户信息获取成功", response = User.class),
		@ApiResponse(code = 404, message = "用户不存在", response = Error.class) })
	public List<User> queryMulti(@QueryParam("id") String[] ids){
		List<User> users = new ArrayList<User>();
		for (String id : ids) {
			if(id != null){
				users = userService.queryMulti(id);
				if(users==null){
					throw new ResourceNotFoundException("用户 【"+id+"】 不存在");
				}
			}
		}
		return users;
	}
}
