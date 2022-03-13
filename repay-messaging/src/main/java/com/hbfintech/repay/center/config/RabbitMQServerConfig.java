package com.hbfintech.repay.center.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class RabbitMQServerConfig {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.heartbeat}")
    private int heartbeat;

    @Value("${batch.channel.cache.size}")
    private int channelCacheSize;

    @Value("${rabbitmq.pre.fetch.count}")
    private int prefetchCount;
}
