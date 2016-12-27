package com.chinacreator.c2.test.web.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.sysmgr.AuthenticationProvider;
import com.chinacreator.c2.sysmgr.User;

@Service
@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SSOClientTestController {

  @GET
  @Path("/me")
  public User whoAmI(){
    AuthenticationProvider provider = ApplicationContextManager.getContext().getBean(AuthenticationProvider.class);
    return provider.getSubject();
  }
  
  
}
