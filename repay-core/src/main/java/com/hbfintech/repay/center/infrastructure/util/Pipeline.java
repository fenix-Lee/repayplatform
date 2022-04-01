package com.hbfintech.repay.center.infrastructure.util;

import com.hbfintech.repay.center.domain.repay.object.ModuleProposal;
import com.hbfintech.repay.center.infrastructure.framework.OperationType;
import com.hbfintech.repay.center.infrastructure.framework.Filter;
import com.hbfintech.repay.center.infrastructure.framework.Module;
import com.hbfintech.repay.center.infrastructure.framework.Validation;

import java.util.function.Consumer;

public interface Pipeline {

    Pipeline beforeProxy(Consumer<ModuleProposal> beforeOperation);

    Pipeline exchange(OperationType one, OperationType another);

    Pipeline operationPoxy(OperationType operationType, Module operation);

    Pipeline validationPoxy(OperationType operationType, Validation validation);

    <F> Pipeline filterPoxy(Filter<F> filter);

    Pipeline afterProxy(Consumer<ModuleProposal> afterOperation);

    void commit();
}
