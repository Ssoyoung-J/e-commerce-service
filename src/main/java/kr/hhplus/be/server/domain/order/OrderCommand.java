package kr.hhplus.be.server.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCommand {

    @Getter
    public static class Order {
        private final Long userId;
        private final List<OrderItem> orderItems;
        private final Long userCouponId;

        @Builder
        private Order(Long userId, List<OrderItem> orderItems, Long userCouponId) {
            this.userId = userId;
            this.orderItems = orderItems;
            this.userCouponId = userCouponId;
        }

        public static Order of(Long userId, List<OrderItem> orderItems, Long userCouponId) {
            return Order.builder()
                    .userId(userId)
                    .orderItems(orderItems)
                    .userCouponId(userCouponId)
                    .build();
        }
    }

    @Getter
    public static class OrderItem {
        private final Long productId;
        private final Long productDetailId;
        private final Long productQuantity;
        private final Long productPrice;

        @Builder
        private OrderItem(Long productId, Long productDetailId, Long productQuantity, Long productPrice) {
            this.productId = productId;
            this.productDetailId = productDetailId;
            this.productQuantity = productQuantity;
            this.productPrice = productPrice;
        }

        public static OrderItem of(Long productId, Long productDetailId, Long productQuantity, Long productPrice) {
            return OrderItem.builder()
                    .productId(productId)
                    .productDetailId(productDetailId)
                    .productQuantity(productQuantity)
                    .productPrice(productPrice)
                    .build();
        }
    }

    @Getter
    public static class OrderItems {

        private final List<OrderItem> orderItems;

        private OrderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
        }

        public static OrderItems of(List<OrderItem> orderItems) {
            return new OrderItems(orderItems);
        }
    }

//    @Getter
//    public static class OrderStatus {
//        private final Long orderId;
//        private final OrderStatus status;
//
//        @Builder
//        private OrderStatus(Long orderId, OrderStatus status) {
//            this.orderId = orderId;
//            this.status = status;
//        }
//
//        public static OrderStatus of(Long orderId, OrderStatus status) {
//            return OrderStatus.builder()
//                    .orderId(orderId)
//                    .status(status)
//                    .build();
//        }
//    }

}