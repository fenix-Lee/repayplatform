package com.hbfintech.repay.center.domain.repay.entity;

import com.hbfintech.repay.center.infrastructure.framework.Entity;
import com.hbfintech.repay.center.infrastructure.repository.po.ContractPO;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import com.hbfintech.repay.common.constant.ProductType;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Data
public class Contract implements Cloneable {

    private long contractIndex;

    private ProductType productType;

    public static Contract create() {
        // query contract in repository
        // if nothing in flow table
        return BeanFactory.getObjectCopy(Contract.class);
    }

    @Override
    public Contract clone() throws CloneNotSupportedException {
       return (Contract) super.clone();
    }
}
