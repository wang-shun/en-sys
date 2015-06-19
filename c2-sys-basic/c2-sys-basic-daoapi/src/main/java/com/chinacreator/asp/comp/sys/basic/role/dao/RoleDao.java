package com.chinacreator.asp.comp.sys.basic.role.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 角色数据访问接口
 * 
 * @author 杨祎程
 * 
 */
@Repository
public interface RoleDao {

	/**
	 * 批量删除用户组与角色关系
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @return 数据库执行操作影响到的行数
	 */
	public int deleteOrgRoleRelationByRoleIds(String... roleIds);

	/**
	 * 查询用户是不是角色的创建者
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleId
	 *            角色ID
	 * @return 大于0为是，否则为否
	 */
	public int isRoleSCreator(@Param("userId") String userId,
			@Param("roleId") String roleId);

	/**
	 * 查询指定用户和指定角色类型所拥有的角色
	 * 
	 * @param userIds
	 *            用户ID集合
	 * @param roleTypeId
	 *            角色类型ID
	 * @return 用户所拥有的角色ID列表
	 */
	public List<String> queryRoleIdsByUserIds(
			@Param("userIds") List<String> userIds,
			@Param("roleType") String roleTypeId);

	/**
	 * 查询指定用户实例和指定角色类型所拥有的角色
	 * 
	 * @param userInstanceIds
	 *            用户实例ID数组
	 * @param roleTypeId
	 *            角色类型ID
	 * @return 用户实例所拥有的角色ID列表
	 */
	public List<String> queryRoleIdsByUserInstance(
			@Param("userInstanceIds") String[] userInstanceIds,
			@Param("roleType") String roleTypeId);

}