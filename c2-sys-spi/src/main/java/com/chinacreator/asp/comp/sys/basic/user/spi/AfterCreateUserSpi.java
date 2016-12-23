package com.chinacreator.asp.comp.sys.basic.user.spi;

import com.chinacreator.asp.comp.sys.common.spiutil.CommonSortSpi;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;

/**
 * 新增用户后调用接口
 * 
 * @author 彭盛
 *
 */
public interface AfterCreateUserSpi extends CommonSortSpi {

	/**
	 * 在指定机构下新增用户后操作
	 * 
	 * @param userDTO
	 *            用户数据传输对象
	 * @param orgId
	 *            机构ID
	 * @param sn
	 *            用户在机构下的排序号
	 */
	public void afterCreate(UserDTO userDTO, String orgId, int sn);

	/**
	 * 在指定机构下新增用户失败异常回调
	 * 
	 * @param userDTO
	 *            用户数据传输对象
	 * @param orgId
	 *            机构ID
	 * @param sn
	 *            用户在机构下的排序号
	 */
	public void createExceptionCallback(UserDTO userDTO, String orgId, int sn);
}
