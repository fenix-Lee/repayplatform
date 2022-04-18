package com.hbfintech.repay.center.domain.repay.service;

import com.hbfintech.repay.center.domain.repay.object.ModuleProposal;
import com.hbfintech.repay.center.infrastructure.framework.OperationType;
import com.hbfintech.repay.center.infrastructure.framework.Module;
import com.hbfintech.repay.center.infrastructure.framework.Validation;
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

    public void business(Map<OperationType, Validation> validationMap, ModuleProposal proposal) {
        Validation validation;

        if (ObjectUtils
                .isEmpty((validation=validationMap
                        .get(OperationType.convert(module))))) {
            module.handle(proposal);
        } else if (validation.validate(proposal)) {
            module.handle(proposal);
        } else {
            proposal.reset(ModuleProposal.FLOW_FAIL_STATE);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public AbstractBusiness<T> clone() throws CloneNotSupportedException {
        return (AbstractBusiness<T>) super.clone();
    }
}
