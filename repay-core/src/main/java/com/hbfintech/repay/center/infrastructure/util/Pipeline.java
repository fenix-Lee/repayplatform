package com.hbfintech.repay.center.infrastructure.util;

import com.hbfintech.repay.center.domain.repay.object.OperationType;
import com.hbfintech.repay.center.domain.repay.service.strategy.Filter;
import com.hbfintech.repay.center.domain.repay.service.strategy.Module;
import com.hbfintech.repay.center.domain.repay.service.strategy.Validation;

public interface Pipeline {

    Pipeline operationPoxy(OperationType operationType, Module operation);

    Pipeline validationPoxy(OperationType operationType, Validation validation);

    <M> Pipeline filterPoxy(Filter<M> filter);

    void commit();
}
