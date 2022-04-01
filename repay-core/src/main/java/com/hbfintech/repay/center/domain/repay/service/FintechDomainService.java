package com.hbfintech.repay.center.domain.repay.service;

import com.hbfintech.repay.center.domain.repay.entity.RepayFlow;
import com.hbfintech.repay.center.infrastructure.repository.RepayFlowRepository;
import com.hbfintech.repay.center.infrastructure.repository.po.ProductRepayFlowPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class FintechDomainService {

    @Autowired
    private RepayFlowRepository repository;

    public RepayFlow findRepayFlow() {
        ProductRepayFlowPO repayFlowPO = repository.searchRepayFlow("");
        if (ObjectUtils.isEmpty(repayFlowPO))
            return null;
        RepayFlow repayFlow = RepayFlow.createRepayFlow();
        repayFlowPO.transit(repayFlow);
        return repayFlow;
    }

}

