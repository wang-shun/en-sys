package com.chinacreator.c2.sys.sdk.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.chinacreator.c2.res.exception.ResourceNotFoundException;
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.client.exception.SysSDKInvokeException;
import com.chinacreator.c2.sys.sdk.service.query.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private SDKUtils utils;
	
	@Override
	public Role get(String id) {
		try {
			if(id==null || id.length()==0) return null;
			Role role = utils.geRestTemplate().getForObject(utils.getUrl("/v1/roles/{id}"), Role.class, id);
			return role;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("角色id 【"+id+"】 不存在");
			}else{
				throw new SysSDKInvokeException("获取角色信息失败", e);
			}
		}
	}

	@Override
	public Role getByName(String roleName) {
		try {
			if(roleName==null || roleName.length()==0) return null;
			StringBuilder builder = new StringBuilder("/v1/roles?");
			builder.append("roleName=").append(roleName);	
			String url = builder.substring(0, builder.length());
			Role role = utils.geRestTemplate().getForObject(utils.getUrl(url), Role.class, roleName);
			return role;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("角色名 【"+roleName+"】 不存在");
			}else{
				throw new SysSDKInvokeException("获取角色信息失败", e);
			}
		}
	}

}
