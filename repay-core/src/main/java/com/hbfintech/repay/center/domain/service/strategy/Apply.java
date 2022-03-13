package com.hbfintech.repay.center.domain.service.strategy;

import com.hbfintech.repay.center.domain.object.OperationType;
import com.hbfintech.repay.center.infrastructure.annotation.Indicator;

@Indicator(OperationType.Sequence.APPLY)
public interface Apply extends Operation {

}
