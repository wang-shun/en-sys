package com.chinacreator.asp.comp.sys.datautil.ide.manager;

import java.util.List;

import org.springframework.stereotype.Component;

import com.chinacreator.c2.res.ResourceManager;
import com.chinacreator.c2.web.util.PermResourceReader;
//import com.chinacreator.platform.mvc.menu.Menus;
//import com.chinacreator.platform.mvc.menu.Module;
//import com.chinacreator.platform.mvc.perm.PRSet;
//import com.chinacreator.platform.mvc.perm.Resource;

/**
 * IDE闁瑰灝绉崇紞鏂款啅閵夈儱寰旂紒顐嫹
 * 
 * @author 鐟滈偊鍘惧ú锟�
 * 
 */

public class IDEOperUtil {

	/**
	 * 闁兼儳鍢茶ぐ鍢擠E閻犙冨缁噣宕氬Δ鍕╋拷锟�
	 * 
	 * @return IDE閻犙冨缁噣宕氬Δ鍕╋拷锟�
	 */
	public List<Object> getIDEResourceList() {
		return null;
//		PRSet set = PermResourceReader.getPRSet();
//		return set.getResources().getResource();
	}

	/**
	 * 闁兼儳鍢茶ぐ鍢擠E闁兼寧绮屽畷鐔煎礆濡ゅ嫨锟斤拷
	 * 
	 * @return IDE闁兼寧绮屽畷鐔煎礆濡ゅ嫨锟斤拷
	 */
	public List<Object> getIDEModuleList() {
		return null;
//		Menus menus = ResourceManager.getInstance().getContent(
//				ResourceManager.SYS_FILE_MENU, Menus.class);
//		return menus.getModule();
	}
}
