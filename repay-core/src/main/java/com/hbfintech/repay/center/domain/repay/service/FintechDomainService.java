package com.hbfintech.repay.center.domain.repay.service;

import com.hbfintech.repay.center.domain.repay.entity.RepayFlow;
import com.hbfintech.repay.center.infrastructure.repository.RepayFlowRepository;
import com.hbfintech.repay.center.infrastructure.repository.po.ProductRepayFlowPO;
import com.hbfintech.repay.center.infrastructure.util.ObjectConverter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class FintechDomainService {

    @Resource
    private RepayFlowRepository repository;

    public RepayFlow findRepayFlow() {
        Optional<ProductRepayFlowPO> repayFlowPO = repository.searchRepayFlow("");
        return repayFlowPO.map(ObjectConverter::transit)
                .orElse(null);
    }

}

