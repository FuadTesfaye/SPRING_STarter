package com.example.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.AmqpTemplate;
import org.mockito.Mockito;

@Configuration
public class MockRabbitBroker {

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate mockTemplate = Mockito.mock(RabbitTemplate.class);

        Mockito.doAnswer(invocation -> {
            String queue = invocation.getArgument(0);
            String message = invocation.getArgument(1);
            System.out.println("[MOCK RABBITMQ] Message dropped in " + queue + ": " + message);
            return null;
        }).when(mockTemplate).convertAndSend(Mockito.anyString(), Mockito.anyString());

        return mockTemplate;
    }
}
