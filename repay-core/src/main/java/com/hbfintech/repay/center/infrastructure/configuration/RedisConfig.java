package com.hbfintech.repay.center.infrastructure.configuration;

import com.hbfintech.repay.center.infrastructure.properties.RedisProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class RedisConfig {

    @Resource
    private RedisProperties properties;

    @Bean
    public RedissonClient getRedisClient() {
        Config config = new Config();
        String host = "redis://" + properties.getHost() + ":" + properties.getPort();
        config.useSingleServer()
                .setAddress(host)
                .setPassword(properties.getPassword())
                .setTimeout(properties.getTimeout())
                .setConnectionMinimumIdleSize(properties.getMaxIdle())
                .setConnectionPoolSize(properties.getMaxActive())
                .setDatabase(properties.getDatabase());
        return Redisson.create(config);
    }
}
