package com.chinacreator.asp.comp.sys.core.security.spi;

import com.chinacreator.asp.comp.sys.common.spiutil.CommonSortSpi;

/**
 * 登出后调用接口
 * 
 * @author 彭盛
 * 
 */
public interface AfterLogoutSpi extends CommonSortSpi {

	/**
	 * 登出后操作
	 * 
	 * @return 返回跳转url，为null时刷新页面
	 * 
	 */
	public String afterLogout();
}
