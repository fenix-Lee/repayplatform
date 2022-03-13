package com.hbfintech.repay.center.domain.service.strategy;

import com.hbfintech.repay.center.domain.object.OperationType;
import com.hbfintech.repay.center.infrastructure.annotation.Indicator;

@Indicator(OperationType.Sequence.RECHARGE)
public interface Recharge extends Operation {

}
