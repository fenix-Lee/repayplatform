package com.hbfintech.repay.center.domain.repay.service;

import com.hbfintech.repay.center.domain.repay.object.ModuleProposal;
import com.hbfintech.repay.center.infrastructure.framework.OperationType;
import com.hbfintech.repay.center.infrastructure.framework.Module;
import com.hbfintech.repay.center.infrastructure.framework.Validation;
import lombok.Data;
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
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public abstract void business(Validation validation, ModuleProposal proposal);

    @Override
    @SuppressWarnings("unchecked")
    public AbstractBusiness<T> clone() throws CloneNotSupportedException {
        return (AbstractBusiness<T>) super.clone();
    }
}
