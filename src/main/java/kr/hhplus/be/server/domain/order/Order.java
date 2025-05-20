package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseEntity;
import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.payment.Payment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.sum;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    /**
     *  주문 고유 ID
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    /**
     * 사용자 고유 ID
     * */
    @Column(name = "user_id", nullable = false)
    private Long userId;


    /**
     * 사용자 쿠폰 고유 ID
     * */
    @Column(name = "user_coupon_id", nullable = false)
    private Long userCouponId;

    /**
     * 주문 상태
     * */
    @Enumerated(EnumType.STRING)
    @Column(name = "orderStatus", nullable = false)
    private OrderStatus status;

    public enum OrderStatus {
        PAYMENT_WAITING , PAID, CANCELED
    }

    /**
     * 주문 일시
     * */
    @Column(name = "orderedAt", nullable = false)
    private LocalDateTime orderedAt;

    /**
     * 상품 총 금액
     * */
    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    /**
     * 할인 금액
     * */
    @Column(name = "discount_amount", nullable = false)
    private Long discountAmount = 0L;

    /**
     * 최종 결제 금액
     * */
    @Column(name = "final_price", nullable = false)
    private Long finalPrice;
    
/*   주문 상태 변경 - updateOrderStatus
     주문 상태 변경을 하는 행위에 대해서는 주문 도메인이 알고 있어야 할 것이고,
     다른 도메인들은 주문 상태를 변경만 하면 되기때문에..!*/
    public void updateOrderStatus(OrderStatus newOrderStatus) {
        this.status = newOrderStatus;
    }

}
