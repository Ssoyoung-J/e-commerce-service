package kr.hhplus.be.server.domain.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderInfo {

    @Getter
    @Builder
    public static class OrderDetails {
        private long orderId;
        private long userId;
        private LocalDateTime orderedAt;
        private Order.OrderStatus status;
        private long totalAmount;
        private List<OrderItemDetails> orderItemList;

        public static OrderDetails from(Order order, List<OrderItem> orderItemList) {
            List<OrderItemDetails> items = orderItemList.stream()
                    .map(OrderItemDetails::from)
                    .collect(Collectors.toList());

            return OrderDetails.builder()
                    .orderId(order.getOrderId())
                    .userId(order.getUserId())
                    .orderedAt(order.getOrderedAt())
                    .status(order.getStatus())
                    .totalAmount(order.getTotalAmount())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class OrderItemDetails {
        private long productDetailId;
        private long productPrice;
        private int productQuantity;
        private Long userCouponId;

        public static OrderItemDetails from(OrderItem orderItem) {
            return OrderItemDetails.builder()
                    .productDetailId(orderItem.getProductDetailId())
                    .productPrice(orderItem.getProductPrice())
                    .productQuantity(orderItem.getProductQuantity())
                    .userCouponId(orderItem.getUserCouponId())
                    .build();
        }
    }



}
