package kr.hhplus.be.server.domain.order;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.infrastructure.order.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final CouponService couponService;

    public Order createOrder(OrderCreateCommand command) {
        // 1. 주문 상품 변환
        List<OrderItem> items = command.getOrderItems().stream()
                .map(item -> OrderItem.of(item.getProductId(), item.getProductQuantity()))
                .toList();

        // 2. 쿠폰 조회
        Coupon coupon = null;
        if (command.getCouponId() != null) {
            coupon = couponService.findById(command.getCouponId());
        }

        // 3. 도메인 Order 생성
        return Order.create(command.getUserId(), items, coupon);
    }

}
