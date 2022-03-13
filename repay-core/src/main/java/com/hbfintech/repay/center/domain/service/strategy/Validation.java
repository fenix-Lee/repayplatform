package com.hbfintech.repay.center.domain.service.strategy;

import com.hbfintech.repay.center.domain.object.Repayment;

@FunctionalInterface
public interface Validation {

    boolean validate(Repayment repayment);
}
