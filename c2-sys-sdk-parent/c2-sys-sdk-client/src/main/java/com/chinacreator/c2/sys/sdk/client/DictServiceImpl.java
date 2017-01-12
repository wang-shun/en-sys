package com.chinacreator.c2.sys.sdk.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinacreator.c2.sys.sdk.client.beans.DictDataImpl;
import com.chinacreator.c2.sys.sdk.client.exception.SysSDKInvokeException;
import com.chinacreator.c2.sysmgr.dict.DictService;
import com.chinacreator.c2.web.exception.ResourceNotFoundException;

@Service
public class DictServiceImpl implements DictService {

	@Autowired
	private SDKUtils utils;

	@Override
	public Map<String, List<DictDataImpl>> getDictDataMap(String... dictTypeNames) {
		try {
			if(dictTypeNames==null || dictTypeNames.length==0) return null;
			StringBuilder builder = new StringBuilder("/v1/dictdata?");
			for (String dictTypeName : dictTypeNames) {
				builder.append("names=").append(dictTypeName);
				builder.append("&");
			}
			String url = builder.substring(0, builder.length()-1);
			@SuppressWarnings("unchecked")
			Map<String, List<DictDataImpl>> dictMap = utils.geRestTemplate().getForObject(utils.getUrl(url), Map.class);
			return dictMap;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("数据字典 【"+dictTypeNames+"】 不存在");
			}else{
				throw new SysSDKInvokeException("获取字典信息失败", e);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DictDataImpl> getDictDataList(String dictTypeName) {
		try {
			if(dictTypeName==null || dictTypeName.isEmpty()) return Collections.emptyList();
			StringBuilder builder = new StringBuilder("/v1/dictdata?");
			builder.append("names=").append(dictTypeName);
			String url = builder.substring(0, builder.length());
			Map<String, List<JSONObject>> dictMap = utils.geRestTemplate().getForObject(utils.getUrl(url), Map.class);
			
			List<DictDataImpl> result = new ArrayList<DictDataImpl>() ;
			if(dictMap!=null && dictMap.size()>0){
				for (Entry<String, List<JSONObject>> entry : dictMap.entrySet()) {
					List<JSONObject> list = entry.getValue();
					for (JSONObject jsonObject : list) {
						result.add(JSON.parseObject(jsonObject.toJSONString(), DictDataImpl.class)) ;
					}
				}
			}
			return result;
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("数据字典 【"+dictTypeName+"】 不存在");
			}else{
				throw new SysSDKInvokeException("获取字典信息失败", e);
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Map<String, String>> getSimpleDictDateMap(String... dictTypeNames) {
		try {
			if(dictTypeNames==null || dictTypeNames.length==0) return null;
			StringBuilder builder = new StringBuilder("/v1/dictdata?");
			for (String dictTypeName : dictTypeNames) {
				builder.append("names=").append(dictTypeName);
				builder.append("&");
			}
			String url = builder.substring(0, builder.length()-1);
			Map<String, List<JSONObject>> dictMap = utils.geRestTemplate().getForObject(utils.getUrl(url), Map.class);
			Map<String, Map<String, String>> simpleDictDataMap = new HashMap<String, Map<String,String>>();
			
			if(dictMap !=null && dictMap.size()>0){
				for (Map.Entry<String, List<JSONObject>> entry : dictMap.entrySet()) {
					List<JSONObject> list = entry.getValue();
					for (JSONObject dictDataImpl : list) {
						Map<String,String> strMap = new HashMap<String, String>();
						String dictTypeName = dictDataImpl.getString("dictdataName");
						String dictdataValue = dictDataImpl.getString("dictdataValue");
						strMap.put(dictTypeName, dictdataValue);
						simpleDictDataMap.put(dictTypeName, strMap);
					}
				}
			}
			return simpleDictDataMap;
			
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode()==HttpStatus.NOT_FOUND){
				throw new ResourceNotFoundException("数据字典 【"+dictTypeNames+"】 不存在");
			}else{
				throw new SysSDKInvokeException("获取字典信息失败", e);
			}
		}
	}


}
