package com.chinacreator.c2.test.web.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.service.query.OrganizationService;
import com.chinacreator.c2.sys.sdk.service.query.UserService;

@Service
@Path("/org")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SSOClientOrgTestController2 {
	
	@Autowired
	private OrganizationService orgService;
	
	@Autowired
	private UserService userService;
	
	@Path("/organization/{id}")
	@GET
	public Organization get(){
		String id = "661BCC915A6A4531B512F28535A27D66";
		return orgService.get(id);
	}       
	
	@Path("/organization/{id}/parents")
	@GET
	public List<Organization> getParents(@PathParam("id")String id){
		return orgService.getParents(id);
	}
	
	@Path("/organization/{id}/children")
	@GET
	public List<Organization> getChildren(@PathParam("id")String id){
		boolean cascade = true;
		return orgService.getChildren(id, cascade);
	}
	
	@Path("/organization/{oid}/user/{uid}")
	@GET
	public boolean containUser(@PathParam("oid")String oid,@PathParam("uid")String uid){
		return orgService.containsUser(oid, uid);
	}
	
	@GET
	public Organization getByName(){
		String orgName = "hh";
		return orgService.getByName(orgName);
	}  
}
