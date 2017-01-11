package com.chinacreator.c2.sys.sdk.web.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.basic.dict.dto.DictDataDTO;
import com.chinacreator.asp.comp.sys.basic.dict.service.DictDataService;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.c2.res.exception.ResourceNotFoundException;
import com.chinacreator.c2.sys.sdk.beans.DictDataImpl;
import com.chinacreator.c2.sysmgr.dict.DictData;

@Service
@Path("/v1/dictdata")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(tags="dictdata")
@ApiResponses({
		@ApiResponse(code = 401, message = "用户没有登录", response = Error.class),
		@ApiResponse(code = 403, message = "没有权限访问", response = Error.class), })
public class DictDataResource {
	@Autowired
	private DictDataService dictDataService;
	
	@GET
	@ApiOperation(value = "通过字典类型名称获取字典数据map")
	@ApiResponses({
		@ApiResponse(code = 200, message = "获取成功", response = DictData.class),
		@ApiResponse(code = 404, message = "数据字典不存在", response = Error.class) })
	public Map<String, List<DictDataImpl>> getDictDataMap(
			@ApiParam("字典类型名称数组")@QueryParam("names")String[] names){
		if(names.length==0 || names==null) return null;
		List<DictDataDTO> dictDatas = new ArrayList<DictDataDTO>();
		Map<String, List<DictDataImpl>> dictDataMap = new HashMap<String, List<DictDataImpl>>();
		
		if(names.length==1){
			dictDatas =  dictDataService.queryByDictTypeName(names[0]);
			List<DictDataImpl> dictDataList = new ArrayList<DictDataImpl>();
			BeanCopierUtil.copy(dictDatas, dictDataList, DictDataDTO.class, DictDataImpl.class);
			dictDataMap.put(names[0], dictDataList);
		}else{
			for (String dictTypeName : names) {
				dictDatas =  dictDataService.queryByDictTypeName(dictTypeName);
				List<DictDataImpl> dictDataList = new ArrayList<DictDataImpl>();
				BeanCopierUtil.copy(dictDatas, dictDataList, DictDataDTO.class, DictDataImpl.class);
				dictDataMap.put(dictTypeName, dictDataList);
			}
		}
		if(dictDataMap.isEmpty() || dictDataMap.size()==0){
			throw new ResourceNotFoundException("数据字典 【"+names+"】 不存在");
		}
		
		return dictDataMap;
	}
}
