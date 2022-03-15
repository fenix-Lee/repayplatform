package com.hbfintech.repay.center.domain.repay.service.factory;

import com.hbfintech.repay.center.domain.repay.object.Repayment;
import com.hbfintech.repay.center.domain.repay.service.Procedure;
import com.hbfintech.repay.center.domain.repay.service.strategy.*;
import com.hbfintech.repay.center.infrastructure.annotation.Chain;
import com.hbfintech.repay.center.infrastructure.annotation.Chains;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FintechDomainDefaultProcedureFactory
        extends AbstractDomainChain<Operation> implements DomainFactory<Procedure> {

    @Override
    public Class<FintechDomainDefaultProcedureFactory> getChainClassType() {
        return FintechDomainDefaultProcedureFactory.class;
    }

    @Override
    public List<Procedure> manufacture() {
        return getModules().stream()
                .map(o -> new Procedure(){{setModule(o);}})
                .collect(Collectors.toList());
    }

    @Component
    @Chains(
            @Chain(chain = FintechDomainDefaultProcedureFactory.class, sequence = 1)
    )
    public static class DefaultApply implements Apply {

        @Override
        public void handle(Repayment repayment) {
            System.out.println(".....apply.....");
        }

        @Override
        public DefaultApply clone() throws CloneNotSupportedException {
            return (DefaultApply)super.clone();
        }
    }

    @Component
    @Chains(
            @Chain(chain = FintechDomainDefaultProcedureFactory.class, sequence = 2)
    )
    public static class DefaultCalculation implements Calculation {

        @Override
        public void handle(Repayment repayment) {
            System.out.println(".....calculation......");
        }

        @Override
        public DefaultCalculation clone() throws CloneNotSupportedException{
            return (DefaultCalculation)super.clone();
        }
    }

    @Component
    @Chains(
            @Chain(chain = FintechDomainDefaultProcedureFactory.class, sequence = 3)
    )
    public static class DefaultRecharge implements Recharge {

        @Override
        public void handle(Repayment repayment) {
            System.out.println("....recharge.....");
        }

        @Override
        public DefaultRecharge clone() throws CloneNotSupportedException{
            return (DefaultRecharge)super.clone();
        }
    }

    @Component
    @Chains(
            @Chain(chain = FintechDomainDefaultProcedureFactory.class, sequence = 4)
    )
    public static class DefaultRepay implements Repay {

        @Override
        public void handle(Repayment repayment) {
            System.out.println("......repay......");
        }

        @Override
        public DefaultRepay clone() throws CloneNotSupportedException{
            return (DefaultRepay)super.clone();
        }
    }

    @Component
    public static class DefaultEnhancement implements Enhancement {

        @Override
        public void beforeOperation(Repayment repayment) {
            System.out.println(".....before.....");
        }

        @Override
        public void afterOperation(Repayment repayment) {
            System.out.println(".....after......");
        }
    }
}
