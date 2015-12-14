package com.chinacreator.asp.comp.sys.core.user.spi;

import com.chinacreator.asp.comp.sys.common.spiutil.CommonSortSpi;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;

/**
 * 编辑用户后调用接口
 * 
 * @author 彭盛
 *
 */
public interface AfterUpdateUserSpi extends CommonSortSpi {

	/**
	 * 编辑用户后操作
	 * 
	 * @param userDTO
	 *            用户数据传输对象
	 */
	public void afterUpdate(UserDTO userDTO);

	/**
	 * 编辑用户失败异常回调
	 * 
	 * @param userDTO
	 *            用户数据传输对象
	 */
	public void updateExceptionCallback(UserDTO userDTO);
}
