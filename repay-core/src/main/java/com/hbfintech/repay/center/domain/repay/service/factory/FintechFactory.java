package com.hbfintech.repay.center.domain.repay.service.factory;

import com.hbfintech.repay.center.infrastructure.util.BeanFactory;

public enum FintechFactory {

    INSTANCE;

    public <T> T getFactoryInstance(Class<T> clazz) {
        return BeanFactory.acquireBean(clazz);
    }

    public FintechDomainDefaultProcedureFactory getProcedureFactory() {
        return getFactoryInstance(FintechDomainDefaultProcedureFactory.class);
    }

    public FintechDomainDefaultValidationFactory getValidationFactory() {
        return getFactoryInstance(FintechDomainDefaultValidationFactory.class);
    }
}
