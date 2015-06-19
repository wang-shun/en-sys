package com.chinacreator.asp.comp.sys.basic.dict.service;

import java.util.List;

import com.chinacreator.asp.comp.sys.basic.dict.dto.DictDataDTO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 字典数据服务接口
 * 
 * @author 彭盛
 * 
 */
public interface DictDataService {

	/**
	 * 新增字典数据
	 * 
	 * @param dictDataDTO
	 *            字典数据传输对象
	 */
	public void create(DictDataDTO dictDataDTO);

	/**
	 * 修改字典数据
	 * 
	 * @param dictDataDTO
	 *            字典数据传输对象
	 */
	public void update(DictDataDTO dictDataDTO);

	/**
	 * 删除字典数据
	 * 
	 * @param dictDataIds
	 *            字典数据ID数组
	 */
	public void deleteByPKs(String... dictDataIds);

	/**
	 * 删除字典数据
	 * 
	 * @param dictTypeIds
	 *            字典类型ID数组
	 */
	public void deleteByDictTypeIds(String... dictTypeIds);

	/**
	 * 查询字典数据
	 * 
	 * @param dictDataDTO
	 *            字典数据传输对象
	 * @return 字典数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<DictDataDTO> queryByDictData(DictDataDTO dictDataDTO);

	/**
	 * 分页查询字典数据
	 * 
	 * @param dictDataDTO
	 *            字典数据传输对象
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的字典数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<DictDataDTO> queryByDictData(DictDataDTO dictDataDTO,
			Pageable pageable, Sortable sortable);

	/**
	 * 查询字典数据
	 * 
	 * @param dictTypeName
	 *            字典类型名称
	 * @return 字典数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<DictDataDTO> queryByDictTypeName(String dictTypeName);

	/**
	 * 查询字典数据
	 * 
	 * @param dictDataId
	 *            字典数据ID
	 * @return 字典数据传输对象<br>
	 *         没查询到的情况下返回null
	 */
	public DictDataDTO queryByPK(String dictDataId);

	/**
	 * 判断字典数据名称是否存在
	 * 
	 * @param dictTypeId
	 *            字典类型ID
	 * @param dictDataName
	 *            字典数据名称
	 * @return true:存在，false:不存在
	 */
	public boolean existsByDictDataName(String dictTypeId, String dictDataName);

	/**
	 * 判断字典数据名称是否存在(忽略指定字典数据ID名称，编辑时用)
	 * 
	 * @param dictDataName
	 *            字典名称
	 * @param dictTypeId
	 *            字典类型ID
	 * @param dictDataId
	 *            字典数据ID
	 * @return true:存在，false:不存在
	 */
	public boolean existsByDictDataNameIgnoreDictDataID(String dictDataName,
			String dictTypeId, String dictDataId);
}
