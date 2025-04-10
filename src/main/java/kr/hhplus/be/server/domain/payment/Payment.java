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
    @Column(name = "paymentId", nullable = false)
    private Long paymentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentStatus", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "paymentPrice", nullable = false)
    private Long paymentPrice;

    @Column(name = "paidAt", nullable = false)
    private LocalDateTime paidAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    public void assignOrder(Order order) {
        this.order = order;
    }

    @Builder
    public Payment(PaymentStatus paymentStatus, Long paymentPrice, LocalDateTime paidAt) {
        this.paymentStatus = paymentStatus;
        this.paymentPrice = paymentPrice;
        this.paidAt = paidAt;
    }
}
