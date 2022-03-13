package com.hbfintech.repay.center.infrastructure.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BeanFactory implements ApplicationContextAware {

    private static ApplicationContext context;

    private static final List<Class<?>> cloneableClazz = new ArrayList<>();

    public static <T> T acquireBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static Object acquireBean(String name) {
        return context.getBean(name);
    }

    public static <T> T getObjectCopy(Class<T> clazz) {
        return copyObject(acquireBean(clazz));
    }

    @SuppressWarnings("unchecked")
    public static<T> T getObjectCopy(Class<T> clazz, String beanName) {
        if (context.isTypeMatch(beanName, clazz))
            return copyObject((T)acquireBean(beanName));
        return null;
    }

    @SuppressWarnings("unchecked")
    public static<T> T copyObject(T source) {
        try {
            return (T)BeanMapper.mapping(source, source.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            return (T)source.getClass().getDeclaredConstructor().newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    public static void addCloneableClazz(Class<?> clazz) {
        if (cloneableClazz.contains(clazz))
            return;
        cloneableClazz.add(clazz);
    }

    public static Class<?>[] getCloneableClass() {
        return cloneableClazz.toArray(new Class<?>[0]);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }
}
