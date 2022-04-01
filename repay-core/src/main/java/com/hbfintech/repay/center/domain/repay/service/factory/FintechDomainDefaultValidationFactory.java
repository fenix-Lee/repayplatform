package com.hbfintech.repay.center.domain.repay.service.factory;

import com.hbfintech.repay.center.domain.repay.object.ModuleProposal;
import com.hbfintech.repay.center.infrastructure.framework.OperationType;
import com.hbfintech.repay.center.domain.repay.service.AbstractValidation;
import com.hbfintech.repay.center.infrastructure.framework.Validation;
import com.hbfintech.repay.center.infrastructure.framework.Chain;
import com.hbfintech.repay.center.infrastructure.framework.Chains;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class FintechDomainDefaultValidationFactory
        extends AbstractDomainChain<AbstractValidation> implements DomainFactory<Map<OperationType, Validation>> {

    @Override
    public Class<?> getChainClassType() {
        return FintechDomainDefaultValidationFactory.class;
    }

    @Override
    public Map<OperationType, Validation> fabricate() {
        return getModules().stream()
                .collect(Collectors.toMap(AbstractValidation::getOperationType,
                        m -> (Validation) m, (k1, k2) -> k1));
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        List<AbstractValidation> validations = accessModules();
        Class<?> clazz = getChainClassType();
        validations.forEach(v -> {
            Chains chains = AnnotationUtils.findAnnotation(v.getClass(), Chains.class);
            if (ObjectUtils.isEmpty(chains)) {
                Chain chain = AnnotationUtils.findAnnotation(v.getClass(), Chain.class);
                if (ObjectUtils.isEmpty(chain) || ObjectUtils.isEmpty(chain.chain()))
                    throw new RuntimeException("");
                if (chain.chain().equals(clazz))
                    v.setOperationType(OperationType.convert(chain.sequence()));
                return;
            }

            Arrays.stream(chains.value()).forEach(c -> {
                if (c.chain().equals(clazz))
                    v.setOperationType(OperationType.convert(c.sequence()));
            });
        });
        setModules(validations);
    }

//    @Chains(
            @Chain(chain = FintechDomainDefaultValidationFactory.class, sequence = OperationType.Sequence.APPLY)
//    )
    @Component
    public static class DefaultApplyValidation
            extends AbstractValidation implements Validation {

        @Override
        public boolean validate(ModuleProposal proposal) {
            proposal.reset(ModuleProposal.FLOW_SUCCESS_STATE);
            return proposal.validateState();
        }
    }

    @Chains(
            @Chain(chain = FintechDomainDefaultValidationFactory.class, sequence = OperationType.Sequence.CALCULATION)
    )
    public static class DefaultCalculationValidation
            extends AbstractValidation implements Validation {

        @Override
        public boolean validate(ModuleProposal proposal) {
            return true;
        }
    }

    @Chains(
            @Chain(chain = FintechDomainDefaultValidationFactory.class, sequence = OperationType.Sequence.RECHARGE)
    )
    public static class DefaultRechargeValidation extends AbstractValidation implements Validation {

        @Override
        public boolean validate(ModuleProposal proposal) {
            return true;
        }
    }

    @Chains(
            @Chain(chain = FintechDomainDefaultValidationFactory.class, sequence = OperationType.Sequence.REPAY)
    )
    @Component
    public static class DefaultRepayValidation extends AbstractValidation implements Validation {

        @Override
        public boolean validate(ModuleProposal proposal) {
            System.out.println("repay validation");
            return true;
        }
    }
}
