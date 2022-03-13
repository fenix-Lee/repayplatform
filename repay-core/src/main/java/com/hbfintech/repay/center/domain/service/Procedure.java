package com.hbfintech.repay.center.domain.service;

import com.hbfintech.repay.center.domain.service.strategy.Operation;
import com.hbfintech.repay.center.infrastructure.annotation.Cloneable;
import com.hbfintech.repay.center.infrastructure.annotation.Entity;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Cloneable
public class Procedure extends AbstractBusiness<Operation> {

    @Override
    public Procedure clone() throws CloneNotSupportedException {
        Procedure procedureCopy = (Procedure) super.clone();
        if (makeAccessible(procedureCopy.getClass(), "module"))
            procedureCopy.setModule(BeanFactory.copyObject(getModule()));
        return procedureCopy;
    }
}
