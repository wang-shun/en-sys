package com.chinacreator.asp.comp.sys.basic.org.spi;

import com.chinacreator.asp.comp.sys.common.spiutil.CommonSortSpi;

/**
 * 删除机构前调用接口
 * 
 * @author 彭盛
 *
 */
public interface BeforeDeleteOrgSpi extends CommonSortSpi {

	/**
	 * 删除机构前操作
	 * 
	 * @param orgIds
	 *            机构ID数组
	 */
	public void beforeDeleteByPKs(String... orgIds);

	/**
	 * 删除机构失败异常回调
	 * 
	 * @param orgIds
	 *            机构ID数组
	 */
	public void deleteByPKsExceptionCallback(String... orgIds);
}
