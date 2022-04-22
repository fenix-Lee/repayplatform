package com.hbfintech.repay.center.domain.repay.service;

import com.hbfintech.repay.center.domain.repay.entity.Contract;
import com.hbfintech.repay.center.domain.repay.entity.RepayFlow;
import com.hbfintech.repay.center.domain.repay.object.ModuleProposal;
import com.hbfintech.repay.center.domain.repay.object.Repayment;
import com.hbfintech.repay.center.domain.repay.service.factory.FintechDomainDefaultProcedureFactory;
import com.hbfintech.repay.center.infrastructure.framework.Apply;
import com.hbfintech.repay.center.infrastructure.framework.Filter;
import com.hbfintech.repay.center.infrastructure.framework.Operation;
import com.hbfintech.repay.center.infrastructure.framework.OperationType;
import com.hbfintech.repay.center.infrastructure.repository.RepayFlowRepository;
import com.hbfintech.repay.center.infrastructure.repository.po.ProductRepayFlowPO;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import com.hbfintech.repay.center.infrastructure.util.ObjectConverter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class FintechDomainService {

    @Resource
    private RepayFlowRepository repository;

//    public RepayFlow findRepayFlow(long id) {
//        Optional<ProductRepayFlowPO> repayFlowPO = repository.searchRepayFlow(id);
//        return repayFlowPO.map(ObjectConverter::transit)
//                .orElse(null);
//    }

    public void repay() {
        Contract contract = Contract.create();
        contract.setContractIndex(34L);

        RepayFlow flow = RepayFlow.createRepayFlow();
        flow.setApplyId(2);
        flow.setSerialNo("cucumber");
        flow.setContract(contract);
        flow.transform()
                .beforeProxy(repayment -> {
                    System.out.println("start before operation");
                    int index = repository.store(flow.transit());
                    if (index < 1) {
                        repayment.reset(ModuleProposal.FLOW_FAIL_STATE);
                    }
                }).validationPoxy(OperationType.APPLY, repayment -> {System.out.println("apply validation proxy");
                    return true;})
                .filterPoxy((Filter<Operation>) (o) -> OperationType.convert(o).equals(OperationType.CALCULATION) ||
                        OperationType.convert(o).equals(OperationType.RECHARGE))
                .afterProxy(p -> System.out.println("after proxy"))
                .commit();

        Repayment repayment = Repayment.createRepayment();

        flow.startTransaction(repayment);
    }

}

