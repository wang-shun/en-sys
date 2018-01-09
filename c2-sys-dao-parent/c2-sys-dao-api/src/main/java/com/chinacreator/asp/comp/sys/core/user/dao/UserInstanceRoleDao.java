package com.chinacreator.asp.comp.sys.core.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.core.user.eo.UserInstanceRoleEO;

/**
 * 用户实例与角色关系数据访问接口
 * 
 * @author 蒋海杰
 * 
 */
@Repository
public interface UserInstanceRoleDao {

	/**
	 * 新增用户实例与角色关系
	 * 
	 * @param userInstanceRoleEO
	 *            用户实例与角色关系数据传输对象
	 */
	public int create(UserInstanceRoleEO userInstanceRoleEO);

	/**
	 * 批量新增用户实例与角色关系
	 * 
	 * @param listUserInstanceRoleEO
	 *            用户实例与角色关系数据传输对象列表
	 */
	public int createBatch(List<UserInstanceRoleEO> listUserInstanceRoleEO);

	/**
	 * 批量删除用户实例与角色关系
	 * 
	 * @param adminUserId
	 *            超级管理员用户ID
	 * @param adminRoleId
	 *            超级管理员角色ID
	 * @param userInstanceIds
	 *            用户实例ID数组
	 * @return 数据库执行操作影响到的行数
	 */
	public int deleteByUserInstanceIds(@Param("adminUserId") String adminUserId,
			@Param("adminRoleId") String adminRoleId, @Param("userInstanceIds") String... userInstanceIds);

	/**
	 * 批量删除用户实例与角色关系
	 * 
	 * @param adminUserId
	 *            超级管理员用户ID
	 * @param adminRoleId
	 *            超级管理员角色ID
	 * @param roleIds
	 *            角色ID数组
	 * @return 数据库执行操作影响到的行数
	 */
	public int deleteByRoleIds(@Param("adminUserId") String adminUserId, @Param("adminRoleId") String adminRoleId,
			@Param("roleIds") String... roleIds);

	/**
	 * 删除用户实例与角色关系
	 * 
	 * @param adminUserId
	 *            超级管理员用户ID
	 * @param adminRoleId
	 *            超级管理员角色ID
	 * @param userInstanceId
	 *            用户实例ID
	 * @param roleId
	 *            角色ID
	 * @return 数据库执行操作影响到的行数
	 */
	public int deleteByUserInstanceIdAndRoleId(@Param("adminUserId") String adminUserId,
			@Param("adminRoleId") String adminRoleId, @Param("userInstanceId") String userInstanceId,
			@Param("roleId") String roleId);

}
