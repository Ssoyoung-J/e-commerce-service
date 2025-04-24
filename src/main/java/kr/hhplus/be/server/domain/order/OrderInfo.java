package kr.hhplus.be.server.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
        private final List<OrderItem> orderItemList;

        private Order(Long orderId, Long userId, OrderStatus status, Long totalAmount, Long discountAmount, Long finalPrice, List<OrderItem> orderItemList) {
            this.orderId = orderId;
            this.userId = userId;
            this.status = status;
            this.totalAmount = totalAmount;
            this.discountAmount = discountAmount;
            this.finalPrice = finalPrice;
            this.orderItemList = orderItemList;
        }


        public static Order of(Long orderId, Long userId, OrderStatus status, Long totalAmount, Long discountAmount, Long finalPrice, List<OrderItem> orderItemList) {
            return new Order(orderId, userId, status, totalAmount, discountAmount, finalPrice, orderItemList);
        }
    }

}
