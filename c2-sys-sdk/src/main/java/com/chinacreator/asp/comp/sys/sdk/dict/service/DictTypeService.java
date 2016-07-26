package com.chinacreator.asp.comp.sys.sdk.dict.service;

import com.chinacreator.asp.comp.sys.sdk.dict.dto.DictTypeDTO;

import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

import java.util.List;


/**
 * 字典类型服务接口
 * @author 彭盛
 */
public interface DictTypeService {
    /**
    * 新增字典类型
    * @param dictTypeDTO字典类型数据传输对象
    */
    public void create(DictTypeDTO dictTypeDTO);

    /**
    * 修改字典类型
    * @param dictTypeDTO字典类型数据传输对象
    */
    public void update(DictTypeDTO dictTypeDTO);

    /**
    * 删除字典类型
    * @param dictTypeIds字典类型ID数组
    */
    public void deleteByPKs(String... dictTypeIds);

    /**
    * 查询所有字典类型
    * @return 字典类型数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<DictTypeDTO> queryAll();

    /**
    * 分页查询所有字典类型
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的字典类型数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<DictTypeDTO> queryAll(Pageable pageable, Sortable sortable);

    /**
    * 查询字典类型
    * @param dictTypeDTO字典类型数据传输对象
    * @return 字典类型数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<DictTypeDTO> queryByDictType(DictTypeDTO dictTypeDTO);

    /**
    * 分页查询字典类型
    * @param dictTypeDTO字典类型数据传输对象
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的字典类型数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<DictTypeDTO> queryByDictType(DictTypeDTO dictTypeDTO,
        Pageable pageable, Sortable sortable);

    /**
    * 查询字典类型
    * @param dictTypeId字典类型ID
    * @return 字典类型数据传输对象<br>
    * 没查询到的情况下返回null
    */
    public DictTypeDTO queryByPK(String dictTypeId);

    /**
    * 判断字典类型名称是否存在
    * @param dictTypeName字典类型名称
    * @return true:存在，false:不存在
    */
    public boolean existsByDictTypeName(String dictTypeName);

    /**
    * 判断字典类型名称是否存在(忽略指定字典类型ID名称，编辑时用)
    * @param dictTypeName字典类型名称
    * @param dictTypeId字典类型ID
    * @return true:存在，false:不存在
    */
    public boolean existsByDictTypeNameIgnoreDictTypeID(String dictTypeName,
        String dictTypeId);
}
