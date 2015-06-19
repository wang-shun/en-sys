package com.chinacreator.asp.comp.sys.common;

import java.util.Locale;
import java.util.UUID;

/**
 * 数据库主键生成工具类 <br>
 * 
 * @author 彭盛
 */
public class PKGenerator {

    /**
     * 随机生成UUID（去除"-"符号）
     * 
     * @return UUID
     */
    public synchronized final static String generate() {
        String s = UUID.randomUUID().toString().toUpperCase(Locale.US);
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
                + s.substring(19, 23) + s.substring(24);
    }
}
