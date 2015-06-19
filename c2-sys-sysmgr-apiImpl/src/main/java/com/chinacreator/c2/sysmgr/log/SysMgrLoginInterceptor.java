package com.chinacreator.c2.sysmgr.log;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import com.alibaba.fastjson.JSON;
import com.chinacreator.asp.comp.sys.basic.log.dto.LogDTO;
import com.chinacreator.asp.comp.sys.basic.log.service.LogService;
import com.chinacreator.asp.comp.sys.core.security.shiro.token.SysMgrUsernamePasswordToken;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.sysmgr.AuthenticationToken;
import com.chinacreator.c2.sysmgr.RemoteHost;

/**
 * 系统管理登录拦截类
 * 
 * @author 彭盛
 * 
 */
@Aspect
public class SysMgrLoginInterceptor {

	@AfterReturning(value = "execution(* com.chinacreator.asp.comp.sys.core.security.service.AccessControlService.login(..))")
	public void afterReturningByLogin(JoinPoint joinPoint) {
		addLog(joinPoint, true, null);
	}

	@AfterThrowing(value = "execution(* com.chinacreator.asp.comp.sys.core.security.service.AccessControlService.login(..))", throwing = "ex")
	public void afterThrowingByLogin(JoinPoint joinPoint, Throwable ex) {
		addLog(joinPoint, false, ex.getMessage());
	}

	private void addLog(JoinPoint joinPoint, boolean status, String errMessage) {
		if (null != joinPoint && null != joinPoint.getArgs()
				&& joinPoint.getArgs().length > 0) {
			Object object = joinPoint.getArgs()[0];
			if (null != object) {
				String userName = null;

				if (object instanceof AuthenticationToken) {
					AuthenticationToken token = (AuthenticationToken) object;
					if (null != token
							&& token instanceof SysMgrUsernamePasswordToken) {
						SysMgrUsernamePasswordToken sysToken = (SysMgrUsernamePasswordToken) token;
						userName = sysToken.getUsername();
					}
				} else if (object instanceof String) {
					userName = (String) object;
				}

				if (null != userName && !userName.trim().equals("")) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("username", userName);
					map.put("errMessage", errMessage);
					
					LogDTO logDTO = new LogDTO();
					/** 用户帐号 */
					logDTO.setLogOperUser(userName);
					/** 日志类型（e：实体操作，ws：服务，dao：持久层操作，custom：自定义） */
					logDTO.setLogType("ws");
					/** 日志操作ID */
					logDTO.setLogOper("rule:com.chinacreator.c2.sysmgr.security.login");
					/** 日志操作描述 */
					logDTO.setLogOperdesc("登录");
					/** 操作来源（一般为操作员机器ip） */
					logDTO.setLogVisitorial(getRemoteHost().getIP());
					/** 操作内容 */
					logDTO.setLogContent(JSON.toJSONString(map));
					/** 操作类型（ 1:新增 2:删除 3:修改 4:查询 5:其他） */
					logDTO.setOperType(5);
					/** 日志状态（1：成功，0：失败） */
					logDTO.setLogStatus(status ? 1 : 0);

					getLogService().createToLogQueue(logDTO);
				}
			}
		}
	}

	private LogService getLogService() {
		return ApplicationContextManager.getContext().getBean(LogService.class);
	}

	private RemoteHost getRemoteHost() {
		return ApplicationContextManager.getContext().getBean(RemoteHost.class);
	}
}
