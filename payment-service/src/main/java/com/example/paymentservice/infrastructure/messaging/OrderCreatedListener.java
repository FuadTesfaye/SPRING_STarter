package com.example.paymentservice.infrastructure.messaging;
import com.example.paymentservice.application.dto.OrderCreatedMessage;
import com.example.paymentservice.application.service.ProcessPaymentUseCase;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {
    private final ProcessPaymentUseCase useCase;
    public OrderCreatedListener(ProcessPaymentUseCase u) { this.useCase = u; }
    @RabbitListener(queues = "payment.queue")
    public void onMessage(OrderCreatedMessage msg) {
        useCase.handle(msg);
    }
}
