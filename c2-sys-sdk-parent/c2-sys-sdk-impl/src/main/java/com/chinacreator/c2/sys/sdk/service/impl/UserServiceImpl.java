package com.chinacreator.c2.sys.sdk.service.impl;

import java.util.ArrayList;
import java.util.Collections;
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
import com.chinacreator.c2.web.exception.ResourceNotFoundException;
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
		if( userDTO == null ){
			return null;
		}
		User user = BeanUtils.toBean(userDTO);
		return user;
	}
	@Override
	public List<User> queryMulti(String... ids) {
		User user = null;
		List<User> list = new ArrayList<User>();
		for(String id : ids){
			UserDTO userDTO = userService.queryByPK(id);
			if( userDTO == null ){
				return null;
			}
			user = BeanUtils.toBean(userDTO);
			list.add(user);
		}
		if(list==null || list.size()==0) return Collections.emptyList();
		return list;
	}
	@Override
	public User getByUsername(String username) {
		UserDTO userDTO = userService.queryByUserName(username);
		if( userDTO == null ){
			return null;
		}
		User user = BeanUtils.toBean(userDTO);
		return user;
	}
	@Override
	public List<User> queryMultiByUsername(String... username) {
		User user = null;
		List<User> userList = new ArrayList<User>();
		for(String name : username){
			UserDTO userDTO = userService.queryByUserName(name);
			if( userDTO == null ){
				return null;
			}
			user = BeanUtils.toBean(userDTO);
			userList.add(user);
		}
		return userList;
	}
	@Override
	public List<User> queryByOrg(String orgId, boolean cascade) {
		UserDTO userDTO = userService.queryByPK(orgId);
		if( userDTO == null ){
			return null;
		}
		List<UserDTO> userList = userService.queryByOrg(userDTO, orgId);
		if(userList.size()==0 || userList==null){
			return new ArrayList<User>();
		}
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
		if( userDTO == null ){
			return null;
		}
		User user = BeanUtils.toBean(userDTO);
		userList.add(user);
		
		return userList;
		
	}
	@Override
	public List<Organization> getOrgs(String userId) {
		List<OrgDTO> orgList = userService.queryOrgs(userId);
		if( orgList == null || orgList.size()==0 ){
			return new ArrayList<Organization>();
		}
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
		OrgDTO orgDTO = userService.queryMainOrg(userId);
		if( orgDTO == null ){
			return null;
		}
		Organization organization = BeanUtils.toBean(orgDTO);
		return organization;
	}
	
	//判断用户是否属于指定机构
	@Override
	public boolean inOrg(String userId, String orgId) {
		UserDTO userDTO = userService.queryByPK(userId);
		List<UserDTO> users = userService.queryByOrg(userDTO, orgId);
		if(users==null || users.size()==0) throw new ResourceNotFoundException("用户id不存在...");
		return true;
	}
	
	@Override
	public boolean isMainOrg(String userId, String orgId) {
		return userService.isMainOrg(userId, orgId);
	}
	
	@Override
	public List<Role> getRoles(String userId) {
		List<RoleDTO> roleList = userService.queryRoles(userId);
		if( roleList == null || roleList.size()==0){
			return Collections.emptyList();
		}
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
	public List<User> queryByRoleInOrg(String orgId, String roleId, boolean cascade) {
		List<OrgDTO> orgList = orgService.queryChildOrgs(orgId, cascade);
		if( orgList == null || orgList.size()==0){
			return new ArrayList<User>();
		}
		for (OrgDTO orgDTO : orgList) {
			String orgId1 = orgDTO.getOrgId();
			UserDTO userDTO = userService.queryByPK(orgId1 );
			if( userDTO == null ){
				return null;
			}
			List<UserDTO> userList = userService.queryByOrgRole(userDTO, orgId1, roleId);
			if( userList == null || userList.size()==0){
				return new ArrayList<User>();
			}
			List<User> reList = Lists.transform(userList, 
					new Function<UserDTO, User>() {
				public User apply(UserDTO input) {
					return BeanUtils.toBean(input);
				}
			});
			return reList;
		}
		return null;
	}
}
