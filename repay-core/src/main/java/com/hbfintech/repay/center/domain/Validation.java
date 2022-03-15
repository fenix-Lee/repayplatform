package com.hbfintech.repay.center.domain;

import com.hbfintech.repay.center.domain.repay.object.Repayment;

@FunctionalInterface
public interface Validation {

    boolean validate(Repayment repayment);
}
