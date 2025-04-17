package kr.hhplus.be.server.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderInfo {

    @Getter
    public static class Order {
        private final Long orderId;
        private final Long userId;
        private OrderStatus status;
        private final Long totalAmount;
        private final Long discountAmount;
        private final Long finalPrice;

        private Order(Long orderId, Long userId, OrderStatus status, Long totalAmount, Long discountAmount, Long finalPrice) {
            this.orderId = orderId;
            this.userId = userId;
            this.status = status;
            this.totalAmount = totalAmount;
            this.discountAmount = discountAmount;
            this.finalPrice = finalPrice;
        }


        public static Order of(Long orderId, Long userId, OrderStatus status, Long totalAmount, Long discountAmount, Long finalPrice) {
            return new Order(orderId, userId, status, totalAmount, discountAmount, finalPrice);
        }
    }

}
