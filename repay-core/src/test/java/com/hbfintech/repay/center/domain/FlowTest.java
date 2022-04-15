package com.hbfintech.repay.center.domain;

import com.hbfintech.repay.center.infrastructure.repository.RepayFlowRepository;
import com.hbfintech.repay.center.infrastructure.repository.po.ProductRepayFlowPO;
import com.hbfintech.repay.center.infrastructure.util.BeanFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.redisson.api.RBucket;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Optional;

public class FlowTest extends BaseTest {

    @Test
    public void test() {
        RepayFlowRepository repository = BeanFactory.acquireBean(RepayFlowRepository.class);
        Optional<ProductRepayFlowPO> po = repository.searchRepayFlow(1);
        Assert.assertEquals("568745896", po.map(ProductRepayFlowPO::getSerialNo).orElse("null"));
//        RedissonClient client = BeanFactory.acquireBean(RedissonClient.class);
//        repository.setClient(client);
//        Field field = ReflectionUtils.findField(repository.getClass(), "client");
//        assert field != null;
//        ReflectionUtils.makeAccessible(field);
//        ReflectionUtils.setField(field, repository, client);
        RBucket<String> bucket = repository.testClient("test");
        Assert.assertNull(bucket);

//        repository.setTemplate(template);
        repository.testMQ("test");
    }
}
