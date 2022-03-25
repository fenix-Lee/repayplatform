package com.hbfintech.repay.center.infrastructure.util;

import com.hbfintech.repay.center.domain.repay.object.OperationType;
import com.hbfintech.repay.center.domain.Filter;
import com.hbfintech.repay.center.infrastructure.framework.Module;
import com.hbfintech.repay.center.domain.Validation;

public interface Pipeline {

    Pipeline exchange(OperationType one, OperationType another);

    Pipeline operationPoxy(OperationType operationType, Module operation);

    Pipeline validationPoxy(OperationType operationType, Validation validation);

    <M> Pipeline filterPoxy(Filter<M> filter);

    void commit();
}
