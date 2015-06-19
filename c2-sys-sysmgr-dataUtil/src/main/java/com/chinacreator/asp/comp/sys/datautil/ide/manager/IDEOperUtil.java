package com.chinacreator.asp.comp.sys.datautil.ide.manager;

import java.util.List;

import org.springframework.stereotype.Component;

import com.chinacreator.c2.res.ResourceManager;
import com.chinacreator.c2.web.util.PermResourceReader;
import com.chinacreator.platform.mvc.menu.Menus;
import com.chinacreator.platform.mvc.menu.Module;
import com.chinacreator.platform.mvc.perm.PRSet;
import com.chinacreator.platform.mvc.perm.Resource;

/**
 * IDE操作工具类
 * 
 * @author 彭盛
 * 
 */
@Component
public class IDEOperUtil {

	/**
	 * 获取IDE资源列表
	 * 
	 * @return IDE资源列表
	 */
	public List<Resource> getIDEResourceList() {
		PRSet set = PermResourceReader.getPRSet();
		return set.getResources().getResource();
	}

	/**
	 * 获取IDE菜单列表
	 * 
	 * @return IDE菜单列表
	 */
	public List<Module> getIDEModuleList() {
		Menus menus = ResourceManager.getInstance().getContent(
				ResourceManager.SYS_FILE_MENU, Menus.class);
		return menus.getModule();
	}
}
