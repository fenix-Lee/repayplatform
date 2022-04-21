package com.hbfintech.repay.center.infrastructure.repository.po;

import com.hbfintech.repay.center.domain.repay.entity.RepayFlow;
import com.hbfintech.repay.center.infrastructure.util.BeanMapper;
import com.hbfintech.repay.center.infrastructure.util.ObjectConverter;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ProductRepayFlowPO extends BasePO implements ObjectConverter<RepayFlow> {

    private long applyId;

    private String serialNo;

    @Override
    public void transit(RepayFlow target) {
        BeanMapper.mapping(this, target);
    }
}
