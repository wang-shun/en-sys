package com.chinacreator.c2.sys.sdk.service.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;
import io.swagger.jaxrs.PATCH;

import java.sql.Timestamp;
import java.util.Date;
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
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.service.OrgnizationService;
import com.chinacreator.c2.web.exception.RequiredPropertyNotFoundException;
import com.chinacreator.c2.web.exception.ResourceNotFoundException;
import com.chinacreator.c2.web.exception.UniqueConstraintException;
import com.google.common.base.Function;
import com.google.common.collect.Lists;


@Service("sdkOrgService")
@Path("/v1/organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api("系统管理 - 机构")
@ApiResponses({
	@ApiResponse(code = 401, message = "没有权限访问", response = Error.class),
	})
public class OrgServiceImpl implements OrgnizationService {
    @Autowired
    @Qualifier("com.chinacreator.asp.comp.sys.advanced.org.service.OrgServiceImpl")
    private com.chinacreator.asp.comp.sys.advanced.org.service.OrgService orgService;

    public static OrgDTO toDto(Organization org) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCreatingTime(org.getCreatAt());
        orgDTO.setCreator(org.getCreator());
        orgDTO.setOrgDesc(org.getDesc());
        orgDTO.setOrgId(org.getId());
        orgDTO.setOrgName(org.getName());
        orgDTO.setOrgNumber(org.getCode());
        orgDTO.setOrgShowName(org.getName());
        orgDTO.setParentId(org.getPid());

        return orgDTO;
    }

    public static Organization toBean(OrgDTO orgDTO) {
        Organization organization = new Organization();
        Date creatingTime = orgDTO.getCreatingTime();

        if (creatingTime != null) {
            organization.setCreatAt(new Timestamp(creatingTime.getTime()));
        }

        organization.setCreator(orgDTO.getCreator());
        organization.setDesc(orgDTO.getOrgDesc());
        organization.setId(orgDTO.getOrgId());
        organization.setName(orgDTO.getOrgName());
        organization.setCode(orgDTO.getOrgNumber());
        organization.setPid(orgDTO.getParentId());

        return organization;
    }

    @POST
    @ApiOperation(value = "新增机构",authorizations={@Authorization(value="oauth",scopes = {@AuthorizationScope(scope = "org:add",description = "添加机构")})})
	@ApiResponses({
		@ApiResponse(code = 200, message = "创建成功", response = Organization.class),
		@ApiResponse(code = 400, message = "机构数据无效", response = Error.class) 
		})
    public Organization create(Organization org) throws UniqueConstraintException, RequiredPropertyNotFoundException {
        OrgDTO orgDTO = toDto(org);
        try{
        	orgService.create(orgDTO);
        }catch (IllegalArgumentException e) {
        	throw new UniqueConstraintException(e.getMessage());
		}catch (NullPointerException e) {
			throw new RequiredPropertyNotFoundException(e.getMessage());
		}
        
        return toBean(orgDTO);
    }

    @Path("/{id}")
    @PATCH
    @ApiOperation(value = "更新机构信息", notes = "仅更新参数中的非空属性,参数中的非空属性的约束请参照创建{@link #create(Organization)}接口\n")
    public Organization update(@ApiParam("要更新的机构ID") @PathParam("id") String orgId,@ApiParam("机构数据传输对象")  Organization org) throws ResourceNotFoundException,UniqueConstraintException{
        OrgDTO orgDTO = toDto(org);
        orgDTO.setOrgId(orgId);
        orgService.update(orgDTO);
        return toBean(orgDTO);
    }

    @Path("/{id}")
    @PUT
    @ApiOperation(value = "替换机构信息，机构id不存在则创建")
    public Organization replace(@ApiParam("机构ID") @PathParam("id") String orgId,@ApiParam("机构数据传输对象") Organization org) throws UniqueConstraintException, RequiredPropertyNotFoundException {
        Organization oldOrg = null;
		try {
			oldOrg = get(orgId);
		} catch (ResourceNotFoundException e) {
		}
    	org.setId(orgId);
        if (oldOrg == null) {
            Organization organization = create(org);
            return organization;
        } else {
            OrgDTO orgDTO = toDto(org);
            //TODO 缺少把字段设置成null的方法
            orgService.update(orgDTO);
            return toBean(orgDTO);
        }
    }

    @Path("/{id}")
    @DELETE
    @ApiOperation(value = "删除机构")
    public void delete(@ApiParam("机构ID") @PathParam("id") String orgId) {
        orgService.deleteByPKs(orgId);
    }

    public List<Organization> query(Organization org) {
        OrgDTO orgDTO = toDto(org);

        List<OrgDTO> orgList = orgService.queryByOrg(orgDTO);
        List<Organization> retList = Lists.transform(orgList,
                new Function<OrgDTO, Organization>() {
                    public Organization apply(OrgDTO input) {
                        return toBean(input);
                    }
                });

        return retList;
    }

    @GET
    @ApiOperation(value = "查询机构")
    public List<Organization> query(@ApiParam("机构ID") @QueryParam("code") String code, @QueryParam("name") String name, @QueryParam("pid") String pid, @QueryParam("creator") String creator,@QueryParam("level") String level) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCode(code);
        orgDTO.setOrgName(name);
        orgDTO.setParentId(pid);
        orgDTO.setCreator(creator);
        orgDTO.setLayer(level);
        
        List<OrgDTO> orgList = orgService.queryByOrg(orgDTO);
        List<Organization> retList = Lists.transform(orgList,
                new Function<OrgDTO, Organization>() {
                    public Organization apply(OrgDTO input) {
                        return toBean(input);
                    }
                });

        return retList;
    }

    @Path("/{id}")
    @GET
    @ApiOperation(value = "获取机构数据")
	@ApiResponses({
		@ApiResponse(code = 200, message = "获取成功", response = Organization.class),
		@ApiResponse(code = 404, message = "机构不存在", response = Error.class) 
		})
    public Organization get(@ApiParam("机构ID") @PathParam("id") String orgId) throws ResourceNotFoundException {
        OrgDTO orgDTO = orgService.queryByPK(orgId);
        Organization orgnization = toBean(orgDTO);
        return orgnization;
    }

    @Path("/{id}/parents")
    @GET
    public List<Organization> getParents(@PathParam("id") String id) {
        List<OrgDTO> orgList = orgService.queryFatherOrgs(id, true);
        List<Organization> retList = Lists.transform(orgList,
                new Function<OrgDTO, Organization>() {
                    public Organization apply(OrgDTO input) {
                        return toBean(input);
                    }
                });

        return retList;
    }

    @Path("/{id}/children")
    @GET
    @ApiOperation(value = "查询子机构，不包含自身")
    public List<Organization> getChildren(@ApiParam("机构ID") @PathParam("id") String id,@ApiParam("是否递归查询所有子机构") @PathParam("cascade") boolean cascade) {
        List<OrgDTO> orgList = orgService.queryChildOrgs(id, cascade);
        List<Organization> retList = Lists.transform(orgList,
                new Function<OrgDTO, Organization>() {
                    public Organization apply(OrgDTO input) {
                        return toBean(input);
                    }
                });

        return retList;
    }

    @Path("/{id}/users")
    @GET
    @ApiOperation(value = "获取机构下所有用户")
	@ApiResponses({
		@ApiResponse(code = 200, message = "获取成功", response = Organization.class),
		@ApiResponse(code = 404, message = "机构不存在", response = Error.class) 
		})
    public Organization getUsers(@ApiParam("机构ID") @PathParam("id") String orgId,@PathParam("page") int page, @QueryParam("limit") int limit,@QueryParam("sort") String sort) throws ResourceNotFoundException {
        OrgDTO orgDTO = orgService.queryByPK(orgId);
        Organization orgnization = toBean(orgDTO);
        return orgnization;
    }
    
    
    @Path("/{id}/users")
    @GET
    @ApiOperation(value = "获取机构下所有用户")
	@ApiResponses({
		@ApiResponse(code = 200, message = "获取成功", response = Organization.class),
		@ApiResponse(code = 404, message = "机构不存在", response = Error.class) 
		})
    public Organization getRoles(@ApiParam("机构ID") @PathParam("id") String orgId,@QueryParam("page") int page, @QueryParam("limit") int limit) throws ResourceNotFoundException {
        OrgDTO orgDTO = orgService.queryByPK(orgId);
        Organization orgnization = toBean(orgDTO);
        return orgnization;
    }
    
    public boolean containsUser(@ApiParam("机构ID") @PathParam("id") String orgId,@ApiParam("用户ID") @PathParam("uid") String userId) {
        return orgService.containsUser(orgId, userId);
    }
    
    public boolean hasRole(@ApiParam("机构ID") @PathParam("id") String id,@ApiParam("角色ID") @PathParam("rid") String rid) {
        return orgService.hasRole(id, rid);
    }
    
   
}
