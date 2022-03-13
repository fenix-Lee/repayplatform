package com.hbfintech.repay.center.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Autowired
    private RabbitMQServerConfig rabbitMQServerConfig;

    @Value("${batchExecutor.core.pool.size}")
    private int corePoolSize;

    @Value("${batchExecutor.max.pool.size}")
    private int maxPoolSize;

    @Value("${batchExecutor.queue.capacity}")
    private int queueCapacity;

    @Bean
    public ConnectionFactory confirmConnection(RabbitMQServerConfig rabbitMQServerConfig) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(rabbitMQServerConfig.getHost());
        cachingConnectionFactory.setPort(rabbitMQServerConfig.getPort());
        cachingConnectionFactory.setUsername(rabbitMQServerConfig.getUsername());
        cachingConnectionFactory.setPassword(rabbitMQServerConfig.getPassword());
        cachingConnectionFactory.setPublisherConfirms(true);
        cachingConnectionFactory.setPublisherReturns(true);
        cachingConnectionFactory.setRequestedHeartBeat(rabbitMQServerConfig.getHeartbeat());
        return cachingConnectionFactory;
    }

    @Bean
    public ConnectionFactory normalConnection(RabbitMQServerConfig rabbitMQServerConfig) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(rabbitMQServerConfig.getHost());
        cachingConnectionFactory.setPort(rabbitMQServerConfig.getPort());
        cachingConnectionFactory.setUsername(rabbitMQServerConfig.getUsername());
        cachingConnectionFactory.setPassword(rabbitMQServerConfig.getPassword());
        cachingConnectionFactory.setRequestedHeartBeat(rabbitMQServerConfig.getHeartbeat());
        cachingConnectionFactory.setChannelCacheSize(rabbitMQServerConfig.getChannelCacheSize());
        return cachingConnectionFactory;
    }

    @Bean(name = "rabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(@Qualifier("confirmConnection") ConnectionFactory firstConnectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(firstConnectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //factory.setConcurrentConsumers(5);
        //factory.setMaxConcurrentConsumers(5);
        factory.setPrefetchCount(rabbitMQServerConfig.getPrefetchCount());
        factory.setDefaultRequeueRejected(true);
        return factory;
    }

    @Bean(name = "secondMultiListenerContainer")
    public SimpleRabbitListenerContainerFactory secondMultiListenerContainer(@Qualifier("normalConnection") ConnectionFactory secondConnectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(secondConnectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //factory.setConcurrentConsumers(5);
        //factory.setMaxConcurrentConsumers(5);
        factory.setPrefetchCount(rabbitMQServerConfig.getPrefetchCount());
        factory.setDefaultRequeueRejected(true);
        return factory;
    }

    @Bean(name = "threeAutoListenerContainer")
    public SimpleRabbitListenerContainerFactory threeAutoListenerContainer(@Qualifier("normalConnection") ConnectionFactory secondConnectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(secondConnectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        //factory.setConcurrentConsumers(5);
        //factory.setMaxConcurrentConsumers(5);
        factory.setPrefetchCount(rabbitMQServerConfig.getPrefetchCount());
        factory.setDefaultRequeueRejected(true);
        return factory;
    }

    @Bean
    public RabbitTemplate confirmRabbitTemplate(ConnectionFactory confirmConnection) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(confirmConnection);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }

    @Bean
    public RabbitTemplate transationalRabbitTemplate(ConnectionFactory normalConnection) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(normalConnection);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setChannelTransacted(true);
        return rabbitTemplate;
    }

    /**
     * declare rabbit template
     *
     * @param connectionFactory 'confirmable' connection
     * @return rabbit template
     */
    @Bean("pigeonExceptionRabbitTemplate")
    public RabbitTemplate pigeonExceptionRabbitTemplate (
            @Qualifier("normalConnection") ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public RabbitAdmin confirmRabbitAdmin(RabbitTemplate confirmRabbitTemplate) {
        return new RabbitAdmin(confirmRabbitTemplate);
    }

    @Bean
    public RabbitAdmin transationalRabbitAdmin(RabbitTemplate transationalRabbitTemplate) {
        return new RabbitAdmin(transationalRabbitTemplate);
    }
}
