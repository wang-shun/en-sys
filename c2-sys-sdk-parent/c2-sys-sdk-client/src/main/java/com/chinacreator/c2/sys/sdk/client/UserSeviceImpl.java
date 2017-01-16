package com.chinacreator.c2.sys.sdk.client;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
			ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() {};
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);

			ResponseEntity<List<User>> result =	utils.geRestTemplate().exchange(utils.getUrl(url), HttpMethod.GET, entity, typeRef);
			List<User> users = result.getBody();
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
			User user = utils.geRestTemplate().getForObject(utils.getUrl(url), User.class);
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
			String url = stringBuilder.substring(0, stringBuilder.length()-1);
			ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() {};
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
			
			ResponseEntity<List<User>> result =	utils.geRestTemplate().exchange(utils.getUrl(url), HttpMethod.GET, entity, typeRef);
			List<User> users = result.getBody();
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
			ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() {};
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
			
			ResponseEntity<List<User>> result =	utils.geRestTemplate().exchange(utils.getUrl(url), HttpMethod.GET, entity, typeRef, orgId);
			List<User> users = result.getBody();
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
			StringBuilder builder = new StringBuilder("/v1/users?");
			builder.append("role=").append(roleId);
			String url = builder.substring(0, builder.length());
			ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() {};
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		
			ResponseEntity<List<User>> result =	utils.geRestTemplate().exchange(utils.getUrl(url), HttpMethod.GET, entity, typeRef);
			List<User> users = result.getBody();
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
			ParameterizedTypeReference<List<Organization>> typeRef = new ParameterizedTypeReference<List<Organization>>() {};
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
			
			ResponseEntity<List<Organization>> result =	utils.geRestTemplate().exchange(utils.getUrl("/v1/users/{id}/orgs"), HttpMethod.GET, entity, typeRef, userId);
			List<Organization> organizations = result.getBody();
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
			ParameterizedTypeReference<List<Role>> typeRef = new ParameterizedTypeReference<List<Role>>() {};
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
			
			ResponseEntity<List<Role>> result =	utils.geRestTemplate().exchange(utils.getUrl("/v1/users/{id}/roles"), HttpMethod.GET, entity, typeRef, userId);
			List<Role> roles = result.getBody();
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
			
			ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() {};
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
			
			ResponseEntity<List<User>> result =	utils.geRestTemplate().exchange(utils.getUrl(url), HttpMethod.GET, entity, typeRef, orgId);
			List<User> users = result.getBody();
			return users;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("机构id 【"+orgId+"】 或角色id 【"+roleId+"】 不存在");
			}else{
				throw new SysSDKInvokeException("获取指定用户信息失败", e);
			}
		}
	}
}
