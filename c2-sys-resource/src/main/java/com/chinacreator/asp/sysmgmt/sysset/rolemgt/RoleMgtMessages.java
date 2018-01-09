package com.chinacreator.asp.sysmgmt.sysset.rolemgt;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 角色管理消息
 * 
 * @author 彭盛
 * 
 */
public class RoleMgtMessages {

	private static final String BUNDLE_NAME = "com.chinacreator.asp.sysmgmt.sysset.rolemgt.roleMgtMessages";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private RoleMgtMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
