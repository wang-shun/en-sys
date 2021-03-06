package com.chinacreator.asp.comp.sys.std.user.spi;

import com.chinacreator.asp.comp.sys.common.spiutil.CommonSortSpi;

/**
 * 设置用户主机构前调用接口
 * 
 * @author 彭盛
 * 
 */
public interface BeforeSetMainOrgSpi extends CommonSortSpi {

	/**
	 * 用户设置主机构前操作
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param orgId
	 *            主机构ID
	 * @param isRetain
	 *            用户是否保留在原机构下(true:是，false:否)
	 */
	public void beforeSetMainOrg(String[] userIds, String orgId,
			boolean isRetain);
}
