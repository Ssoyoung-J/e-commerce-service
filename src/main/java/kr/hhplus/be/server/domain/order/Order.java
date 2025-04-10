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
    @Enumerated(EnumType.STRING)
    @Column(name = "orderStatus", nullable = false)
    private OrderStatus orderStatus;

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

    /**
     * 결제
     * */
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Payment payment;

    public void assignPayment(Payment payment) {
        this.payment = payment;
        payment.assignOrder(this);
    }

    @Builder
    public Order(Long userId, OrderStatus orderStatus, List<OrderItem> orderItemList, LocalDateTime orderedAt, Long totalAmount, Long discountAmount, Long finalPrice) {
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.orderedAt = orderedAt;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.finalPrice = finalPrice;
        this.orderItemList = orderItemList != null ? orderItemList : new ArrayList<>();

        // 연관관계 설정
        for(OrderItem item : this.orderItemList) {
            item.assignOrder(this);
        }
    }




    // 주문 정보 생성 - createOrder
    public static Order create(OrderCreateCommand command) {
        List<OrderItem> items = command.getOrderItemList();

        // 주문 상품 총 금액
        long totalAmount = items.stream()
                .mapToLong(OrderItem::calculateAmount)
                .sum();

        // 쿠폰 적용에 따른 할인 금액
        long discountAmount = command.getCoupon().calculateDiscountAmount();

       /*  금액 할인 적용 정책을 사용
         총 금액 - 할인 금액 */
        long finalPrice = totalAmount - discountAmount;

        return Order.builder()
                .userId(command.getUserId())
                .orderStatus(OrderStatus.CREATED)
                .orderItemList(items)
                .orderedAt(LocalDateTime.now())
                .totalAmount(totalAmount)
                .discountAmount(discountAmount)
                .finalPrice(finalPrice)
                .build();
    }
    
/*   주문 상태 변경 - updateOrderStatus
     주문 상태 변경을 하는 행위에 대해서는 주문 도메인이 알고 있어야 할 것이고,
     다른 도메인들은 주문 상태를 변경만 하면 되기때문에..!*/
    public void updateOrderStatus(OrderStatus newOrderStatus) {
        this.orderStatus = newOrderStatus;
    }

}
