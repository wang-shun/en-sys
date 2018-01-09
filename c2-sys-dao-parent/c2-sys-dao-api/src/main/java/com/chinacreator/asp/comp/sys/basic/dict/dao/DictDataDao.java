package com.chinacreator.asp.comp.sys.basic.dict.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.basic.dict.eo.DictDataEO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 字典数据访问接口
 * 
 * @author 杨祎程
 * 
 */

@Repository
public interface DictDataDao {

	/**
	 * 新增字典数据
	 * 
	 * @param dictDataEO
	 *            字典数据访问对象
	 * @return 影响的行数
	 */
	public int create(DictDataEO dictDataEO);

	/**
	 * 修改字典数据
	 * 
	 * @param dictDataEO
	 *            字典数据访问对象
	 * @return 影响的行数
	 */
	public int update(DictDataEO dictDataEO);

	/**
	 * 删除字典数据
	 * 
	 * @param dictDataIds
	 *            字典数据ID数组
	 * @return 影响的行数
	 */
	public int deleteByPKs(String... dictDataIds);

	/**
	 * 通过字典类型删除字典数据
	 * 
	 * @param dictTypeIds
	 *            字典类型ID数组
	 * @return 影响的行数
	 */
	public int deleteByDictTypeIds(String... dictTypeIds);

	/**
	 * 查询字典数据（分页）
	 * 
	 * @param dictDataEO
	 *            字典数据库传输对象
	 * @param pageable
	 *            分页参数
	 * @param sortable
	 *            排序参数
	 * @return 分页后的字段数据
	 */
	public Page<DictDataEO> queryByDictData(
			@Param("dictDataEO") DictDataEO dictDataEO, Pageable pageable,
			Sortable sortable);

	/**
	 * 查询字典数据
	 * 
	 * @param dictDataEO
	 *            字典数据库传输对象
	 * @return 字典数据列表
	 */
	public List<DictDataEO> queryByDictData(
			@Param("dictDataEO") DictDataEO dictDataEO);

	/**
	 * 查询字典数据
	 * 
	 * @param dictDataEO
	 *            字典数据库传输对象
	 * @return 字典数据列表
	 */
	public List<DictDataEO> queryByDictTypeName(
			@Param("dictTypeName") String dictTypeName);

	/**
	 * 通过字典ID查询字典项
	 * 
	 * @param dictDataId
	 *            字典数据ID
	 * @return 字段数据库访问对象
	 */
	public DictDataEO queryByPK(@Param("dictDataId") String dictDataId);

	/**
	 * 查询字典类型ID下已存在的字典名称条数
	 * 
	 * @param dictDataName
	 *            字典名称
	 * @param dictTypeId
	 *            字典类型ID
	 * @return 字典类型ID下已存在的字典名称条数
	 */
	public int existsByDictDataName(@Param("dictDataName") String dictDataName,
			@Param("dictTypeId") String dictTypeId);

	/**
	 * 查询字典类型ID下已存在的字典名称条数(忽略指定字典数据ID名称，编辑时用)
	 * 
	 * @param dictDataName
	 *            字典名称
	 * @param dictTypeId
	 *            字典类型ID
	 * @param dictDataId
	 *            字典数据ID
	 * @return 字典类型ID下已存在的字典名称条数
	 */
	public int existsByDictDataNameIgnoreDictDataID(
			@Param("dictDataName") String dictDataName,
			@Param("dictTypeId") String dictTypeId,
			@Param("dictDataId") String dictDataId);

}
