package com.hbfintech.repay.center.infrastructure.util;

import com.hbfintech.repay.center.domain.object.OperationType;
import com.hbfintech.repay.center.domain.service.strategy.Filter;
import com.hbfintech.repay.center.domain.service.strategy.Module;
import com.hbfintech.repay.center.domain.service.strategy.Operation;
import com.hbfintech.repay.center.domain.service.strategy.Validation;

public interface Pipeline {

    Pipeline operationPoxy(OperationType operationType, Module operation);

    Pipeline validationPoxy(OperationType operationType, Validation validation);

    <M> Pipeline filterPoxy(Filter<M> filter);

    void commit();
}
