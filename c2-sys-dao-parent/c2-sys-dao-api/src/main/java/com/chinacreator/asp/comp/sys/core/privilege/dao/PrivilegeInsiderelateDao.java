package com.chinacreator.asp.comp.sys.core.privilege.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeInsiderelateEO;

/**
 * 权限内部关联数据访问接口
 * 
 * @author 彭盛
 * 
 */
@Repository
public interface PrivilegeInsiderelateDao {

	/**
	 * 新增权限关联
	 * 
	 * @param privilegeInsiderelateEO
	 *            权限关联数据访问对象
	 * @return 数据库执行操作影响到的行数
	 */
	public int create(PrivilegeInsiderelateEO privilegeInsiderelateEO);

	/**
	 * 修改权限
	 * 
	 * @param privilegeInsiderelateEO
	 *            权限关联数据访问对象
	 * @return 数据库执行操作影响到的行数
	 */
	public int update(PrivilegeInsiderelateEO privilegeInsiderelateEO);

	/**
	 * 批量删除权限关联
	 * 
	 * @param ids
	 *            权限ID数组
	 */
	public int deleteByPKs(String... ids);

	/**
	 * 查询所有权限关联
	 * 
	 * @return 权限关联数据访问对象列表
	 */
	public List<PrivilegeInsiderelateEO> queryAll();

	/**
	 * 查询权限关联
	 * 
	 * @param id
	 *            权限ID
	 * @return 权限关联数据访问对象列表
	 */
	public List<PrivilegeInsiderelateEO> queryByPK(String id);

	/**
	 * 查询权限关联
	 * 
	 * @param relateId
	 *            关联ID(父ID)
	 * @return 权限关联数据访问对象列表
	 */
	public List<PrivilegeInsiderelateEO> queryByRelate(String relateId);

	/**
	 * 判断权限关联是否存在
	 * 
	 * @param id
	 *            权限ID
	 * @param relateId
	 *            关联ID(父ID)
	 * @return 大于0：存在，否则不存在
	 */
	public int exists(@Param("id") String id, @Param("relateId") String relateId);
}
