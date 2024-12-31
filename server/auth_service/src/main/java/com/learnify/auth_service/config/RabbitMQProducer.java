package com.learnify.auth_service.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // TODO : Implement the MessageDTO class
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend("auth-service", "routing.key", message);
    }

}
