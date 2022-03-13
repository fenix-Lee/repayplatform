package com.hbfintech.repay.center.domain.service.strategy;

import com.hbfintech.repay.center.domain.object.Repayment;

public interface Business extends Cloneable{

    void startTransaction(Repayment repayment);
}
