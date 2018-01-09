package com.chinacreator.asp.sysmgmt.sysset.resmgt;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 资源管理消息
 * 
 * @author 彭盛
 * 
 */
public class ResMgtMessages {

	private static final String BUNDLE_NAME = "com.chinacreator.asp.sysmgmt.sysset.resmgt.resMgtMessages";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private ResMgtMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
