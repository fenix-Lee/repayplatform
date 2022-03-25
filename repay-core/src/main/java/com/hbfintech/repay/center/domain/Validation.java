package com.hbfintech.repay.center.domain;

import com.hbfintech.repay.center.domain.repay.object.ModuleProposal;

@FunctionalInterface
public interface Validation {

    boolean validate(ModuleProposal proposal);
}
