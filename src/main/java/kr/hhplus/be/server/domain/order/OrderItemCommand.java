package kr.hhplus.be.server.domain.order;

import lombok.Value;

@Value
public class OrderItemCommand {
    Long productId;
    Long productQuantity;
}
