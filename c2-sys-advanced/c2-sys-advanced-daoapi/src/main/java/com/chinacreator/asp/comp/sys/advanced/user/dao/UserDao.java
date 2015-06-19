package com.chinacreator.asp.comp.sys.advanced.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.core.user.eo.UserEO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 
 * 用户信息数据访问接口
 * 
 * @author 彭盛
 * 
 */
@Repository
public interface UserDao {

	/**
	 * 查询指定岗位下用户
	 * 
	 * @param userEO
	 *            用户数据访问对象
	 * @param jobId
	 *            岗位ID
	 * @return 用户数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserEO> queryByJob(@Param("userEO") UserEO userEO,
			@Param("jobId") String jobId);

	/**
	 * 分页查询指定岗位下用户
	 * 
	 * @param userEO
	 *            用户数据访问对象
	 * @param jobId
	 *            岗位ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的用户数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<UserEO> queryByJob(@Param("userEO") UserEO userEO,
			@Param("jobId") String jobId, Pageable pageable, Sortable sortable);

	/**
	 * 查询指定机构岗位下用户
	 * 
	 * @param userEO
	 *            用户数据访问对象
	 * @param orgId
	 *            机构ID
	 * @param jobId
	 *            岗位ID
	 * @return 用户数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserEO> queryByOrgJob(@Param("userEO") UserEO userEO,
			@Param("orgId") String orgId, @Param("jobId") String jobId);

	/**
	 * 分页查询指定机构岗位下用户
	 * 
	 * @param userEO
	 *            用户数据访问对象
	 * @param orgId
	 *            机构ID
	 * @param jobId
	 *            岗位ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的用户数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<UserEO> queryByOrgJob(@Param("userEO") UserEO userEO,
			@Param("orgId") String orgId, @Param("jobId") String jobId,
			Pageable pageable, Sortable sortable);
}
