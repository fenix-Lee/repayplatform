package com.hbfintech.repay.center.domain.repay.object;

import com.hbfintech.repay.center.domain.repay.service.strategy.Module;
import com.hbfintech.repay.center.infrastructure.annotation.Indicator;
import org.springframework.core.annotation.AnnotationUtils;

public enum OperationType {
    APPLY(1),
    CALCULATION(2),
    RECHARGE(3),
    REPAY(4);

    private final int sequence;

    OperationType(int sequence) {
        this.sequence = sequence;
    }

    public static OperationType convert(int sequence) {
        OperationType[] types = values();
        if (sequence < 0)
            throw new RuntimeException("");
        for (OperationType type : types) {
            if (type.sequence == sequence)
                return type;
        }
        return null;
    }

    public static OperationType convert(Module operation) {
        assert operation != null;
        Indicator indicator = AnnotationUtils.findAnnotation(operation.getClass(), Indicator.class);
        assert indicator != null;
        return convert(indicator.value());
    }

    public interface Sequence {
        int APPLY = 1;
        int CALCULATION = 2;
        int RECHARGE = 3;
        int REPAY = 4;
    }
}
