package com.hbfintech.repay.center.infrastructure.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

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
    public static<T> T copyObject(T source) {
        try {
            return (T)BeanMapper.mapping(source, source.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
