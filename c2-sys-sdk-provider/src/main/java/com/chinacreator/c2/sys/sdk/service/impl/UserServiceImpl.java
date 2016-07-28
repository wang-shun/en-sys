package com.chinacreator.c2.sys.sdk.service.impl;

import java.util.List;

import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.comp.sys.std.user.facade.UserFacade;
import com.chinacreator.c2.sys.sdk.bean.Orgnization;
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.bean.User;
import com.chinacreator.c2.sys.sdk.service.UserService;

public class UserServiceImpl implements UserService {
    private com.chinacreator.asp.comp.sys.advanced.user.service.UserService userService;
    private UserFacade userFacade;
    public void setUserService(com.chinacreator.asp.comp.sys.advanced.user.service.UserService userService) {
        this.userService = userService;
    }
    
    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @Override
    public String create(User user, String orgId) {
        UserDTO userDto = new UserDTO();
        BeanCopierUtil.copy(user, userDto);
        userService.create(userDto, orgId, 0);
        return userDto.getUserId();
    }
    
    @Override
    public void setMainOrg(String[] userIds, String orgId, boolean isRetain) {
        userFacade.setMainOrg(userIds, orgId, isRetain);
    }
    
    @Override
    public void delete(String... userId) {
        userService.deleteByPKs(userId);
    }
    
    @Override
    public void deleteUserOrgRelationship(String userId, String orgId) {
        userService.removeFromOrg(userId, orgId);
    }
    
    @Override
    public List<User> queryByOrg(User userDto, String orgId, boolean cascade) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<User> queryByRole(User userDto, String roleId) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<Orgnization> getOrgs(String userId) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Orgnization getMainOrg(String userId) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void addToOrg(String userId, String orgId) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public boolean inOrg(String userId, String orgId) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean isMainOrg(String userId, String orgId) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public void update(User userDto) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public List<User> query(User userDto) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public User get(User userDto) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<User> query(String... userIds) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<Role> getRoles(String userId) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public boolean hasRole(String userId, String roleId) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
