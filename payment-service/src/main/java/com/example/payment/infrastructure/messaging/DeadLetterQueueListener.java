package com.example.payment.infrastructure.messaging;

import com.example.payment.infrastructure.config.DeadLetterQueueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Listener for Dead Letter Queue
 * 
 * Handles messages that failed processing after all retries.
 * These messages need manual inspection and handling.
 */
@Component
public class DeadLetterQueueListener {

    private static final Logger logger = LoggerFactory.getLogger(DeadLetterQueueListener.class);

    @RabbitListener(queues = DeadLetterQueueConfig.DLQ_QUEUE)
    public void handleDeadLetterMessage(String message) {
        logger.error("❌ DEAD LETTER QUEUE: Message failed after all retries: {}", message);
        logger.error("⚠️  ACTION REQUIRED: Manual inspection and handling needed for this message");
        
        // TODO: Implement alerting mechanism (email, Slack, etc.)
        // TODO: Store in database for manual review
        // TODO: Implement compensation logic if needed
    }
}
