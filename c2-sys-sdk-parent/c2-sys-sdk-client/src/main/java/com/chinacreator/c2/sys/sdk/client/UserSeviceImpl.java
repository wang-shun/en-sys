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
			String url=builder.substring(0, builder.length()-1);
			ArrayList users = utils.geRestTemplate().getForObject(utils.getUrl(url), ArrayList.class);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> queryMultiByUsername(String... username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> queryByOrg(String orgId, boolean cascade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> queryByRole(String roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Organization> getOrgs(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Organization getMainOrg(String userId) {
		// TODO Auto-generated method stub
		return null;
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
	public List<Role> getRoles(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasRole(String userId, String roleId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> queryByRoleInOrg(String orgId, String roleId) {
		// TODO Auto-generated method stub
		return null;
	}

}
