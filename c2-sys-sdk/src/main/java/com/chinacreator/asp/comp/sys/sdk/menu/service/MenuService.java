package com.chinacreator.asp.comp.sys.sdk.menu.service;

import com.chinacreator.asp.comp.sys.sdk.menu.dto.MenuDTO;

import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

import java.util.List;


/**
 * 菜单服务接口
 * @author 彭盛
 */
public interface MenuService {
    /**
    * 新增菜单
    * @param menuDTO菜单数据传输对象
    */
    public void create(MenuDTO menuDTO);

    /**
    * 修改菜单
    * @param menuDTO菜单数据传输对象
    */
    public void update(MenuDTO menuDTO);

    /**
    * 删除菜单
    * @param menuIds菜单ID数组
    */
    public void deleteByPKs(String... menuIds);

    /**
    * 删除菜单
    * @param menuCodes菜单编码数组
    */
    public void deleteByMenuCodes(String... menuCodes);

    /**
    * 查询所有菜单
    * @return 菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<MenuDTO> queryAll();

    /**
    * 带权限查询所有菜单
    * @return 菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<MenuDTO> queryAllByPermission();

    /**
    * 分页查询所有菜单
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<MenuDTO> queryAll(Pageable pageable, Sortable sortable);

    /**
    * 带权限分页查询所有菜单
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<MenuDTO> queryAllByPermission(Pageable pageable,
        Sortable sortable);

    /**
    * 查询菜单
    * @param menuDTO菜单数据传输对象
    * @return 菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<MenuDTO> queryByMenu(MenuDTO menuDTO);

    /**
    * 带权限查询菜单
    * @param menuDTO菜单数据传输对象
    * @return 菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<MenuDTO> queryByMenuAndPermission(MenuDTO menuDTO);

    /**
    * 分页查询菜单
    * @param menuDTO菜单数据传输对象
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<MenuDTO> queryByMenu(MenuDTO menuDTO, Pageable pageable,
        Sortable sortable);

    /**
    * 带权限分页查询菜单
    * @param menuDTO菜单数据传输对象
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<MenuDTO> queryByMenuAndPermission(MenuDTO menuDTO,
        Pageable pageable, Sortable sortable);

    /**
    * 查询菜单
    * @param menuId菜单ID
    * @return 菜单数据传输对象<br>
    * 没查询到的情况下返回null
    */
    public MenuDTO queryByPK(String menuId);

    /**
    * 查询菜单
    * @param menuCode菜单编码
    * @return 菜单数据传输对象<br>
    * 没查询到的情况下返回null
    */
    public MenuDTO queryByMenuCode(String menuCode);

    /**
    * 查询当前菜单及其父菜单
    * @param menuId菜单ID
    * @param recursively是否递归取得所有父菜单（true:所有父菜单，false:仅上级父菜单）
    * @return 当前菜单及其父菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<MenuDTO> queryParents(String menuId, boolean recursively);

    /**
    * 分页查询当前菜单及其父菜单
    * @param menuId菜单ID
    * @param recursively是否递归取得所有父菜单（true:所有父菜单，false:仅上级父菜单）
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的当前菜单及其父菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public Page<MenuDTO> queryParents(String menuId, boolean recursively,
        Pageable pageable, Sortable sortable);

    /**
    * 查询当前菜单的子菜单
    * @param menuId菜单ID
    * @param recursively是否递归取得所有子菜单（true:所有子菜单，false:仅上级子菜单）
    * @return 当前菜单的子菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<MenuDTO> queryChildren(String menuId, boolean recursively);

    /**
    * 带权限查询当前菜单的子菜单
    * @param menuId菜单ID
    * @param recursively是否递归取得所有子菜单（true:所有子菜单，false:仅上级子菜单）
    * @return 当前菜单的子菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<MenuDTO> queryChildrenByPermission(String menuId,
        boolean recursively);

    /**
    * 分页查询当前菜单的子菜单
    * @param menuId菜单ID
    * @param isRecursion是否递归取得所有子菜单（true:所有子菜单，false:仅上级子菜单）
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的当前菜单的子菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public Page<MenuDTO> queryChildren(String menuId, boolean isRecursion,
        Pageable pageable, Sortable sortable);

    /**
    * 带权限分页查询当前菜单的子菜单
    * @param menuId菜单ID
    * @param isRecursion是否递归取得所有子菜单（true:所有子菜单，false:仅上级子菜单）
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的当前菜单及其子菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public Page<MenuDTO> queryChildrenByPermission(String menuId,
        boolean isRecursion, Pageable pageable, Sortable sortable);

    /**
    * 判断菜单是否显示
    * @param menuId菜单ID
    * @return true:显示，false:不显示
    */
    public boolean isEnabledByMenuId(String menuId);

    /**
    * 判断菜单是否显示
    * @param menuCode菜单编码
    * @return true:显示，false:不显示
    */
    public boolean isEnabledByMenuCode(String menuCode);

    /**
    * 判断菜单编码是否存在
    * @param menuCode菜单编码
    * @return true:存在，false:不存在
    */
    public boolean existsByMenuCode(String menuCode);

    /**
    * 判断菜单名称是否存在（同级下唯一）
    * @param menuName菜单名称
    * @param parentId父模块ID
    * @return true:存在，false:不存在
    */
    public boolean existsByMenuName(String menuName, String parentId);

    /**
    * 判断菜单名称是否存在(忽略指定菜单ID名称，编辑时用)
    * @param menuName菜单名称
    * @param menuId菜单ID
    * @return true:存在，false:不存在
    */
    public boolean existsByMenuNameIgnoreMenuID(String menuName, String menuId);

    /**
    * 设置菜单的排列顺序
    * @param menuDTOList菜单数据传输对象列表<br>
    * 菜单数据传输对象必须包含菜单ID和菜单排序号
    */
    public void setOrder(List<MenuDTO> menuDTOList);

    /**
    * 判断指定菜单是否用户的可管理菜单
    * @param userId用户ID
    * @param menuId菜单ID
    * @return true:是，false:否
    */
    public boolean isMgtPermitted(String userId, String menuId);

    /**
    * 判断指定菜单是否有子菜单
    * @param menuId菜单ID
    * @return true:有，false:无
    */
    public boolean existsChildMenus(String menuId);
}
