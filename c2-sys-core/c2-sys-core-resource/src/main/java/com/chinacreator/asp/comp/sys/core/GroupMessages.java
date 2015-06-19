package com.chinacreator.asp.comp.sys.core;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 用户组消息
 * 
 * @author 彭盛
 * 
 */
public class GroupMessages {

    private static final String BUNDLE_NAME = "com.chinacreator.asp.comp.sys.core.groupMessages";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private GroupMessages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
