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
}
