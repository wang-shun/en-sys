package com.chinacreator.asp.sysmgmt.sysset.dictmgt;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 字典管理消息
 * 
 * @author 彭盛
 * 
 */
public class DictMgtMessages {

    private static final String BUNDLE_NAME = "com.chinacreator.asp.sysmgmt.sysset.dictmgt.dictMgtMessages";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private DictMgtMessages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
