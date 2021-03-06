package com.chinacreator.asp.comp.sys.core;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 用户消息
 * 
 * @author 彭盛
 * 
 */
public class UserMessages {

    private static final String BUNDLE_NAME = "com.chinacreator.asp.comp.sys.core.userMessages";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private UserMessages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
