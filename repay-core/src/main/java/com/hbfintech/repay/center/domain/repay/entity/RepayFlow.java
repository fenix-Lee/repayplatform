package com.hbfintech.repay.center.domain.repay.entity;

import com.google.common.collect.Maps;
import com.hbfintech.repay.center.domain.repay.object.OperationType;
import com.hbfintech.repay.center.domain.repay.service.factory.FintechDomainDefaultProcedureFactory;
import com.hbfintech.repay.center.domain.repay.service.factory.FintechDomainDefaultValidationFactory;
import com.hbfintech.repay.center.domain.repay.service.factory.FintechFactory;
import com.hbfintech.repay.center.domain.Filter;
import com.hbfintech.repay.center.infrastructure.framework.Module;
import com.hbfintech.repay.center.domain.Operation;
import com.hbfintech.repay.center.domain.Validation;
import com.hbfintech.repay.center.infrastructure.framework.OverrideClone;
import com.hbfintech.repay.center.infrastructure.framework.Entity;
import com.hbfintech.repay.center.infrastructure.repository.po.ProductRepayFlowPO;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import com.hbfintech.repay.center.infrastructure.util.BeanMapper;
import com.hbfintech.repay.center.infrastructure.util.ObjectConverter;
import com.hbfintech.repay.center.infrastructure.util.Pipeline;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Chang Su
 * @since 4/03/2022
 * @see RepayFlowStream
 * @see Procedure
 * @see Validation
 * @see Contract
 */
@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@OverrideClone
public class RepayFlow extends Flow<Procedure, Operation>
        implements ObjectConverter<RepayFlow, ProductRepayFlowPO> {

    // root
    private Contract contract;

    public void init() {
        setProcedures(FintechFactory.INSTANCE
                .getFactoryInstance(FintechDomainDefaultProcedureFactory.class)
                .manufacture());
        setValidationMap(FintechFactory.INSTANCE
                .getFactoryInstance(FintechDomainDefaultValidationFactory.class)
                .fabricate());
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

    public Optional<Contract> getOptionalContract() {
        return Optional.ofNullable(contract);
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
                throw new RuntimeException("flow state is: " + hook.getState() + " and filter can only reset once");
            }
            return this;
         }
    }
}
