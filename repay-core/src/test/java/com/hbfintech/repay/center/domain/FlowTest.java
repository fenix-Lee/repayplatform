package com.hbfintech.repay.center.domain;

import com.hbfintech.repay.center.infrastructure.repository.RepayFlowRepository;
import com.hbfintech.repay.center.infrastructure.repository.po.ProductRepayFlowPO;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FlowTest {

    @Test
    public void test() {
        RepayFlowRepository repository = BeanFactory.acquireBean(RepayFlowRepository.class);
        Optional<ProductRepayFlowPO> po = repository.searchRepayFlow(1);
        Assert.assertEquals("568745896", po.map(ProductRepayFlowPO::getSerialNo).orElse("null"));
        RBucket<String> bucket = repository.testClient("test");
        Assert.assertNull(bucket);
        repository.testMQ("test");
    }
}
