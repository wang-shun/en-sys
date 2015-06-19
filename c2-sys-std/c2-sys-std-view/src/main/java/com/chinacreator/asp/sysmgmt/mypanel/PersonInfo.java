package com.chinacreator.asp.sysmgmt.mypanel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;

@Component
public class PersonInfo {

	@Autowired
	private AccessControlService accessControlService;

	@Autowired
	private UserService userService;

	/**
	 * 获取指定用户所属机构显示名称字符串
	 * 
	 * @return 用户所属机构显示名称字符串，例："(主)机构A，机构B，机构C"<br>
	 *         一条记录也没查询到的情况下返回空字符串
	 */
	public String getOrgs() {
		String userId = accessControlService.getUserID();
		List<OrgDTO> orgList = userService.queryOrgs(userId);
		OrgDTO mainOrg = userService.queryMainOrg(userId);
		String mainOrgId = null;
		if (null != mainOrg) {
			mainOrgId = mainOrg.getOrgId();
		}
		StringBuffer str = new StringBuffer();
		for (OrgDTO orgDTO : orgList) {
			if (null != orgDTO) {
				if (null != mainOrgId && !mainOrgId.trim().equals("")
						&& mainOrgId.equals(orgDTO.getOrgId())) {
					str.insert(0, ",");
					str.insert(0, orgDTO.getOrgShowName());
					str.insert(0, MyPanelMessages.getString("MYPANEL.MAINORG"));
				} else {
					str.append(orgDTO.getOrgShowName());
					str.append(",");
				}
			}
		}
		return str.length() > 0 ? str.substring(0, str.length() - 1) : "";
	}
}
