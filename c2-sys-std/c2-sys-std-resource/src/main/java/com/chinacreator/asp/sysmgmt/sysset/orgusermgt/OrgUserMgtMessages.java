package com.chinacreator.asp.sysmgmt.sysset.orgusermgt;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 机构用户管理消息
 * 
 * @author 彭盛
 * 
 */
public class OrgUserMgtMessages {

	private static final String BUNDLE_NAME = "com.chinacreator.asp.sysmgmt.sysset.orgusermgt.orgUserMgtMessages";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private OrgUserMgtMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
