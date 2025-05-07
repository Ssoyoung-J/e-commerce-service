package kr.hhplus.be.server.presentation.order;

import kr.hhplus.be.server.application.order.OrderResult;

public record OrderResponse() {

    public record Order(
            Long orderId,

            Long userId,

            Order.OrderStatus status,
            Long totalAmount,
            Long discountAmount,
            Long finalPrice
    ) {
        public static Order from(OrderResult.Order order) {
            return new Order(
                    order.getOrderId(),
                    order.getUserId(),
                    order.getStatus(),
                    order.getTotalAmount(),
                    order.getDiscountAmount(),
                    order.getFinalPrice()
            );
        }
    }
}
