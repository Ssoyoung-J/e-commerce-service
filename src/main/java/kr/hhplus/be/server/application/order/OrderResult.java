package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.domain.order.OrderInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderResult {

    @Getter
    @Builder
    public static class OrderDetails {

        private Long orderId;
        private Long userId;
        private LocalDateTime orderedAt;
        private Order.OrderStatus status;
        private List<OrderItemDetails> orderItems;
        private Long totalAmount;

        public static OrderDetails from(OrderInfo.OrderDetails order) {
            List<OrderItemDetails> items = order.getOrderItemList().stream()
                    .map(OrderItemDetails::from)
                    .collect(Collectors.toList());

            return OrderDetails.builder()
                    .orderId(order.getOrderId())
                    .userId(order.getUserId())
                    .orderedAt(order.getOrderedAt())
                    .status(order.getStatus())
                    .totalAmount(order.getTotalAmount())
                    .orderItems(items)
                    .build();
        }

    }

    @Getter
    @Builder
    public static class OrderItemDetails {
        private long productDetailId;
        private long productPrice;
        private int quantity;
        private long userCouponId;

        public static OrderItemDetails from(OrderInfo.OrderItemDetails orderItem) {
            return OrderItemDetails.builder()
                    .productDetailId(orderItem.getProductDetailId())
                    .productPrice(orderItem.getProductPrice())
                    .quantity(orderItem.getProductQuantity())
                    .userCouponId(orderItem.getUserCouponId())
                    .build();
        }
    }
}
