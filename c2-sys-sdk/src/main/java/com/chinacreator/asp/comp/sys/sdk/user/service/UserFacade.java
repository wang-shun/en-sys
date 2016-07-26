package com.chinacreator.asp.comp.sys.sdk.user.service;

/**
 * 用户Facade接口
 * 
 * @author 彭盛
 * 
 */
public interface UserFacade {

	/**
	 * 用户设置主机构
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param orgId
	 *            主机构ID
	 * @param isRetain
	 *            用户是否保留在原机构下(true:是，false:否)
	 */
	public void setMainOrg(String[] userIds, String orgId, boolean isRetain);
}
