package com.chinacreator.asp.comp.sys.advanced.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.advanced.user.eo.UserInstanceJobEO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserInstanceEO;

/**
 * 用户实例与岗位关系表数据访问接口
 * 
 * @author 杨祎程
 * 
 */
@Repository
public interface UserInstanceJobDao {
	
	/**
	 * 创建用户的岗位扩展信息
	 * @param userInstanceJobEO 用户实例与岗位关系数据访问对象类
	 * @return 影响的行数
	 */
	public int create(UserInstanceJobEO userInstanceJobEO);

	/**
	 * 通过用户实例批量删除岗位与用户实例关系
	 * 
	 * @param userinstanceIds
	 *            用户实例ID数组
	 * @return 影响的行数
	 */
	public int deleteByUserInstanceIds(String[] userinstanceIds);

	/**
	 * 查询属于用户且属于机构的岗位
	 * 
	 * @param orgId
	 *            机构ID
	 * @param userIds
	 *            用户ID数组
	 * @return 岗位ID集合
	 */
	public List<UserInstanceEO> queryUserJobsInOrg(
			@Param("orgId") String orgId, @Param("userIds") String[] userIds);

}
