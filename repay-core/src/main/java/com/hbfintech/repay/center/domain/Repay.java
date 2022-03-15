package com.hbfintech.repay.center.domain;

import com.hbfintech.repay.center.domain.repay.object.OperationType;
import com.hbfintech.repay.center.infrastructure.framework.Indicator;

@Indicator(OperationType.Sequence.REPAY)
public interface Repay extends Operation {

}
