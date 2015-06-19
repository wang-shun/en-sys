package com.chinacreator.asp.comp.sys.advanced;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class JobMessages {

    private static final String BUNDLE_NAME = "com.chinacreator.asp.comp.sys.advanced.jobMessages";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private JobMessages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }


}
