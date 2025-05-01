package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.order.OrderInfo;
import kr.hhplus.be.server.domain.order.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderResult {

    @Getter
    public static class Order {

        private final Long orderId;
        private final Long userId;
        private OrderStatus status;
        private final Long totalAmount;
        private final Long discountAmount;
        private final Long finalPrice;

        @Builder
        private Order(Long orderId, Long userId, OrderStatus status, Long totalAmount, Long discountAmount, Long finalPrice) {
            this.orderId = orderId;
            this.userId = userId;
            this.status = status;
            this.totalAmount = totalAmount;
            this.discountAmount = discountAmount;
            this.finalPrice = finalPrice;
        }

        public static Order of(OrderInfo.Order order) {
            return new Order(order.getOrderId(), order.getUserId(), order.getStatus(), order.getTotalAmount(), order.getDiscountAmount(), order.getFinalPrice());
        }
    }
}
