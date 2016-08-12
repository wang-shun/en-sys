package com.chinacreator.c2.sys.sdk.service.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.jaxrs.PATCH;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.exception.SysResourcesException;
import com.chinacreator.c2.sys.sdk.service.OrgService;


@Service("sdkOrgService")
@Path("/api/v1/organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api("机构接口")
public class OrgServiceImpl implements OrgService {
	
    @Autowired
    @Qualifier("com.chinacreator.asp.comp.sys.advanced.org.service.OrgServiceImpl")
    private com.chinacreator.asp.comp.sys.advanced.org.service.OrgService orgService;

    @POST
    @ApiOperation(value="创建机构",notes="创建机构接口的详细描述")
    public String create(Organization org) {
        OrgDTO orgDTO = new OrgDTO();
        BeanCopierUtil.copy(org, orgDTO);
        orgService.create(orgDTO);
        return orgDTO.getOrgId();
    }

    @Path("/{id}")
    @PATCH
    public void update(@PathParam("id") String orgId, Organization org) {
        OrgDTO orgDTO = new OrgDTO();
        BeanCopierUtil.copy(org, orgDTO);
        orgService.update(orgDTO);
    }
    
    @Path("/{id}")
    @PUT
	public void replace(String orgId, Organization org)
			throws SysResourcesException {
    	
	}

    @Path("/{id}")
    @DELETE
    public void delete(@PathParam("id") String orgId) {
        orgService.deleteByPKs(orgId);
    }

    public List<Organization> query(Organization org) {
        OrgDTO orgDTO = new OrgDTO();
        BeanCopierUtil.copy(org, orgDTO);

        List<OrgDTO> orgList = orgService.queryByOrg(orgDTO);
        List<Organization> retList = new ArrayList<Organization>();
        BeanCopierUtil.copy(orgList, retList, OrgDTO.class, Organization.class);

        return retList;
    }
    
    @GET
    public List<Organization> query(@QueryParam("code") String code, @QueryParam("name") String name) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCode(code);
        orgDTO.setOrgName(name);

        List<OrgDTO> orgList = orgService.queryByOrg(orgDTO);
        List<Organization> retList = new ArrayList<Organization>();
        BeanCopierUtil.copy(orgList, retList, OrgDTO.class, Organization.class);

        return retList;
    }

    @Path("/{id}")
    @GET
    public Organization get(@PathParam("id") String orgId) {
        Organization orgnization = new Organization();
        OrgDTO orgDto = orgService.queryByPK(orgId);
        BeanCopierUtil.copy(orgDto, orgnization);

        return orgnization;
    }

    @Path("/{id}/parents")
    @GET
    public List<Organization> getParents(@PathParam("id") String id) {
        List<OrgDTO> orgList = orgService.queryFatherOrgs(id, true);
        List<Organization> retList = new ArrayList<Organization>();
        BeanCopierUtil.copy(orgList, retList, OrgDTO.class, Organization.class);

        return retList;
    }

    @Path("/{id}/children")
    @GET
    public List<Organization> getChildren(@PathParam("id") String id, @PathParam("cascade") boolean cascade) {
        List<OrgDTO> orgList = orgService.queryChildOrgs(id, cascade);
        List<Organization> retList = new ArrayList<Organization>();
        BeanCopierUtil.copy(orgList, retList, OrgDTO.class, Organization.class);

        return retList;
    }

    @Override
    @HEAD
    @Path("/{id}/users/{uid}")
    public boolean containsUser(@PathParam("id") String orgId, @PathParam("uid") String userId) {
        return orgService.containsUser(orgId, userId);
    }
    
    
    @Override
    @HEAD
    @Path("/{id}/roles/{rid}")
    public boolean hasRole(@PathParam("id") String id, @PathParam("rid") String rid) {
        return orgService.hasRole(id, rid);
    }
}
