package com.chinacreator.asp.comp.sys.basic.dict.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.basic.dict.eo.DictTypeEO;
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
public interface DictTypeDao {

	/**
	 * 新增字典类型
	 * 
	 * @param dictTypeEO
	 *            字典类型数据库访问对象
	 * @return 影响的行数
	 */
	public int create(DictTypeEO dictTypeEO);

	/**
	 * 更新字典类型
	 * 
	 * @param dictTypeEO
	 *            字典类型数据库访问对象
	 * @return 影响的行数
	 */
	public int update(DictTypeEO dictTypeEO);

	/**
	 * 批量删除字典类型
	 * 
	 * @param dictTypeIds
	 *            字典类型数组
	 * @return 影响的行数
	 */
	public int deleteByPKs(String... dictTypeIds);

	/**
	 * 查询所有字典类型（分页）
	 * 
	 * @param pageable
	 *            分页参数
	 * @param sortable
	 *            排序参数
	 * @return 字典类型分页对象
	 */
	public Page<DictTypeEO> queryAll(Pageable pageable, Sortable sortable);

	/**
	 * 查询所有字典类型
	 * 
	 * @return 字典类型列表
	 */
	public List<DictTypeEO> queryAll();

	/**
	 * 根据字典类型参数查询字典类型（分页）
	 * 
	 * @param dictTypeEO
	 *            字典类型数据库访问对象
	 * @param pageable
	 *            分页参数
	 * @param sortable
	 *            排序参数
	 * @return 字典类型分页对象
	 */
	public Page<DictTypeEO> queryByDictType(
			@Param("dictTypeEO") DictTypeEO dictTypeEO, Pageable pageable,
			Sortable sortable);

	/**
	 * 根据字典类型参数查询字典类型
	 * 
	 * @param dictTypeEO
	 *            字典类型数据库访问对象
	 * @return 字典类型列表
	 */
	public List<DictTypeEO> queryByDictType(
			@Param("dictTypeEO") DictTypeEO dictTypeEO);

	/**
	 * 根据字典类型ID查询字典类型数据库访问对象
	 * 
	 * @param dictTypeId
	 *            字典类型ID
	 * @return 字典类型数据库访问对象
	 */
	public DictTypeEO queryByPK(@Param("dictTypeId") String dictTypeId);

	/**
	 * 判断字典类型是否存在
	 * 
	 * @param dictTypeId
	 *            字典类型ID
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsByDictTypeId(@Param("dictTypeId") String dictTypeId);

	/**
	 * 判断字典类型名称是否存在
	 * 
	 * @param dictTypeName
	 *            字典类型名称
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsByDictTypeName(@Param("dictTypeName") String dictTypeName);

	/**
	 * 判断字典类型名称是否存在(忽略指定字典类型ID名称，编辑时用)
	 * 
	 * @param dictTypeName
	 *            字典类型名称
	 * @param dictTypeId
	 *            字典类型ID
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsByDictTypeNameIgnoreDictTypeID(
			@Param("dictTypeName") String dictTypeName,
			@Param("dictTypeId") String dictTypeId);

}
