package com.chinacreator.asp.comp.sys.basic.org.spi;

import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.common.spiutil.CommonSortSpi;

/**
 * 新增机构前调用接口
 * 
 * @author 彭盛
 *
 */
public interface BeforeCreateOrgSpi extends CommonSortSpi {

	/**
	 * 新增机构前操作
	 * 
	 * @param orgDTO
	 *            机构数据传输对象
	 */
	public void beforeCreate(OrgDTO orgDTO);

	/**
	 * 新增机构失败异常回调
	 * 
	 * @param orgDTO
	 *            机构数据传输对象
	 */
	public void createExceptionCallback(OrgDTO orgDTO);
}
