package com.hbfintech.repay.center.interfaces;

import com.hbfintech.repay.center.domain.repay.entity.Car;
import com.hbfintech.repay.center.domain.repay.entity.CarEntity;
import com.hbfintech.repay.center.domain.repay.entity.RepayFlow;
import com.hbfintech.repay.center.infrastructure.framework.OperationType;
import com.hbfintech.repay.center.domain.repay.entity.Contract;
import com.hbfintech.repay.center.infrastructure.framework.Apply;
import com.hbfintech.repay.center.infrastructure.framework.Filter;
import com.hbfintech.repay.center.infrastructure.framework.Operation;
import com.hbfintech.repay.center.domain.repay.object.Repayment;
import com.hbfintech.repay.center.infrastructure.framework.Indicator;
import com.hbfintech.repay.center.infrastructure.repository.RepayFlowRepository;
import com.hbfintech.repay.center.infrastructure.repository.po.ProductRepayFlowPO;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import com.hbfintech.repay.center.infrastructure.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class RepayInterface {

    @Autowired
    private RepayFlowRepository repository;

    @GetMapping("/test")
    public void getEntity() throws NoSuchFieldException, CloneNotSupportedException {
//        Contract contract = BeanFactory.acquireBean(Contract.class);
//        contract.setContractIndex(34L);
//        RepayFlow flow = RepayFlow.createRepayFlow();
////        flow.startTransaction(null);
//        flow.setContract(contract);
//        flow.transform()
//                .exchange(OperationType.APPLY, OperationType.REPAY)
//                .operationPoxy(OperationType.APPLY, (Apply) repayment -> System.out.println("apply proxy"))
//                .validationPoxy(OperationType.APPLY, repayment -> {System.out.println("apply validation proxy");
//                        return true;})
//                .filterPoxy((Filter<Operation>) (o) -> OperationType.convert(o).equals(OperationType.CALCULATION) ||
//                        OperationType.convert(o).equals(OperationType.RECHARGE))
//                .afterProxy(p -> System.out.println("after proxy"))
//                .commit();
//
//        Repayment repayment = Repayment.createRepayment();
//
//        flow.startTransaction(repayment);
//        System.out.println(".....proxy done");

        ProductRepayFlowPO po = repository.searchRepayFlow(11L);
        System.out.println(po);
    }
}
