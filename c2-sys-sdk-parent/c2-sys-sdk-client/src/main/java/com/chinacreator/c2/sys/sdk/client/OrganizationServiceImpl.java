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
import com.chinacreator.c2.sys.sdk.service.query.OrganizationService;
import com.chinacreator.c2.web.exception.ResourceNotFoundException;

@Service
public class OrganizationServiceImpl implements OrganizationService {
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
	public Organization getByName(String orgName) {
		try{
			if(orgName==null || orgName.length()==0) return null;
			StringBuilder builder = new StringBuilder("/v1/organizations?");
			builder.append("orgname=").append(orgName);
			String url = builder.substring(0, builder.length());
			Organization org = utils.geRestTemplate().getForObject(utils.getUrl(url), Organization.class);
			return org;
		}catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("机构 【"+orgName+"】 不存在");
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

			ArrayList<Organization> organizations = utils.geRestTemplate().getForObject(utils.getUrl(url), ArrayList.class, orgId);
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
			ArrayList<Organization> organizations = utils.geRestTemplate().getForObject(utils.getUrl("/v1/organizations/{id}/parents"), ArrayList.class, orgId);
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
				throw new ResourceNotFoundException("机构 【"+orgId+"】 不存在");
			}else{
				throw new SysSDKInvokeException("获取机构下的用户信息失败", e);
			}
		}
	}

}
