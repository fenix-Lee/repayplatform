package com.hbfintech.repay.center.domain.repay.entity;

import com.hbfintech.repay.center.domain.repay.service.AbstractBusiness;
import com.hbfintech.repay.center.domain.Operation;
import com.hbfintech.repay.center.infrastructure.framework.OverrideClone;
import com.hbfintech.repay.center.infrastructure.framework.Entity;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@OverrideClone
public class Procedure extends AbstractBusiness<Operation> {

    @Override
    public Procedure clone() throws CloneNotSupportedException {
        Procedure procedureCopy = (Procedure) super.clone();
        if (makeAccessible(procedureCopy.getClass(), "module"))
            procedureCopy.setModule(BeanFactory.copyObject(getModule()));
        return procedureCopy;
    }
}
