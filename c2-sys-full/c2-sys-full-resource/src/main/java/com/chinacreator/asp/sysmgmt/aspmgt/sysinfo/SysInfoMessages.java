package com.chinacreator.asp.sysmgmt.aspmgt.sysinfo;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 我的面板消息
 * 
 * @author 彭盛
 * 
 */
public class SysInfoMessages {

    private static final String BUNDLE_NAME = "com.chinacreator.asp.sysmgmt.aspmgt.sysinfo.sysInfoMessages";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private SysInfoMessages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
