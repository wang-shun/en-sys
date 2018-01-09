package com.chinacreator.asp.comp.sys.core.user.spi;

import com.chinacreator.asp.comp.sys.common.spiutil.CommonSortSpi;

/**
 * 修改用户密码后调用接口
 * 
 * @author 彭盛
 *
 */
public interface AfterUpdatePasswordSpi extends CommonSortSpi {
	
	/**
	 * 修改用户密码后操作
	 * 
	 * @param userId
	 *            用户ID
	 * @param password
	 *            用户密码
	 */
	public void afterUpdatePassword(String userId, String password);

	/**
	 * 修改用户密码后操作
	 * 
	 * @param userName
	 *            用户账号
	 * @param oldPassword
	 *            用户原密码
	 * @param newPassword
	 *            用户新密码
	 */
	public void afterUpdatePassword(String userName, String oldPassword, String newPassword);

	/**
	 * 修改用户密码失败异常回调
	 * 
	 * @param userId
	 *            用户ID
	 * @param password
	 *            用户密码
	 */
	public void updatePasswordExceptionCallback(String userId, String password);

	/**
	 * 修改用户密码失败异常回调
	 * 
	 * @param userName
	 *            用户账号
	 * @param oldPassword
	 *            用户原密码
	 * @param newPassword
	 *            用户新密码
	 */
	public void updatePasswordExceptionCallback(String userName, String oldPassword, String newPassword);
}
