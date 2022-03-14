package com.hbfintech.repay.center.domain.repay.service.strategy;

import com.hbfintech.repay.center.domain.repay.object.OperationType;
import com.hbfintech.repay.center.infrastructure.annotation.Indicator;

@Indicator(OperationType.Sequence.CALCULATION)
public interface Calculation extends Operation {

//    default Object clone() throws CloneNotSupportedException {
//        return null;
//    }
}
