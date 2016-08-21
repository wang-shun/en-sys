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
//import com.chinacreator.platform.mvc.menu.Menu;
//import com.chinacreator.platform.mvc.menu.Module;
//import com.chinacreator.platform.mvc.perm.Resource;

/**
 * IDE鍚屾鍒扮郴缁熺鐞嗗伐鍏风被
 * 
 * @author 褰洓
 * 
 */

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
	 * 鍚屾IDE涓殑璧勬簮鍒扮郴缁熺鐞�
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
	 * 鑾峰彇鍚屾璧勬簮淇℃伅
	 * 
	 * @param addPrivilegeList
	 *            闇�瑕佹柊澧炵殑璧勬簮
	 * @param updatePrivilegeList
	 *            闇�瑕佷慨鏀圭殑璧勬簮
	 * @param delPrivilegeList
	 *            闇�瑕佸垹闄ょ殑璧勬簮
	 */
	public void getSynchroResInfo(List<PrivilegeEO> addPrivilegeList,
			List<PrivilegeEO> updatePrivilegeList,
			List<PrivilegeEO> delPrivilegeList) {
/*		List<Resource> resList = ideOperUtil.getIDEResourceList();
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
									"璧勬簮銆�%s(%s)銆戝湪鏁版嵁搴撲腑涓嶅瓨鍦紒", resource.getName(),
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
		}*/
	}

	private String getPrivilegeNameByResName(String resId, String resName) {
		if (null != resName && !resName.trim().equals("")) {
			return resName;
		} else {
			throw new SysException(String.format("璧勬簮銆�%s銆戝悕绉颁负绌猴紒", resId));
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
				throw new SysException(String.format("璧勬簮銆�%s銆戠被鍨嬨��%s銆戞棤娉曡瘑鍒紒",
						resId, resType));
			}
		} else {
			throw new SysException(String.format("璧勬簮銆�%s銆戠被鍨嬩负绌猴紒", resId));
		}
	}

	/**
	 * 鍚屾IDE涓殑鑿滃崟鍒扮郴缁熺鐞�
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
	 * 鑾峰彇鍚屾鑿滃崟淇℃伅
	 * 
	 * @param addMenuList
	 *            闇�瑕佹柊澧炵殑鑿滃崟
	 * @param updateMenuList
	 *            闇�瑕佷慨鏀圭殑鑿滃崟
	 * @param delMenuList
	 *            闇�瑕佸垹闄ょ殑鑿滃崟
	 */
	public void getSynchroMenuInfo(List<MenuAllInfoEO> addMenuList,
			List<MenuAllInfoEO> updateMenuList, List<MenuAllInfoEO> delMenuList) {
/*		List<Module> menuList = ideOperUtil.getIDEModuleList();
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
		}*/
	}

/*	private void reModules(List<Module> moduleList,
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
									"鑿滃崟銆�%s(%s)銆戝湪鏁版嵁搴撲腑涓嶅瓨鍦紒", module.getName(),
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
					throw new SysException("鑿滃崟ID涓虹┖锛�");
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
									"鑿滃崟銆�%s(%s)銆戝湪鏁版嵁搴撲腑涓嶅瓨鍦紒", menu.getName(),
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
					throw new SysException("鑿滃崟ID涓虹┖锛�");
				}
			}
		}
	}*/

	/**
	 * 灏嗚彍鍗曟暟鎹璞¤浆鎹㈡垚鏉冮檺鏁版嵁搴撹闂璞�
	 * 
	 * @param menuAllInfoEO
	 *            鑿滃崟鏁版嵁瀵硅薄
	 * @param privilegeEO
	 *            鏉冮檺鏁版嵁搴撹闂璞�
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
