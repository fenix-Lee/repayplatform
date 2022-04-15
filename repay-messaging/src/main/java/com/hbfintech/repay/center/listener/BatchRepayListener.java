package com.hbfintech.repay.center.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BatchRepayListener {

    @RabbitListener(queues = "")
    public void message(Message message) {
        System.out.println("message: " + message);
    }
}
