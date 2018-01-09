package com.chinacreator.asp.sysmgmt.sysset.usermgt;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 用户管理消息
 * 
 * @author 彭盛
 * 
 */
public class UserMgtMessages {

	private static final String BUNDLE_NAME = "com.chinacreator.asp.sysmgmt.sysset.usermgt.userMgtMessages";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private UserMgtMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
