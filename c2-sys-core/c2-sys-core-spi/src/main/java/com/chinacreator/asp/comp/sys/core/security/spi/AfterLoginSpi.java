package com.chinacreator.asp.comp.sys.core.security.spi;

import com.chinacreator.asp.comp.sys.common.spiutil.CommonSortSpi;
import com.chinacreator.c2.sysmgr.AuthenticationToken;

/**
 * 登录后调用接口
 * 
 * @author 彭盛
 * 
 */
public interface AfterLoginSpi extends CommonSortSpi {

	/**
	 * 登录后操作
	 * 
	 * @param token
	 *            身份认证信息
	 */
	public void afterLogin(AuthenticationToken token);
}
