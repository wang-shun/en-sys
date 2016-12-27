package com.chinacreator.c2.sys.sdk.web.rest;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinacreator.c2.sys.sdk.service.query.UserService;
import com.chinacreator.c2.sysmgr.User;

@Path("/v1/remote/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api("系统管理SDK - 用户")
@ApiResponses({
	@ApiResponse(code = 401, message = "用户没有登录", response = Error.class),
	@ApiResponse(code = 403, message = "没有权限访问", response = Error.class),
	})
public class UserResource {
	@Autowired
	UserService orgService;
	
	
	@GET
	public List<User> query(){
		return null;
		
	}
}
