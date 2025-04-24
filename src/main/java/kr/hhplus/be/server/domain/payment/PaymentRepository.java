package kr.hhplus.be.server.domain.payment;

import java.util.Optional;

public interface PaymentRepository {

    // 결제 정보 생성
    Payment save(Payment payment);

    // 결제 정보 조회
    Payment findById(Long paymentId);
}
