package com.hbfintech.repay.center.infrastructure.util;

import com.hbfintech.repay.center.domain.repay.object.ModuleProposal;
import com.hbfintech.repay.center.infrastructure.framework.*;

import java.util.function.Consumer;

/**
 * This interface is designed for any classes about repayment process transformation
 *
 * @author Chang Su
 * @since 15/4/2022
 * @see Pipeline
 */
public interface RepayPipeline extends Pipeline {

    /*
     * change before method in enhancement class
     */
    RepayPipeline beforeProxy(Consumer<ModuleProposal> beforeOperation);

    /*
     * switch two operations in flow
     */
    RepayPipeline exchange(OperationType one, OperationType another);

    /*
     * change current operation
     */
    RepayPipeline operationPoxy(OperationType operationType, Operation operation);

    /*
     * change current validation for specific operation
     */
    RepayPipeline validationPoxy(OperationType operationType, Validation validation);

    /*
     * filter specific operations which are not necessary under flow transaction
     */
    <F> RepayPipeline filterPoxy(Filter<F> filter);

    /*
     * change after method in Enhancement class
     */
    RepayPipeline afterProxy(Consumer<ModuleProposal> afterOperation);
}
