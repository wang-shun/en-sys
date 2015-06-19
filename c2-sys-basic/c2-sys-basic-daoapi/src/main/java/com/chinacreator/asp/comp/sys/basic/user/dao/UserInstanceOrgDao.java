package com.chinacreator.asp.comp.sys.basic.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.basic.user.eo.UserInstanceOrgEO;

/**
 * 
 * 用户实例机构扩展信息数据访问接口
 * 
 * @author 杨祎程
 * 
 */
@Repository
public interface UserInstanceOrgDao {

	/**
	 * 创建用户的机构扩展信息
	 * 
	 * @param userInstanceOrgEO
	 *            用户机构扩展信息数据库访问对象
	 * @return 影响的行数
	 */
	public int create(UserInstanceOrgEO userInstanceOrgEO);

	/**
	 * 修改机构下用户的排序号
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @param sn
	 *            机构下用户排序号
	 * @return 影响的行数
	 */
	public int setOrderInOrg(@Param("userId") String userId,
			@Param("orgId") String orgId, @Param("sn") Integer sn);

	/**
	 * 修改机构下用户的主机构信息
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @param isMain
	 *            是否主机构（'1':是 '0':否）
	 * @return
	 */
	public int setMainOrg(@Param("userId") String userId,
			@Param("orgId") String orgId, @Param("isMain") String isMain);

	/**
	 * 将用户下所有用户实例的主机构设置都置为非主机构
	 * 
	 * @param userId
	 *            用户ID
	 * @return 影响的行数
	 */
	public int updateMainOrgFalse(@Param("userId") String userId);

	/**
	 * 通过用户实例ID批量删除用户实例的机构扩展信息
	 * 
	 * @param userInstanceIds
	 *            用户实例数组
	 * @return 影响的行数
	 */
	public int deleteByUserInstanceIds(String... userInstanceIds);

	/**
	 * 通过用户实例ID查询用户实例的机构扩展信息
	 * 
	 * @param userInstanceIds
	 *            用户实例数组
	 * @return 影响的行数
	 */
	public List<UserInstanceOrgEO> queryByUserInstanceIds(
			@Param("userInstanceIds") String... userInstanceIds);

	/**
	 * 判断指定机构是否用户的主机构
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @return 大于0：是，否则：不是
	 */
	public int isMainOrg(@Param("userId") String userId,
			@Param("orgId") String orgId);
}
