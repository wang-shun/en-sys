package com.chinacreator.asp.sysmgmt.sysset.orgmgt;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 机构管理消息
 * 
 * @author 彭盛
 * 
 */
public class OrgMgtMessages {

	private static final String BUNDLE_NAME = "com.chinacreator.asp.sysmgmt.sysset.orgmgt.orgMgtMessages";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private OrgMgtMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
