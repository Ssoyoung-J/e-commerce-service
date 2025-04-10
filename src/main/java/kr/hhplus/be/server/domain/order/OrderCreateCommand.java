package kr.hhplus.be.server.domain.order;

import kr.hhplus.be.server.domain.coupon.Coupon;
import lombok.Value;

import java.util.List;

@Value
public class OrderCreateCommand {
    // 사용자 고유 id
    Long userId;

    // 주문 상품
    List<OrderItemCommand> orderItems;
    
    // 쿠폰
    Long couponId;

}