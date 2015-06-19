package com.chinacreator.asp.comp.sys.advanced.job.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.advanced.job.eo.JobEO;
import com.chinacreator.asp.comp.sys.basic.menu.eo.MenuAllInfoEO;
import com.chinacreator.asp.comp.sys.basic.org.eo.OrgEO;
import com.chinacreator.asp.comp.sys.core.role.eo.RoleEO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 岗位数据访问接口
 * 
 * @author 杨祎程
 * 
 */
@Repository
public interface JobDao {

	/**
	 * 创建岗位
	 * 
	 * @param jobEO
	 *            岗位数据访问对象
	 * @return 受影响的行数
	 */
	public int create(JobEO jobEO);

	/**
	 * 修改岗位
	 * 
	 * @param jobEO
	 *            岗位数据访问对象
	 * @return 受影响的行数
	 */
	public int update(JobEO jobEO);

	/**
	 * 查询岗位
	 * 
	 * @param jobId
	 *            岗位ID
	 * @return 岗位数据访问对象
	 */
	public JobEO queryByPK(@Param("jobId") String jobId);

	/**
	 * 查询岗位
	 * 
	 * @return 岗位数据访问对象列表
	 */
	public List<JobEO> queryAll();

	/**
	 * 查询岗位
	 * 
	 * @param jobEO
	 *            岗位数据访问对象
	 * @return 岗位数据访问对象列表
	 */
	public List<JobEO> queryByJob(@Param("jobEO") JobEO jobEO);

	/**
	 * 查询岗位
	 * 
	 * @param jobEO
	 *            岗位数据访问对象
	 * @param pageable
	 *            分页条件
	 * @param sortable
	 *            排序条件
	 * @return 分页后的岗位列表
	 */
	public Page<JobEO> queryByJob(@Param("jobEO") JobEO jobEO,
			Pageable pageable, Sortable sortable);

	/**
	 * 查询机构岗位信息
	 * 
	 * @param jobEO
	 *            岗位数据访问对象
	 * @param orgId
	 *            机构ID
	 * @return 岗位列表
	 */
	public List<JobEO> queryOrgJob(@Param("jobEO") JobEO jobEO,
			@Param("orgId") String orgId);

	/**
	 * 查询机构岗位信息(带分页)
	 * 
	 * @param jobEO
	 *            岗位数据访问对象
	 * @param orgId
	 *            机构ID
	 * @param pageable
	 *            分页条件
	 * @param sortable
	 *            排序条件
	 * @return 分页后的岗位列表
	 */
	public Page<JobEO> queryOrgJob(@Param("jobEO") JobEO jobEO,
			@Param("orgId") String orgId, Pageable pageable, Sortable sortable);

	/**
	 * 查询用户岗位信息
	 * 
	 * @param jobEO
	 *            岗位数据访问对象
	 * @param userId
	 *            用户ID
	 * @return 岗位列表
	 */
	public List<JobEO> queryUserJob(@Param("jobEO") JobEO jobEO,
			@Param("userId") String userId);

	/**
	 * 查询用户岗位信息（带分页）
	 * 
	 * @param jobEO
	 *            岗位数据访问对象
	 * @param userId
	 *            用户ID
	 * @param pageable
	 *            分页条件
	 * @param sortable
	 *            排序条件
	 * @return 分页后的岗位列表
	 */
	public Page<JobEO> queryUserJob(@Param("jobEO") JobEO jobEO,
			@Param("userId") String userId, Pageable pageable, Sortable sortable);

	/**
	 * 查询岗位
	 * 
	 * @param orgId
	 *            机构ID
	 * @param userId
	 *            用户ID
	 * @return 岗位列表
	 */
	public List<JobEO> queryByOrgUser(@Param("orgId") String orgId,
			@Param("userId") String userId);

	/**
	 * 查询岗位的角色
	 * 
	 * @param jobId
	 * @return 角色列表
	 */
	public List<RoleEO> queryRoles(@Param("jobId") String jobId);

	/**
	 * 查询指定岗位所属机构
	 * 
	 * @param jobId
	 *            岗位ID
	 * @return 机构数据访问对象
	 */
	public OrgEO queryOrgByJobId(@Param("jobId") String jobId);

	/**
	 * 查询岗位的匿名角色
	 * 
	 * @param jobId
	 *            岗位ID
	 * @param anonymousRoleType
	 *            匿名角色类型ID
	 * @return 匿名角色列表
	 */
	public RoleEO queryAnonymousRoles(@Param("jobId") String jobId,
			@Param("anonymousRoleType") String anonymousRoleType);

	/**
	 * 通过岗位ID查询岗位所在用户组
	 * 
	 * @param jobId
	 *            岗位ID
	 * @return 用户组ID
	 */
	public String queryGroupIdByJobId(@Param("jobId") String jobId);

	/**
	 * 通过岗位ID查询岗位所在用户组
	 * 
	 * @param jobId
	 *            岗位ID数组
	 * @return 用户组ID数组
	 */
	public List<String> queryGroupIdsByJobIds(@Param("jobIds") String[] jobIds);

	/**
	 * 判断岗位是否归属于某个机构
	 * 
	 * @param jobId
	 *            岗位ID
	 * @param orgId
	 *            机构ID
	 * @return 大于0表示归属，否则不归属
	 */
	public int belongsToOrg(@Param("jobId") String jobId,
			@Param("orgId") String orgId);

	/**
	 * 判断岗位是否拥有某角色
	 * 
	 * @param jobId
	 *            岗位ID
	 * @param roleId
	 *            角色ID
	 * @return 大于0表示拥有角色，否则不拥有角色
	 */
	public int hasRole(@Param("jobId") String jobId,
			@Param("roleId") String roleId);

	/**
	 * 判断用户是否属于指定岗位（直接岗位）
	 * 
	 * @param userId
	 *            用户ID
	 * @param jobId
	 *            岗位ID
	 * @return 大于0表示属于指定岗位，否则不属于指定岗位
	 */
	public int belongsToJob(@Param("userId") String userId,
			@Param("jobId") String jobId);

	/**
	 * 判断用户是否属于指定岗位（机构岗位）
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @param jobId
	 *            岗位ID
	 * @return 大于0表示属于指定岗位，否则不属于指定岗位
	 */
	public int belongsToOrgJob(@Param("userId") String userId,
			@Param("orgId") String orgId, @Param("jobId") String jobId);

	/**
	 * 删除岗位信息
	 * 
	 * @param jobIds
	 *            岗位ID数组
	 * @return 影响的行数
	 */
	public int deleteByPKs(String... jobIds);

	/**
	 * 批量查询岗位的菜单信息列表
	 * 
	 * @param jobIds
	 *            岗位ID数组
	 * @return 菜单信息列表
	 */
	public List<MenuAllInfoEO> queryMenusByJobs(
			@Param("jobIds") String... jobIds);

	/**
	 * 判断机构岗位名称是否存在（机构下唯一）
	 * 
	 * @param jobName
	 *            岗位名称
	 * @param orgId
	 *            机构ID
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsJobNameByOrg(@Param("jobName") String jobName,
			@Param("orgId") String orgId);

	/**
	 * 判断通用岗位名称是否存在
	 * 
	 * @param jobName
	 *            岗位名称
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsJobNameByCommon(@Param("jobName") String jobName);

	/**
	 * 判断机构岗位名称是否存在(忽略指定岗位ID名称，编辑时用)
	 * 
	 * @param jobName
	 *            岗位名称
	 * @param orgId
	 *            机构ID
	 * @param jobId
	 *            岗位ID
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsJobNameByOrgIgnoreJobID(@Param("jobName") String jobName,
			@Param("orgId") String orgId, @Param("jobId") String jobId);

	/**
	 * 判断通用岗位名称是否存在(忽略指定岗位ID名称，编辑时用)
	 * 
	 * @param jobName
	 *            岗位名称
	 * @param jobId
	 *            岗位ID
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsJobNameByCommonIgnoreJobID(
			@Param("jobName") String jobName, @Param("jobId") String jobId);
}
