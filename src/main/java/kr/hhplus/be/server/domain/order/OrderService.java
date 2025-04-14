package kr.hhplus.be.server.domain.order;

import jakarta.transaction.Transactional;
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

    // 주문 정보 생성
    @Transactional
    public Order createOrder(OrderCreateCommand command) {
        // 사용자 주문 상품 조회 OrderItem
        List<OrderItem> items = command.getOrderItems().stream()
                .map(item -> OrderItem.of(item.getProductId(), item.getProductPrice(), item.getProductQuantity()))
                .toList();
        
        // 쿠폰 조회
        Coupon coupon = null;
        if(command.getCouponId() != null) {
            coupon = couponService.findById(command.getCouponId());
        }

        // 주문 생성
        Order order = Order.create(command.getUserId(), items, coupon);

        return order;
    }



}
