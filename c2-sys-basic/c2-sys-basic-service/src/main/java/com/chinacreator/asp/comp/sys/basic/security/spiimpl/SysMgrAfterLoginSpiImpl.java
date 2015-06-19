package com.chinacreator.asp.comp.sys.basic.security.spiimpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.chinacreator.asp.comp.sys.basic.SysMgrAfterLoginSpiImplMessages;
import com.chinacreator.asp.comp.sys.basic.user.service.UserService;
import com.chinacreator.asp.comp.sys.common.exception.SysException;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;
import com.chinacreator.asp.comp.sys.core.security.shiro.token.SysMgrUsernamePasswordToken;
import com.chinacreator.asp.comp.sys.core.security.spi.AfterLoginSpi;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.sysmgr.AuthenticationToken;

/**
 * 登录后调用接口实现
 * 
 * @author 彭盛
 * 
 */
@Component
public class SysMgrAfterLoginSpiImpl implements AfterLoginSpi {

	@Autowired
	private AccessControlService accessControlService;

	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.basic.user.service.UserServiceImpl")
	private UserService userService;

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void afterLogin(AuthenticationToken token) {
		UserDTO userDTO = accessControlService.getUser();
		if (null != userDTO) {
			// 用户开通状态
			if (null != userDTO.getUserIsvalid()
					&& 2 != userDTO.getUserIsvalid()) {
				throw new SysException(
						SysMgrAfterLoginSpiImplMessages
								.getString("SYSMGRAFTERLOGINSPIIMPL.USER_HAS_NOT_OPENED"));
			}

			int currentDate = getIntByDate(new Date());

			// 用户开通时间
			if (null != userDTO.getDredgeTime()
					&& !userDTO.getDredgeTime().trim().equals("")) {
				Date dredgeTime = null;
				try {
					dredgeTime = new Date(Long.parseLong(userDTO
							.getDredgeTime()));
				} catch (Exception e) {
				}
				if (null != dredgeTime) {
					if (currentDate < getIntByDate(dredgeTime)) {
						throw new SysException(
								SysMgrAfterLoginSpiImplMessages
										.getString("SYSMGRAFTERLOGINSPIIMPL.USER_NOT_TO_OPEN_TIME"));
					}
				}
			}
			// 用户过期时间
			if (null != userDTO.getPastTime()) {
				if (currentDate >= getIntByDate(userDTO.getPastTime())) {
					throw new SysException(
							SysMgrAfterLoginSpiImplMessages
									.getString("SYSMGRAFTERLOGINSPIIMPL.USER_HAS_EXPIRED"));
				}
			}

			// 用户最后登录时间
			userDTO.setLastloginDate(new Date());
			// 用户最后登录IP
			if (null != token && token instanceof SysMgrUsernamePasswordToken) {
				SysMgrUsernamePasswordToken sysMgrUsernamePasswordToken = (SysMgrUsernamePasswordToken) token;
				if (null != sysMgrUsernamePasswordToken.getHost()
						&& !sysMgrUsernamePasswordToken.getHost().trim()
								.equals("")) {
					userDTO.setLoginIp(sysMgrUsernamePasswordToken.getHost());
				}
			}
			// 用户登录次数
			int userLogincount = 0;
			if (null != userDTO.getUserLogincount()) {
				userLogincount = userDTO.getUserLogincount();
				if (userLogincount >= Integer.MAX_VALUE) {
					userLogincount = Integer.MAX_VALUE - 1;
				}
			}
			userDTO.setUserLogincount(++userLogincount);

			userService.update(userDTO);
		}
	}

	private int getIntByDate(Date time) {
		SimpleDateFormat st = new SimpleDateFormat("yyyyMMdd");
		return Integer.parseInt(st.format(time));
	}
}
