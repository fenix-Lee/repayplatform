package com.hbfintech.repay.center.domain.repay.entity;

import com.google.common.collect.Maps;
import com.hbfintech.repay.center.domain.repay.object.OperationType;
import com.hbfintech.repay.center.domain.repay.object.Repayment;
import com.hbfintech.repay.center.domain.repay.service.Procedure;
import com.hbfintech.repay.center.domain.repay.service.factory.FintechDomainDefaultProcedureFactory;
import com.hbfintech.repay.center.domain.repay.service.strategy.*;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Data
public class Flow<P extends Procedure, O extends Operation> implements Business {

    private volatile State state = State.READY;

    private List<P> procedures;

    private Map<OperationType, Validation> validationMap = Maps.newHashMap();

    private Filter<O> filter = (o) -> false;

    private Enhancement enhancement;

    protected enum State {
        READY,
        UNDER,
        COMMIT
    }

    protected void updateValidation(Map<OperationType, Validation> updates) {
        validationMap.putAll(updates);
    }

    protected void updateOperation(Map<OperationType, Operation> updates) {
        procedures.forEach(p -> {
            OperationType type;
            if (updates.containsKey((type=OperationType.convert(p.getModule()))))
                p.setModule((updates.get(type)));
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public void startTransaction(Repayment repayment) {
        // state validation
        if (state.equals(State.UNDER))
            throw new RuntimeException("flow is in invalid state to start transaction....");

        if (ObjectUtils.isEmpty(enhancement))
            enhancement = BeanFactory
                    .acquireBean(FintechDomainDefaultProcedureFactory.DefaultEnhancement.class);

        // before operation
        enhancement.beforeOperation(repayment);

        procedures.stream()
                .filter(p -> !ObjectUtils.isEmpty(p.getModule()) && !filter.accept((O)p.getModule()))
                .forEach(p -> p.business(validationMap, repayment));

        // after operation
        enhancement.afterOperation(repayment);
    }
}
