package kr.hhplus.be.server.presentation.order;

import kr.hhplus.be.server.application.order.OrderResult;
import kr.hhplus.be.server.domain.order.Order;

public record OrderResponse() {

    public record OrderInfo(
            Long orderId,
            Long userId,
            Order.OrderStatus status,
            Long totalAmount
    ) {
        public static OrderInfo from(OrderResult.OrderDetails order) {
            return new OrderInfo(
                    order.getOrderId(),
                    order.getUserId(),
                    order.getStatus(),
                    order.getTotalAmount()
            );
        }
    }
}
