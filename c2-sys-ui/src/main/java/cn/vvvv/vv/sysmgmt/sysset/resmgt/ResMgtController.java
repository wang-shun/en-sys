package cn.vvvv.vv.sysmgmt.sysset.resmgt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinacreator.asp.comp.sys.basic.MenuMessages;
import com.chinacreator.asp.comp.sys.basic.menu.dto.MenuDTO;
import com.chinacreator.asp.comp.sys.basic.menu.service.MenuService;
import com.chinacreator.asp.comp.sys.basic.privilege.service.PrivilegeService;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.core.PrivilegeMessages;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.sysmgmt.sysset.resmgt.ResMgtMessages;

@RestController
@RequestMapping("menu")
public class ResMgtController {

	@Autowired
	private MenuService menuService;

	@Autowired
	private PrivilegeService privilegeService;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	@RequestMapping("create")
	public Object addMenu(@RequestBody MenuDTO menuDTO) {
		if (null != menuDTO) {
			menuService.create(menuDTO);
			String id = menuDTO.getMenuId();
			Map result = new HashMap();
			result.put("id",id);
			return result;
		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUDTO_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public String addRes(PrivilegeDTO privilegeDTO) {
		if (null != privilegeDTO) {
			privilegeDTO.setParentId("other/");
			privilegeDTO.setSource("0");
			privilegeDTO.setType("9");
			privilegeService.create(privilegeDTO);
			return privilegeDTO.getPrivilegeId();
		} else {
			throw new NullPointerException(
					PrivilegeMessages
							.getString("PRIVILEGE.PRIVILEGEDTO_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	@RequestMapping("delRes")
	public void deletePrivileges(@RequestBody JSONObject params) {
		 String[] customIds = params.getJSONArray("customIds").toArray(new String[]{});
		 String[] menuIds = params.getJSONArray("menuIds").toArray(new String[]{});
		if (null != customIds && customIds.length > 0) {
			privilegeService.deleteByPKs(customIds);
		}
		if (null != menuIds && menuIds.length > 0) {
			menuService.deleteByPKs(menuIds);
		}
	}

	@RequestMapping("validatemenu")
	public Map<String, String> validateFormByMenu(@RequestBody JSONObject params) {
		String field = params.getString("field");
		String fieldValue = params.getString("fieldValue"); 
		String formType = params.getString("formType"); 
		MenuDTO menuDTO = params.getObject("menuDTO", MenuDTO.class); 
		Map<String, String> map = new HashMap<String, String>();
		String validate = "success";
		String errmess = "";
		if (null != field && !field.trim().equals("")
				&& null != fieldValue && !fieldValue.trim().equals("")
				&& null != formType && !formType.trim().equals("")) {
			field = field.trim();
			fieldValue = fieldValue.trim();
			if ("add".equals(formType)) {
				if ("privilegeName".equals(field)) {
					if (menuService.existsByMenuName(fieldValue,
							menuDTO.getParentId())) {
						validate = "error";
						errmess = ResMgtMessages
								.getString("RESMGT.PRIVILEGENAME_IS_EXISTS");
					}
				} else if ("privilegeCode".equals(field)) {
					if (menuService.existsByMenuCode(fieldValue)) {
						validate = "error";
						errmess = ResMgtMessages
								.getString("RESMGT.PRIVILEGECODE_IS_EXISTS");
					}
				}
			} else if ("edit".equals(formType)) {
				if ("privilegeName".equals(field)) {
					if (menuService.existsByMenuNameIgnoreMenuID(fieldValue,
							menuDTO.getMenuId())) {
						validate = "error";
						errmess = ResMgtMessages
								.getString("RESMGT.PRIVILEGENAME_IS_EXISTS");
					}
				}
			}
		}
		map.put("validate", validate);
		map.put("errmess", errmess);
		return map;
	}

	@RequestMapping("validateres")
	public Map<String, String> validateFormByRes(@RequestBody JSONObject params) {
		String field = params.getString("field");
		String fieldValue = params.getString("fieldValue"); 
		String formType = params.getString("formType"); 
		PrivilegeDTO privilegeDTO = params.getObject("privilegeDTO", PrivilegeDTO.class); 	
		Map<String, String> map = new HashMap<String, String>();
		String validate = "success";
		String errmess = "";
		if (null != field && !field.trim().equals("")
				&& null != fieldValue && !fieldValue.trim().equals("")
				&& null != formType && !formType.trim().equals("")) {
			field = field.trim();
			fieldValue = fieldValue.trim();
			if ("add".equals(formType)) {
				if ("privilegeCode_Field".equals(field)) {
					if (privilegeService.existsByPrivilegeCode(privilegeDTO
							.getPrivilegeCode())) {
						validate = "error";
						errmess = ResMgtMessages
								.getString("RESMGT.PRIVILEGECODE_IS_EXISTS");
					}
				}
			} else if ("edit".equals(formType)) {
				if ("privilegeCode_Field".equals(field)) {
					if (privilegeService
							.existsByPrivilegeCodeIgnorePrivilegeId(
									privilegeDTO.getPrivilegeCode(),
									privilegeDTO.getParentId())) {
						validate = "error";
						errmess = ResMgtMessages
								.getString("RESMGT.PRIVILEGECODE_IS_EXISTS");
					}
				}
			}
		}
		map.put("validate", validate);
		map.put("errmess", errmess);
		return map;
	}

	/**
	 * 移动资源
	 * 
	 * @param resIds
	 *            被移动的资源ID
	 * @param parentId
	 *            移动到的父资源ID
	 * @param parentType
	 *            移动到的父资源类型
	 * @param sortResIds
	 *            父资源下子资源排序
	 */
	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void moveRes(String[] resIds, String parentId, String parentType,
			String... sortResIds) {
		Map<String, PrivilegeDTO> map = new HashMap<String, PrivilegeDTO>();
		if ("menu".equals(parentType)) {
			if (null != resIds && resIds.length > 0 && null != parentId
					&& !parentId.trim().equals("")) {
				parentId = "menu".equals(parentId) ? "0" : parentId;
				for (String resId : resIds) {
					PrivilegeDTO privilegeDTO = new PrivilegeDTO();
					privilegeDTO.setPrivilegeId(resId);
					privilegeDTO.setParentId(parentId);
					map.put(resId, privilegeDTO);
				}
			}
		}
		if (null != sortResIds && sortResIds.length > 0) {
			for (int i = 0; i < sortResIds.length; i++) {
				PrivilegeDTO privilegeDTO = map.get(sortResIds[i]);
				if (null == privilegeDTO) {
					privilegeDTO = new PrivilegeDTO();
				}
				privilegeDTO.setPrivilegeId(sortResIds[i]);
				privilegeDTO.setSn(i);
				map.put(sortResIds[i], privilegeDTO);
			}
		}
		if (!map.isEmpty()) {
			for (String key : map.keySet()) {
				privilegeService.update(map.get(key));
			}
		}
	}
}
