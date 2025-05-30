package kr.hhplus.be.server.domain.payment;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseEntity;
import kr.hhplus.be.server.domain.order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "payment_price", nullable = false)
    private Long paymentPrice;

    @Column(name = "paid_at", nullable = false)
    private LocalDateTime paidAt;

    @Column(name = "order_id", nullable = false)
    private Long orderId;


    @Builder
    public Payment(Long paymentId, Long orderId, PaymentStatus paymentStatus, Long paymentPrice, LocalDateTime paidAt) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
        this.paymentPrice = paymentPrice;
        this.paidAt = paidAt;
    }

    // 결제 정보 생성
    public static Payment create(Long orderId, Long paymentPrice) {
        return Payment.builder()
                .orderId(orderId)
                .paymentStatus(PaymentStatus.PENDING)
                .paymentPrice(paymentPrice)
                .build();
    }

    // 결제
    public void pay() {
        if(paymentStatus.equals(PaymentStatus.CANCELED)) {
            throw new IllegalArgumentException("결제가 취소되었습니다.");
        }
        this.paymentStatus = PaymentStatus.COMPLETED;
        this.paidAt = LocalDateTime.now();
    }
}
