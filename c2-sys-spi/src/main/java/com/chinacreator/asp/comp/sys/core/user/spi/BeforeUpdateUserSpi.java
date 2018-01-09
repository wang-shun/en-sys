package com.chinacreator.asp.comp.sys.core.user.spi;

import com.chinacreator.asp.comp.sys.common.spiutil.CommonSortSpi;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;

/**
 * 编辑用户前调用接口
 * 
 * @author 彭盛
 *
 */
public interface BeforeUpdateUserSpi extends CommonSortSpi {

	/**
	 * 编辑用户前操作
	 * 
	 * @param userDTO
	 *            用户数据传输对象
	 */
	public void beforeUpdate(UserDTO userDTO);

	/**
	 * 编辑用户失败异常回调
	 * 
	 * @param userDTO
	 *            用户数据传输对象
	 */
	public void updateExceptionCallback(UserDTO userDTO);
}
