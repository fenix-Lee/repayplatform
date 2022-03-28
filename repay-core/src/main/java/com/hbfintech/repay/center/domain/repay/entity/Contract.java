package com.hbfintech.repay.center.domain.repay.entity;

import com.hbfintech.repay.center.infrastructure.framework.Entity;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import com.hbfintech.repay.common.constant.ProductType;
import lombok.Data;;

@Entity
@Data
public class Contract implements Cloneable {

    private Long contractIndex;

    private ProductType productType;

    public static Contract create(Long contractIndex) {
        // query contract in repository
        // if nothing in flow table
        Contract copy = BeanFactory.getObjectCopy(Contract.class);
        copy.setContractIndex(contractIndex);
        return copy;
    }

    @Override
    public Contract clone() throws CloneNotSupportedException {
       return (Contract) super.clone();
    }
}
