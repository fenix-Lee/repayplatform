package com.hbfintech.repay.center.domain.entity;

import com.google.common.collect.Maps;
import com.hbfintech.repay.center.domain.object.OperationType;
import com.hbfintech.repay.center.domain.root.Contract;
import com.hbfintech.repay.center.domain.service.Procedure;
import com.hbfintech.repay.center.domain.service.factory.FintechDomainDefaultProcedureFactory;
import com.hbfintech.repay.center.domain.service.factory.FintechFactory;
import com.hbfintech.repay.center.domain.service.strategy.Filter;
import com.hbfintech.repay.center.domain.service.strategy.Module;
import com.hbfintech.repay.center.domain.service.strategy.Operation;
import com.hbfintech.repay.center.domain.service.strategy.Validation;
import com.hbfintech.repay.center.infrastructure.annotation.Cloneable;
import com.hbfintech.repay.center.infrastructure.annotation.Entity;
import com.hbfintech.repay.center.infrastructure.repository.po.ProductRepayFlowPO;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import com.hbfintech.repay.center.infrastructure.util.BeanMapper;
import com.hbfintech.repay.center.infrastructure.util.ObjectConverter;
import com.hbfintech.repay.center.infrastructure.util.Pipeline;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Cloneable
public class RepayFlow extends Flow<Procedure, Operation>
        implements ObjectConverter<RepayFlow, ProductRepayFlowPO> {

    // aggregation root
    private Contract contract;

    public void init() {
        setProcedures(FintechFactory.INSTANCE
                .getFactoryInstance(FintechDomainDefaultProcedureFactory.class)
                .manufacture());
    }

    public static RepayFlow createRepayFlow() {
        return BeanFactory.getObjectCopy(RepayFlow.class);
    }

    public RepayFlowStream transform() {
        if (getState().equals(State.UNDER))
            throw new RuntimeException("repay flow is on invalid state....");
        setState(State.UNDER);
        return new RepayFlowStream(this);
    }

    @Override
    public ProductRepayFlowPO transit(RepayFlow data) {
        return BeanMapper.mapping(data, ProductRepayFlowPO.class);
    }

    @Override
    public RepayFlow clone() throws CloneNotSupportedException {
        RepayFlow copy = (RepayFlow)super.clone();
        copy.init();
        return copy;
    }

    public static class RepayFlowStream implements Pipeline {
        private final Map<OperationType, Validation> validationMap = Maps.newHashMap();

        private final Map<OperationType, Operation> operationMap = Maps.newHashMap();

        private int filterModCount = 0;

        private final Flow<Procedure, Operation> hook;

        public RepayFlowStream(Flow<Procedure, Operation> outer) {
            this.hook = outer;
        }

        @Override
        public void commit() {
            if (hook.getState().equals(State.UNDER)) {
                hook.updateValidation(validationMap);
                hook.updateOperation(operationMap);
                hook.setState(State.COMMIT);
            }
        }

        @Override
        public Pipeline operationPoxy(OperationType operationType, Module operation) {
            if (hook.getState().equals(State.UNDER)) {
                operationMap.put(operationType, (Operation) operation);
            }
            return this;
        }

        @Override
        public Pipeline validationPoxy(OperationType operationType, Validation validation) {
            if (hook.getState().equals(State.UNDER))
                validationMap.put(operationType, validation);
            return this;
        }

         @Override
         @SuppressWarnings("unchecked")
         public <O> Pipeline filterPoxy(Filter<O> filter) {
            if (hook.getState().equals(State.UNDER) && filterModCount == 0) {
                hook.setFilter((Filter<Operation>) filter);
                filterModCount ++;
            } else {
                throw new RuntimeException("filter operation can only reset once");
            }
            return this;
         }
    }
}
