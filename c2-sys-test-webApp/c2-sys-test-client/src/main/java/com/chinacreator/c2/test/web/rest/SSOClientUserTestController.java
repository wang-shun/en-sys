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

import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.service.query.UserService;
import com.chinacreator.c2.sysmgr.AuthenticationProvider;
import com.chinacreator.c2.sysmgr.User;

@Service
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SSOClientUserTestController {

  @Autowired
  private UserService userService;
	
  @Path("/me")
  @GET
  public User whoAmI(){
    AuthenticationProvider provider = ApplicationContextManager.getContext().getBean(AuthenticationProvider.class);
    return provider.getSubject();
  }
  @Path("/user/{id}")
  @GET
  public User getUser(@PathParam("id")String id){
    return userService.get(id);
  }
  
  @Path("/testmulti")
  @GET
  public List<User> queryMulti(){
	  String[] ids = new String[]{"6C1F40EC63634D17B3A5DB0D2B25C021","6C8F0C71205948ED92BC2ED227DC38FA"};
	  return userService.queryMulti(ids);
  }
  
  @Path("/username")
  @GET
  public User getByUsername(){
	  String username = "gdjyj";
	  return userService.getByUsername(username);
  }
  
  @Path("/usernames")
  @GET
  public List<User> queryMultiByUsername(){
	  String[] username = new String[]{"gdjyj","qi.peng"};
	  return userService.queryMultiByUsername(username);
  }
  
  @Path("/testorg")
  @GET
  public List<User> queryByOrg(){
	  boolean cascade = true;
	  String orgId = "00BA41D20F5B4CE98844834A2B63B65D";
	  return userService.queryByOrg(orgId, cascade);
  }
  
  @Path("/testrole")
  @GET
  public List<User> queryByRole(){
	  String roleId = "2";
	  return userService.queryByRole(roleId);
  }
  
  @Path("/{id}/testorgs")
  @GET
  public List<Organization> getOrgs(@PathParam("id")String id){
	  return userService.getOrgs(id);
  }
  
  @Path("/{id}/ismainorg")
  @GET
  public boolean isMainOrg(@PathParam("id")String id){
	  String orgId = "661BCC915A6A4531B512F28535A27D66";
	  return userService.isMainOrg(id, orgId);
  }
  
  @Path("/{id}/mainorg")
  @GET
  public Organization getMainOrg(@PathParam("id")String id){
	  return userService.getMainOrg(id);
  }
  
  @Path("/{id}/inorg")
  @GET
  public boolean inOrg(@PathParam("id")String id){
	  String orgId = "661BCC915A6A4531B512F28535A27D66";
	  return userService.inOrg(id, orgId);
  }
  
  @Path("/{id}/getroles")
  @GET
  public List<Role> getRoles(@PathParam("id")String id){
	  return userService.getRoles(id);
  }
  
  @Path("/{id}/hasrole")
  @GET
  public boolean hasRole(@PathParam("id")String id){
	  String roleId = "1";
	  return userService.hasRole(id, roleId);
  }
  
  @Path("/roleorg")
  @GET
  public List<User> queryByRoleInOrg(){
	  String orgId = "24DF30F4297549A3B1A4540D7F63FA04";
	  String roleId = "2";
	  return userService.queryByRoleInOrg(orgId, roleId,true);
  }
}
