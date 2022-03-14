package com.hbfintech.repay.center.domain.repay.service.strategy;

import com.hbfintech.repay.center.domain.repay.object.Repayment;

public interface Enhancement {

    void beforeOperation(Repayment repayment);

    void afterOperation(Repayment repayment);
}
