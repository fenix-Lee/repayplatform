package com.hbfintech.repay.center.infrastructure.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbfintech.repay.center.infrastructure.properties.RedisProperties;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;

@Configuration
public class RedisConfig {

    @Resource
    private RedisProperties properties;

//    @Bean
//    public RedissonClient getRedisClient() {
//        Config config = new Config();
//        String host = "redis://" + properties.getHost() + ":" + properties.getPort();
//        config.useSingleServer()
//                .setAddress(host)
//                .setPassword(properties.getPassword())
//                .setTimeout(properties.getTimeOut())
//                .setConnectionMinimumIdleSize(properties.getMaxIdle())
//                .setConnectionPoolSize(properties.getMaxActive())
//                .setDatabase(properties.getDatabase());
//        return Redisson.create(config);
//    }
}
