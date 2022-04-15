package com.hbfintech.repay.center.infrastructure.configuration;

import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
public class RedisConfig {

    @Bean
    @Primary
    public RedissonClient getClient() {
        RedissonClient mock = mock(RedissonClient.class);
        when(mock.getBucket(any())).thenReturn(null);
        when(mock.getLock(anyString())).thenReturn(null);
        return mock;
    }
}
