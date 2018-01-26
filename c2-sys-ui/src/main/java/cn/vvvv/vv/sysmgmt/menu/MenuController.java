package cn.vvvv.vv.sysmgmt.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinacreator.asp.comp.sys.basic.menu.dao.MenuDao;
import com.chinacreator.asp.comp.sys.basic.menu.dto.MenuDTO;
import com.chinacreator.asp.comp.sys.basic.privilege.service.PrivilegeService;
import com.chinacreator.asp.comp.sys.basic.user.service.UserService;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.sysmgr.menu.M;
import com.chinacreator.c2.sysmgr.menu.MenuService;

/**
 * 
 * 
 * @author
 * 
 */
@Controller
@RequestMapping(value = "menu")
public class MenuController {

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

	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.basic.menu.service.MenuServiceImpl")
	private com.chinacreator.asp.comp.sys.basic.menu.service.MenuService menuService;

	@ResponseBody
	@RequestMapping(value = "/getmenubypermission", method = RequestMethod.GET)

	public Map getMenuByPermission() {
//		MenuService menuService = (MenuService) ApplicationContextManager.getContext().getBean(MenuService.class);
		List<MenuDTO> menus = menuService.queryAllByPermission();
		Map map = new HashMap();
		map.put("result", this.handleMenus(menus));
		return map;
	}

	private List<M> handleMenus(List<MenuDTO> menus) {
		Map<String, ArrayList<M>> tree = new HashMap<String, ArrayList<M>>();
		List<M> roots = new ArrayList<M>();
		for (MenuDTO menu : menus) {
			M m = this.convertMenuToM(menu);
			if ("0".equals(menu.getParentId())) {
				roots.add(m);
			} else {
				if (!tree.containsKey(menu.getParentId())) {
					tree.put(menu.getParentId(), new ArrayList<M>());
				} else {				
					tree.get(menu.getParentId()).add(m);
				}
			}
		}
		
		this.doRoot(roots,tree);
		
		return roots;
	}

	private void doRoot(List<M> roots, Map<String, ArrayList<M>> tree) {
		for(M m : roots) {
			if(tree.containsKey(m.getO())) {
				this.doRoot(tree.get(m.getO()), tree);
				m.setC(tree.get(m.getO()));
			}
		}
	}

	private M convertMenuToM(MenuDTO menu) {
		M m = new M();
		m.setO(menu.getMenuId());
		m.setI(menu.getIcon());
		m.setT(menu.getMenuName());
		m.setL(menu.getHref());
		return m;
	}

	@ResponseBody
	@RequestMapping(value = "/create")

	public void create(@RequestBody MenuDTO menuDTO) {
		try {
			menuService.create(menuDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)

	public void update(@RequestBody MenuDTO menuDTO) {
		menuService.update(menuDTO);
	}

	@ResponseBody
	@RequestMapping(value = "/deletebypks")

	public void deleteByPKs(@RequestBody String[] menuIds) {
		menuService.deleteByPKs(menuIds);
	}

	public void deleteByMenuCodes(String... menuCodes) {
		// TODO Auto-generated method stub

	}

	public List<MenuDTO> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MenuDTO> queryAllByPermission() {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<MenuDTO> queryAll(Pageable pageable, Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<MenuDTO> queryAllByPermission(Pageable pageable, Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MenuDTO> queryByMenu(MenuDTO menuDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MenuDTO> queryByMenuAndPermission(MenuDTO menuDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<MenuDTO> queryByMenu(MenuDTO menuDTO, Pageable pageable, Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<MenuDTO> queryByMenuAndPermission(MenuDTO menuDTO, Pageable pageable, Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/getmenubymenuid", method = RequestMethod.GET)

	public MenuDTO queryByPK(@RequestParam("menuId") String menuId) {
		MenuDTO menuDTO = menuService.queryByPK(menuId);
		return menuDTO;
	}

	@ResponseBody
	@RequestMapping(value = "/getmenubymenucode", method = RequestMethod.GET)

	public MenuDTO queryByMenuCode(@RequestParam("menuCode") String menuCode) {
		MenuDTO menuDTO = menuService.queryByMenuCode(menuCode);
		return menuDTO;
	}

	public List<MenuDTO> queryParents(String menuId, boolean recursively) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<MenuDTO> queryParents(String menuId, boolean recursively, Pageable pageable, Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MenuDTO> queryChildren(String menuId, boolean recursively) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MenuDTO> queryChildrenByPermission(String menuId, boolean recursively) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<MenuDTO> queryChildren(String menuId, boolean isRecursion, Pageable pageable, Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<MenuDTO> queryChildrenByPermission(String menuId, boolean isRecursion, Pageable pageable,
			Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isEnabledByMenuId(String menuId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEnabledByMenuCode(String menuCode) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean existsByMenuCode(String menuCode) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean existsByMenuName(String menuName, String parentId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean existsByMenuNameIgnoreMenuID(String menuName, String menuId) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setOrder(List<MenuDTO> menuDTOList) {
		// TODO Auto-generated method stub

	}

	public boolean isMgtPermitted(String userId, String menuId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean existsChildMenus(String menuId) {
		// TODO Auto-generated method stub
		return false;
	}

}
