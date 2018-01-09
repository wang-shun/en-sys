package com.chinacreator.c2.sysmgr.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinacreator.asp.comp.sys.basic.dict.dto.DictDataDTO;
import com.chinacreator.asp.comp.sys.basic.dict.service.DictDataService;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.c2.ioc.ApplicationContextManager;

/**
 * 字典接口实现
 * 
 * @author 彭盛
 * 
 */
public class SysMgrDictServiceImpl implements DictService {

	@Override
	public Map<String, List<SysMgrDictData>> getDictDataMap(
			String... dictTypeNames) {
		Map<String, List<SysMgrDictData>> map = new HashMap<String, List<SysMgrDictData>>();
		if (null != dictTypeNames && dictTypeNames.length > 0) {
			for (String dictTypeName : dictTypeNames) {
				List<DictDataDTO> dictDataDTOs = getDictDataService()
						.queryByDictTypeName(dictTypeName);
				if (null != dictDataDTOs && !dictDataDTOs.isEmpty()) {
					List<SysMgrDictData> list = new ArrayList<SysMgrDictData>();
					BeanCopierUtil.copy(dictDataDTOs, list, DictDataDTO.class,
							SysMgrDictData.class);
					map.put(dictTypeName, list);
				}
			}
		}
		return map;
	}

	@Override
	public List<SysMgrDictData> getDictDataList(String dictTypeName) {
		List<DictDataDTO> dictDataDTOList = new ArrayList<DictDataDTO>();
		DictDataDTO dictDataDTO = new DictDataDTO();
		dictDataDTO.setDictdataName("");
		dictDataDTO.setDictdataValue("");
		dictDataDTO.setDictdataIsdefault(true);
		dictDataDTOList.add(dictDataDTO);

		if (null != dictTypeName && !dictTypeName.trim().equals("")) {
			List<DictDataDTO> dictDataDTOs = getDictDataService()
					.queryByDictTypeName(dictTypeName);
			for (DictDataDTO dataDTO : dictDataDTOs) {
				dataDTO.setDictdataIsdefault(false);
				dictDataDTOList.add(dataDTO);
			}
		}
		List<SysMgrDictData> list = new ArrayList<SysMgrDictData>();
		BeanCopierUtil.copy(dictDataDTOList, list, DictDataDTO.class,
				SysMgrDictData.class);
		return list;
	}

	public Map<String, Map<String, String>> getSimpleDictDateMap(
			String... dictTypeNames) {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		if (null != dictTypeNames && dictTypeNames.length > 0) {
			for (String dictTypeName : dictTypeNames) {
				List<DictDataDTO> dictDataDTOs = getDictDataService()
						.queryByDictTypeName(dictTypeName);
				if (null != dictDataDTOs && !dictDataDTOs.isEmpty()) {
					Map<String, String> dictDataMap = new HashMap<String, String>();
					for (DictDataDTO dictDataDTO : dictDataDTOs) {
						dictDataMap.put(dictDataDTO.getDictdataName(),
								dictDataDTO.getDictdataValue());
					}
					map.put(dictTypeName, dictDataMap);
				}
			}
		}
		return map;
	}

	private DictDataService getDictDataService() {
		return ApplicationContextManager.getContext().getBean(
				DictDataService.class);
	}
}
