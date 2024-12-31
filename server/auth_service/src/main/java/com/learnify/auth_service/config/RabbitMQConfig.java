package com.learnify.auth_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String QUEUE_NAME = "auth-service-queue";
    private static final String EXCHANGE_NAME = "auth-service";
    private static final String ROUTING_KEY = "routing.key.#";

    // Define a durable queue
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    // Define a topic exchange
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // Bind the queue to the exchange with a routing key
    @Bean
    public Binding bind(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        // Ensure SimpleMessageConverter for plain string messages
        template.setMessageConverter(new SimpleMessageConverter());
        return template;
    }

}
