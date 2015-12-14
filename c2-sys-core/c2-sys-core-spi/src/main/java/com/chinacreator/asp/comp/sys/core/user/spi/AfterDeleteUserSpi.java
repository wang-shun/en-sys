package com.chinacreator.asp.comp.sys.core.user.spi;

import com.chinacreator.asp.comp.sys.common.spiutil.CommonSortSpi;

/**
 * 删除用户后调用接口
 * 
 * @author 彭盛
 *
 */
public interface AfterDeleteUserSpi extends CommonSortSpi {

	/**
	 * 删除用户后操作
	 * 
	 * @param userIds
	 *            用户ID数组
	 */
	public void afterDeleteByPKs(String... userIds);

	/**
	 * 删除用户失败异常回调
	 * 
	 * @param userIds
	 *            用户ID数组
	 */
	public void deleteByPKsExceptionCallback(String... userIds);
}
