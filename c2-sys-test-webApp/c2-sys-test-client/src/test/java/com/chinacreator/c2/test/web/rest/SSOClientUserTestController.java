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
	  String[] ids = new String[]{"C4C3B59D303947C3B4D093A31A40A969","2677A85FF10549D591DD23375C87DDE7"};
	  return userService.queryMulti(ids);
  }
  
  @Path("/username")
  @GET
  public User getByUsername(){
	  String username = "111";
	  return userService.getByUsername(username);
  }
  
  @Path("/usernames")
  @GET
  public List<User> queryMultiByUsername(){
	  String[] username = new String[]{"111","org2"};
	  return userService.queryMultiByUsername(username);
  }
  
  @Path("/testorg")
  @GET
  public List<User> queryByOrg(){
	  boolean cascade = true;
	  String orgId = "717273911EDA48DE9A9A7BC1FFFA79D8";
	  return userService.queryByOrg(orgId, cascade);
  }
  
  @Path("/testrole")
  @GET
  public List<User> queryByRole(){
	  String roleId = "1";
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
	  String orgId = "C0B8B5F298C34195A10A005E8293C57F";
	  String roleId = "1";
	  return userService.queryByRoleInOrg(orgId, roleId,true);
  }
}
