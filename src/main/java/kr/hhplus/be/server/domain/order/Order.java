package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Order extends BaseEntity {

    /**
     *  주문 고유 ID
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId", nullable = false)
    private Long orderId;

    /**
     * 사용자 고유 ID
     * */
    @Column(name = "userId", nullable = false)
    private Long userId;

    /**
     * 주문 상태
     * */
    @Column(name = "orderStatus", nullable = false)
    private OrderStatus orderStatus;

    /**
     * 주문 일시
     * */
    @Column(name = "orderedAt", nullable = false)
    private LocalDateTime orderedAt;

    /**
     * 상품 총 금액
     * */
    @Column(name = "totalAmount", nullable = false)
    private Long totalAmount;

    /**
     * 할인 금액
     * */
    @Column(name = "discountAmount", nullable = false)
    private Long discountAmount;

    /**
     * 최종 결제 금액
     * */
    @Column(name = "finalPrice", nullable = false)
    private Long finalPrice;

    @Builder
    public Order(Long orderId, Long userId, OrderStatus orderStatus, LocalDateTime orderedAt, Long totalAmount, Long discountAmount, Long finalPrice) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.orderedAt = orderedAt;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.finalPrice = finalPrice;
    }


    // 주문 정보 생성 - createOrder
    public static Order create(OrderCreateCommand command) {
        return new Order (
                command.getUserId(),
                command.getOrderItemList(),
                OrderStatus.CREATED
        );
    }
    
    // 주문 상태 변경 - updateOrderStatus


}
