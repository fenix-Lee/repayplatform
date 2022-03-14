package com.hbfintech.repay.center.domain.repay.service.factory;

import com.google.common.collect.Maps;
import com.hbfintech.repay.center.infrastructure.annotation.Chain;
import com.hbfintech.repay.center.infrastructure.annotation.Chains;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Chang Su
 * @since 3/3/2022
 * @see InitializingBean
 * @param <T>
 */
public abstract class AbstractDomainChain<T extends Cloneable>
        implements InitializingBean, Cloneable {

    @Autowired
    protected ApplicationContext context;

    private List<T> modules;

    public abstract Class<?> getChainClassType();

    /*
     * strongly recommend returning a copy of operations instead of
     * original one
     *
     */
    protected final List<T> getModules() {
        return copyModules();
    }

    protected final List<T> accessModules() {
        return modules;
    }

    private List<T> copyModules() {
        return modules.stream()
                .map(BeanFactory::copyObject)
                .collect(Collectors.toList());
    }

    protected void setModules(List<T> modules) {
        this.modules = modules;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void afterPropertiesSet() {
        Class<?> chainClass = getChainClassType();
        // get all classes with specific annotation
        Map<String, Object> modulesWithChainsMap = context.getBeansWithAnnotation(Chains.class);

        Map<Integer, T> container = Maps.newHashMap();
        modulesWithChainsMap.values().forEach(m -> {
            Class<?> moduleClass = m.getClass();
            Chains chains = AnnotationUtils.findAnnotation(moduleClass, Chains.class);
            assert chains != null;
            Arrays.stream(chains.value()).forEach(c -> {
                if (c.chain().equals(chainClass)) {
                    if (container.containsKey(c.sequence()))
                        throw new RuntimeException("");
                    container.put(c.sequence(), (T)m);
                }
            });
        });

        Map<String, Object> modulesWithChainMap = context.getBeansWithAnnotation(Chain.class);
        modulesWithChainMap.values().forEach(m -> {
            Chain chain = AnnotationUtils.findAnnotation(m.getClass(), Chain.class);
            assert chain != null;
            if (chain.chain().equals(chainClass)) {
                if (container.containsKey(chain.sequence()))
                    throw new RuntimeException();
                container.put(chain.sequence(), (T)m);
            }
        });
        modules = sortModule(container);
    }

    protected List<T> sortModule(Map<Integer, T> container) {
        return container.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public AbstractDomainChain<T> clone() throws CloneNotSupportedException {
        return (AbstractDomainChain<T>)super.clone();
    }
}
