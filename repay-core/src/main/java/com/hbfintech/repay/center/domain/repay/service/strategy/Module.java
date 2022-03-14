package com.hbfintech.repay.center.domain.repay.service.strategy;

import com.hbfintech.repay.center.domain.repay.object.Repayment;

@FunctionalInterface
public interface Module extends Cloneable {

    void handle(Repayment repayment);
}
