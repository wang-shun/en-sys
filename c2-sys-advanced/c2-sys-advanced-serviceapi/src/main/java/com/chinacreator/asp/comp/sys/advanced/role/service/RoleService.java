package com.chinacreator.asp.comp.sys.advanced.role.service;

/**
 * 角色服务接口
 * 
 * @author 彭盛
 * 
 */
public interface RoleService extends
		com.chinacreator.asp.comp.sys.basic.role.service.RoleService {

	/**
	 * 添加角色给岗位
	 * 
	 * @param roleId
	 *            角色ID
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void assignToJob(String roleId, String... jobIds);

	/**
	 * 批量添加角色给岗位
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void assignToJob(String[] roleIds, String[] jobIds);

	/**
	 * 设置角色给岗位
	 * 
	 * @param roleId
	 *            角色ID
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void setToJob(String roleId, String... jobIds);

	/**
	 * 批量设置角色给岗位
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void setToJob(String[] roleIds, String[] jobIds);

	/**
	 * 批量回收指定角色与其授予的所有岗位关系
	 * 
	 * @param roleIds
	 *            角色ID数组
	 */
	public void revokeFromAllJobs(String... roleIds);

	/**
	 * 回收指定角色与其授予的岗位关系
	 * 
	 * @param roleId
	 *            角色ID
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void revokeFromJobs(String roleId, String... jobIds);

	/**
	 * 批量回收指定角色与其授予的岗位关系
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void revokeFromJobs(String[] roleIds, String[] jobIds);

	/**
	 * 判断角色是否已分配给岗位
	 * 
	 * @param roleId
	 *            角色ID
	 * @param jobId
	 *            岗位ID
	 * @return true:有，false:无
	 */
	public boolean isAssignedToJob(String roleId, String jobId);

}
