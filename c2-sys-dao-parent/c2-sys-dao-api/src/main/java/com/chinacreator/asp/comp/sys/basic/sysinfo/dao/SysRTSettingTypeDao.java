package com.chinacreator.asp.comp.sys.basic.sysinfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.basic.sysinfo.eo.SystemTypeEO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 字典类型数据访问接口
 * 
 * @author 杨祎程
 * 
 */

@Repository
public interface SysRTSettingTypeDao {

	/**
	 * 新增系统信息类型
	 * 
	 * @param systemTypeEO
	 *            系统信息类型数据库访问对象
	 * @return 影响的行数
	 */
	public int create(SystemTypeEO systemTypeEO);

	/**
	 * 修改系统信息类型
	 * 
	 * @param systemTypeEO
	 *            系统信息类型数据库访问对象
	 * @return 影响的行数
	 */
	public int update(SystemTypeEO systemTypeEO);

	/**
	 * 批量删除系统信息类型
	 * 
	 * @param ids
	 *            系统信息类型ID数组
	 * @return 影响的行数
	 */
	public int deleteByPKs(String... ids);

	/**
	 * 查询所有系统信息类型(分页)
	 * 
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的所有系统类型
	 */
	public Page<SystemTypeEO> queryAll(Pageable pageable, Sortable sortable);

	/**
	 * 查询所有系统信息类型
	 * 
	 * @return 系统类型列表
	 */
	public List<SystemTypeEO> queryAll();

	/**
	 * 查询系统信息类型(分页)
	 * 
	 * @param systemTypeEO
	 *            系统信息类型数据库访问对象
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的系统类型
	 */
	public Page<SystemTypeEO> queryBySystemType(
			@Param("systemTypeEO") SystemTypeEO systemTypeEO,
			Pageable pageable, Sortable sortable);

	/**
	 * 查询系统信息类型
	 * 
	 * @param systemTypeEO
	 *            系统信息类型数据库访问对象
	 * @return 系统类型列表
	 */
	public List<SystemTypeEO> queryBySystemType(
			@Param("systemTypeEO") SystemTypeEO systemTypeEO);

	/**
	 * 通过系统信息类型主键查询系统信息数据库访问对象
	 * 
	 * @param id
	 *            系统信息类型主键
	 * @return 系统信息数据库访问对象
	 */
	public SystemTypeEO queryByPK(@Param("id") String id);

	/**
	 * 查询系统类型ID的存在性
	 * 
	 * @param id
	 *            系统类型ID
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsByTypeId(@Param("id") String id);

	/**
	 * 查询系统类型名称的存在性
	 * 
	 * @param typeName
	 *            系统类型名称
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsByTypeName(@Param("typeName") String typeName);

	/**
	 * 查询系统类型名称的存在性(忽略指定系统类型ID名称，编辑时用)
	 * 
	 * @param typeName
	 *            系统类型名称
	 * @param id
	 *            系统类型ID
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsByTypeNameIgnoreID(@Param("typeName") String typeName,
			@Param("id") String id);

}
