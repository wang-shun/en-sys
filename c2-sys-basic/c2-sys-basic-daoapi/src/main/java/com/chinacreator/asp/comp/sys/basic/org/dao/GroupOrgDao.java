package com.chinacreator.asp.comp.sys.basic.org.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.basic.org.eo.GroupOrgEO;

@Repository
public interface GroupOrgDao {

	/**
	 * 创建机构与用户组关系
	 * 
	 * @param groupOrgEO
	 *            机构与用户组关系数据库访问对象
	 * @return 影响的行数
	 */
	public int create(GroupOrgEO groupOrgEO);

	/**
	 * 通过机构ID与用户组ID删除机构与用户组关系
	 * 
	 * @param orgId
	 *            机构ID
	 * @param groupId
	 *            用户组ID
	 * @return 影响的行数
	 */
	public int delete(@Param("orgId") String orgId,
			@Param("groupId") String groupId);

	/**
	 * 通过机构ID数组删除机构与用户组关系
	 * 
	 * @param orgIds
	 *            机构ID数组
	 * @return 影响的行数
	 */
	public int deleteByOrgIds(String... orgIds);

}
