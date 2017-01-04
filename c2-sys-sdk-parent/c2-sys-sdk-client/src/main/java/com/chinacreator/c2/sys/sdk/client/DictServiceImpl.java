package com.chinacreator.c2.sys.sdk.client;

import java.util.List;
import java.util.Map;

import com.chinacreator.c2.sysmgr.dict.DictService;

public class DictServiceImpl implements DictService {

	
	/**
	 * 通过字典类型名称获取字典数据map
	 * 
	 * @param <T>
	 * 
	 * @param dictTypeNames
	 * 			字典类型名称数组
	 * 
	 * @return 字典数据map
	 */
	@Override
	public <T extends com.chinacreator.c2.sysmgr.dict.DictData> Map<String, List<T>> getDictDataMap(
			String... dictTypeNames) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 通过字典类型名称获取字典数据列表
	 * 
	 * @param <T>
	 * 
	 * @param dictTypeNames
	 * 			字典类型名称数组
	 * 
	 * @return 字典数据列表
	 */
	@Override
	public <T extends com.chinacreator.c2.sysmgr.dict.DictData> List<T> getDictDataList(
			String dictTypeNames) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 通过字典类型名称获取字典数据map
	 * 
	 * @param <T>
	 * 
	 * @param dictTypeNames
	 * 			字典类型名称数组
	 * 
	 * @return 字典数据map<<字典类型名称>,map<字典数据真实值，字典数据显示值>>
	 */
	@Override
	public Map<String, Map<String, String>> getSimpleDictDateMap(String... dictTypeNames) {
		// TODO Auto-generated method stub
		return null;
	}


}
