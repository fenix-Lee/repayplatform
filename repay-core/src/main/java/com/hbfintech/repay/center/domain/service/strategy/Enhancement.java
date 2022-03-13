package com.hbfintech.repay.center.domain.service.strategy;

import com.hbfintech.repay.center.domain.object.Repayment;

public interface Enhancement {

    void beforeOperation(Repayment repayment);

    void afterOperation(Repayment repayment);
}
