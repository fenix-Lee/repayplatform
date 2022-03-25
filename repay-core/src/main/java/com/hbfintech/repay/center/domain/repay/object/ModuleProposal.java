package com.hbfintech.repay.center.domain.repay.object;

public abstract class ModuleProposal {

    public static final boolean MODULE_SUCCESS_STATE = Boolean.TRUE;

    public static final boolean MODULE_FAIL_STATE = Boolean.FALSE;

    protected boolean moduleState;

    public boolean validateState() {
        return moduleState;
    }

    public void reset(boolean state) {
        moduleState = state;
    }
}
