package com.chinacreator.asp.sysmgmt.sysset.jobmenumgt;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 岗位菜单管理消息
 * 
 * @author 彭盛
 * 
 */
public class JobMenuMgtMessages {

	private static final String BUNDLE_NAME = "com.chinacreator.asp.sysmgmt.sysset.jobmenumgt.jobMenuMgtMessages";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private JobMenuMgtMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
