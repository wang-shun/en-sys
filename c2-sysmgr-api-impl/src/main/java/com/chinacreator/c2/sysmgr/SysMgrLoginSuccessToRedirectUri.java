package com.chinacreator.c2.sysmgr;

import org.apache.shiro.authc.credential.PasswordService;

import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.common.CommonPropertiesUtil;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.ioc.ApplicationContextManager;

/**
 * 登录认证成功返回重定向地址接口实现
 * 
 * @author 彭盛
 *
 */
public class SysMgrLoginSuccessToRedirectUri implements LoginSuccessToRedirectUri {

	@Override
	public String getRedirectUri() {
		String redirectUri = null;
		if (CommonPropertiesUtil.isUpdateDefaultPwd()) {
			AccessControlService accessControlService = ApplicationContextManager.getContext()
					.getBean(AccessControlService.class);
			UserService userService = ApplicationContextManager.getContext().getBean(UserService.class);
			PasswordService passwordService = ApplicationContextManager.getContext().getBean(PasswordService.class);

			String userId = accessControlService.getUserID();
			UserDTO userDTO = userService.queryByPK(userId);
			if (null != userDTO
					&& passwordService.passwordsMatch(userService.getDefaultPwd(), userDTO.getUserPassword())) {
				redirectUri = CommonPropertiesUtil.getUpdateDefaultPwdUrl();
			}
		}
		return redirectUri;
	}

}
