package com.company.payment.application.usecase;

import com.company.payment.domain.model.Payment;
import com.company.payment.domain.repository.IPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProcessPaymentUseCase {
    private final IPaymentRepository paymentRepository;

    @Transactional
    public Payment execute(Long orderId, BigDecimal amount) {
        Payment payment = new Payment(orderId, amount);
        // External gateway call logic would go here
        payment.complete();
        return paymentRepository.save(payment);
    }
}
