package com.chinacreator.asp.comp.sys.basic.menu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.basic.menu.eo.MenuAllInfoEO;
import com.chinacreator.asp.comp.sys.basic.menu.eo.MenuEO;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 菜单数据访问接口
 * 
 * @author 杨祎程
 * 
 */
@Repository
public interface MenuDao {

	/**
	 * 新增菜单（往TB_SM_MENU表里插数据）
	 * 
	 * @param menuEO
	 *            菜单数据访问对象
	 * @return 影响的行数
	 */
	public int create(MenuEO menuEO);

	/**
	 * 修改菜单（修改TB_SM_MENU表的数据）
	 * 
	 * @param menuEo
	 *            菜单数据访问对象
	 * @return 影响的行数
	 */
	public int update(MenuEO menuEo);

	/**
	 * 根据菜单ID批量删除菜单表的数据
	 * 
	 * @param menuIds
	 *            菜单ID数组
	 * @return 影响的行数
	 */
	public int deleteByPKs(String... menuIds);

	/**
	 * 根据菜单编码批量删除菜单表的数据
	 * 
	 * @param menuCodes
	 *            菜单编码数组
	 * @return 影响的行数
	 */
	public int deleteByMenuCodes(String... menuCodes);

	/**
	 * 查询所有菜单信息（不分页）
	 * 
	 * @return 菜单信息列表
	 */
	public List<MenuAllInfoEO> queryAll();

	/**
	 * 查询所有菜单信息（分页）
	 * 
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的菜单信息
	 */
	public Page<MenuAllInfoEO> queryAll(Pageable pageable, Sortable sortable);

	/**
	 * 通过参数查询菜单（不分页）
	 * 
	 * @param menuAllInfoEO
	 *            菜单信息数据库访问对象
	 * @return 菜单信息列表
	 */
	public List<MenuAllInfoEO> queryByMenu(
			@Param("menuAllInfoEO") MenuAllInfoEO menuAllInfoEO);

	/**
	 * 通过参数（权限筛选后的菜单）查询菜单（不分页）
	 * 
	 * @param menuAllInfoEO
	 *            菜单信息数据库访问对象
	 * @return 菜单信息列表
	 */
	public List<MenuAllInfoEO> queryByMenuPermission(
			@Param("menuAllInfoEO") MenuAllInfoEO menuAllInfoEO,
			@Param("menuIdList") List<String> menuIdList);

	/**
	 * 通过参数（权限筛选后的菜单）查询菜单（分页）
	 * 
	 * @param menuAllInfoEO
	 *            菜单信息数据库访问对象
	 * @return 分页后的菜单信息
	 */
	public Page<MenuAllInfoEO> queryByMenuPermission(
			@Param("menuAllInfoEO") MenuAllInfoEO menuAllInfoEO,
			@Param("menuIdList") List<String> menuIdList, Pageable pageable,
			Sortable sortable);

	/**
	 * 通过参数查询菜单（分页）
	 * 
	 * @param menuAllInfoEO
	 *            菜单信息数据库访问对象
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的菜单信息
	 */
	public Page<MenuAllInfoEO> queryByMenu(
			@Param("menuAllInfoEO") MenuAllInfoEO menuAllInfoEO,
			Pageable pageable, Sortable sortable);

	/**
	 * 通过菜单ID查询菜单信息
	 * 
	 * @param menuId
	 *            菜单ID
	 * @return 菜单所有信息数据库访问对象
	 */
	public MenuAllInfoEO queryByPK(@Param("menuId") String menuId);

	/**
	 * 通过菜单ID集合批量查询菜单信息
	 * 
	 * @param menuIds
	 *            菜单ID集合
	 * @return 菜单信息列表
	 */
	public List<MenuAllInfoEO> queryByPKs(@Param("menuIds") List<String> menuIds);

	/**
	 * 通过菜单ID集合批量查询菜单信息（带分页）
	 * 
	 * @param menuIds
	 *            菜单ID集合
	 * @return 分页后的菜单信息
	 */
	public Page<MenuAllInfoEO> queryByPKs(
			@Param("menuIds") List<String> menuIds, Pageable pageable,
			Sortable sortable);

	/**
	 * 通过菜单编码查询菜单信息
	 * 
	 * @param menuCode
	 *            菜单编码
	 * @return 菜单所有信息数据库访问对象
	 */
	public MenuAllInfoEO queryByMenuCode(@Param("menuCode") String menuCode);

	/**
	 * 查询当前菜单及其所有父菜单（递归）
	 * 
	 * @param menuId
	 *            菜单ID
	 * @return 菜单列表
	 */
	public List<MenuAllInfoEO> queryParents(@Param("menuId") String menuId);

	/**
	 * 查询当前菜单及其父菜单（不递归）
	 * 
	 * @param menuId
	 *            菜单ID
	 * @return 菜单列表
	 */
	public List<MenuAllInfoEO> queryParentsUnRecursive(
			@Param("menuId") String menuId);

	/**
	 * 查询当前菜单及其父菜单（分页）
	 * 
	 * @param menuId
	 *            菜单ID
	 * @return 分页后的菜单信息
	 */
	public Page<MenuAllInfoEO> queryParents(@Param("menuId") String menuId,
			Pageable pageable, Sortable sortable);

	/**
	 * 查询当前菜单的所有子菜单（递归）
	 * 
	 * @param menuId
	 *            菜单ID
	 * @return 菜单列表
	 */
	public List<MenuAllInfoEO> queryChildren(@Param("menuId") String menuId);

	/**
	 * 查询当前菜单的所有子菜单（带权限）（递归）
	 * 
	 * @param menuId
	 *            菜单ID
	 * @param menuIdList
	 *            菜单ID列表
	 * @return 菜单列表
	 */
	public List<MenuAllInfoEO> queryChildrenPermission(
			@Param("menuId") String menuId,
			@Param("menuIdList") List<String> menuIdList);

	/**
	 * 查询当前菜单的所有子菜单（带权限）（递归）
	 * 
	 * @param menuId
	 *            菜单ID
	 * @param menuIdList
	 *            菜单ID列表
	 * @return 菜单列表
	 */
	public Page<MenuAllInfoEO> queryChildrenPermission(
			@Param("menuId") String menuId,
			@Param("menuIdList") List<String> menuIdList, Pageable pageable,
			Sortable sortable);

	/**
	 * 查询当前菜单的子菜单（不递归）
	 * 
	 * @param menuId
	 *            菜单ID
	 * @return 菜单列表
	 */
	public List<MenuAllInfoEO> queryChildrenUnRecursive(
			@Param("menuId") String menuId);

	/**
	 * 查询当前菜单的子菜单（带权限）（不递归）
	 * 
	 * @param menuId
	 *            菜单ID
	 * @param menuIdList
	 *            菜单ID列表
	 * @return 菜单列表
	 */
	public List<MenuAllInfoEO> queryChildrenUnRecursivePermission(
			@Param("menuId") String menuId,
			@Param("menuIdList") List<String> menuIdList);

	/**
	 * 查询当前菜单的子菜单（带权限）（不递归）
	 * 
	 * @param menuId
	 *            菜单ID
	 * @param menuIdList
	 *            菜单ID列表
	 * @return 菜单列表
	 */
	public Page<MenuAllInfoEO> queryChildrenUnRecursivePermission(
			@Param("menuId") String menuId,
			@Param("menuIdList") List<String> menuIdList, Pageable pageable,
			Sortable sortable);

	/**
	 * 查询当前菜单的子菜单（带分页）
	 * 
	 * @param menuId
	 *            菜单ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的菜单信息
	 */
	public Page<MenuAllInfoEO> queryChildren(@Param("menuId") String menuId,
			Pageable pageable, Sortable sortable);

	/**
	 * 查询当前菜单的子菜单（不递归，带分页）
	 * 
	 * @param menuId
	 *            菜单ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的菜单信息
	 */
	public Page<MenuAllInfoEO> queryChildrenUnRecursive(
			@Param("menuId") String menuId, Pageable pageable, Sortable sortable);

	/*    *//**
	 * 查询当前菜单的子菜单（带分页带权限）
	 * 
	 * @param menuId
	 *            菜单ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的菜单信息
	 */
	/*
	 * public Page<MenuAllInfoEO>
	 * queryChildMenusByPermission(@Param("menuId")String menuId, Pageable
	 * pageable, Sortable sortable);
	 */

	/**
	 * 查询菜单是否显示
	 * 
	 * @param menuId
	 *            菜单ID
	 * @return 大于0表示显示，否则不显示
	 */
	public int isEnabledByMenuId(@Param("menuId") String menuId);

	/**
	 * 查询菜单是否显示
	 * 
	 * @param menuCode
	 *            菜单编码
	 * @return 大于0表示显示，否则不显示
	 */
	public int isEnabledByMenuCode(@Param("menuCode") String menuCode);

	/**
	 * 判断菜单编码是否存在
	 * 
	 * @param menuCode
	 *            菜单编码
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsByMenuCode(@Param("menuCode") String menuCode);

	/**
	 * 设置菜单的排列顺序
	 * 
	 * @param menuAllInfoEO
	 *            重新设置好排序的菜单数据库操作对象
	 * @return 影响的行数
	 */
	public int setOrder(MenuEO menuEO);

	/**
	 * 判断指定菜单是否是用户的可执行菜单
	 * 
	 * @param userId
	 *            用户ID
	 * @param menuId
	 *            菜单ID
	 * @return 大于0表示可执行，否则不可执行
	 */
	public int isMgtPermitted(@Param("userId") String userId,
			@Param("menuId") String menuId);

	/**
	 * 查询用户权限下所有的菜单信息
	 * 
	 * @param userId
	 *            用户ID
	 * @return 菜单权限列表
	 */
	public List<PrivilegeEO> queryPrivilegesOfMenuByUserId(
			@Param("userId") String userId);

	/**
	 * 查询用户的指定菜单ID对应的菜单信息
	 * 
	 * @param userId
	 *            用户ID
	 * @param menuId
	 *            菜单ID
	 * @return 菜单列表
	 */
	public List<PrivilegeEO> hasMenu(@Param("userId") String userId,
			@Param("menuId") String menuId);

	/**
	 * 查询用户是否是菜单创建者
	 * 
	 * @param userId
	 *            用户ID
	 * @param menuId
	 *            菜单ID
	 * @return 大于0表示是，否则表示否
	 */
	public int isMenuSCreator(@Param("userId") String userId,
			@Param("menuId") String menuId);

	/**
	 * 判断指定菜单是否有子菜单
	 * 
	 * @param menuId
	 *            菜单ID
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsChildMenus(@Param("menuId") String menuId);

	/**
	 * 判断菜单名称是否存在（同级下唯一）
	 * 
	 * @param menuName
	 *            菜单名称
	 * @param parentId
	 *            父模块ID
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsByMenuName(@Param("menuName") String menuName,
			@Param("parentId") String parentId);

	/**
	 * 判断菜单名称是否存在(忽略指定菜单ID名称，编辑时用)
	 * 
	 * @param menuName
	 *            菜单名称
	 * @param menuId
	 *            菜单ID
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsByMenuNameIgnoreMenuID(@Param("menuName") String menuName,
			@Param("menuId") String menuId);
}
