package com.chinacreator.c2.sys.sdk.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.client.exception.SysSDKInvokeException;
import com.chinacreator.c2.sys.sdk.service.query.UserService;
import com.chinacreator.c2.sysmgr.User;
import com.chinacreator.c2.web.exception.ResourceNotFoundException;

@Service
public class UserSeviceImpl implements UserService {
	@Autowired
	private SDKUtils utils;
	
	@Override
	public User get(String id) {
		try{
			if(id==null || id.length()==0)  return null;
			
			User user = utils.geRestTemplate().getForObject(utils.getUrl("/v1/users/{id}"), User.class, id);
			return user;
		}catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("用户 【"+id+"】 不存在");
			}else{
				throw new SysSDKInvokeException("获取用户信息失败", e);
			}
		}
	}

	@Override
	public List<User> queryMulti(String... ids) {
		try {
			if(ids==null || ids.length==0) return Collections.emptyList();
			
			StringBuilder builder = new StringBuilder("/v1/users?");
			for(String id: ids){
				builder.append("id=").append(id);
				builder.append("&");
			}
			String url = builder.substring(0, builder.length()-1);
			List<User> users = utils.geRestTemplate().getForObject(utils.getUrl(url), List.class);
			return users;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("用户 【"+ids+"】 不存在");
			}else{
				throw new SysSDKInvokeException("获取用户信息失败", e);
			}
		}
	}

	@Override
	
	public User getByUsername(String username) {
		try {
			if(username==null || username.length()==0) return null;
			StringBuilder builder = new StringBuilder("/v1/users?");
			builder.append("username=").append(username);
			String url = builder.substring(0, builder.length());
			User user = utils.geRestTemplate().getForObject(utils.getUrl(url), User.class, username);
			return user;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("用户 【"+username+"】 不存在");
			}else{
				throw new SysSDKInvokeException("获取用户信息失败", e);
			}
		}
		
	}

	@Override
	public List<User> queryMultiByUsername(String... username) {
		try {
			if(username==null || username.length==0)  return Collections.emptyList();
			StringBuilder stringBuilder = new StringBuilder("/v1/users?");
			for (String name : username) {
				stringBuilder.append("username=").append(name);
				stringBuilder.append("&");
			}
			String nameParams = stringBuilder.substring(0, stringBuilder.length()-1); 
			ArrayList users = utils.geRestTemplate().getForObject(utils.getUrl(nameParams), ArrayList.class);
			return users;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("用户 【"+username+"】 不存在");
			}else{
				throw new SysSDKInvokeException("获取用户信息失败", e);
			}
		}
	}

	@Override
	public List<User> queryByOrg(String orgId, boolean cascade) {
		try {
			if(orgId==null || orgId.length()==0) return null;
			
			StringBuilder builder = new StringBuilder("/v1/organizations/{oid}/users?");
			builder.append("cascade=").append(cascade);
			String url = builder.substring(0, builder.length());
			
			List<User> users = utils.geRestTemplate().getForObject(utils.getUrl(url), List.class, orgId, cascade);
			return users;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("机构id 【"+orgId+"】 不存在");
			}else{
				throw new SysSDKInvokeException("获取用户信息失败", e);
			}
		}
	}

	@Override
	public List<User> queryByRole(String roleId) {
		try {
			if(roleId==null || roleId.length()==0) return null;
			List<User> users = utils.geRestTemplate().getForObject(utils.getUrl("/v1/users?role={rid}"), List.class, roleId);
			return users;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("角色id 【"+roleId+"】 不存在");
			}else{
				throw new SysSDKInvokeException("获取用户信息失败", e);
			}
		}
	}

	@Override
	public List<Organization> getOrgs(String userId) {
		try {
			if(userId==null || userId.length()==0) return null;
			List<Organization> organizations = utils.geRestTemplate().getForObject(utils.getUrl("/v1/users/{id}/orgs"), List.class, userId);
			return organizations;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("用户id 【"+userId+"】 不存在");
			}else{
				throw new SysSDKInvokeException("获取用户所属机构信息失败", e);
			}
		}
	}

	@Override
	public Organization getMainOrg(String userId) {
		try {
			if(userId==null || userId.length()==0) return null;
			Organization organization = utils.geRestTemplate().getForObject(utils.getUrl("/v1/users/{id}/mainorg"), Organization.class, userId);
			return organization;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("用户id 【"+userId+"】 不存在");
			}else{
				throw new SysSDKInvokeException("获取用户所属主机构信息失败", e);
			}
		}
	}

	@Override
	public boolean inOrg(String userId, String orgId) {
		try {
			if(userId==null || userId.length()==0) return false;
			utils.geRestTemplate().headForHeaders(utils.getUrl("/v1/organizations/{oid}/users/{uid}"), orgId, userId);
			return true;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				return false;
			}else{
				throw new SysSDKInvokeException("获取用户所属主机构信息失败", e);
			}
		}
	}

	@Override
	public boolean isMainOrg(String userId, String orgId) {
		try {
			if(userId==null || userId.length()==0) return false;
			if(orgId==null || orgId.length()==0) return false;
			
			Organization mainOrg = utils.geRestTemplate().getForObject(utils.getUrl("/v1/users/{id}/mainorg"), Organization.class, userId);
			return orgId.equals(mainOrg.getId());
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				return false;
			}else{
				throw new SysSDKInvokeException("获取用户所属主机构信息失败", e);
			}
		}
	}

	@Override
	public List<Role> getRoles(String userId) {
		try {
			if(userId==null || userId.length()==0) return null;
			List<Role> roles = utils.geRestTemplate().getForObject(utils.getUrl("/v1/users/{id}/roles"), List.class, userId);
			return roles;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("用户id 【"+userId+"】 不存在");
			}else{
				throw new SysSDKInvokeException("获取用户的角色信息失败", e);
			}
		}
	}

	@Override
	public boolean hasRole(String userId, String roleId) {
		try {
			if(userId==null || userId.length()==0) return false;
			if(roleId==null || roleId.length()==0) return false;
			
			utils.geRestTemplate().headForHeaders(utils.getUrl("/v1/users/{id}/roles/{rid}"), userId, roleId);
			return true;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				return false;
			}else{
				throw new SysSDKInvokeException("获取用户角色信息失败", e);
			}
		}
	}

	@Override
	public List<User> queryByRoleInOrg(String orgId, String roleId, boolean cascade) {
		try {
			if(orgId==null || orgId.length()==0) return null;
			if(roleId==null || roleId.length()==0) return null;
			
			StringBuilder builder = new StringBuilder("/v1/organizations/{oid}/users?");
			builder.append("role=").append(roleId).append("&");
			builder.append("cascade=").append(cascade);
			String url = builder.substring(0, builder.length());
			
			List<User> roles = utils.geRestTemplate().getForObject(utils.getUrl(url), List.class, orgId, roleId, cascade);
			return roles;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("机构id 【"+orgId+"】 或角色id 【"+roleId+"】 不存在");
			}else{
				throw new SysSDKInvokeException("获取指定用户信息失败", e);
			}
		}
	}
}
