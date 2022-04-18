package com.hbfintech.repay.center.domain;

import com.hbfintech.repay.center.infrastructure.repository.RepayFlowRepository;
import com.hbfintech.repay.center.infrastructure.repository.po.ProductRepayFlowPO;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class RepayTest extends BaseTest {

    @Test
    public void test() {
        RepayFlowRepository repository = BeanFactory.acquireBean(RepayFlowRepository.class);
        Optional<ProductRepayFlowPO> po = repository.searchRepayFlow(2);
        Assert.assertFalse(po.isPresent());
    }
}
