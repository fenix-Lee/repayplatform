package com.hbfintech.repay.center.infrastructure.configuration;

import com.hbfintech.repay.center.domain.entity.RepayFlow;
import com.hbfintech.repay.center.infrastructure.util.BeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class EntityConfig {

    /**
     * must cast init method before any other beans
     *
     * @return BeanMapper bean
     */
    @Bean(initMethod = "init")
    public BeanMapper getMapper() {
        return new BeanMapper();
    }

    @Bean(initMethod = "init")
    @Primary
    public RepayFlow getFlow() {
        return new RepayFlow();
    }
}
