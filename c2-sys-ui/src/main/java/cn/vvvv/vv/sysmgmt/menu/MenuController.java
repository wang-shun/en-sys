package cn.vvvv.vv.sysmgmt.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinacreator.asp.comp.sys.basic.menu.dto.MenuDTO;
import com.chinacreator.asp.comp.sys.basic.menu.service.MenuService;

@RestController
@RequestMapping(value = "menu")
public class MenuController {

	@Autowired
	MenuService menuService;

	@RequestMapping(value = "queryAllByPermission")
	public List<MenuDTO> queryAllByPermission() {
		List<MenuDTO> menus = menuService.queryAllByPermission();
		return menus;
	}
}
