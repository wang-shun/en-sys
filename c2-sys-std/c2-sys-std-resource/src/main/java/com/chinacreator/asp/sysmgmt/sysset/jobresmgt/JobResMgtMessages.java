package com.chinacreator.asp.sysmgmt.sysset.jobresmgt;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 岗位资源管理消息
 * 
 * @author 彭盛
 * 
 */
public class JobResMgtMessages {

	private static final String BUNDLE_NAME = "com.chinacreator.asp.sysmgmt.sysset.jobresmgt.jobResMgtMessages";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private JobResMgtMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
