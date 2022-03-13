package com.hbfintech.repay.center.domain.service.strategy;

import com.hbfintech.repay.center.domain.object.Repayment;

@FunctionalInterface
public interface Module extends Cloneable {

    void handle(Repayment repayment);
}
