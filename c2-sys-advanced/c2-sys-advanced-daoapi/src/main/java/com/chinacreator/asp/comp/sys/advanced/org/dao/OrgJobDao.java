package com.chinacreator.asp.comp.sys.advanced.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.advanced.org.eo.OrgJobEO;

/**
 * 机构岗位关系表数据访问接口
 * 
 * @author 杨祎程
 * 
 */
@Repository
public interface OrgJobDao {

	/**
	 * 批量创建机构与岗位关系
	 * 
	 * @param orgJobEOList
	 *            机构与岗位关系集合
	 * @return 影响的行数
	 */
	public int createBatch(@Param("orgJobEOList") List<OrgJobEO> orgJobEOList);

	/**
	 * 删除岗位与机构关系
	 * 
	 * @param orgId
	 *            机构ID
	 * @param jobId
	 *            岗位ID
	 * @return 受影响的行数
	 */
	public int delete(@Param("orgId") String orgId, @Param("jobId") String jobId);

	/**
	 * 批量删除岗位与机构信息
	 * 
	 * @param jobIds
	 *            岗位ID数组
	 * @return 受影响的行数
	 */
	public int deleteByJobIds(@Param("jobIds") String... jobIds);

	/**
	 * 批量删除岗位与机构信息
	 * 
	 * @param orgIds
	 *            机构ID数组
	 * @return 受影响的行数
	 */
	public int deleteByOrgIds(@Param("orgIds") String... orgIds);

	/**
	 * 批量删除机构下的某岗位信息
	 * 
	 * @param jobId
	 *            岗位ID
	 * @param orgIds
	 *            机构ID数组
	 * @return 影响的行数
	 */
	public int deleteByJobIdOrgIds(@Param("jobId") String jobId,
			@Param("orgIds") String... orgIds);

}
