package com.chinacreator.c2.test.web.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.service.query.RoleService;

@Service
@Path("/role")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SSOClientRoleTestController2 {
	
	@Autowired
	private RoleService roleService;
	
	@Path("/{id}")
	@GET
	public Role get(@PathParam("id")String id){
		return roleService.get(id);
	}
	
	@GET
	public Role getByName(){
		String roleName = "超级管理员";
		return roleService.getByName(roleName);
	}
}
