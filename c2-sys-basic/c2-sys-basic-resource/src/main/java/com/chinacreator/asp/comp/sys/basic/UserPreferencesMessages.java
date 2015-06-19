package com.chinacreator.asp.comp.sys.basic;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class UserPreferencesMessages {

	private static final String BUNDLE_NAME = "com.chinacreator.asp.comp.sys.basic.userPreferencesMessages"; 

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private UserPreferencesMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
