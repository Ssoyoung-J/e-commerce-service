package kr.hhplus.be.server.domain.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    // 결제
    public PaymentInfo.Payment pay(PaymentCommand.Payment command) {
        Payment payment = Payment.create(command.getOrderId(), command.getPaymentPrice());
        payment.pay();

        Payment savedPayment = paymentRepository.save(payment);
        return PaymentInfo.Payment.of(savedPayment.getOrderId(), savedPayment.getPaymentId());
    }

}
