package com.chinacreator.asp.comp.sys.sdk.sysinfo.service;

import com.chinacreator.asp.comp.sys.sdk.sysinfo.dto.SystemInfoDTO;

import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

import java.util.List;


/**
 * 系统运行时设置服务接口
 * @author 彭盛
 */
public interface SysRTSettingService {
    /**
    * 新增系统信息数据
    * @param systemInfoDTO系统信息数据传输对象
    */
    public void create(SystemInfoDTO systemInfoDTO);

    /**
    * 修改系统信息数据
    * @param systemInfoDTO系统信息数据传输对象
    */
    public void update(SystemInfoDTO systemInfoDTO);

    /**
    * 删除系统信息数据
    * @param ids系统信息数据ID数组
    */
    public void deleteByPKs(String... ids);

    /**
    * 删除系统信息数据
    * @param systemTypeIds系统信息数据类型ID数组
    */
    public void deleteBySystemTypeIds(String... systemTypeIds);

    /**
    * 查询所有系统信息数据
    * @return 系统信息数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<SystemInfoDTO> queryAll();

    /**
    * 分页查询所有系统信息数据
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的系统信息数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<SystemInfoDTO> queryAll(Pageable pageable, Sortable sortable);

    /**
    * 查询系统信息数据
    * @param systemInfoDTO系统信息数据传输对象
    * @return 系统信息数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<SystemInfoDTO> queryBySystemInfo(SystemInfoDTO systemInfoDTO);

    /**
    * 分页查询系统信息数据
    * @param systemInfoDTO系统信息数据传输对象
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的系统信息数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public Page<SystemInfoDTO> queryBySystemInfo(SystemInfoDTO systemInfoDTO,
        Pageable pageable, Sortable sortable);

    /**
    * 查询系统信息数据
    * @param id系统信息数据ID
    * @return 系统信息数据传输对象<br>
    * 没查询到的情况下返回null
    */
    public SystemInfoDTO queryByPK(String id);

    /**
    * 判断系统信息数据名称是否存在
    * @param systemTypeId系统信息类型ID
    * @param systemInfoName系统信息数据名称
    * @return true:存在，false:不存在
    */
    public boolean existsBySystemInfoName(String systemTypeId,
        String systemInfoName);

    /**
    * 判断系统信息数据名称是否存在(忽略指定系统参数ID名称，编辑时用)
    * @param systemTypeId系统类型ID
    * @param systemInfoName系统信息名称
    * @param systemInfoId系统信息ID
    * @return true:存在，false:不存在
    */
    public boolean existsByInfoNameIgnoreID(String systemTypeId,
        String systemInfoName, String systemInfoId);

    /**
    * 判断系统信息数据是否可删除
    * @param id系统信息数据ID
    * @return true:不可删除，false:可删除
    */
    public boolean isDeletable(String id);
}
