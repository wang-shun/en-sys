package com.chinacreator.c2.sys.sdk.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.client.exception.SysSDKInvokeException;
import com.chinacreator.c2.sys.sdk.service.query.UserService;
import com.chinacreator.c2.sysmgr.User;
import com.chinacreator.c2.web.util.RestUtils;

public class UserSeviceImpl implements UserService {
	@Autowired
	private RemoteServerConfig config;
	
	@Override
	public User current() {
		return null;
	}

	@Override
	public User get(String id) {
		RestTemplate restTemplate = RestUtils.createRestTemplate();
		try{
			//调用REST接口
			User user = restTemplate.getForObject(config.getUrl("/v1/users/{id}"), User.class, id);
			return user;
		}catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				return null;
			}else{
				throw new SysSDKInvokeException("获取用户信息失败", e);
			}
		}
	}

	@Override
	public List<User> queryMulti(String... ids) {
		// TODO Auto-generated method stub
		return null;
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

}
