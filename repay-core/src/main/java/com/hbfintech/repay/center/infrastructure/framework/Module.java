package com.hbfintech.repay.center.infrastructure.framework;

import com.hbfintech.repay.center.domain.repay.object.ModuleProposal;

/**
 *
 * @author Chang Su
 * @since 5/03/2022
 */
@FunctionalInterface
public interface Module extends Cloneable {

    void handle(ModuleProposal proposal);
}
