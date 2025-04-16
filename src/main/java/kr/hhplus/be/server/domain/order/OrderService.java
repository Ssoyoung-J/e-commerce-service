package kr.hhplus.be.server.domain.order;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CouponService couponService;
    private final ExternalPlatform externalPlatform;

    public Order createOrder(OrderCreateCommand command) {
        List<OrderItem> orderItems = command.getOrderItems().stream()
                .map(item -> OrderItem.of(item.getProductId(), item.getProductPrice(), item.getProductQuantity()))
                .toList();
        Coupon coupon = couponService.getCoupon(command.getCouponId());

        Order order = Order.create(command.getUserId(), orderItems, coupon);

        orderRepository.save(order);

        return order;
    }

    // 주문 결제 완료
    public void paidOrder(Long orderId) {
        Order order = orderRepository.findById(orderId);
        order.updateOrderStatus(OrderStatus.PAID);
        externalPlatform.sendOrder(order);
    }

    // 주문 취소
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId);
        order.updateOrderStatus(OrderStatus.CANCELED);
    }


}
