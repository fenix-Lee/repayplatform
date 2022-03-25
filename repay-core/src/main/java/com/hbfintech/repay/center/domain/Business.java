package com.hbfintech.repay.center.domain;

import com.hbfintech.repay.center.domain.repay.object.ModuleProposal;

public interface Business extends Cloneable{

    void startTransaction(ModuleProposal proposal);
}
