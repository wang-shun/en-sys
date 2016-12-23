package com.chinacreator.asp.comp.sys.core.security.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Service;

import com.chinacreator.c2.sysmgr.AuthenticationToken;

/**
 * 身份验证令牌接口实现(用户名密码方式)
 * 
 * @author 彭盛
 * 
 */
@Service
public class SysMgrUsernamePasswordToken extends UsernamePasswordToken
		implements AuthenticationToken {

	private static final long serialVersionUID = 1L;

}
