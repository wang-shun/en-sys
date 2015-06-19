package com.chinacreator.asp.comp.sys.basic.role.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.basic.role.eo.RoleTypeEO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 角色类型数据访问接口
 * 
 * @author 杨祎程
 * 
 */
@Repository
public interface RoleTypeDao {

	/**
	 * 创建角色类型
	 * 
	 * @param roleTypeEO
	 *            角色类型数据访问对象
	 * @return 影响的行数
	 */
	public int create(RoleTypeEO roleTypeEO);

	/**
	 * 更新角色类型
	 * 
	 * @param roleTypeEO
	 *            角色类型数据访问对象
	 * @return 影响的行数
	 */
	public int update(RoleTypeEO roleTypeEO);

	/**
	 * 批量删除角色类型
	 * 
	 * @param roleTypeIds
	 *            角色类型ID数组
	 * @return 影响的行数
	 */
	public int deleteByPKs(String... roleTypeIds);

	/**
	 * 查询所有角色类型
	 * 
	 * @return 所有角色类型列表
	 */
	public List<RoleTypeEO> queryAll();

	/**
	 * 查询所有角色类型（带分页）
	 * 
	 * @param pageable
	 *            分页参数
	 * @param sortable
	 *            排序参数
	 * @return 分页后的角色类型列表
	 */
	public Page<RoleTypeEO> queryAll(Pageable pageable, Sortable sortable);

	/**
	 * 通过角色类型数据访问对象查询角色类型列表
	 * 
	 * @param roleTypeEO
	 *            角色类型数据访问对象
	 * @return 角色类型列表
	 */
	public List<RoleTypeEO> queryByRoleType(
			@Param("roleTypeEO") RoleTypeEO roleTypeEO);

	/**
	 * 通过角色类型数据访问对象查询角色类型列表（带分页）
	 * 
	 * @param roleTypeEO
	 *            角色类型数据访问对象
	 * @param pageable
	 *            分页参数
	 * @param sortable
	 *            排序参数
	 * @return 分页后的角色类型列表
	 */
	public Page<RoleTypeEO> queryByRoleType(
			@Param("roleTypeEO") RoleTypeEO roleTypeEO, Pageable pageable,
			Sortable sortable);

	/**
	 * 通过角色类型ID查询角色类型信息
	 * 
	 * @param roleTypeId
	 *            角色类型ID
	 * @return 角色类型数据访问对象
	 */
	public RoleTypeEO queryByPK(@Param("roleTypeId") String roleTypeId);

	/**
	 * 查询角色类型名称是否已经存在
	 * 
	 * @param roleTypeName
	 *            角色类型名称
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsByRoleTypeName(@Param("roleTypeName") String roleTypeName);

	/**
	 * 查询角色类型名称是否已经存在(忽略指定角色类型ID名称，编辑时用)
	 * 
	 * @param roleTypeName
	 *            角色类型名称
	 * @param roleTypeId
	 *            角色类型ID
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsByRoleTypeNameIgnoreRoleTypeID(@Param("roleTypeName") String roleTypeName,
			@Param("roleTypeId") String roleTypeId);

}
