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

    public OrderInfo.Order createOrder(OrderCommand.Order command) {
        List<OrderItem> orderItems = command.getOrderItems().stream()
                .map(this::createOrderItem).toList();
        Coupon coupon = couponService.getCoupon(command.getUserCouponId());

        Order order = Order.create(command.getUserId(), orderItems, coupon);

        orderRepository.save(order);

        return OrderInfo.Order.of(order.getOrderId(), order.getUserId(), order.getOrderStatus(), order.getTotalAmount(), order.getDiscountAmount(), order.getFinalPrice(), order.getOrderItemList());
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

    private OrderItem createOrderItem(OrderCommand.OrderItem orderItem) {
        return OrderItem.of(
                orderItem.getProductId(),
                orderItem.getProductQuantity(),
                orderItem.getProductPrice()
        );
    }

}


