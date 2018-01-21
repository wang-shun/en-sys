package com.chinacreator.asp.sysmgmt.sysset.dictmgt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chinacreator.asp.comp.sys.basic.DictMessages;
import com.chinacreator.asp.comp.sys.basic.dict.dto.DictDataDTO;
import com.chinacreator.asp.comp.sys.basic.dict.dto.DictTypeDTO;
import com.chinacreator.asp.comp.sys.basic.dict.service.DictDataService;
import com.chinacreator.asp.comp.sys.basic.dict.service.DictTypeService;
import com.chinacreator.asp.comp.sys.common.CommonConstants;

@Controller
public class DictMgt {

	@Autowired
	private DictTypeService dictTypeService;

	@Autowired
	private DictDataService dictDataService;

	/**
	 * 通过字典类型ID获取字典类型对象<br>
	 * 如果字典类型ID为空，返回一个空字典类型对象
	 * 
	 * @param dictTypeId
	 *            字典类型ID
	 * @return 字典类型对象
	 */
	@RequestMapping(value = "dicttype/{dictTypeId}",method=RequestMethod.GET)
	public DictTypeDTO getDictTypeByPK(@PathVariable() String dictTypeId) {
		DictTypeDTO dictTypeDTO = new DictTypeDTO();
		if (null != dictTypeId && !dictTypeId.trim().equals("")) {
			dictTypeDTO = dictTypeService.queryByPK(dictTypeId);
		}
		return dictTypeDTO;
	}
	
	@RequestMapping(value = "dicttype",method=RequestMethod.POST)
	public boolean createDictType(@RequestBody() DictTypeDTO dictTypeDTO){
		boolean suceess = true;
		try{
			dictTypeService.create(dictTypeDTO);			
		}catch(Exception e){
			e.printStackTrace();
			suceess = false;
		}
		return suceess;
	}
	
	@RequestMapping(value = "dicttype",method=RequestMethod.PUT)
	public boolean updateDictType(@RequestBody() DictTypeDTO dictTypeDTO){
		boolean suceess = true;
		try{
			dictTypeService.update(dictTypeDTO);			
		}catch(Exception e){
			e.printStackTrace();
			suceess = false;
		}
		return suceess;
	}	
	
	@RequestMapping(value = "dicttype/delete",method=RequestMethod.POST)
	public boolean deleteDictTypeByIds(@RequestBody() String[] ids){
		boolean suceess = true;
		try{
			dictTypeService.deleteByPKs(ids);	
		}catch(Exception e){
			e.printStackTrace();
			suceess = false;
		}
		return suceess;
	}	
	
	@RequestMapping(value = "dictdata",method=RequestMethod.POST)
	public boolean createDictdata(@RequestBody() DictDataDTO dictDataDTO){
		boolean suceess = true;
		try{
			dictDataService.create(dictDataDTO);	
		}catch(Exception e){
			e.printStackTrace();
			suceess = false;
		}
		return suceess;		
	}
	
	@RequestMapping(value = "dictdata",method=RequestMethod.PUT)
	public boolean updateDictdata(@RequestBody() DictDataDTO dictDataDTO){
		boolean suceess = true;
		try{
			dictDataService.update(dictDataDTO);	
		}catch(Exception e){
			e.printStackTrace();
			suceess = false;
		}
		return suceess;		
	}	
	
	@RequestMapping(value = "dictdata/delete",method=RequestMethod.POST)
	public boolean deleteDictdataByPks(@RequestBody() String[] ids){
		boolean suceess = true;
		try{
			dictDataService.deleteByPKs(ids);	
		}catch(Exception e){
			e.printStackTrace();
			suceess = false;
		}
		return suceess;		
	}	
	
	/**
	 * 查询字典数据
	 * 
	 * @param dictTypeId
	 *            字典类型ID
	 * @param dictDataId
	 *            字典数据ID
	 * @return 字典数据对象
	 */
	@RequestMapping(value = "dictdata/{dictDataId}",method=RequestMethod.GET)
	public DictDataDTO getDictDataByPK(@RequestParam() String dictTypeId, @PathVariable() String dictDataId) {
		DictDataDTO dictDataDTO = new DictDataDTO();
		if (null != dictDataId && !dictDataId.trim().equals("")) {
			dictDataDTO = dictDataService.queryByPK(dictDataId);
		} else {
			if (null != dictTypeId && !dictTypeId.trim().equals("")) {
				dictDataDTO.setDicttypeId(dictTypeId);
			}
		}
		return dictDataDTO;
	}

	/**
	 * 字典数据排序
	 * 
	 * @param rowNum
	 *            每页记录条数
	 * @param page
	 *            当前第几页
	 * @param ids
	 *            排序后的字典数据ID数组
	 */
	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	@RequestMapping(value = "dictdata/sort",method=RequestMethod.POST)
	public void sortDict(int rowNum, int page, String... ids) {
		if (null != ids && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				DictDataDTO dictDataDTO = new DictDataDTO();
				dictDataDTO.setDictdataId(ids[i]);
				dictDataDTO.setDictdataOrder((page - 1) * rowNum + i);
				dictDataService.update(dictDataDTO);
			}
		}
	}
	
	@RequestMapping(value = "dicttype/validate",method=RequestMethod.GET)
	public Map<String, String> validateFormByDictType(@RequestParam() String elementId,
			@RequestParam() String elementValue, @RequestParam() String formType, @RequestParam() String dictTypeId) {
		Map<String, String> map = new HashMap<String, String>();
		String validate = "success";
		String errmess = "";
		if (null != elementId && !elementId.trim().equals("")
				&& null != elementValue && !elementValue.trim().equals("")
				&& null != formType && !formType.trim().equals("")) {
			elementId = elementId.trim();
			elementValue = elementValue.trim();
			if ("add".equals(formType)) {
				if ("dicttypeName".equals(elementId)) {
					if (dictTypeService.existsByDictTypeName(elementValue)) {
						validate = "error";
						errmess = DictMessages
								.getString("DICTTYPE.DICT_TYPE_NAME_ALREADY_EXIST");
					}
				}
			} else if ("edit".equals(formType)) {
				if ("dicttypeName".equals(elementId)) {
					if (dictTypeService.existsByDictTypeNameIgnoreDictTypeID(
							elementValue, dictTypeId)) {
						validate = "error";
						errmess = DictMessages
								.getString("DICTTYPE.DICT_TYPE_NAME_ALREADY_EXIST");
					}
				}
			}
		}
		map.put("validate", validate);
		map.put("errmess", errmess);
		return map;
	}
	
	@RequestMapping(value = "dictdata/validate",method=RequestMethod.POST)
	public Map<String, String> validateFormByDictData(@RequestParam() String elementId,
			@RequestParam() String elementValue, @RequestParam() String formType, @RequestBody() DictDataDTO dictDataDTO) {
		Map<String, String> map = new HashMap<String, String>();
		String validate = "success";
		String errmess = "";
		if (null != elementId && !elementId.trim().equals("")
				&& null != elementValue && !elementValue.trim().equals("")
				&& null != formType && !formType.trim().equals("")
				&& null != dictDataDTO) {
			elementId = elementId.trim();
			elementValue = elementValue.trim();
			if ("add".equals(formType)) {
				if ("dictdataName".equals(elementId)) {
					if (dictDataService.existsByDictDataName(
							dictDataDTO.getDicttypeId(), elementValue)) {
						validate = "error";
						errmess = DictMessages
								.getString("DICTDATA.DICTTYPE_NAME_ALREADY_EXIST");
					}
				}
			} else if ("edit".equals(formType)) {
				if ("dictdataName".equals(elementId)) {
					if (dictDataService.existsByDictDataNameIgnoreDictDataID(
							elementValue, dictDataDTO.getDicttypeId(),
							dictDataDTO.getDictdataId())) {
						validate = "error";
						errmess = DictMessages
								.getString("DICTDATA.DICTTYPE_NAME_ALREADY_EXIST");
					}
				}
			}
		}
		map.put("validate", validate);
		map.put("errmess", errmess);
		return map;
	}
}
