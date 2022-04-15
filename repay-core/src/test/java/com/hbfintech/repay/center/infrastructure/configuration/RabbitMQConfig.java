package com.hbfintech.repay.center.infrastructure.configuration;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@Configuration
public class RabbitMQConfig {

    @Bean
    @Primary
    public RabbitTemplate getTemplate() {
        RabbitTemplate rabbitMock = mock(RabbitTemplate.class);
        doAnswer(invocation -> {
            return null;
        }).when(rabbitMock).convertAndSend(anyString(), anyString(), (Object) any());
        return rabbitMock;
    }
}
