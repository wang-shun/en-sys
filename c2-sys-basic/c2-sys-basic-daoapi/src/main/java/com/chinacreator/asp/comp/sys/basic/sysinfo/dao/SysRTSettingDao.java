package com.chinacreator.asp.comp.sys.basic.sysinfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.basic.sysinfo.eo.SystemInfoEO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 系统运行时设置数据访问接口
 * 
 * @author 杨祎程
 * 
 */

@Repository
public interface SysRTSettingDao {

	/**
	 * 新增系统信息数据
	 * 
	 * @param systemInfoEO
	 *            系统信息数据库访问对象
	 * @return 影响的行数
	 */
	public int create(SystemInfoEO systemInfoEO);

	/**
	 * 修改系统信息数据
	 * 
	 * @param systemInfoEO
	 *            系统信息数据库访问对象
	 * @return 影响的行数
	 */
	public int update(SystemInfoEO systemInfoEO);

	/**
	 * 通过系统信息主键数组批量删除系统信息
	 * 
	 * @param ids
	 *            系统信息主键数组
	 * @return 影响的行数
	 */
	public int deleteByPKs(String... ids);

	/**
	 * 根据系统类别数组删除系统信息
	 * 
	 * @param systemTypeIds
	 *            系统类别数组
	 * @return 影响的行数
	 */
	public int deleteBySystemTypeIds(String... systemTypeIds);

	/**
	 * 查询所有系统信息（分页）
	 * 
	 * @param pageable
	 *            分页参数
	 * @param sortable
	 *            排序参数
	 * @return 分页后的系统信息
	 */
	public Page<SystemInfoEO> queryAll(Pageable pageable, Sortable sortable);

	/**
	 * 查询所有系统信息
	 * 
	 * @return 系统信息列表
	 */
	public List<SystemInfoEO> queryAll();

	/**
	 * 查询系统信息（分页）
	 * 
	 * @param systemInfoEO
	 *            系统信息数据访问对象
	 * @param pageable
	 *            分页参数
	 * @param sortable
	 *            排序参数
	 * @return 分页后的系统信息
	 */
	public Page<SystemInfoEO> queryBySystemInfo(
			@Param("systemInfoEO") SystemInfoEO systemInfoEO,
			Pageable pageable, Sortable sortable);

	/**
	 * 查询系统信息
	 * 
	 * @param systemInfoEO
	 *            系统信息数据访问对象
	 * @return 系统信息列表
	 */
	public List<SystemInfoEO> queryBySystemInfo(
			@Param("systemInfoEO") SystemInfoEO systemInfoEO);

	/**
	 * 通过系统信息主键查询系统信息数据库访问对象
	 * 
	 * @param id
	 *            系统信息主键
	 * @return 系统信息数据库访问对象
	 */
	public SystemInfoEO queryByPK(@Param("id") String id);

	/**
	 * 查询系统类型下已存在的系统信息名称条数
	 * 
	 * @param infoType
	 *            系统类型ID
	 * @param infoName
	 *            系统信息名称
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsByInfoName(@Param("infoType") String infoType,
			@Param("infoName") String infoName);

	/**
	 * 查询系统类型下已存在的系统信息名称条数(忽略指定系统参数ID名称，编辑时用)
	 * 
	 * @param infoType
	 *            系统类型ID
	 * @param infoName
	 *            系统信息名称
	 * @param id
	 *            系统信息ID
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsByInfoNameIgnoreID(@Param("infoType") String infoType,
			@Param("infoName") String infoName, @Param("id") String id);

}
