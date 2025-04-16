package kr.hhplus.be.server.domain.order;

import lombok.Value;

@Value
public class OrderStatusUpdateCommand {
    // 주문 id
    Long orderId;

    // 주문 상태
    OrderStatus status;
}
