package com.hbfintech.repay.center.domain.service;

import com.hbfintech.repay.center.domain.object.OperationType;

public abstract class AbstractValidation implements Cloneable {

    private OperationType operationType;

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    @Override
    public AbstractValidation clone() throws CloneNotSupportedException{
        return (AbstractValidation)super.clone();
    }
}
