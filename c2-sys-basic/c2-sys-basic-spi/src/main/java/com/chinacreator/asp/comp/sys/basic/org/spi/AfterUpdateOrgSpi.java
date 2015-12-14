package com.chinacreator.asp.comp.sys.basic.org.spi;

import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.common.spiutil.CommonSortSpi;

/**
 * 编辑机构后调用接口
 * 
 * @author 彭盛
 *
 */
public interface AfterUpdateOrgSpi extends CommonSortSpi{
	
	/**
	 * 编辑机构后操作
	 * 
	 * @param orgDTO
	 *            机构数据传输对象
	 */
	public void afterUpdate(OrgDTO orgDTO);

	/**
	 * 编辑机构失败异常回调
	 * 
	 * @param orgDTO
	 *            机构数据传输对象
	 */
	public void updateExceptionCallback(OrgDTO orgDTO);
}
