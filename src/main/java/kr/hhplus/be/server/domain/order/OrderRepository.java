package kr.hhplus.be.server.domain.order;


import java.util.List;

public interface OrderRepository {

    // 주문 생성
    Order save(Order order);

    // 주문 조회 - 주문 ID
    Order findOrderById(long orderId);

    // 주문 목록 조회 - 사용자 ID
    List<Order> findOrdersByUserId(long userId);


}
