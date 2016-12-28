package com.chinacreator.c2.sys.sdk.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

@Service
@Path("/v1/remote/permissions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(tags={"authorizations"})
@ApiResponses({
		@ApiResponse(code = 401, message = "用户没有登录", response = Error.class),
		@ApiResponse(code = 403, message = "没有权限访问", response = Error.class), })
public class AuthorizationProviderResource {

}
