package com.chinacreator.asp.comp.sys.basic;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class SysMgrAfterLoginSpiImplMessages {

	private static final String BUNDLE_NAME = "com.chinacreator.asp.comp.sys.basic.sysMgrAfterLoginSpiImplMessages";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private SysMgrAfterLoginSpiImplMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
