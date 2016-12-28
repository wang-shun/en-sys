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
  
  @Path("/multi")
  @GET
  public List<User> queryMulti(){
	  String[] ids = new String[]{"C4C3B59D303947C3B4D093A31A40A969","98180E1A188646829E16DD6C844412CE"};
	  return userService.queryMulti(ids);
  }
  
}
