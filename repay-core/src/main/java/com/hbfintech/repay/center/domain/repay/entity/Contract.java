package com.hbfintech.repay.center.domain.repay.entity;

import com.hbfintech.repay.center.infrastructure.framework.Entity;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import lombok.Data;;

@Entity
@Data
public class Contract implements Cloneable {

    private Long contractIndex;

    public static Contract create(Long contractIndex) {
        // query contract in repository
        // if nothing in flow table
        Contract copy = BeanFactory.getObjectCopy(Contract.class);
        copy.setContractIndex(contractIndex);
        return copy;
    }

    public void setContractIndex(Long index) {
        this.contractIndex = index;
    }

    @Override
    public Contract clone() throws CloneNotSupportedException {
       return (Contract) super.clone();
    }
}
