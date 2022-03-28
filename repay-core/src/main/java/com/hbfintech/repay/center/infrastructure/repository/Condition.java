package com.hbfintech.repay.center.infrastructure.repository;

import com.google.common.collect.Maps;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * @author Chang Su
 * @since 25/03/2022
 */
@Component
@Getter
public class Condition implements Cloneable{

    private final Map<String, Object> parameters = Maps.newHashMap();

    public static Condition createCondition() {
        return BeanFactory.getObjectCopy(Condition.class);
    }

    public Condition addParameter(String field, Object value) {
        parameters.put(field, value);
        return this;
    }

    @Override
    public Condition clone() throws CloneNotSupportedException {
        return (Condition) super.clone();
    }
}
