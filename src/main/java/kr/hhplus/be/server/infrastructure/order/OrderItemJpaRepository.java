package kr.hhplus.be.server.infrastructure.order;

import kr.hhplus.be.server.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemJpaRepository extends JpaRepository<OrderItem, Long> {

    // 주문 상품 생성
//    OrderItem save(OrderItem orderItem);

    // 주문 상품 목록 생성
//    List<OrderItem> save(List<OrderItem> orderItemList);

    // 주문 상품 조회
//    OrderItem getOrderItem(Long orderId, Long itemId);

    // 주문 상품 목록 조회
//    List<OrderItem> getOrderItemList(Long orderItemId);


}
