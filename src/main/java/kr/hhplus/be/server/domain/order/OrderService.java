package kr.hhplus.be.server.domain.order;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
//    private final ExternalPlatform externalPlatform;

    // 주문 생성
    @Transactional
    public OrderInfo.OrderDetails createOrder(OrderCommand.Create command) {
        // 주문 상품들
        List<OrderCommand.OrderItem> items = command.getOrderItems();

        // 총 주문 금액
        long totalAmount = calculateTotalPrice(items);
        // 주문 생성
        Order order = Order.builder()
                .userId(command.getUserId())
                .orderedAt(LocalDateTime.now())
                .status(Order.OrderStatus.PAYMENT_WAITING)
                .totalAmount(totalAmount)
                .build();

        // 주문 저장
        Order savedOrder = orderRepository.save(order);

        // 주문 상품 생성
        List<OrderItem> orderItems = items.stream()
                .map(item -> OrderItem.builder()
                        .orderId(savedOrder.getOrderId())
                        .productDetailId(item.getProductDetailId())
                        .productPrice(item.getProductPrice())
                        .productQuantity(item.getProductQuantity())
                        .userCouponId(item.getUserCouponId())
                        .build())
                .toList();

        return OrderInfo.OrderDetails.from(savedOrder, orderItems);
    }


    // 주문 결제 완료
    public void paidOrder(long orderId) {
        Order order = orderRepository.findOrderById(orderId);
        order.updateOrderStatus(Order.OrderStatus.PAID);
//        externalPlatform.sendOrder(order);
    }

    // 주문 취소
    public void cancelOrder(long orderId) {
        Order order = orderRepository.findOrderById(orderId);
        order.updateOrderStatus(Order.OrderStatus.CANCELED);
    }

    // 주문 결제 대기
    public void waitingForPay(long orderId) {
        Order order = orderRepository.findOrderById(orderId);
        order.updateOrderStatus(Order.OrderStatus.PAYMENT_WAITING);
    }

    public long calculateTotalPrice(List<OrderCommand.OrderItem> items) {
        if (CollectionUtils.isEmpty(items)) {
            return 0L;
        }

        return items.stream()
                .reduce(0L,
                        (acc, cur) -> acc + ((cur.getProductPrice() - Objects.requireNonNullElse(cur.getDiscount(), 0L)) * cur.getProductQuantity()),
                        Long::sum);
    }

}


