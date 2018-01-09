package com.chinacreator.asp.comp.sys.basic.user.spi;

import com.chinacreator.asp.comp.sys.common.spiutil.CommonSortSpi;

/**
 * 重置密码前调用接口
 * 
 * @author 彭盛
 *
 */
public interface BeforeResetPasswordSpi extends CommonSortSpi{

	/**
	 * 重置密码前操作
	 * 
	 * @param userIds
	 *            用户ID数组
	 * 
	 */
	public void beforeResetPasswords(String... userIds);

	/**
	 * 重置密码失败异常回调
	 * 
	 * @param userIds
	 *            用户ID数组
	 */
	public void resetPasswordsExceptionCallback(String... userIds);
}
