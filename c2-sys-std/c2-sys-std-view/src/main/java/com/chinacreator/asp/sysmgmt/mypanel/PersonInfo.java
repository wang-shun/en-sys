package com.chinacreator.asp.sysmgmt.mypanel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.log.dto.LogDTO;
import com.chinacreator.asp.comp.sys.basic.log.service.LogService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.common.RemoteHostUtil;
import com.chinacreator.asp.comp.sys.common.exception.SysException;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;
import com.chinacreator.c2.context.OperationContextHolder;
import com.chinacreator.c2.context.WebOperationContext;

@Component
public class PersonInfo {

	@Autowired
	private AccessControlService accessControlService;

	@Autowired
	private UserService userService;

	@Autowired
	private LogService logService;

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
				if (null != mainOrgId && !mainOrgId.trim().equals("") && mainOrgId.equals(orgDTO.getOrgId())) {
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

	/**
	 * 修改密码
	 * 
	 * @param userName
	 *            用户名
	 * @param oldPassword
	 *            旧密码
	 * @param newPassword
	 *            新密码
	 */
	public void modifyPwd(String userName, String oldPassword, String newPassword) {
		String logOper = "rule:asp.sysmgmt.mypanel.modifyPwd";
		String logOperdesc = "修改密码";
		try {
			userService.updatePassword(userName, oldPassword, newPassword);
			saveLog(logOper, logOperdesc, userName, 1, null);
		} catch (Exception e) {
			saveLog(logOper, logOperdesc, userName, 0, e.getMessage());
			throw new SysException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * 修改默认密码
	 * 
	 * @param userName
	 *            用户名
	 * @param newPassword
	 *            新密码
	 */
	public void modifyDefaultPwd(String userName, String newPassword) {
		String logOper = "rule:asp.sysmgmt.mypanel.modifyDefaultPwd";
		String logOperdesc = "修改初始密码";
		try {
			userService.updatePassword(userName, userService.getDefaultPwd(), newPassword);
			saveLog(logOper, logOperdesc, userName, 1, null);
		} catch (Exception e) {
			saveLog(logOper, logOperdesc, userName, 0, e.getMessage());
			throw new SysException(e.getMessage(), e.getCause());
		}
	}

	private void saveLog(String logOper, String logOperdesc, String userName, int status, String errMess) {
		LogDTO logDTO = new LogDTO();
		logDTO.setLogOperUser(accessControlService.getUserName());
		logDTO.setLogType("ws");
		logDTO.setLogOper(logOper);
		logDTO.setLogOperdesc(logOperdesc);

		WebOperationContext context = (WebOperationContext) OperationContextHolder.getContext();
		HttpServletRequest request = context.getRequest();
		logDTO.setLogVisitorial(RemoteHostUtil.getIpAddr(request));

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userName", userName);
		if (0 == status && null != errMess && !errMess.trim().equals("")) {
			jsonObject.put("errMess", errMess);
		}

		logDTO.setLogContent(jsonObject.toJSONString());
		logDTO.setOperType(5);
		logDTO.setLogStatus(status);

		logService.createToLogQueue(logDTO);
	}
}
