package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseEntity;
import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.payment.Payment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.sum;

@Getter
@NoArgsConstructor
@Entity
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
     * 주문 항목 목록
     * */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItemList = new ArrayList<>();

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


    @Builder
    public Order(Long userId, OrderStatus status, List<OrderItem> orderItemList, LocalDateTime orderedAt, Long totalPrice) {
        this.userId = userId;
        this.status = status;
        this.orderedAt = orderedAt;
        this.totalAmount = totalAmount;
        this.orderItemList = orderItemList != null ? orderItemList : new ArrayList<>();

    }

    // 주문 정보 생성 - createOrder
    public static Order create(Long userId ,List<OrderItem> items, Coupon coupon) {
        long totalPrice = items.stream()
                .mapToLong(OrderItem::calculateAmount)
                .sum();

        return Order.builder()
                .userId(userId)
                .status(OrderStatus.PAYMENT_WAITING)
                .orderItemList(items)
                .orderedAt(LocalDateTime.now())
                .totalPrice(totalPrice)
                .build();
    }
    
/*   주문 상태 변경 - updateOrderStatus
     주문 상태 변경을 하는 행위에 대해서는 주문 도메인이 알고 있어야 할 것이고,
     다른 도메인들은 주문 상태를 변경만 하면 되기때문에..!*/
    public void updateOrderStatus(OrderStatus newOrderStatus) {
        this.status = newOrderStatus;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    // Order Entity에서 OrderItem Entity의 메소드를 호출하는 형태
    public Long calculateTotalAmount() {
        return orderItemList.stream()
                .mapToLong(OrderItem::calculateAmount)
                .sum();
    }
}
