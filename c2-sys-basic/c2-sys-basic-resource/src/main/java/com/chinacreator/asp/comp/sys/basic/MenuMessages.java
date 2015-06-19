package com.chinacreator.asp.comp.sys.basic;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 菜单消息
 * 
 * @author 彭盛
 * 
 */
public class MenuMessages {

    private static final String BUNDLE_NAME = "com.chinacreator.asp.comp.sys.basic.menuMessages";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private MenuMessages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
