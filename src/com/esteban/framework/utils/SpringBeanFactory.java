package com.esteban.framework.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

/**
 * SpringBeanFactory 手动获取
 *
 */
public class SpringBeanFactory {
    private static BeanFactory bf = null;

    private SpringBeanFactory() {
    }

    public static <T> T getBean(String name, Class<T> clazz) throws BeansException {
        return bf.getBean(name, clazz);
    }

    public static Object getBean(String name) throws BeansException {
        return bf.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return bf.getBean(requiredType);
    }

    public static boolean containsBean(String name) {
        return bf.containsBean(name);
    }

    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return bf.isSingleton(name);
    }

    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return bf.getType(name);
    }

    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return bf.getAliases(name);
    }

    public static BeanFactory getBf() {
        return bf;
    }

    public static void setBf(BeanFactory b) {
        bf = b;
    }
}
