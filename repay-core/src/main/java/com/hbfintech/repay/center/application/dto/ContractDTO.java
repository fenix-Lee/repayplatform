package com.hbfintech.repay.center.application.dto;

import com.hbfintech.repay.center.domain.root.Contract;
import com.hbfintech.repay.center.infrastructure.util.ObjectConverter;
import lombok.Data;

@Data
public class ContractDTO implements ObjectConverter<ContractDTO, Contract> {

    private Long contractIndex;

    @Override
    public Contract transit(ContractDTO data) {
        return null;
    }
}
