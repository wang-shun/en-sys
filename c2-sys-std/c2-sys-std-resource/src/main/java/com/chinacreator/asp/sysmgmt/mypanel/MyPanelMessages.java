package com.chinacreator.asp.sysmgmt.mypanel;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 我的面板消息
 * 
 * @author 彭盛
 * 
 */
public class MyPanelMessages {

    private static final String BUNDLE_NAME = "com.chinacreator.asp.sysmgmt.mypanel.myPanelMessages";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private MyPanelMessages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
