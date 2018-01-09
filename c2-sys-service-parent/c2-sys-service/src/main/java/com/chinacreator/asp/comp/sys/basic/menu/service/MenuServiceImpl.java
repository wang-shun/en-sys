package com.chinacreator.asp.comp.sys.basic.menu.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.basic.MenuMessages;
import com.chinacreator.asp.comp.sys.basic.menu.dao.MenuDao;
import com.chinacreator.asp.comp.sys.basic.menu.dto.MenuDTO;
import com.chinacreator.asp.comp.sys.basic.menu.eo.MenuAllInfoEO;
import com.chinacreator.asp.comp.sys.basic.menu.eo.MenuEO;
import com.chinacreator.asp.comp.sys.basic.privilege.service.PrivilegeService;
import com.chinacreator.asp.comp.sys.basic.user.service.UserService;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 菜单服务接口实现类
 * 
 * @author 杨祎程
 * 
 */
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.basic.privilege.service.PrivilegeServiceImpl")
	private PrivilegeService privilegeService;

	@Autowired
	private AccessControlService accessControlService;

	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.basic.user.service.UserServiceImpl")
	private UserService userService;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void create(MenuDTO menuDTO) {
		// 验证
		validateCreateMenu(menuDTO);
		menuDTO.setType("4");
		if (null == menuDTO.getPermExpr()
				|| menuDTO.getPermExpr().trim().equals("")) {
			menuDTO.setPermExpr(menuDTO.getMenuCode());
		}
		if (null == menuDTO.getSource()
				|| menuDTO.getSource().trim().equals("")) {
			menuDTO.setSource("0");
		}

		// 先进行权限表中的记录的插入
		PrivilegeDTO privilegeDTO = new PrivilegeDTO();
		copyMenuDTOToPrivilegeDTO(menuDTO, privilegeDTO);
		privilegeService.create(privilegeDTO);

		menuDTO.setMenuId(privilegeDTO.getPrivilegeId());
		menuDTO.setCreator(privilegeDTO.getCreator());

		// 进行菜单表的记录插入
		MenuEO menuEO = new MenuEO();
		BeanCopierUtil.copy(menuDTO, menuEO);
		menuDao.create(menuEO);
	}

	private void validateCreateMenu(MenuDTO menuDTO) {
		if (null != menuDTO) {
			if (isBlank(menuDTO.getMenuCode())) {
				throw new NullPointerException(
						MenuMessages
								.getString("MENU.MENUCODE_IS_NULL_EMPTY_BLANK"));
			}
			// 进行菜单编码的唯一性约束校验
			if (existsByMenuCode(menuDTO.getMenuCode())) {
				throw new IllegalArgumentException(
						MenuMessages.getString("MENU.MENUCODE_IS_EXIST"));
			}
			if (isBlank(menuDTO.getMenuName())) {
				throw new NullPointerException(
						MenuMessages.getString("MENU.MENUNAME_IS_NULL"));
			} else if (existsByMenuName(menuDTO.getMenuName(),
					menuDTO.getParentId())) {
				throw new IllegalArgumentException(
						MenuMessages.getString("MENU.MENUNAME_IS_EXIST"));
			}

			if (isBlank(menuDTO.getParentId())) {
				throw new NullPointerException(
						MenuMessages.getString("MENU.PARNETID_IS_NULL"));
			}
		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUDTO_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void update(MenuDTO menuDTO) {
		// 验证
		validateUpdateMenu(menuDTO);

		// 进行权限表的修改
		PrivilegeDTO privilegeDTO = new PrivilegeDTO();
		copyMenuDTOToPrivilegeDTO(menuDTO, privilegeDTO);
		privilegeService.update(privilegeDTO);

		// 进行菜单表的修改
		MenuEO menuEO = new MenuEO();
		BeanCopierUtil.copy(menuDTO, menuEO);
		menuDao.update(menuEO);
	}

	private void validateUpdateMenu(MenuDTO menuDTO) {
		if (null != menuDTO) {
			if (isBlank(menuDTO.getMenuId())) {
				throw new NullPointerException(
						MenuMessages
								.getString("MENU.MENUID_IS_NULL_EMPTY_BLANK"));
			}
			if (isBlank(menuDTO.getMenuName())) {
				menuDTO.setMenuName(null);
			} else if (existsByMenuNameIgnoreMenuID(menuDTO.getMenuName(),
					menuDTO.getMenuId())) {
				throw new IllegalArgumentException(
						MenuMessages.getString("MENU.MENUNAME_IS_EXIST"));
			}
		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUDTO_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByPKs(String... menuIds) {
		if (null != menuIds) {
			if (menuIds.length > 0) {
				Set<String> delMenuIdSet = new HashSet<String>();
				for (String menuId : menuIds) {
					if (isBlank(menuId)) {
						throw new NullPointerException(
								MenuMessages
										.getString("MENU.MENUID_ARRAY_COMTAINS_NULL_EMPTY_BLANK_ITEM"));
					}
					if (!delMenuIdSet.contains(menuId)) {
						delMenuIdSet.add(menuId);
						List<MenuDTO> menuDTOs = queryChildren(menuId, true);
						for (MenuDTO menuDTO : menuDTOs) {
							delMenuIdSet.add(menuDTO.getMenuId());
						}
					}
				}
				if (!delMenuIdSet.isEmpty()) {
					String[] delMenuIds = delMenuIdSet
							.toArray(new String[delMenuIdSet.size()]);
					menuDao.deleteByPKs(delMenuIds);
					privilegeService.deleteByPKs(delMenuIds);
				}
			} else {
				throw new NullPointerException(
						MenuMessages
								.getString("MENU.MENUID_ARRAY_COMTAINS_NO_ITEM"));
			}
		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUID_ARRAY_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByMenuCodes(String... menuCodes) {
		if (null != menuCodes) {
			if (menuCodes.length > 0) {
				List<String> ids = new ArrayList<String>();
				for (String menuCode : menuCodes) {
					if (isBlank(menuCode)) {
						throw new NullPointerException(
								MenuMessages
										.getString("MENU.MENUCODE_ARRAY_CONTAINS_NULL_EMPTY_BLANK_ITEM"));
					} else {
						MenuDTO menuDTO = queryByMenuCode(menuCode);
						if (null != menuDTO) {
							ids.add(menuDTO.getMenuId());
						}
					}
				}
				if (null != ids && ids.size() > 0) {
					deleteByPKs(ids.toArray(new String[ids.size()]));
				}

			} else {
				throw new NullPointerException(
						MenuMessages
								.getString("MENU.MENUCODE_ARRAY_CONTAINS_NO_ITEM"));
			}
		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUCODE_ARRAY_IS_NULL"));
		}
	}

	public List<MenuDTO> queryAll() {
		List<MenuAllInfoEO> menuAllInfoEOList = menuDao.queryAll();
		List<MenuDTO> menuDTOList = new ArrayList<MenuDTO>();
		if (null != menuAllInfoEOList && menuAllInfoEOList.size() > 0) {
			BeanCopierUtil.copy(menuAllInfoEOList, menuDTOList,
					MenuAllInfoEO.class, MenuDTO.class);
		}
		return menuDTOList;
	}

	public List<MenuDTO> queryAllByPermission() {
		// 从上下文查询用户ID
		String userId = accessControlService.getUserID();
		// 通过用户ID查询用户的所有菜单权限列表
		if (!isBlank(userId)) {
			List<MenuDTO> allMenuDTOList = queryAll();
			// 如果是超级管理员，直接调用不带权限方法
			if (userService.isAdmin(userId)) {
				return allMenuDTOList;
			}

			List<MenuDTO> menuDTOList = new ArrayList<MenuDTO>();
			for (MenuDTO menuDTO : allMenuDTOList) {
				if(accessControlService.isPermitted(menuDTO.getPermExpr())){
					menuDTOList.add(menuDTO);
				}
			}
		
			return menuDTOList;
		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.USERID_IS_NULL_EMPTY_BLANK"));
		}
	}

	private List<String> getMenuIdListByPrivilegeEOList(
			List<PrivilegeEO> privilegeEOList) {
		List<String> menuIdList = new ArrayList<String>();
		if (null != privilegeEOList && privilegeEOList.size() > 0) {
			for (PrivilegeEO privilegeEO : privilegeEOList) {
				String menuId = privilegeEO.getPrivilegeId();
				menuIdList.add(menuId);
			}
		}
		return menuIdList;
	}

	public Page<MenuDTO> queryAll(Pageable pageable, Sortable sortable) {
		Page<MenuAllInfoEO> menuAllInfoEOPage = menuDao.queryAll(pageable,
				sortable);
		Page<MenuDTO> menuDTOPage = new Page<MenuDTO>(pageable.getPageIndex(),
				pageable.getPageSize(), 0, new ArrayList<MenuDTO>());
		if (null != menuAllInfoEOPage && menuAllInfoEOPage.getTotal() > 0) {
			List<MenuDTO> mtList = new ArrayList<MenuDTO>();
			BeanCopierUtil.copy(menuAllInfoEOPage.getContents(), mtList,
					MenuAllInfoEO.class, MenuDTO.class);
			menuDTOPage = new Page<MenuDTO>(menuAllInfoEOPage.getPageIndex(),
					menuAllInfoEOPage.getPageSize(),
					menuAllInfoEOPage.getTotal(), mtList);
		}
		return menuDTOPage;
	}

	public Page<MenuDTO> queryAllByPermission(Pageable pageable,
			Sortable sortable) {
		// 从上下文查询用户ID
		String userId = accessControlService.getUserID();
		// 通过用户ID查询用户的所有菜单权限列表
		if (!isBlank(userId)) {

			// 如果是超级管理员，直接调用不带权限方法
			if (userService.isAdmin(userId)) {
				return queryAll(pageable, sortable);
			}

			// 查询用户下的所有菜单权限列表
			List<PrivilegeEO> privilegeEOList = menuDao
					.queryPrivilegesOfMenuByUserId(userId);

			// 筛选菜单权限ID并查询出所有的菜单详细信息列表
			List<String> menuIdList = getMenuIdListByPrivilegeEOList(privilegeEOList);
			Page<MenuDTO> menuDTOList = new Page<MenuDTO>(
					pageable.getPageIndex(), pageable.getPageSize(), 0,
					new ArrayList<MenuDTO>());
			if (!menuIdList.isEmpty()) {
				// 查询菜单详情
				Page<MenuAllInfoEO> menuAllInfoEOList = menuDao.queryByPKs(
						menuIdList, pageable, sortable);
				if (null != menuAllInfoEOList
						&& menuAllInfoEOList.getTotal() > 0) {
					// 将目录对象互转
					List<MenuDTO> contents = new ArrayList<MenuDTO>();
					BeanCopierUtil.copy(menuAllInfoEOList.getContents(),
							contents, MenuAllInfoEO.class, MenuDTO.class);
					menuDTOList = new Page<MenuDTO>(
							menuAllInfoEOList.getPageIndex(),
							menuAllInfoEOList.getPageSize(),
							menuAllInfoEOList.getTotal(), contents);
				}
			}
			return menuDTOList;
		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.USERID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public List<MenuDTO> queryByMenu(MenuDTO menuDTO) {
		if (null != menuDTO) {
			MenuAllInfoEO menuAllInfoEO = new MenuAllInfoEO();
			BeanCopierUtil.copy(menuDTO, menuAllInfoEO);
			List<MenuAllInfoEO> menuAllInfoEOList = menuDao
					.queryByMenu(menuAllInfoEO);
			List<MenuDTO> menuDTOList = new ArrayList<MenuDTO>();
			if (null != menuAllInfoEOList && menuAllInfoEOList.size() > 0) {
				BeanCopierUtil.copy(menuAllInfoEOList, menuDTOList,
						MenuAllInfoEO.class, MenuDTO.class);
			}
			return menuDTOList;
		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUDTO_IS_NULL"));
		}
	}

	public List<MenuDTO> queryByMenuAndPermission(MenuDTO menuDTO) {
		if (null != menuDTO) {
			// 从上下文查询用户ID
			String userId = accessControlService.getUserID();
			// 通过用户ID查询用户的所有菜单权限列表
			if (!isBlank(userId)) {

				// 如果是超级管理员，直接调用不带权限方法
				if (userService.isAdmin(userId)) {
					return queryByMenu(menuDTO);
				}

				// 查询用户下的所有菜单权限列表
				List<PrivilegeEO> privilegeEOList = menuDao
						.queryPrivilegesOfMenuByUserId(userId);

				// 筛选菜单权限ID并查询出所有的菜单详细信息列表
				List<String> menuIdList = getMenuIdListByPrivilegeEOList(privilegeEOList);
				List<MenuDTO> menuDTOList = new ArrayList<MenuDTO>();
				if (!menuIdList.isEmpty()) {
					MenuAllInfoEO menuAllInfoEO = new MenuAllInfoEO();
					BeanCopierUtil.copy(menuDTO, menuAllInfoEO);

					// 查询菜单详情
					List<MenuAllInfoEO> menuAllInfoEOList = menuDao
							.queryByMenuPermission(menuAllInfoEO, menuIdList);

					if (null != menuAllInfoEOList
							&& menuAllInfoEOList.size() > 0) {
						BeanCopierUtil.copy(menuAllInfoEOList, menuDTOList,
								MenuAllInfoEO.class, MenuDTO.class);
					}
				}
				return menuDTOList;
			} else {
				throw new NullPointerException(
						MenuMessages
								.getString("MENU.USERID_IS_NULL_EMPTY_BLANK"));
			}
		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUDTO_IS_NULL"));
		}
	}

	public Page<MenuDTO> queryByMenu(MenuDTO menuDTO, Pageable pageable,
			Sortable sortable) {
		if (null != menuDTO) {
			MenuAllInfoEO menuAllInfoEO = new MenuAllInfoEO();
			BeanCopierUtil.copy(menuDTO, menuAllInfoEO);

			Page<MenuAllInfoEO> menuAllInfoEOPage = menuDao.queryByMenu(
					menuAllInfoEO, pageable, sortable);
			Page<MenuDTO> menuDTOPage = new Page<MenuDTO>(
					pageable.getPageIndex(), pageable.getPageSize(), 0,
					new ArrayList<MenuDTO>());
			if (null != menuAllInfoEOPage && menuAllInfoEOPage.getTotal() > 0) {
				List<MenuDTO> contents = new ArrayList<MenuDTO>();
				BeanCopierUtil.copy(menuAllInfoEOPage.getContents(), contents,
						MenuAllInfoEO.class, MenuDTO.class);
				menuDTOPage = new Page<MenuDTO>(
						menuAllInfoEOPage.getPageIndex(),
						menuAllInfoEOPage.getPageSize(),
						menuAllInfoEOPage.getTotal(), contents);
			}
			return menuDTOPage;
		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUDTO_IS_NULL"));
		}

	}

	public Page<MenuDTO> queryByMenuAndPermission(MenuDTO menuDTO,
			Pageable pageable, Sortable sortable) {
		if (null != menuDTO) {
			// 从上下文查询用户ID
			String userId = accessControlService.getUserID();
			// 通过用户ID查询用户的所有菜单权限列表
			if (!isBlank(userId)) {

				// 如果是超级管理员，直接调用不带权限方法
				if (userService.isAdmin(userId)) {
					return queryByMenu(menuDTO, pageable, sortable);
				}

				// 查询用户下的所有菜单权限列表
				List<PrivilegeEO> privilegeEOList = menuDao
						.queryPrivilegesOfMenuByUserId(userId);

				// 筛选菜单权限ID并查询出所有的菜单详细信息列表
				List<String> menuIdList = getMenuIdListByPrivilegeEOList(privilegeEOList);
				Page<MenuDTO> menuDTOList = new Page<MenuDTO>(
						pageable.getPageIndex(), pageable.getPageSize(), 0,
						new ArrayList<MenuDTO>());
				if (!menuIdList.isEmpty()) {

					MenuAllInfoEO menuAllInfoEO = new MenuAllInfoEO();
					BeanCopierUtil.copy(menuDTO, menuAllInfoEO);

					// 查询菜单详情
					Page<MenuAllInfoEO> menuAllInfoEOList = menuDao
							.queryByMenuPermission(menuAllInfoEO, menuIdList,
									pageable, sortable);

					if (null != menuAllInfoEOList
							&& menuAllInfoEOList.getTotal() > 0) {
						List<MenuDTO> menuDtoList = new ArrayList<MenuDTO>();
						BeanCopierUtil
								.copy(menuAllInfoEOList.getContents(),
										menuDtoList, MenuAllInfoEO.class,
										MenuDTO.class);
						menuDTOList = new Page<MenuDTO>(
								menuAllInfoEOList.getPageIndex(),
								menuAllInfoEOList.getPageSize(),
								menuAllInfoEOList.getTotal(), menuDtoList);
					}
				}
				return menuDTOList;
			} else {
				throw new NullPointerException(
						MenuMessages
								.getString("MENU.USERID_IS_NULL_EMPTY_BLANK"));
			}
		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUDTO_IS_NULL"));
		}
	}

	public MenuDTO queryByPK(String menuId) {
		if (!isBlank(menuId)) {
			MenuAllInfoEO menuAllInfoEO = menuDao.queryByPK(menuId);
			MenuDTO menuDTO = null;
			if (null != menuAllInfoEO) {
				menuDTO = new MenuDTO();
				BeanCopierUtil.copy(menuAllInfoEO, menuDTO);
			}
			return menuDTO;
		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public MenuDTO queryByMenuCode(String menuCode) {
		if (!isBlank(menuCode)) {
			MenuAllInfoEO menuAllInfoEO = menuDao.queryByMenuCode(menuCode);
			MenuDTO menuDTO = null;
			if (null != menuAllInfoEO) {
				menuDTO = new MenuDTO();
				BeanCopierUtil.copy(menuAllInfoEO, menuDTO);
			}
			return menuDTO;
		} else {
			throw new NullPointerException(
					MenuMessages
							.getString("MENU.MENUCODE_CANT_BE_NULL_EMPTY_BLANK"));
		}
	}

	public List<MenuDTO> queryParents(String menuId, boolean recursively) {
		if (!isBlank(menuId)) {
			List<MenuAllInfoEO> menuAllInfoEOList = null;
			// 递归
			if (recursively) {
				menuAllInfoEOList = menuDao.queryParents(menuId);
			} else {// 不递归
				menuAllInfoEOList = menuDao.queryParentsUnRecursive(menuId);
			}
			List<MenuDTO> menuDTOList = new ArrayList<MenuDTO>();
			if (null != menuAllInfoEOList && menuAllInfoEOList.size() > 0) {
				BeanCopierUtil.copy(menuAllInfoEOList, menuDTOList,
						MenuAllInfoEO.class, MenuDTO.class);
			}
			return menuDTOList;
		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public Page<MenuDTO> queryParents(String menuId, boolean recursively,
			Pageable pageable, Sortable sortable) {
		if (!isBlank(menuId)) {
			Page<MenuAllInfoEO> menuAllInfoEOPage = null;
			// 递归
			if (recursively) {
				menuAllInfoEOPage = menuDao.queryParents(menuId, pageable,
						sortable);
			} else {// 不递归,查询的父菜单只有一个，故为了兼容，还是做Page化处理
				List<MenuAllInfoEO> eoList = menuDao
						.queryParentsUnRecursive(menuId);
				menuAllInfoEOPage = new Page<MenuAllInfoEO>(
						pageable.getPageIndex(), pageable.getPageSize(), 1,
						eoList);
			}
			Page<MenuDTO> menuDTOList = new Page<MenuDTO>(
					pageable.getPageIndex(), pageable.getPageSize(), 0,
					new ArrayList<MenuDTO>());
			if (null != menuAllInfoEOPage && menuAllInfoEOPage.getTotal() > 0) {
				List<MenuDTO> contents = new ArrayList<MenuDTO>();
				BeanCopierUtil.copy(menuAllInfoEOPage.getContents(), contents,
						MenuAllInfoEO.class, MenuDTO.class);
				menuDTOList = new Page<MenuDTO>(
						menuAllInfoEOPage.getPageIndex(),
						menuAllInfoEOPage.getPageSize(),
						menuAllInfoEOPage.getTotal(), contents);
			}
			return menuDTOList;
		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public List<MenuDTO> queryChildren(String menuId, boolean recursively) {
		if (!isBlank(menuId)) {
			List<MenuAllInfoEO> menuAllInfoEOList = null;
			// 递归
			if (recursively) {
				menuAllInfoEOList = menuDao.queryChildren(menuId);
			} else {// 不递归
				menuAllInfoEOList = menuDao.queryChildrenUnRecursive(menuId);
			}
			List<MenuDTO> menuDTOList = new ArrayList<MenuDTO>();
			if (null != menuAllInfoEOList && menuAllInfoEOList.size() > 0) {
				BeanCopierUtil.copy(menuAllInfoEOList, menuDTOList,
						MenuAllInfoEO.class, MenuDTO.class);
			}
			return menuDTOList;
		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public List<MenuDTO> queryChildrenByPermission(String menuId,
			boolean recursively) {
		if (!isBlank(menuId)) {
			// 从上下文查询用户ID
			String userId = accessControlService.getUserID();
			// 通过用户ID查询用户的所有菜单权限列表
			if (!isBlank(userId)) {

				// 如果是超级管理员，直接调用不带权限方法
				if (userService.isAdmin(userId)) {
					return queryChildren(menuId, recursively);
				}

				// 查询用户下的所有菜单权限列表
				List<PrivilegeEO> privilegeEOList = menuDao
						.queryPrivilegesOfMenuByUserId(userId);

				// 筛选菜单权限ID并查询出所有的菜单详细信息列表
				List<String> menuIdList = getMenuIdListByPrivilegeEOList(privilegeEOList);

				List<MenuAllInfoEO> menuAllInfoEOList = null;
				if (!menuIdList.isEmpty()) {
					// 递归
					if (recursively) {
						menuAllInfoEOList = menuDao.queryChildrenPermission(
								menuId, menuIdList);
					} else {// 不递归
						menuAllInfoEOList = menuDao
								.queryChildrenUnRecursivePermission(menuId,
										menuIdList);
					}
				}
				List<MenuDTO> menuDTOList = new ArrayList<MenuDTO>();
				if (null != menuAllInfoEOList && menuAllInfoEOList.size() > 0) {
					BeanCopierUtil.copy(menuAllInfoEOList, menuDTOList,
							MenuAllInfoEO.class, MenuDTO.class);
				}
				return menuDTOList;

			} else {
				throw new NullPointerException(
						MenuMessages
								.getString("MENU.USERID_IS_NULL_EMPTY_BLANK"));
			}

		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public Page<MenuDTO> queryChildren(String menuId, boolean isRecursion,
			Pageable pageable, Sortable sortable) {
		if (!isBlank(menuId)) {
			Page<MenuAllInfoEO> menuAllInfoEOPage = null;
			// 递归
			if (isRecursion) {
				menuAllInfoEOPage = menuDao.queryChildren(menuId, pageable,
						sortable);
			} else {// 不递归
				menuAllInfoEOPage = menuDao.queryChildrenUnRecursive(menuId,
						pageable, sortable);
			}
			Page<MenuDTO> menuDTOList = new Page<MenuDTO>(
					pageable.getPageIndex(), pageable.getPageSize(), 0,
					new ArrayList<MenuDTO>());
			if (null != menuAllInfoEOPage && menuAllInfoEOPage.getTotal() > 0) {
				List<MenuDTO> contents = new ArrayList<MenuDTO>();
				BeanCopierUtil.copy(menuAllInfoEOPage.getContents(), contents,
						MenuAllInfoEO.class, MenuDTO.class);
				menuDTOList = new Page<MenuDTO>(
						menuAllInfoEOPage.getPageIndex(),
						menuAllInfoEOPage.getPageSize(),
						menuAllInfoEOPage.getTotal(), contents);
			}
			return menuDTOList;
		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public Page<MenuDTO> queryChildrenByPermission(String menuId,
			boolean isRecursion, Pageable pageable, Sortable sortable) {
		if (!isBlank(menuId)) {

			// 从上下文查询用户ID
			String userId = accessControlService.getUserID();
			// 通过用户ID查询用户的所有菜单权限列表
			if (!isBlank(userId)) {

				// 如果是超级管理员，直接调用不带权限方法
				if (userService.isAdmin(userId)) {
					return queryChildren(menuId, isRecursion, pageable,
							sortable);
				}

				// 查询用户下的所有菜单权限列表
				List<PrivilegeEO> privilegeEOList = menuDao
						.queryPrivilegesOfMenuByUserId(userId);

				// 筛选菜单权限ID并查询出所有的菜单详细信息列表
				List<String> menuIdList = getMenuIdListByPrivilegeEOList(privilegeEOList);

				Page<MenuAllInfoEO> menuAllInfoEOList = new Page<MenuAllInfoEO>(
						pageable.getPageIndex(), pageable.getPageSize(), 0,
						new ArrayList<MenuAllInfoEO>());
				// 递归
				if (isRecursion) {
					menuAllInfoEOList = menuDao.queryChildrenPermission(menuId,
							menuIdList, pageable, sortable);
				} else {// 不递归
					menuAllInfoEOList = menuDao
							.queryChildrenUnRecursivePermission(menuId,
									menuIdList, pageable, sortable);
				}
				Page<MenuDTO> menuDTOList = new Page<MenuDTO>(
						pageable.getPageIndex(), pageable.getPageSize(), 0,
						new ArrayList<MenuDTO>());
				if (null != menuAllInfoEOList
						&& menuAllInfoEOList.getTotal() > 0) {
					List<MenuDTO> contents = new ArrayList<MenuDTO>();
					BeanCopierUtil.copy(menuAllInfoEOList.getContents(),
							contents, MenuAllInfoEO.class, MenuDTO.class);
					menuDTOList = new Page<MenuDTO>(
							menuAllInfoEOList.getPageIndex(),
							menuAllInfoEOList.getPageSize(),
							menuAllInfoEOList.getTotal(), contents);
				}
				return menuDTOList;

			} else {
				throw new NullPointerException(
						MenuMessages
								.getString("MENU.USERID_IS_NULL_EMPTY_BLANK"));
			}

		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public boolean isEnabledByMenuId(String menuId) {
		if (!isBlank(menuId)) {
			return menuDao.isEnabledByMenuId(menuId) > 0;
		} else {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public boolean isEnabledByMenuCode(String menuCode) {
		if (!isBlank(menuCode)) {
			return menuDao.isEnabledByMenuCode(menuCode) > 0;
		} else {
			throw new NullPointerException(
					MenuMessages
							.getString("MENU.MENUCODE_CANT_BE_NULL_EMPTY_BLANK"));
		}
	}

	public boolean existsByMenuCode(String menuCode) {
		if (!isBlank(menuCode)) {

			// 实际上查询的是权限表中的编码存在情况
			return menuDao.existsByMenuCode(menuCode) > 0;
		} else {
			throw new NullPointerException(
					MenuMessages
							.getString("MENU.MENUCODE_CANT_BE_NULL_EMPTY_BLANK"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setOrder(List<MenuDTO> menuDTOList) {
		if (null != menuDTOList && menuDTOList.size() > 0) {
			for (MenuDTO menuDTO : menuDTOList) {
				if (null != menuDTO) {
					if (!isBlank(menuDTO.getMenuId())
							&& null != menuDTO.getSn()) {
						MenuEO menuEO = new MenuEO();
						BeanCopierUtil.copy(menuDTO, menuEO);
						menuDao.setOrder(menuEO);
					} else {
						throw new NullPointerException(
								MenuMessages
										.getString("MENU.MENUID_CANT_BE_NULL_EMPTY_BLANK_AND_SN_CANT_BE_NULL"));
					}
				} else {
					throw new NullPointerException(
							MenuMessages
									.getString("MENU.MENUDTO_ARRAY_CONTAINS_NULL_ITEM"));
				}
			}
		} else {
			throw new NullPointerException(
					MenuMessages
							.getString("MENU.MENUDTO_ARRAY_IS_NULL_OR_EMPTY"));
		}
	}

	public boolean isMgtPermitted(String userId, String menuId) {
		// TODO 待可管理功能出来后完成
		return false;
	}

	/**
	 * 判断字符串是否为空或空格或null
	 * 
	 * @param str
	 *            被判断的字符串
	 * @return 字符串是否为null或者""或者全是空格 是：true 否：false
	 */
	private boolean isBlank(String str) {
		return (null == str || "".equals(str.trim()));
	}

	/**
	 * 将菜单业务数据对象转换成权限数据库访问对象
	 * 
	 * @param menuDTO
	 *            菜单业务数据对象
	 * @param privilegeEO
	 *            权限数据库访问对象
	 */
	private void copyMenuDTOToPrivilegeDTO(MenuDTO menuDTO,
			PrivilegeDTO privilegeDTO) {
		if (null != privilegeDTO) {
			privilegeDTO.setPrivilegeId(menuDTO.getMenuId());
			privilegeDTO.setPrivilegeName(menuDTO.getMenuName());
			privilegeDTO.setPrivilegeCode(menuDTO.getMenuCode());
			privilegeDTO.setParentId(menuDTO.getParentId());
			privilegeDTO.setPermExpr(menuDTO.getPermExpr());
			privilegeDTO.setType(menuDTO.getType());
			privilegeDTO.setCreator(menuDTO.getCreator());
			privilegeDTO.setCreatorTime(menuDTO.getCreatorTime());
			privilegeDTO.setSn(menuDTO.getSn());
			privilegeDTO.setSource(menuDTO.getSource());
		}
	}

	public boolean existsChildMenus(String menuId) {
		if (isBlank(menuId)) {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUID_IS_NULL_EMPTY_BLANK"));
		}
		return menuDao.existsChildMenus(menuId) > 0;
	}

	public boolean existsByMenuName(String menuName, String parentId) {
		if (isBlank(menuName)) {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUNAME_IS_NULL"));
		}
		if (isBlank(parentId)) {
			throw new NullPointerException(
					MenuMessages.getString("MENU.PARNETID_IS_NULL"));
		}
		return menuDao.existsByMenuName(menuName, parentId) > 0;
	}

	public boolean existsByMenuNameIgnoreMenuID(String menuName, String menuId) {
		if (isBlank(menuId)) {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUID_IS_NULL_EMPTY_BLANK"));
		}
		if (isBlank(menuName)) {
			throw new NullPointerException(
					MenuMessages.getString("MENU.MENUNAME_IS_NULL"));
		}
		return menuDao.existsByMenuNameIgnoreMenuID(menuName, menuId) > 0;
	}
}
