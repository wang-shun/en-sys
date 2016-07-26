package com.chinacreator.asp.comp.sys.sdk.org.service;

/**
 * 机构Facade接口
 * 
 * @author 彭盛
 * 
 */
public interface OrgFacade {

	/**
	 * 删除机构
	 * 
	 * @param orgIds
	 *            机构ID数组
	 */
	public void deleteByPKs(String... orgIds);
}
