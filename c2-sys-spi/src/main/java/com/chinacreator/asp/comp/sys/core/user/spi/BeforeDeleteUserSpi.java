package com.chinacreator.asp.comp.sys.core.user.spi;

import com.chinacreator.asp.comp.sys.common.spiutil.CommonSortSpi;

/**
 * 删除用户前调用接口
 * 
 * @author 彭盛
 *
 */
public interface BeforeDeleteUserSpi extends CommonSortSpi {

	/**
	 * 删除用户前操作
	 * 
	 * @param userIds
	 *            用户ID数组
	 */
	public void beforeDeleteByPKs(String... userIds);

	/**
	 * 删除用户失败异常回调
	 * 
	 * @param userIds
	 *            用户ID数组
	 */
	public void deleteByPKsExceptionCallback(String... userIds);
}
