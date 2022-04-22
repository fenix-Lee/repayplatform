package com.hbfintech.repay.center.domain;

import com.hbfintech.repay.center.infrastructure.repository.RepayFlowRepository;
import com.hbfintech.repay.center.infrastructure.repository.po.ProductRepayFlowPO;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import com.hbfintech.repay.center.interfaces.RepayInterface;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepayTest {

    @Test
    public void interfaceTest() {
        RepayInterface controller = (RepayInterface) BeanFactory.acquireBean("repayInterface");
        controller.getEntity();
    }

    @Test
    public void test() {
        RepayFlowRepository repository = BeanFactory.acquireBean(RepayFlowRepository.class);
        Optional<ProductRepayFlowPO> po = repository.searchRepayFlow(2);
        Assert.assertFalse(po.isPresent());
    }
}
