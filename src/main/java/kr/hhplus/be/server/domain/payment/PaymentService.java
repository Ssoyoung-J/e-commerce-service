package kr.hhplus.be.server.domain.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    // 결제
    public Payment pay(PaymentCommand command) {
        Payment payment = Payment.create(command.getOrderId(), command.getPaymentPrice());
        payment.pay();

        paymentRepository.save(payment);
        return payment;
    }
}
