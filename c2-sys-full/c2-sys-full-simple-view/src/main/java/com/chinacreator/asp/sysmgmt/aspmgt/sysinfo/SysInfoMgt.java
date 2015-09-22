package com.chinacreator.asp.sysmgmt.aspmgt.sysinfo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinacreator.asp.comp.sys.basic.SysInfoMessages;
import com.chinacreator.asp.comp.sys.basic.sysinfo.dto.SystemInfoDTO;
import com.chinacreator.asp.comp.sys.basic.sysinfo.dto.SystemTypeDTO;
import com.chinacreator.asp.comp.sys.basic.sysinfo.service.SysRTSettingService;
import com.chinacreator.asp.comp.sys.basic.sysinfo.service.SysRTSettingTypeService;

@Component
public class SysInfoMgt {

	@Autowired
	private SysRTSettingTypeService sysRTSettingTypeService;

	@Autowired
	private SysRTSettingService sysRTSettingService;

	/**
	 * 查询参数类型
	 * 
	 * @param sysInfoTypeId
	 *            参数类型ID
	 * @return 参数类型
	 */
	public SystemTypeDTO getSysInfoTypeByPK(String sysInfoTypeId) {
		SystemTypeDTO systemTypeDTO = new SystemTypeDTO();
		if (null != sysInfoTypeId && !sysInfoTypeId.trim().equals("")) {
			systemTypeDTO = sysRTSettingTypeService.queryByPK(sysInfoTypeId);
		}
		return systemTypeDTO;
	}

	/**
	 * 查询参数
	 * 
	 * @param sysInfoTypeId
	 *            参数类型ID
	 * @param sysInfoId
	 *            参数ID
	 * @return 参数
	 */
	public SystemInfoDTO getSysInfoByPK(String sysInfoTypeId, String sysInfoId) {
		SystemInfoDTO systemInfoDTO = new SystemInfoDTO();
		if (null != sysInfoId && !sysInfoId.trim().equals("")) {
			systemInfoDTO = sysRTSettingService.queryByPK(sysInfoId);
		} else {
			if (null != sysInfoTypeId && !sysInfoTypeId.trim().equals("")) {
				systemInfoDTO.setInfoType(sysInfoTypeId);
			}
		}
		return systemInfoDTO;
	}

	public Map<String, String> validateFormBySysInfoType(String elementId,
			String elementValue, String formType, String sysInfoTypeId) {
		Map<String, String> map = new HashMap<String, String>();
		String validate = "success";
		String errmess = "";
		if (null != elementId && !elementId.trim().equals("")
				&& null != elementValue && !elementValue.trim().equals("")
				&& null != formType && !formType.trim().equals("")) {
			elementId = elementId.trim();
			elementValue = elementValue.trim();
			if ("add".equals(formType)) {
				if ("typeName".equals(elementId)) {
					if (sysRTSettingTypeService.existsByTypeName(elementValue)) {
						validate = "error";
						errmess = SysInfoMessages
								.getString("SYSTYPE.SYSTYPENAME_ALREADY_EXIST");
					}
				}
			} else if ("edit".equals(formType)) {
				if ("typeName".equals(elementId)) {
					if (sysRTSettingTypeService.existsByTypeNameIgnoreID(
							elementValue, sysInfoTypeId)) {
						validate = "error";
						errmess = SysInfoMessages
								.getString("SYSTYPE.SYSTYPENAME_ALREADY_EXIST");
					}
				}
			}
		}
		map.put("validate", validate);
		map.put("errmess", errmess);
		return map;
	}

	public Map<String, String> validateFormBySysInfo(String elementId,
			String elementValue, String formType, SystemInfoDTO systemInfoDTO) {
		Map<String, String> map = new HashMap<String, String>();
		String validate = "success";
		String errmess = "";
		if (null != elementId && !elementId.trim().equals("")
				&& null != elementValue && !elementValue.trim().equals("")
				&& null != formType && !formType.trim().equals("")
				&& null != systemInfoDTO) {
			elementId = elementId.trim();
			elementValue = elementValue.trim();
			if ("add".equals(formType)) {
				if ("infoName".equals(elementId)) {
					if (sysRTSettingService.existsBySystemInfoName(
							systemInfoDTO.getInfoType(), elementValue)) {
						validate = "error";
						errmess = SysInfoMessages
								.getString("SYS.SYSTYPE_NAME_ALREADY_EXIST");
					}
				}
			} else if ("edit".equals(formType)) {
				if ("infoName".equals(elementId)) {
					if (sysRTSettingService.existsByInfoNameIgnoreID(
							systemInfoDTO.getInfoType(), elementValue,
							systemInfoDTO.getId())) {
						validate = "error";
						errmess = SysInfoMessages
								.getString("SYS.SYSTYPE_NAME_ALREADY_EXIST");
					}
				}
			}
		}
		map.put("validate", validate);
		map.put("errmess", errmess);
		return map;
	}
}
