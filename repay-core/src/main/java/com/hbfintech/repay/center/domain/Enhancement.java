package com.hbfintech.repay.center.domain;

import com.hbfintech.repay.center.domain.repay.object.ModuleProposal;

public interface Enhancement {

    void before(ModuleProposal proposal);

    void after(ModuleProposal proposal);
}
