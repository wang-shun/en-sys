package com.chinacreator.asp.comp.sys.basic.sysinfo.service;

import java.util.List;

import com.chinacreator.asp.comp.sys.basic.sysinfo.dto.SystemTypeDTO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 系统运行时设置类型服务接口
 * 
 * @author 彭盛
 * 
 */
public interface SysRTSettingTypeService {

	/**
	 * 新增系统信息类型
	 * 
	 * @param systemTypeDTO
	 *            系统信息类型传输对象
	 */
	public void create(SystemTypeDTO systemTypeDTO);

	/**
	 * 修改系统信息类型
	 * 
	 * @param systemTypeDTO
	 *            系统信息类型传输对象
	 */
	public void update(SystemTypeDTO systemTypeDTO);

	/**
	 * 删除系统信息类型
	 * 
	 * @param ids
	 *            系统信息类型ID数组
	 */
	public void deleteByPKs(String... ids);

	/**
	 * 查询所有系统信息类型
	 * 
	 * @return 系统信息类型数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<SystemTypeDTO> queryAll();

	/**
	 * 分页查询所有系统信息类型
	 * 
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的系统信息类型数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<SystemTypeDTO> queryAll(Pageable pageable, Sortable sortable);

	/**
	 * 查询系统信息类型
	 * 
	 * @param systemTypeDTO
	 *            系统信息类型数据传输对象
	 * @return 系统信息类型数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<SystemTypeDTO> queryBySystemType(SystemTypeDTO systemTypeDTO);

	/**
	 * 分页查询系统信息类型
	 * 
	 * @param systemTypeDTO
	 *            系统信息类型数据传输对象
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的系统信息类型数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<SystemTypeDTO> queryBySystemType(SystemTypeDTO systemTypeDTO,
			Pageable pageable, Sortable sortable);

	/**
	 * 查询系统信息类型
	 * 
	 * @param id
	 *            系统信息类型ID
	 * @return 系统信息类型数据传输对象<br>
	 *         没查询到的情况下返回null
	 */
	public SystemTypeDTO queryByPK(String id);

	/**
	 * 判断系统信息类型名称是否存在
	 * 
	 * @param typeName
	 *            系统信息类型名称
	 * @return true:存在，false:不存在
	 */
	public boolean existsByTypeName(String typeName);

	/**
	 * 判断系统信息类型名称是否存在(忽略指定系统类型ID名称，编辑时用)
	 * 
	 * @param typeName
	 *            系统类型名称
	 * @param id
	 *            系统类型ID
	 * @return true:存在，false:不存在
	 */
	public boolean existsByTypeNameIgnoreID(String typeName, String id);
}
