package com.hbfintech.repay.center.domain;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BaseTest {

    @Before
    public void setup() {
        // set up mocks
//        RedissonClient mock = mock(RedissonClient.class);
//        when(mock.getBucket(any())).thenReturn(null);
//        when(mock.getLock(anyString())).thenReturn(null);

        // mock mq
//        RabbitTemplate rabbitMock = mock(RabbitTemplate.class);
//        doAnswer(invocation -> {
//            return null;
//        }).when(rabbitMock).convertAndSend(anyString(), anyString(), (Object) any());
    }
}
