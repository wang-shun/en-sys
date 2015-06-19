package com.chinacreator.asp.comp.sys.advanced.group.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.advanced.group.eo.GroupOrgJobEO;

/**
 * 用户组与机构岗位关系数据访问接口
 * 
 * @author 杨祎程
 * 
 */
@Repository
public interface GroupOrgJobDao {

	/**
	 * 创建用户组与机构岗位关系
	 * 
	 * @param groupOrgJobEO
	 *            用户组与机构岗位关系数据访问对象
	 * @return 影响的行数
	 */
	public int create(GroupOrgJobEO groupOrgJobEO);

	/**
	 * 删除岗位与用户组信息
	 * 
	 * @param jobId
	 *            岗位ID
	 * @param groupId
	 *            用户组ID
	 * @return 影响的行数
	 */
	public int delete(@Param("jobId") String jobId,
			@Param("groupId") String groupId);

	/**
	 * 删除岗位与用户组信息
	 * 
	 * @param jobIds
	 *            岗位ID数组
	 * @return 影响的行数
	 */
	public int deleteByJobIds(String... jobIds);

}
