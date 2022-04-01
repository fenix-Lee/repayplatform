package com.hbfintech.repay.center.infrastructure.repository.po;

import com.hbfintech.repay.center.domain.repay.entity.Contract;
import com.hbfintech.repay.center.infrastructure.util.BeanMapper;
import com.hbfintech.repay.center.infrastructure.util.ObjectConverter;

public class ContractPO extends BasePO implements ObjectConverter<Contract> {

    @Override
    public void transit(Contract target) {
        BeanMapper.mapping(this, target);
    }
}
