package com.hbfintech.repay.center.domain.repay.service.strategy;

import com.hbfintech.repay.center.domain.repay.object.Repayment;

public interface Business extends Cloneable{

    void startTransaction(Repayment repayment);
}
