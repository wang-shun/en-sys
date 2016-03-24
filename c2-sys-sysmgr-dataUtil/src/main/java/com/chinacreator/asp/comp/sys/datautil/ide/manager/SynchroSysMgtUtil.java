package com.chinacreator.asp.comp.sys.datautil.ide.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.basic.menu.dao.MenuDao;
import com.chinacreator.asp.comp.sys.basic.menu.eo.MenuAllInfoEO;
import com.chinacreator.asp.comp.sys.basic.menu.eo.MenuEO;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.PKGenerator;
import com.chinacreator.asp.comp.sys.common.exception.SysException;
import com.chinacreator.asp.comp.sys.core.privilege.dao.PrivilegeDao;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.asp.comp.sys.core.role.dao.RolePrivilegeDao;
import com.chinacreator.platform.mvc.menu.Menu;
import com.chinacreator.platform.mvc.menu.Module;
import com.chinacreator.platform.mvc.perm.Resource;

/**
 * IDE同步到系统管理工具类
 * 
 * @author 彭盛
 * 
 */
@Component
public class SynchroSysMgtUtil {

	@Autowired
	private IDEOperUtil ideOperUtil;

	@Autowired
	private PrivilegeDao privilegeDao;

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private RolePrivilegeDao rolePrivilegeDao;

	/**
	 * 同步IDE中的资源到系统管理
	 */
	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void synchroRes() {
		List<PrivilegeEO> addPrivilegeList = new ArrayList<PrivilegeEO>();
		List<PrivilegeEO> updatePrivilegeList = new ArrayList<PrivilegeEO>();
		List<PrivilegeEO> delPrivilegeList = new ArrayList<PrivilegeEO>();
		getSynchroResInfo(addPrivilegeList, updatePrivilegeList,
				delPrivilegeList);
		for (PrivilegeEO privilegeEO : addPrivilegeList) {
			privilegeDao.create(privilegeEO);
		}
		for (PrivilegeEO privilegeEO : updatePrivilegeList) {
			privilegeDao.update(privilegeEO);
		}
		for (PrivilegeEO privilegeEO : delPrivilegeList) {
			rolePrivilegeDao.deleteByPrivileges(privilegeEO.getParentId());
			privilegeDao.deleteByPK(privilegeEO.getPrivilegeId());
		}
	}

	/**
	 * 获取同步资源信息
	 * 
	 * @param addPrivilegeList
	 *            需要新增的资源
	 * @param updatePrivilegeList
	 *            需要修改的资源
	 * @param delPrivilegeList
	 *            需要删除的资源
	 */
	public void getSynchroResInfo(List<PrivilegeEO> addPrivilegeList,
			List<PrivilegeEO> updatePrivilegeList,
			List<PrivilegeEO> delPrivilegeList) {
		List<Resource> resList = ideOperUtil.getIDEResourceList();
		Set<String> set = new HashSet<String>();
		if (null != resList) {
			PrivilegeEO privilegeEO = null;
			int sn = 0;
			for (Resource resource : resList) {
				if (null != resource.getId()
						&& !resource.getId().trim().equals("")) {
					set.add(resource.getId());
					if (privilegeDao.existsByCode(resource.getId()) > 0) {
						privilegeEO = privilegeDao
								.queryByCode(resource.getId());
						if (null != privilegeEO) {
							privilegeEO
									.setPrivilegeName(getPrivilegeNameByResName(
											resource.getId(),
											resource.getName()));
							privilegeEO
									.setParentId(getParentIdByResPath(resource
											.getPath()));
							privilegeEO.setType(getTypeByResType(
									resource.getId(), resource.getType()));
							privilegeEO.setCreator("1");
							privilegeEO.setCreatorTime(new Date());
							privilegeEO.setSn(sn++);
							privilegeEO.setSource("1");
							updatePrivilegeList.add(privilegeEO);
						} else {
							throw new SysException(String.format(
									"资源【%s(%s)】在数据库中不存在！", resource.getName(),
									resource.getId()));
						}
					} else {
						privilegeEO = new PrivilegeEO();
						privilegeEO.setPrivilegeId(PKGenerator.generate());
						privilegeEO.setPrivilegeCode(resource.getId());
						privilegeEO.setPermExpr(resource.getId());
						privilegeEO.setPrivilegeName(getPrivilegeNameByResName(
								resource.getId(), resource.getName()));
						privilegeEO.setParentId(getParentIdByResPath(resource
								.getPath()));
						privilegeEO.setType(getTypeByResType(resource.getId(),
								resource.getType()));
						privilegeEO.setCreator("1");
						privilegeEO.setCreatorTime(new Date());
						privilegeEO.setSn(sn++);
						privilegeEO.setSource("1");
						addPrivilegeList.add(privilegeEO);
					}
				}
			}
		}
		PrivilegeEO privilegeEO = new PrivilegeEO();
		privilegeEO.setSource("1");
		List<PrivilegeEO> allPrivilegeList = privilegeDao.query(privilegeEO);
		if (null != allPrivilegeList && !allPrivilegeList.isEmpty()) {
			for (PrivilegeEO pEO : allPrivilegeList) {
				if (!"4".equals(pEO.getType())
						&& !set.contains(pEO.getPrivilegeCode())) {
					delPrivilegeList.add(pEO);
				}
			}
		}
	}

	private String getPrivilegeNameByResName(String resId, String resName) {
		if (null != resName && !resName.trim().equals("")) {
			return resName;
		} else {
			throw new SysException(String.format("资源【%s】名称为空！", resId));
		}
	}

	private String getParentIdByResPath(String resPath) {
		return resPath;
	}

	private String getTypeByResType(String resId, String resType) {
		if (null != resType && !resType.trim().equals("")) {
			if ("service".equals(resType)) {
				return "1";
			} else if ("form".equals(resType)) {
				return "2";
			} else if ("dom".equals(resType)) {
				return "3";
			} else if ("entity".equals(resType)) {
				return "5";
			} else if ("entity_op".equals(resType)) {
				return "6";
			} else if("custom".equals(resType)){
				return "9";
			}else {
				throw new SysException(String.format("资源【%s】类型【%s】无法识别！",
						resId, resType));
			}
		} else {
			throw new SysException(String.format("资源【%s】类型为空！", resId));
		}
	}

	/**
	 * 同步IDE中的菜单到系统管理
	 */
	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void synchroMenu() {
		List<MenuAllInfoEO> addMenuList = new ArrayList<MenuAllInfoEO>();
		List<MenuAllInfoEO> updateMenuList = new ArrayList<MenuAllInfoEO>();
		List<MenuAllInfoEO> delMenuList = new ArrayList<MenuAllInfoEO>();
		getSynchroMenuInfo(addMenuList, updateMenuList, delMenuList);
		for (MenuAllInfoEO menuAllInfoEO : addMenuList) {
			PrivilegeEO privilegeEO = new PrivilegeEO();
			copyMenuToPrivilege(menuAllInfoEO, privilegeEO);
			privilegeDao.create(privilegeEO);

			MenuEO menuEO = new MenuEO();
			BeanCopierUtil.copy(menuAllInfoEO, menuEO);
			menuDao.create(menuEO);
		}
		for (MenuAllInfoEO menuAllInfoEO : updateMenuList) {
			PrivilegeEO privilegeEO = new PrivilegeEO();
			copyMenuToPrivilege(menuAllInfoEO, privilegeEO);
			privilegeDao.update(privilegeEO);

			MenuEO menuEO = new MenuEO();
			BeanCopierUtil.copy(menuAllInfoEO, menuEO);
			menuDao.update(menuEO);
		}
		Set<String> menuIdSet = new HashSet<String>();
		for (MenuAllInfoEO menuAllInfoEO : delMenuList) {
			menuIdSet.add(menuAllInfoEO.getMenuId());
		}
		if (!menuIdSet.isEmpty()) {
			String[] menuIds = menuIdSet.toArray(new String[menuIdSet.size()]);
			rolePrivilegeDao.deleteByPrivileges(menuIds);
			menuDao.deleteByPKs(menuIds);
			privilegeDao.deleteByPKs(menuIds);
		}
	}

	/**
	 * 获取同步菜单信息
	 * 
	 * @param addMenuList
	 *            需要新增的菜单
	 * @param updateMenuList
	 *            需要修改的菜单
	 * @param delMenuList
	 *            需要删除的菜单
	 */
	public void getSynchroMenuInfo(List<MenuAllInfoEO> addMenuList,
			List<MenuAllInfoEO> updateMenuList, List<MenuAllInfoEO> delMenuList) {
		List<Module> menuList = ideOperUtil.getIDEModuleList();
		reModules(menuList, addMenuList, updateMenuList, "0");
		Set<String> set = new HashSet<String>();
		for (MenuAllInfoEO menuAllInfoEO : addMenuList) {
			set.add(menuAllInfoEO.getMenuCode());
		}
		for (MenuAllInfoEO menuAllInfoEO : updateMenuList) {
			set.add(menuAllInfoEO.getMenuCode());
		}
		List<MenuAllInfoEO> allMenuList = menuDao.queryAll();
		if (null != allMenuList && !allMenuList.isEmpty()) {
			for (MenuAllInfoEO menuAllInfoEO : allMenuList) {
				if ("1".equals(menuAllInfoEO.getSource())
						&& !set.contains(menuAllInfoEO.getMenuCode())) {
					delMenuList.add(menuAllInfoEO);
				}
			}
		}
	}

	private void reModules(List<Module> moduleList,
			List<MenuAllInfoEO> addMenuList,
			List<MenuAllInfoEO> updateMenuList, String parentId) {
		if (null != moduleList && !moduleList.isEmpty()) {
			MenuAllInfoEO menuAllInfoEO = null;
			int sn = 0;
			for (Module module : moduleList) {
				if (null != module.getRes()
						&& !module.getRes().trim().equals("")) {
					if (privilegeDao.existsByCode(module.getRes()) > 0) {
						menuAllInfoEO = menuDao
								.queryByMenuCode(module.getRes());
						if (null != menuAllInfoEO) {
							menuAllInfoEO.setMenuName(module.getName());
							menuAllInfoEO.setParentId(parentId);
							menuAllInfoEO.setIsEnabled(true);
							menuAllInfoEO.setHref(module.getUrl());
							menuAllInfoEO.setIcon(module.getIcon());
							menuAllInfoEO.setCreator("1");
							menuAllInfoEO.setCreatorTime(new Date());
							menuAllInfoEO.setSn(sn++);
							menuAllInfoEO.setType("4");
							menuAllInfoEO.setDisplayMode("0");
							menuAllInfoEO.setSource("1");
							updateMenuList.add(menuAllInfoEO);
						} else {
							throw new SysException(String.format(
									"菜单【%s(%s)】在数据库中不存在！", module.getName(),
									module.getRes()));
						}

					} else {
						menuAllInfoEO = new MenuAllInfoEO();
						menuAllInfoEO.setMenuId(PKGenerator.generate());
						menuAllInfoEO.setMenuCode(module.getRes());
						menuAllInfoEO.setPermExpr(module.getRes());
						menuAllInfoEO.setMenuName(module.getName());
						menuAllInfoEO.setParentId(parentId);
						menuAllInfoEO.setIsEnabled(true);
						menuAllInfoEO.setHref(module.getUrl());
						menuAllInfoEO.setIcon(module.getIcon());
						menuAllInfoEO.setCreator("1");
						menuAllInfoEO.setCreatorTime(new Date());
						menuAllInfoEO.setSn(sn++);
						menuAllInfoEO.setType("4");
						menuAllInfoEO.setDisplayMode("0");
						menuAllInfoEO.setSource("1");
						addMenuList.add(menuAllInfoEO);
					}
				} else {
					throw new SysException("菜单ID为空！");
				}
				reItems(module.getItem(), addMenuList, updateMenuList, parentId);
				reModules(module.getModule(), addMenuList, updateMenuList,
						menuAllInfoEO.getMenuId());
			}
		}
	}

	private void reItems(List<Menu> menuList, List<MenuAllInfoEO> addMenuList,
			List<MenuAllInfoEO> updateMenuList, String parentId) {
		if (null != menuList && !menuList.isEmpty()) {
			MenuAllInfoEO menuAllInfoEO = null;
			int sn = 0;
			for (Menu menu : menuList) {
				if (null != menu.getRes() && !menu.getRes().trim().equals("")) {
					if (privilegeDao.existsByCode(menu.getRes()) > 0) {
						menuAllInfoEO = menuDao.queryByMenuCode(menu.getRes());
						if (null != menuAllInfoEO) {
							menuAllInfoEO.setMenuName(menu.getName());
							menuAllInfoEO.setParentId(parentId);
							menuAllInfoEO.setIsEnabled(true);
							menuAllInfoEO.setHref(menu.getUrl());
							menuAllInfoEO.setIcon(menu.getIcon());
							menuAllInfoEO.setCreator("1");
							menuAllInfoEO.setCreatorTime(new Date());
							menuAllInfoEO.setSn(sn++);
							menuAllInfoEO.setType("4");
							menuAllInfoEO.setDisplayMode("0");
							menuAllInfoEO.setSource("1");
							updateMenuList.add(menuAllInfoEO);
						} else {
							throw new SysException(String.format(
									"菜单【%s(%s)】在数据库中不存在！", menu.getName(),
									menu.getRes()));
						}
					} else {
						menuAllInfoEO = new MenuAllInfoEO();
						menuAllInfoEO.setMenuId(PKGenerator.generate());
						menuAllInfoEO.setMenuCode(menu.getRes());
						menuAllInfoEO.setPermExpr(menu.getRes());
						menuAllInfoEO.setMenuName(menu.getName());
						menuAllInfoEO.setParentId(parentId);
						menuAllInfoEO.setIsEnabled(true);
						menuAllInfoEO.setHref(menu.getUrl());
						menuAllInfoEO.setIcon(menu.getIcon());
						menuAllInfoEO.setCreator("1");
						menuAllInfoEO.setCreatorTime(new Date());
						menuAllInfoEO.setSn(sn++);
						menuAllInfoEO.setType("4");
						menuAllInfoEO.setDisplayMode("0");
						menuAllInfoEO.setSource("1");
						addMenuList.add(menuAllInfoEO);
					}
				} else {
					throw new SysException("菜单ID为空！");
				}
			}
		}
	}

	/**
	 * 将菜单数据对象转换成权限数据库访问对象
	 * 
	 * @param menuAllInfoEO
	 *            菜单数据对象
	 * @param privilegeEO
	 *            权限数据库访问对象
	 */
	private void copyMenuToPrivilege(MenuAllInfoEO menuAllInfoEO,
			PrivilegeEO privilegeEO) {
		if (null != privilegeEO) {
			privilegeEO.setPrivilegeId(menuAllInfoEO.getMenuId());
			privilegeEO.setPrivilegeName(menuAllInfoEO.getMenuName());
			privilegeEO.setPrivilegeCode(menuAllInfoEO.getMenuCode());
			privilegeEO.setParentId(menuAllInfoEO.getParentId());
			privilegeEO.setPermExpr(menuAllInfoEO.getPermExpr());
			privilegeEO.setType(menuAllInfoEO.getType());
			privilegeEO.setCreator(menuAllInfoEO.getCreator());
			privilegeEO.setCreatorTime(menuAllInfoEO.getCreatorTime());
			privilegeEO.setSn(menuAllInfoEO.getSn());
			privilegeEO.setSource(menuAllInfoEO.getSource());
		}
	}
}
