package com.chinacreator.asp.comp.sys.advanced.org.service;

import java.util.List;

import com.chinacreator.asp.comp.sys.advanced.job.dto.JobDTO;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;

/**
 * 机构服务接口
 * 
 * @author 彭盛
 * 
 */
public interface OrgService extends
		com.chinacreator.asp.comp.sys.basic.org.service.OrgService {

	/**
	 * 查询指定岗位所属机构
	 * 
	 * @param jobId
	 *            岗位ID
	 * @return 机构数据传输对象<br>
	 *         没查询到的情况下返回null
	 */
	public OrgDTO queryByJobId(String jobId);

	/**
	 * 查询机构下拥有的岗位
	 * 
	 * @param orgId
	 *            机构ID
	 * @return 岗位数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<JobDTO> queryJobByOrgId(String orgId);

	/**
	 * 给机构添加岗位
	 * 
	 * @param orgId
	 *            机构ID
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void addJobs(String orgId, String... jobIds);

	/**
	 * 批量给机构添加岗位
	 * 
	 * @param orgIds
	 *            机构ID数组
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void addJobs(String[] orgIds, String[] jobIds);

	/**
	 * 给机构设置岗位
	 * 
	 * @param orgId
	 *            机构ID
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void setJobs(String orgId, String... jobIds);

	/**
	 * 批量给机构设置岗位
	 * 
	 * @param orgIds
	 *            机构ID数组
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void setJobs(String[] orgIds, String[] jobIds);

	/**
	 * 批量删除机构下所有岗位关系
	 * 
	 * @param orgIds
	 *            机构ID数组
	 */
	public void removeAllJobs(String... orgIds);

	/**
	 * 删除机构下岗位关系
	 * 
	 * @param orgId
	 *            机构ID
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void removeJobs(String orgId, String... jobIds);

	/**
	 * 批量删除机构下岗位关系
	 * 
	 * @param orgIds
	 *            机构ID
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void removeJobs(String[] orgIds, String[] jobIds);

	/**
	 * 判断机构下是否有指定岗位
	 * 
	 * @param orgId
	 *            机构ID
	 * @param jobId
	 *            岗位ID
	 * @return true:有，false:无
	 */
	public boolean containsJob(String orgId, String jobId);
}
