package com.chinacreator.asp.comp.sys.sdk.role.service;

import com.chinacreator.asp.comp.sys.sdk.role.dto.RoleTypeDTO;

import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

import java.util.List;


/**
 * 角色类型服务接口
 * @author 彭盛
 */
public interface RoleTypeService {
    /**
    * 新增角色类型
    * @param roleTypeDTO角色类型数据传输对象
    */
    public void create(RoleTypeDTO roleTypeDTO);

    /**
    * 修改角色类型
    * @param roleTypeDTO角色类型数据传输对象
    */
    public void update(RoleTypeDTO roleTypeDTO);

    /**
    * 删除角色类型
    * @param roleTypeIds角色类型ID数组
    */
    public void deleteByPKs(String... roleTypeIds);

    /**
    * 查询所有角色类型
    * @return 角色类型数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<RoleTypeDTO> queryAll();

    /**
    * 分页查询所有角色类型
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的角色类型数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<RoleTypeDTO> queryAll(Pageable pageable, Sortable sortable);

    /**
    * 查询角色类型
    * @param roleTypeDTO
    * @return 角色类型数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<RoleTypeDTO> queryByRoleType(RoleTypeDTO roleTypeDTO);

    /**
    * 分页查询角色类型
    * @param pageable分页对象
    * @param sortable排序对象
    * @param roleTypeDTO
    * @return 分页后的角色类型数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<RoleTypeDTO> queryByRoleType(RoleTypeDTO roleTypeDTO,
        Pageable pageable, Sortable sortable);

    /**
    * 查询角色类型
    * @param roleTypeId角色类型ID
    * @return 角色类型数据传输对象<br>
    * 没查询到的情况下返回null
    */
    public RoleTypeDTO queryByPK(String roleTypeId);

    /**
    * 判断角色类型名称是否存在
    * @param roleTypeName角色类型名称
    * @return true:存在，false:不存在
    */
    public boolean existsByRoleTypeName(String roleTypeName);

    /**
    * 判断角色类型名称是否存在(忽略指定角色类型ID名称，编辑时用)
    * @param roleTypeName角色类型名称
    * @param roleTypeId角色类型ID
    * @return true:存在，false:不存在
    */
    public boolean existsByRoleTypeNameIgnoreRoleTypeId(String roleTypeName,
        String roleTypeId);

    /**
    * 获取匿名角色类型ID
    * @return 匿名角色类型ID
    */
    public String getAnonymousRoleTypeId();
}
