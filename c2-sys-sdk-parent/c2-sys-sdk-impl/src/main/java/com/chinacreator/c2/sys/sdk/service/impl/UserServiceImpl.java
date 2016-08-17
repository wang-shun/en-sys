package com.chinacreator.c2.sys.sdk.service.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.PATCH;

import java.sql.Timestamp;
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
import org.springframework.web.bind.annotation.PathVariable;

import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.comp.sys.std.user.facade.UserFacade;
import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.bean.User;
import com.chinacreator.c2.sys.sdk.exception.SysResourcesException;
import com.chinacreator.c2.sys.sdk.service.UserService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;


@Service("sdkUserService")
@Path("/api/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api("用户接口")
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier("com.chinacreator.asp.comp.sys.advanced.user.service.UserServiceImpl")
    private com.chinacreator.asp.comp.sys.advanced.user.service.UserService userService;
    @Autowired
    @Qualifier("com.chinacreator.asp.comp.sys.std.user.facade.UserFacadeImpl")
    private UserFacade userFacade;
    @Autowired
    @Qualifier("com.chinacreator.asp.comp.sys.advanced.org.service.OrgServiceImpl")
    private OrgService orgService;

    private UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setDredgeTime(user.getDredgeTime());
        userDTO.setExtFields(user.getExtFields());
        userDTO.setLastloginDate(user.getLastloginDate());
        userDTO.setLoginIp(user.getLoginIp());
        userDTO.setPastTime(user.getPastTime());
        userDTO.setPolitics(user.getPolitics());
        userDTO.setUserAddress(user.getAddress());
        userDTO.setUserBirthday(user.getBirthday());
        userDTO.setUserEmail(user.getEmail());
        userDTO.setUserFax(user.getFax());
        userDTO.setUserHometel(user.getHometel());
        userDTO.setUserId(user.getId());
        userDTO.setUserIdcard(user.getIdcard());
        userDTO.setUserIsvalid(user.getIsvalid());
        userDTO.setUserLogincount(user.getLogincount());
        userDTO.setUserMobiletel1(user.getMobiletel1());
        userDTO.setUserMobiletel2(user.getMobiletel2());
        userDTO.setUserName(user.getName());
        userDTO.setUserOicq(user.getQq());
        userDTO.setUserPassword(user.getPassword());
        userDTO.setUserPostalcode(user.getPostalcode());
        userDTO.setUserRealname(user.getRealname());
        userDTO.setUserRegdate(user.getRegdate());
        userDTO.setUserSex(user.getSex());
        userDTO.setUserSn(user.getSn());
        userDTO.setUserType(user.getType());
        userDTO.setUserWorkaddress(user.getWorkaddress());
        userDTO.setUserWorktel(user.getWorktel());
        userDTO.setWorklength(user.getWorklength());

        return userDTO;
    }

    private User toBean(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getUserId());
        user.setName(userDTO.getUserName());
        user.setPassword(userDTO.getUserPassword());
        user.setRealname(userDTO.getUserRealname());
        user.setSex(userDTO.getUserSex());
        user.setHometel(userDTO.getUserHometel());
        user.setWorktel(userDTO.getUserWorktel());
        user.setWorkaddress(userDTO.getUserWorkaddress());
        user.setMobiletel1(userDTO.getUserMobiletel1());
        user.setMobiletel2(userDTO.getUserMobiletel2());
        user.setFax(userDTO.getUserFax());
        user.setQq(userDTO.getUserOicq());

        if (userDTO.getUserBirthday() != null) {
            user.setBirthday(new Timestamp(userDTO.getUserBirthday().getTime()));
        }

        user.setEmail(userDTO.getUserEmail());
        user.setAddress(userDTO.getUserAddress());
        user.setPostalcode(userDTO.getUserPostalcode());
        user.setIdcard(userDTO.getUserIdcard());
        user.setIsvalid(userDTO.getUserIsvalid());

        if (userDTO.getUserRegdate() != null) {
            user.setRegdate(new Timestamp(userDTO.getUserRegdate().getTime()));
        }

        user.setLogincount(userDTO.getUserLogincount());
        user.setType(userDTO.getUserType());

        if (userDTO.getPastTime() != null) {
            user.setPastTime(new Timestamp(userDTO.getPastTime().getTime()));
        }

        user.setDredgeTime(userDTO.getDredgeTime());

        if (userDTO.getLastloginDate() != null) {
            user.setLastloginDate(new Timestamp(userDTO.getLastloginDate()
                                                       .getTime()));
        }

        user.setWorklength(userDTO.getWorklength());
        user.setPolitics(userDTO.getPolitics());
        user.setLoginIp(userDTO.getLoginIp());
        user.setSn(userDTO.getUserSn());
        user.setExtFields(userDTO.getExtFields());

        return user;
    }
    
    @POST
    @ApiOperation(value = "在指定机构下新增用户", notes = "约束：\n"
        +"1.orgId不能为空\n"
        +"2.用户名都会被强制转换成小写进行判断和存储\n"
        +"3.用户名不能已存在\n"
        +"4.密码不能为空\n"
        +"5.用户实名不能为空")
    public User create(@ApiParam("机构ID") @QueryParam("orgId") String orgId, @ApiParam("用户数据传输对象") User user) throws SysResourcesException {
        UserDTO userDto = toDTO(user);
        userService.create(userDto, orgId, 0);

        return toBean(userDto);
    }
    
    @POST
    @Path("/{id}/orgnizations/{orgId}")
    @ApiOperation(value = "添加用户的所属机构", notes = "如需设置主机构请使用{@link #setMainOrg(String[],String,boolean)}")
    public void addOrg(@PathParam("id") @ApiParam("用户ID") String userId, @PathParam("orgId") @ApiParam("机构ID") String orgId)
        throws SysResourcesException {
        userService.addToOrg(userId, orgId);
    }
    
    @DELETE
    @Path("/{ids}")
    @ApiOperation(value = "删除用户及用户所有相关的关联关系")
    public void delete(@PathParam("ids") @ApiParam("用户ID串，多个用逗号分开") String userIds) throws SysResourcesException {
        String[] userIdArray=userIds.split(",");
        userService.deleteByPKs(userIdArray);
    }
    
    @DELETE
    @Path("/{id}/orgnizations/{orgId}")
    @ApiOperation(value = "删除用户和机构的关联关系", notes = "主机构不能删")
    public void deleteUserOrgRelationship(@PathParam("id") @ApiParam("用户ID") String userId, @PathParam("orgId") @ApiParam("机构ID") String orgId)
        throws SysResourcesException {
        userService.removeFromOrg(userId, orgId);
    }
    
    @Path("/{id}")
    @PATCH
    @ApiOperation(value = "修改用户")
    public User update(@ApiParam("用户id") @PathVariable("id") String id, @ApiParam("用户数据传输对象") User user) throws SysResourcesException {
        UserDTO userDto = toDTO(user);
        userDto.setUserId(id);
        userService.update(userDto);
        
        return toBean(userDto);
    }
    
    @Path("/{id}/orgnizations/main/{orgId}")
    @PATCH
    @ApiOperation(value = "用户设置主机构")
    public void setMainOrg(@ApiParam("用户ID逗号分隔串") @PathParam("id") String userIds,@PathParam("orgId") @ApiParam("主机构ID") String orgId,
        @QueryParam("isRetain") @ApiParam("用户是否保留在原机构下(true:是，false:否)") boolean isRetain)
        throws SysResourcesException {
        userFacade.setMainOrg(userIds.split(","), orgId, isRetain);
    }
    
    public List<User> queryByOrg(User user, String orgId, boolean cascade) {
        UserDTO userDto = new UserDTO();
        BeanCopierUtil.copy(user, userDto);

        List<UserDTO> userList = userService.queryByOrg(userDto, orgId);

        if (cascade) {
            List<OrgDTO> childOrgs = orgService.queryChildOrgs(orgId, true);

            for (OrgDTO orgDTO : childOrgs) {
                userList.addAll(userService.queryByOrg(userDto,
                        orgDTO.getOrgId()));
            }
        }

        List<User> retList = Lists.transform(userList, new Function<UserDTO, User>() {
            public User apply(UserDTO input) {
                return toBean(input);
            }
        });

        return retList;
    }
    
    
    public List<User> queryByRole(User user, String roleId) {
        UserDTO userDto = new UserDTO();
        BeanCopierUtil.copy(user, userDto);

        List<UserDTO> userList = userService.queryByOrgRole(userDto, null,
                roleId);
        List<User> retList = Lists.transform(userList, new Function<UserDTO, User>() {
            public User apply(UserDTO input) {
                return toBean(input);
            }
        });

        return retList;
    }
    
    @GET
    @ApiOperation("按条件查询用户")
    public List<User> query(@ApiParam("用户账号") @QueryParam("name") String name,@ApiParam("密码") @QueryParam("password") String password,
        @ApiParam("用户实名") @QueryParam("realname") String realname,@ApiParam("性别") @QueryParam("sex") String sex,@ApiParam("手机号码") @QueryParam("mobiletel1") String mobiletel1,
        @ApiParam("email") @QueryParam("email") String email,@ApiParam("身份证号码") @QueryParam("idcard") String idcard,@ApiParam("用户是否有效") @QueryParam("isvalid") Integer isvalid,
        @ApiParam("角色ID") @PathParam("roleId") String roleId,
        @ApiParam("机构ID") @PathParam("orgId") String orgId,
        @ApiParam("是否递归查询") @QueryParam("cascade") boolean cascade) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setRealname(realname);
        user.setSex(sex);
        user.setMobiletel1(mobiletel1);
        user.setEmail(email);
        user.setIdcard(idcard);
        user.setIsvalid(isvalid);
        user.setName(name);
        //TODO 考虑修改系统管理原生接口，只提供一个query（各种条件）的方法,但批量查询如何处理？
        return queryByOrg(user, roleId, cascade);
    }
    
    @ApiOperation(value = "查询指定用户所属机构")
    @Path("/{id}/organizations")
    @GET
    public List<Organization> getOrgs(@ApiParam("用户id") @PathVariable("id") String userId) {
        List<OrgDTO> orgList = userService.queryOrgs(userId);
        List<Organization> retList = Lists.transform(orgList, new Function<OrgDTO, Organization>() {
            public Organization apply(OrgDTO input) {
                return OrgServiceImpl.toBean(input);
            }
        });

        return retList;
    }
    
    @ApiOperation(value = "查询指定用户所属主机构")
    @Path("/{id}/organizations/main")
    @GET
    public Organization getMainOrg(@ApiParam("用户id") @PathVariable("id") String userId) {
        OrgDTO orgDTO = userService.queryMainOrg(userId);
        return OrgServiceImpl.toBean(orgDTO);
    }
    
    @ApiOperation(value = "判断用户是否属于指定机构")
    @Path("/{id}/organizations/{orgId}")
    @HEAD
    public boolean inOrg(@ApiParam("用户id") @PathVariable("id") String userId,@ApiParam("机构id") @PathVariable("orgId") String orgId) {
        return userService.belongsToOrg(userId, orgId);
    }
    
    @ApiOperation(value = "判断指定机构是否用户的主机构")
    @Path("/{id}/organizations/main/{orgId}")
    @HEAD
    public boolean isMainOrg(@ApiParam("用户id") @PathVariable("id") String userId,@ApiParam("机构id") @PathVariable("orgId") String orgId) {
        return userService.isMainOrg(userId, orgId);
    }

    public List<User> query(User user) {
        UserDTO userDto = new UserDTO();
        BeanCopierUtil.copy(user, userDto);

        List<UserDTO> userList = userService.queryByUser(userDto);
        List<User> retList = Lists.transform(userList, new Function<UserDTO, User>() {
            public User apply(UserDTO input) {
                return toBean(input);
            }
        });

        return retList;
    }

    public User get(User user) {
        List<User> retList = query(user);

        return ((retList != null) && !retList.isEmpty()) ? retList.get(0) : null;
    }

    public List<User> query(String... userIds) {
        List<User> userList = new ArrayList<User>();

        for (String id : userIds) {
            UserDTO userDto = userService.queryByPK(id);
            User user = toBean(userDto);
            userList.add(user);
        }

        return userList;
    }
    
    @ApiOperation(value = "查询指定用户所拥有的角色")
    @Path("/{id}/roles")
    @GET
    public List<Role> getRoles(@ApiParam("用户ID") @PathVariable("id") String userId) {
        List<RoleDTO> roleList = userService.queryRoles(userId);
        List<Role> retList = Lists.transform(roleList, new Function<RoleDTO, Role>() {
            public Role apply(RoleDTO input) {
                return RoleServiceImpl.toBean(input);
            }
        });

        return retList;
    }
    
    @ApiOperation(value = "判断用户是否拥有指定角色", notes = "包括直接授予用户的角色和授予用户所属用户组的角色")
    @Path("/{id}/roles/{roleId}")
    @HEAD
    public boolean hasRole(@ApiParam("用户ID") @PathVariable("id") String userId,@ApiParam("角色ID") @PathVariable("roleId") String roleId) {
        return userService.hasRole(userId, roleId);
    }
    
    @ApiOperation(value = "替换用户信息",notes="使用参数中的用户对象（包含空属性）整体替换库中现有的记录,如果用户id不存在则创建一条新纪录")
    @Path("/{id}")
    @PUT
    public User replace(@ApiParam("用户ID") @PathVariable("id") String id,@ApiParam("用户数据传输对象") User user) throws SysResourcesException {
        user.setId(id);
        if (query(id) == null) {
            return create("0",user);    //TODO 增加一个机构参数？
        } else {
            UserDTO userDTO = toDTO(user);
            //TODO 缺少把字段设置成null的方法
            userService.update(userDTO);
            return toBean(userDTO);
        }
    }
}
