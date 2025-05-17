package kr.hhplus.be.server.domain.order;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCommand {

    @Getter
    public static class Create {
        private final Long userId;
        private final List<OrderItem> orderItems;

        @Builder
        private Create(Long userId, List<OrderItem> orderItems) {
            this.userId = userId;
            this.orderItems = orderItems;
        }

        public static Create of(Long userId, List<OrderItem> orderItems) {
            return Create.builder()
                    .userId(userId)
                    .orderItems(orderItems)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class OrderItem {
        private final long productDetailId;
        private final int productQuantity;
        private final long productPrice;
        @Nullable
        private Long userCouponId;
        @Nullable
        private Long discount;

        public static OrderItem of(long productDetailId, int productQuantity, long productPrice, Long userCouponId, Long discount) {
            return OrderItem.builder()
                    .productDetailId(productDetailId)
                    .productQuantity(productQuantity)
                    .productPrice(productPrice)
                    .userCouponId(userCouponId)
                    .discount(discount)
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