package com.hbfintech.repay.center.infrastructure.framework;

import com.hbfintech.repay.center.domain.repay.object.ModuleProposal;

/**
 *
 * @author Chang Su
 * @since 3/03/2022
 */
public interface Enhancement {

    void before(ModuleProposal proposal);

    void after(ModuleProposal proposal);
}
