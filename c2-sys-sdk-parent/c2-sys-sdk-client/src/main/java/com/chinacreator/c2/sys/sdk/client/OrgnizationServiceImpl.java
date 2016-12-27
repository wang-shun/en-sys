package com.chinacreator.c2.sys.sdk.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.client.exception.SysSDKInvokeException;
import com.chinacreator.c2.sys.sdk.service.query.OrgnizationService;

@Service
public class OrgnizationServiceImpl implements OrgnizationService {
	@Autowired
	private SDKUtils utils;

	@Override
	public Organization get(String orgId) {
		try{
			//调用REST接口
			Organization org = utils.geRestTemplate().getForObject(utils.getUrl("/v1/remote/organizations/{id}"), Organization.class, orgId);
			return org;
		}catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				return null;
			}else{
				throw new SysSDKInvokeException("获取用户信息失败", e);
			}
		}
	}

	@Override
	public List<Organization> getChildren(String orgId, boolean cascade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Organization> getParents(String orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsUser(String orgId, String userId) {
		// TODO Auto-generated method stub
		return false;
	}

}
