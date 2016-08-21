package com.chinacreator.asp.comp.sys.datautil.ide.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinacreator.asp.comp.sys.basic.menu.eo.MenuAllInfoEO;
import com.chinacreator.asp.comp.sys.datautil.ide.manager.SynchroSysMgtUtil;


public class IdeSynSysMgt {

	@Autowired
	private SynchroSysMgtUtil synchroSysMgtUtil;

	private static Map<String, MenuAllInfoEO> map = new HashMap<String, MenuAllInfoEO>();

	public MenuAllInfoEO getMenuByMenuRes(String menuRes) {
		MenuAllInfoEO menuAllInfoEO = new MenuAllInfoEO();
		if (null != menuRes && !menuRes.trim().equals("")) {
			if (map.isEmpty()) {
				refreshMenu();
			}
			if (null != map.get(menuRes)) {
				menuAllInfoEO = map.get(menuRes);
			}
		}
		return menuAllInfoEO;
	}

	public void refreshMenu() {
		List<MenuAllInfoEO> addMenuList = new ArrayList<MenuAllInfoEO>();
		List<MenuAllInfoEO> updateMenuList = new ArrayList<MenuAllInfoEO>();
		List<MenuAllInfoEO> delMenuList = new ArrayList<MenuAllInfoEO>();
		synchroSysMgtUtil.getSynchroMenuInfo(addMenuList, updateMenuList,
				delMenuList);
		for (MenuAllInfoEO menuAllInfoEO : addMenuList) {
			map.put(menuAllInfoEO.getMenuCode(), menuAllInfoEO);
		}
		for (MenuAllInfoEO menuAllInfoEO : updateMenuList) {
			map.put(menuAllInfoEO.getMenuCode(), menuAllInfoEO);
		}
		for (MenuAllInfoEO menuAllInfoEO : delMenuList) {
			map.put(menuAllInfoEO.getMenuCode(), menuAllInfoEO);
		}
	}

}
