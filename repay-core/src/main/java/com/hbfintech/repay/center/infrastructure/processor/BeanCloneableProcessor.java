package com.hbfintech.repay.center.infrastructure.processor;

import com.hbfintech.repay.center.infrastructure.framework.OverrideClone;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
//@DependsOn("beanMapper")
public class BeanCloneableProcessor implements BeanPostProcessor  {

    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean,
                                                 @NonNull String beanName) throws BeansException {
        OverrideClone overrideClone = AnnotationUtils.findAnnotation(bean.getClass(), OverrideClone.class);
        if (ObjectUtils.isEmpty(overrideClone))
            return bean;
        else if (overrideClone.value())
            BeanFactory.addCloneableClazz(bean.getClass());
        return bean;
    }
}
