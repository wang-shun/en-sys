package com.chinacreator.c2.sys.sdk.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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


@Service("sdkUserService")
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
    
    @Override
    public String create(User user, String orgId) throws SysResourcesException {
        UserDTO userDto = new UserDTO();
        BeanCopierUtil.copy(user, userDto);
        userService.create(userDto, orgId, 0);

        return userDto.getUserId();
    }

    @Override
    public void addOrg(String userId, String orgId)
        throws SysResourcesException {
        userService.addToOrg(userId, orgId);
    }

    @Override
    public void delete(String... userId) throws SysResourcesException {
        userService.deleteByPKs(userId);
    }

    @Override
    public void deleteUserOrgRelationship(String userId, String orgId)
        throws SysResourcesException {
        userService.removeFromOrg(userId, orgId);
    }

    @Override
    public void update(User user) throws SysResourcesException {
        UserDTO userDto = new UserDTO();
        BeanCopierUtil.copy(user, userDto);
        userService.update(userDto);
    }

    @Override
    public void setMainOrg(String[] userIds, String orgId, boolean isRetain)
        throws SysResourcesException {
        userFacade.setMainOrg(userIds, orgId, isRetain);
    }

    @Override
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

        List<User> retList = new ArrayList<User>();
        BeanCopierUtil.copy(userList, retList, UserDTO.class, User.class);

        return retList;
    }

    @Override
    public List<User> queryByRole(User user, String roleId) {
        UserDTO userDto = new UserDTO();
        BeanCopierUtil.copy(user, userDto);

        List<UserDTO> userList = userService.queryByOrgRole(userDto, null,
                roleId);
        List<User> retList = new ArrayList<User>();
        BeanCopierUtil.copy(userList, retList, UserDTO.class, User.class);

        return retList;
    }

    @Override
    public List<Organization> getOrgs(String userId) {
        List<OrgDTO> orgList = userService.queryOrgs(userId);
        List<Organization> retList = new ArrayList<Organization>();
        BeanCopierUtil.copy(orgList, retList, OrgDTO.class, Organization.class);

        return retList;
    }

    @Override
    public Organization getMainOrg(String userId) {
        OrgDTO orgDTO = userService.queryMainOrg(userId);
        Organization orgnization = new Organization();
        BeanCopierUtil.copy(orgDTO, orgnization);

        return orgnization;
    }

    @Override
    public boolean inOrg(String userId, String orgId) {
        return userService.belongsToOrg(userId, orgId);
    }

    @Override
    public boolean isMainOrg(String userId, String orgId) {
        return userService.isMainOrg(userId, orgId);
    }

    @Override
    public List<User> query(User user) {
        UserDTO userDto = new UserDTO();
        BeanCopierUtil.copy(user, userDto);

        List<UserDTO> userList = userService.queryByUser(userDto);
        List<User> retList = new ArrayList<User>();
        BeanCopierUtil.copy(userList, retList, UserDTO.class, User.class);

        return retList;
    }

    @Override
    public User get(User user) {
        List<User> retList = query(user);

        return ((retList != null) && !retList.isEmpty()) ? retList.get(0) : null;
    }

    @Override
    public List<User> query(String... userIds) {
        List<User> userList = new ArrayList<User>();

        for (String id : userIds) {
            UserDTO userDto = userService.queryByPK(id);
            User user = new User();
            BeanCopierUtil.copy(userDto, user);
            userList.add(user);
        }

        return userList;
    }

    @Override
    public List<Role> getRoles(String userId) {
        List<RoleDTO> roleList = userService.queryRoles(userId);
        List<Role> retList=new ArrayList<Role>();
        BeanCopierUtil.copy(roleList, retList, RoleDTO.class, Role.class);
        return retList;
    }

    @Override
    public boolean hasRole(String userId, String roleId) {
        return userService.hasRole(userId, roleId);
    }
}
