package com.chinacreator.c2.sys.sdk.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.client.exception.SysSDKInvokeException;
import com.chinacreator.c2.sys.sdk.service.query.OrgnizationService;
import com.chinacreator.c2.web.exception.ResourceNotFoundException;

@Service
public class OrgnizationServiceImpl implements OrgnizationService {
	@Autowired
	private SDKUtils utils;

	@Override
	public Organization get(String orgId) {
		try{
			if(orgId==null || orgId.length()==0) return null;
			Organization org = utils.geRestTemplate().getForObject(utils.getUrl("/v1/organizations/{id}"), Organization.class, orgId);
			return org;
		}catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("机构 【"+orgId+"】 不存在");
			}else{
				throw new SysSDKInvokeException("获取机构信息失败", e);
			}
		}
	}

	@Override
	public List<Organization> getChildren(String orgId, boolean cascade) {
		try {
			if(orgId==null || orgId.length()==0)   return Collections.emptyList();
			StringBuilder builder = new StringBuilder("/v1/organizations/{id}/children?");
			builder.append("cascade=").append(cascade);
			String url = builder.substring(0, builder.length());

			ArrayList organizations = utils.geRestTemplate().getForObject(utils.getUrl(url), ArrayList.class, orgId ,cascade);
			return organizations;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("机构 【"+orgId+"】 对应的子机构不存在");
			}else{
				throw new SysSDKInvokeException("获取机构的子机构信息失败", e);
			}
		}
		
	}

	@Override
	public List<Organization> getParents(String orgId) {
		try {
			if(orgId==null || orgId.length()==0)   return Collections.emptyList();
			ArrayList organizations = utils.geRestTemplate().getForObject(utils.getUrl("/v1/organizations/{id}/parents"), ArrayList.class, orgId);
			return organizations;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("机构 【"+orgId+"】 对应的父机构不存在");
			}else{
				throw new SysSDKInvokeException("获取父机构信息失败", e);
			}
		}
		
	}

	@Override
	public boolean containsUser(String orgId, String userId) {
		try {
			if(orgId==null || orgId.length()==0 || userId==null || userId.length()==0)   return false;
			utils.geRestTemplate().headForHeaders(utils.getUrl("/v1/organizations/{oid}/users/{uid}"), orgId ,userId);
			return true;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				return false;
			}else{
				throw new SysSDKInvokeException("获取机构下的用户信息失败", e);
			}
		}
	}

}
