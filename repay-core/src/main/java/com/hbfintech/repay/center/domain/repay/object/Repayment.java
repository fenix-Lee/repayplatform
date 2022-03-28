package com.hbfintech.repay.center.domain.repay.object;

import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import org.springframework.stereotype.Component;

@Component
public class Repayment extends ModuleProposal {

    public static Repayment createRepayment() {
        return BeanFactory.getObjectCopy(Repayment.class);
    }
}
