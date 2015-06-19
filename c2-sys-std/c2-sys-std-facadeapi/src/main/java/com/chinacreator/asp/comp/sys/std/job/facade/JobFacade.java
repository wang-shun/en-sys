package com.chinacreator.asp.comp.sys.std.job.facade;

import java.util.List;

import com.chinacreator.asp.comp.sys.advanced.job.dto.JobDTO;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;

/**
 * 岗位Facade接口
 * 
 * @author 彭盛
 * 
 */
public interface JobFacade {

	/**
	 * 新增岗位
	 * 
	 * @param jobDTO
	 *            岗位数据传输对象
	 * @param orgId
	 *            机构ID
	 */
	public void create(JobDTO jobDTO, String orgId);

	/**
	 * 修改岗位
	 * 
	 * @param jobDTO
	 *            岗位数据传输对象
	 */
	public void update(JobDTO jobDTO);

	/**
	 * 删除岗位
	 * 
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void deleteByPKs(String... jobIds);

	/**
	 * 查询岗位拥有的权限
	 * 
	 * @param jobIds
	 *            岗位ID数组
	 * @return 权限数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<PrivilegeDTO> queryPrivilegeByJobs(String... jobIds);

	/**
	 * 添加岗位拥有的权限
	 * 
	 * @param jobId
	 *            岗位ID
	 * @param privilegeIds
	 *            权限ID数组
	 */
	public void assignPrivilege(String jobId, String... privilegeIds);

	/**
	 * 添加岗位拥有的权限
	 * 
	 * @param jobIds
	 *            岗位ID数组
	 * @param privilegeIds
	 *            权限ID数组
	 */
	public void assignPrivilege(String[] jobIds, String[] privilegeIds);

	/**
	 * 设置岗位拥有的权限
	 * 
	 * @param jobId
	 *            岗位ID
	 * @param privilegeIds
	 *            权限ID数组
	 */
	public void setPrivileges(String jobId, String... privilegeIds);

	/**
	 * 设置岗位拥有的权限
	 * 
	 * @param jobIds
	 *            岗位ID数组
	 * @param privilegeIds
	 *            权限ID数组
	 */
	public void setPrivileges(String[] jobIds, String[] privilegeIds);

	/**
	 * 批量回收岗位拥有的所有权限
	 * 
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void revokeAllPrivileges(String... jobIds);

	/**
	 * 回收岗位拥有的权限
	 * 
	 * @param jobId
	 *            岗位ID
	 * @param privilegeIds
	 *            权限ID数组
	 */
	public void revokePrivileges(String jobId, String... privilegeIds);

	/**
	 * 回收岗位拥有的权限
	 * 
	 * @param jobIds
	 *            岗位ID数组
	 * @param privilegeIds
	 *            权限ID数组
	 */
	public void revokePrivileges(String[] jobIds, String[] privilegeIds);

	/**
	 * 判断岗位是否拥有指定权限
	 * 
	 * @param jobId
	 *            岗位ID
	 * @param privilegeId
	 *            权限ID
	 * @return true:有，false:无
	 */
	public boolean hasPrivilege(String jobId, String privilegeId);

	/**
	 * 获取管理员岗位ID
	 * 
	 * @return 管理员岗位ID
	 */
	public String getAdministratorJobId();

	/**
	 * 获取普通用户岗位ID
	 * 
	 * @return 普通用户岗位ID
	 */
	public String getRoleofeveryoneJobId();

	/**
	 * 判断通用岗位名称是否存在
	 * 
	 * @param jobName
	 *            岗位名称
	 * @return true:存在，false:不存在
	 */
	public boolean existsJobNameByCommon(String jobName);

	/**
	 * 判断通用岗位名称是否存在(忽略指定岗位ID名称，编辑时用)
	 * 
	 * @param jobName
	 *            岗位名称
	 * @param jobId
	 *            岗位ID
	 * @return true:存在，false:不存在
	 */
	public boolean existsJobNameByCommonIgnoreJobID(String jobName, String jobId);

	/**
	 * 判断机构岗位名称是否存在（机构下唯一）
	 * 
	 * @param jobName
	 *            岗位名称
	 * @param orgId
	 *            机构ID
	 * @return true:存在，false:不存在
	 */
	public boolean existsJobNameByOrg(String jobName, String orgId);

	/**
	 * 判断机构岗位名称是否存在(忽略指定岗位ID名称，编辑时用)
	 * 
	 * @param jobName
	 *            岗位名称
	 * @param orgId
	 *            机构ID
	 * @param jobId
	 *            岗位ID
	 * @return true:存在，false:不存在
	 */
	public boolean existsJobNameByOrgIgnoreJobID(String jobName, String orgId,
			String jobId);

}
