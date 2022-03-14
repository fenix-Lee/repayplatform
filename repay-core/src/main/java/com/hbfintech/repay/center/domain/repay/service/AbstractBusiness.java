package com.hbfintech.repay.center.domain.repay.service;

import com.hbfintech.repay.center.domain.repay.object.OperationType;
import com.hbfintech.repay.center.domain.repay.object.Repayment;
import com.hbfintech.repay.center.domain.repay.service.strategy.Module;
import com.hbfintech.repay.center.domain.repay.service.strategy.Validation;
import lombok.Data;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.util.Map;

@Data
public abstract class AbstractBusiness<T extends Module> implements Cloneable {

    private T module;

    protected boolean makeAccessible(Class<?> clazz, String fieldName) {
        try {
            ReflectionUtils.makeAccessible(clazz.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            System.out.println("");
            return false;
        }
        return true;
    }

    public void business(Map<OperationType, Validation> validationMap, Repayment repayment) {
        Validation validation;

        if (ObjectUtils
                .isEmpty((validation=validationMap
                        .get(OperationType.convert(module)))))
            module.handle(repayment);
        else if (validation.validate(repayment))
            module.handle(repayment);
    }

    @Override
    @SuppressWarnings("unchecked")
    public AbstractBusiness<T> clone() throws CloneNotSupportedException {
        return (AbstractBusiness<T>) super.clone();
    }
}
