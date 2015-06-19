package com.chinacreator.asp.comp.sys.common;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * 自定义Spring注解bean的命名策略工具类 <br>
 * 自定义Spring注解默认的bean命名策略修改为 类的全路径<br>
 * 
 * @author 彭盛
 */
public class SimpleAnnotationBeanNameGenerator extends
        AnnotationBeanNameGenerator {

    @Override
    protected String buildDefaultBeanName(BeanDefinition definition) {
        return definition.getBeanClassName();
    }
}
