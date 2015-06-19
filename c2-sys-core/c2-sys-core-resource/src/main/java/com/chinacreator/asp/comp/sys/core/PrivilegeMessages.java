package com.chinacreator.asp.comp.sys.core;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 权限消息
 * 
 * @author 彭盛
 * 
 */
public class PrivilegeMessages {

    private static final String BUNDLE_NAME = "com.chinacreator.asp.comp.sys.core.privilegeMessages";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private PrivilegeMessages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
