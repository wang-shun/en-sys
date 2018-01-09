package com.chinacreator.c2.test.web.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinacreator.c2.sys.sdk.client.DictServiceImpl;
import com.chinacreator.c2.sys.sdk.client.beans.DictDataImpl;

@Service
@Path("/dictdata")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SSOClientDictDataTestController {

	@Autowired
	private DictServiceImpl dictService;
	
	@GET
	public  Map<String, List<DictDataImpl>> getDictDataMap() {
		String[] names = {"性别","政治面貌"};
		return dictService.getDictDataMap(names);
	}
	
	@GET
	public List<DictDataImpl> getDictDataList() {
		String dtName = "性别";
		return dictService.getDictDataList(dtName);
	}
	
	@GET
	public Map<String, Map<String, String>> getSimpleDictDateMap() {
		String[] dictTypeNames = {"性别","行政级别"};
		return dictService.getSimpleDictDateMap(dictTypeNames);
	}
}
