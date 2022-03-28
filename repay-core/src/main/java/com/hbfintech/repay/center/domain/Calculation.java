package com.hbfintech.repay.center.domain;

import com.hbfintech.repay.center.infrastructure.framework.Indicator;

@Indicator(OperationType.Sequence.CALCULATION)
public interface Calculation extends Operation {

//    default Object clone() throws CloneNotSupportedException {
//        return null;
//    }
}
