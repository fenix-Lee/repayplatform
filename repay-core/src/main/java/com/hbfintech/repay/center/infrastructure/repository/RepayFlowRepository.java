package com.hbfintech.repay.center.infrastructure.repository;


import com.hbfintech.repay.center.infrastructure.annotation.Repository;
import com.hbfintech.repay.center.infrastructure.repository.shardingdao.ProductRepayFlowDao;
import com.hbfintech.repay.center.infrastructure.repository.po.ProductRepayFlowPO;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

@Repository
public class RepayFlowRepository extends
        BaseRepository<ProductRepayFlowDao, ProductRepayFlowPO> {

    @Resource
    private RedissonClient redisClient;

    @Resource
    private RabbitTemplate template;

    @Autowired
    public RepayFlowRepository(ProductRepayFlowDao dao) {
        setDao(dao);
    }

    /**
     * find repay flow by primary key
     *
     * @param id primary key
     * @return repay flow
     */
    public Optional<ProductRepayFlowPO> searchRepayFlow(long id) {
        Condition condition = Condition.createCondition()
                .addParameter("id", id)
                .addParameter("create_time", new Date());
        return uniqueQueryWithCondition(condition.getParameters());
    }

    /**
     * find repay flow by serial number
     *
     * @param serial serial number
     * @return repay flow
     */
    public Optional<ProductRepayFlowPO> searchRepayFlow(String serial) {
       Condition condition = Condition.createCondition()
                .addParameter("serial", serial)
                .addParameter("create_time", "2022-4-7");
        return uniqueQueryWithCondition(condition.getParameters());
    }

    public RBucket<String> testClient(String name) {
        return redisClient.getBucket(name);
    }

    public void testMQ(String message) {
        template.convertAndSend("xxx", "xxxx", message);
    }

    public int store(ProductRepayFlowPO productRepayFlowPO) {
        return insert(productRepayFlowPO);
    }

}
