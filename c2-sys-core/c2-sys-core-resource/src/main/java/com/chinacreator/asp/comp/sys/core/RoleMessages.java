package com.chinacreator.asp.comp.sys.core;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 角色消息
 * 
 * @author 彭盛
 * 
 */
public class RoleMessages {

    private static final String BUNDLE_NAME = "com.chinacreator.asp.comp.sys.core.roleMessages";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private RoleMessages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
