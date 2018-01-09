package com.chinacreator.c2.sysmgr.menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chinacreator.asp.comp.sys.basic.menu.dto.MenuDTO;
import com.chinacreator.c2.ioc.ApplicationContextManager;

/**
 * 菜单接口实现
 * 
 * @author 彭盛
 * 
 */
public class SysMgrMenuServiceImpl implements MenuService {

	@Override
	public List<M> getMenuByPermission() {
		List<M> menuList = new ArrayList<M>();

		List<MenuDTO> menuDTOList = getMenuService().queryAllByPermission();

		reMenu("0", menuList, null, menuDTOList);

		return menuList;
	}

	private void reMenu(String menuId, List<M> menuList, M parentM,
			List<MenuDTO> menuDTOList) {
		for (MenuDTO menuDTO : menuDTOList) {
			if (menuId.equals(menuDTO.getParentId()) && menuDTO.getIsEnabled()) {
				M m = new M();
				m.setI(menuDTO.getIcon());				
				m.setT(menuDTO.getMenuName());
				m.setO("2".equals(menuDTO.getDisplayMode()) ? "_blank"
						: "_self");
				String href = menuDTO.getHref();
				if(null!=href && !href.trim().equals("") && "1".equals(menuDTO.getDisplayMode())){
					if(href.startsWith("#/")){
						href = href.substring(2);
					}
					href = "#/template/iframe.jsp?src="+href+"&title="+menuDTO.getMenuName();
				}
				m.setL(href);
				m.setE(menuDTO.getMenuExt());
				if ("0".equals(menuId)) {
					menuList.add(m);
				} else {
					Collection<M> cm = parentM.getC();
					if (null == cm) {
						cm = new ArrayList<M>();
						parentM.setC(cm);
					}
					cm.add(m);
				}
				reMenu(menuDTO.getMenuId(), menuList, m, menuDTOList);
			}
		}
	}

	private com.chinacreator.asp.comp.sys.basic.menu.service.MenuService getMenuService() {
		return ApplicationContextManager
				.getContext()
				.getBean(
						com.chinacreator.asp.comp.sys.basic.menu.service.MenuService.class);
	}
}
