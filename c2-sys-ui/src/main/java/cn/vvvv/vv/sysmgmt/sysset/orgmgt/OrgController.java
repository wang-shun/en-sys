package cn.vvvv.vv.sysmgmt.sysset.orgmgt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.basic.dict.dto.DictDataDTO;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.sysmgmt.sysset.orgmgt.OrgMgt;

@RestController
public class OrgController {

	@Autowired
	OrgService orgService;

	@Autowired
	OrgMgt orgMgt;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "ws/getOrgByPK", method = RequestMethod.POST)
	public Map getOrgByPK(@RequestBody JSONObject params) {
		String orgId = params.getString("orgId");
		String parentId = params.getString("parentId");
		Map map = new HashMap();
		map.put("result", orgMgt.getOrgByPK(orgId, parentId));
		return map;
	}
	
	@RequestMapping(value = "ws/editOrg", method = RequestMethod.POST)
	public int editOrg(@RequestBody JSONObject params) {
		OrgDTO org = params.getObject("result", OrgDTO.class);
		orgService.update(org);
		return 1;
	}
	
	@RequestMapping(value = "ws/addOrg", method = RequestMethod.POST)
	public int addOrg(@RequestBody JSONObject params) {
		OrgDTO org = params.getObject("result", OrgDTO.class);
		orgService.create(org);
		return 1;
	}
	
	@RequestMapping(value = "ws/delOrg", method = RequestMethod.POST)
	public int delOrg(@RequestBody String[] orgIds) {
		orgService.deleteByPKs(orgIds);
		return 1;
	}
	
	@RequestMapping(value = "ws/validateFormByOrg",method=RequestMethod.POST)
	public Map<String, String> validateFormByDictData(@RequestBody JSONObject params) {
		String field = params.getString("field");
		String fieldValue = params.getString("fieldValue"); 
		String formType = params.getString("formType"); 
		OrgDTO orgDTO = params.getObject("orgDTO", OrgDTO.class);
		return orgMgt.validateFormByOrg(field, fieldValue, formType, orgDTO);
	}
}
