package com.hbfintech.repay.center.domain.repay.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hbfintech.repay.center.domain.repay.service.factory.DomainFactory;
import com.hbfintech.repay.center.infrastructure.framework.EnhancementType;
import com.hbfintech.repay.center.domain.repay.object.ModuleProposal;
import com.hbfintech.repay.center.infrastructure.framework.OperationType;
import com.hbfintech.repay.center.domain.repay.service.factory.FintechFactory;
import com.hbfintech.repay.center.infrastructure.framework.Filter;
import com.hbfintech.repay.center.infrastructure.framework.Operation;
import com.hbfintech.repay.center.infrastructure.framework.Validation;
import com.hbfintech.repay.center.infrastructure.framework.OverrideClone;
import com.hbfintech.repay.center.infrastructure.framework.Entity;
import com.hbfintech.repay.center.infrastructure.repository.RepayFlowRepository;
import com.hbfintech.repay.center.infrastructure.repository.po.ProductRepayFlowPO;
import com.hbfintech.repay.center.infrastructure.util.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * {@code  RepayFlow} controls the process of repayment in RepayCenter. This is the main entity for entire
 * business service including offline-mode repayment.
 *
 * The key feature of this class is {@code RepayFlowStream}, which comes from the ideal of Stream API in
 * Java 8, by using transform method.
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
        implements ObjectConverter<ProductRepayFlowPO> {

    // id
    private String serial;

    private Contract contract;

    private RepayFlowRepository repository;

    public RepayFlow (RepayFlowRepository repository) {
        this.repository = repository;
    }

    public void init() {
        setProcedures(FintechFactory.INSTANCE.getProcedureFactory().manufacture());
        setValidationMap(FintechFactory.INSTANCE.getValidationFactory().fabricate());
    }

    public static RepayFlow createRepayFlow() {
        return BeanFactory.getObjectCopy(RepayFlow.class);
    }

    public static RepayFlow createRepayFlow(DomainFactory<Procedure> factory) {
        RepayFlow copy = createRepayFlow();
        copy.setProcedures(factory.manufacture());
        return copy;
    }

    public RepayFlowStream transform() {
        if (getState().equals(State.UNDER))
            throw new RuntimeException("repay flow is on invalid state....");
        setState(State.UNDER);
        return new RepayFlowStream(this);
    }

    @Override
    public ProductRepayFlowPO transit() {
        return BeanMapper.mapping(this, ProductRepayFlowPO.class);
    }

    @Override
    public void save() {
        repository.store(transit());
    }

    @Override
    public RepayFlow clone() throws CloneNotSupportedException {
        RepayFlow copy = (RepayFlow)super.clone();
        copy.init();
        return copy;
    }

    /**
     * New feature of transformation for validating, operating and exchanging modules by following methods
     *
     * need to commit in the end to close-cycle this stream
     */
    public static class RepayFlowStream implements RepayPipeline {

        private final Map<OperationType, Validation> validationMap = Maps.newHashMap();

        private final Map<OperationType, Operation> operationMap = Maps.newHashMap();

        private final List<Pair<OperationType>> exchanges = Lists.newArrayList();

        private final Map<EnhancementType, Consumer<ModuleProposal>> enhancementMap = Maps.newHashMap();

        private int filterModCount = 0;

        private final Flow<Procedure, Operation> hook;

        public RepayFlowStream(Flow<Procedure, Operation> outer) {
            this.hook = outer;
        }

        /*
         * This is important and must be declared in the end of the stream
         */
        @Override
        public void commit() {
            if (hook.getState().equals(State.UNDER)) {
                hook.exchangeOperation(exchanges);
                hook.updateValidation(validationMap);
                hook.updateOperation(operationMap);
                hook.updateEnhancement(enhancementMap);
                hook.setState(State.COMMIT);
            }
        }

        @Override
        public RepayPipeline beforeProxy(Consumer<ModuleProposal> beforeOperation) {
            if (hook.getState().equals(State.UNDER)) {
                enhancementMap.put(EnhancementType.BEFORE, beforeOperation);
            } else {
                throw new RuntimeException("flow state is: " + hook.getState());
            }
            return this;
        }

        @Override
        public RepayPipeline exchange(OperationType one, OperationType another) {
            if (hook.getState().equals(State.UNDER)) {
                Pair<OperationType> pair = new Pair<>(one, another);
                exchanges.add(pair);
            } else {
                throw new RuntimeException("flow state is: " + hook.getState());
            }
            return this;
        }

        @Override
        public RepayPipeline operationPoxy(OperationType operationType, Operation operation) {
            if (hook.getState().equals(State.UNDER)) {
                operationMap.put(operationType, operation);
            } else {
                throw new RuntimeException("flow state is: " + hook.getState());
            }
            return this;
        }

        @Override
        public RepayPipeline validationPoxy(OperationType operationType, Validation validation) {
            if (hook.getState().equals(State.UNDER))
                validationMap.put(operationType, validation);
            else
                throw new RuntimeException("flow state is: " + hook.getState());
            return this;
        }

         @Override
         @SuppressWarnings("unchecked")
         public <O> RepayPipeline filterPoxy(Filter<O> filter) {
            if (hook.getState().equals(State.UNDER) && filterModCount == 0) {
                hook.setFilter((Filter<Operation>) filter);
                filterModCount ++;
            } else {
                throw new RuntimeException("flow state is: " + hook.getState() + " and filter can only reset once");
            }
            return this;
         }

        @Override
        public RepayPipeline afterProxy(Consumer<ModuleProposal> afterOperation) {
            if (hook.getState().equals(State.UNDER)) {
                enhancementMap.put(EnhancementType.AFTER, afterOperation);
            } else {
                throw new RuntimeException("flow state is: " + hook.getState());
            }
            return this;
        }
    }
}
