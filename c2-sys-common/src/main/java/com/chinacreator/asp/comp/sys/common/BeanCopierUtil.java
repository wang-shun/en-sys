package com.chinacreator.asp.comp.sys.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.beans.BeanCopier;

import com.chinacreator.asp.comp.sys.common.exception.SysException;
import com.chinacreator.c2.dao.mybatis.enhance.Page;

/**
 * 数据bean转换工具类 <br>
 * 
 * @author 彭盛
 */
public class BeanCopierUtil {

    /**
     * 将源类型转换为目标实体类型
     * 
     * @param <S>
     *            源类型
     * @param <T>
     *            目标类型
     * @param source
     *            源类型对象
     * @param target
     *            目标类型对象
     * @param s
     *            源类型
     * @param t
     *            目标类型
     * 
     */
    public static <S, T> void copy(S source, T target) {
        if (source == null || target == null) {
            return;
        }
        BeanCopier copier = BeanCopier.create(source.getClass(),
                target.getClass(), false);
        copier.copy(source, target, null);
    }

    /**
     * 将源类型集合转换为目标实体类型集合
     * 
     * @param <S>
     *            源类型
     * @param <T>
     *            目标类型
     * @param source
     *            源类型集合对象
     * @param target
     *            目标类型集合对象
     * @param s
     *            源类型
     * @param t
     *            目标类型
     * 
     */
    public static <S, T> void copy(List<S> source, List<T> target, Class s,
            Class t) {
        if (source == null || target == null) {
            return;
        }
        BeanCopier copier = BeanCopier.create(s, t, false);
        for (S sbean : source) {
            try {
                T tbean = (T) t.newInstance();
                copier.copy(sbean, tbean, null);
                target.add(tbean);
            } catch (InstantiationException e) {
                e.printStackTrace();
                throw new SysException("无法实例化类！", e);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new SysException("指定类无法访问！", e);
            }
        }
    }

    /**
     * 将源分页类型转换成目标的分页类型
     * 
     * @param <S>
     *            源类型
     * @param <T>
     *            目标类型
     * @param source
     *            源类型分页对象
     * @param s
     *            源类型
     * @param t
     *            目标类型
     * @return Page<T> 目标类型的分页对象
     */
    public static <S, T> Page<T> copyPage(Page<S> source, Class s, Class t) {
        if (source == null) {
            throw new SysException("源分页对象为空！");
        }
        BeanCopier copier = BeanCopier.create(s, t, false);
        List<S> sourceL = source.getContents();
        List<T> targetL = new ArrayList<T>();
        for (S sbean : sourceL) {
            try {
                T tbean = (T) t.newInstance();
                copier.copy(sbean, tbean, null);
                targetL.add(tbean);
            } catch (InstantiationException e) {
                e.printStackTrace();
                throw new SysException("无法实例化类！", e);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new SysException("指定类无法访问！", e);
            }
        }
        return new Page<T>(source.getPageIndex(), source.getPageSize(),
                source.getTotal(), targetL);
    }

    /**
     * 将源类型集合转换为新目标实体类型集合
     * 
     * @param <S>
     *            源类型
     * @param <T>
     *            目标类型
     * @param source
     *            源类型集合对象
     * @param s
     *            源类型
     * @param t
     *            目标类型
     * @return 新目标实体类型集合
     */
    public static <S, T> List<T> copy(List<S> source, Class s, Class t) {
        List<T> target = new ArrayList<T>();
        if (source == null) {
            return target;
        }
        BeanCopier copier = BeanCopier.create(s, t, false);
        for (S sbean : source) {
            try {
                T tbean = (T) t.newInstance();
                copier.copy(sbean, tbean, null);
                target.add(tbean);
            } catch (InstantiationException e) {
                e.printStackTrace();
                throw new SysException("无法实例化类！", e);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new SysException("指定类无法访问！", e);
            }
        }
        return target;
    }
}
