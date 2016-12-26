package com.chinacreator.c2.sys.sdk.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.sys.sdk.BeanUtils;
import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.service.query.UserService;
import com.chinacreator.c2.sysmgr.User;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Service("sdkUserService")
public class UserServiceImpl implements UserService {

	@Autowired
    @Qualifier("com.chinacreator.asp.comp.sys.advanced.user.service.UserServiceImpl")
    private com.chinacreator.asp.comp.sys.advanced.user.service.UserService userService;
    @Autowired
    @Qualifier("com.chinacreator.asp.comp.sys.advanced.org.service.OrgServiceImpl")
    private OrgService orgService;
    
	@Override
	public User get(String id) {
		UserDTO userDTO = userService.queryByPK(id);
		User user = BeanUtils.toBean(userDTO);
		return user;
	}
	
	@Override
	public List<User> queryMulti(String... ids) {
		User user = null;
		List<User> list = new ArrayList<User>();
		for(String id : ids){
			UserDTO userDTO = userService.queryByPK(id);
			user = BeanUtils.toBean(userDTO);
			list.add(user);
		}
		return list;
	}
	@Override
	public User getByUsername(String username) {
		UserDTO userDTO = userService.queryByUserName(username);
		User user = BeanUtils.toBean(userDTO);
		return user;
	}
	@Override
	public List<User> queryMultiByUsername(String... username) {
		User user = null;
		List<User> userList = new ArrayList<User>();
		for(String name : username){
			UserDTO userDTO = userService.queryByUserName(name);
			user = BeanUtils.toBean(userDTO);
			userList.add(user);
		}
		return userList;
	}
	@Override
	public List<User> queryByOrg(String orgId, boolean cascade) {
		UserDTO userDTO = userService.queryByPK(orgId);
		List<UserDTO> userList = userService.queryByOrg(userDTO, orgId);
		List<User> reList = Lists.transform(userList,
	               new Function<UserDTO, User>() {
            public User apply(UserDTO input) {
                return BeanUtils.toBean(input);
            }
        });
		return reList;
	}
	@Override
	public List<User> queryByRole(String roleId) {
		List<User> userList = new ArrayList<User>();
		UserDTO userDTO = userService.queryByPK(roleId);
		User user = BeanUtils.toBean(userDTO);
		userList.add(user);
		return userList;
		
	}
	@Override
	public List<Organization> getOrgs(String userId) {
		List<OrgDTO> orgList = userService.queryOrgs(userId);
		List<Organization> retList = Lists.transform(orgList,
               new Function<OrgDTO, Organization>() {
                   public Organization apply(OrgDTO input) {
                       return BeanUtils.toBean(input);
                   }
               });
		
		return retList;	
	}
	@Override
	public Organization getMainOrg(String userId) {
		OrgDTO orgDTO = orgService.queryByPK(userId);
		Organization organization = BeanUtils.toBean(orgDTO);
		return organization;
	}
	@Override
	public boolean inOrg(String userId, String orgId) {
		return orgService.containsUser(userId, orgId);
	}
	@Override
	public boolean isMainOrg(String userId, String orgId) {
		return userService.isMainOrg(userId, orgId);
	}
	@Override
	public List<Role> getRoles(String userId) {
		List<RoleDTO> roleList = userService.queryRoles(userId);
		List<Role> retList = Lists.transform(roleList,
	               new Function<RoleDTO, Role>() {
	                   public Role apply(RoleDTO input) {
	                       return BeanUtils.toBean(input);
	                   }
	               });
			
			return retList;	
	}
	@Override
	public boolean hasRole(String userId, String roleId) {
		return userService.hasRole(userId, roleId);
	}

	@Override
	public List<User> queryByRoleInOrg(String orgId, String roleId) {
		// TODO Auto-generated method stub
		return null;
	}
}
