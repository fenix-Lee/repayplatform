package com.hbfintech.repay.center.infrastructure.framework;

import com.hbfintech.repay.center.domain.repay.object.ModuleProposal;

@FunctionalInterface
public interface Validation {

    boolean validate(ModuleProposal proposal);
}
