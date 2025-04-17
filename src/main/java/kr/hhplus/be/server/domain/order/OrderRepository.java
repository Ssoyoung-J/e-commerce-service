package kr.hhplus.be.server.domain.order;


public interface OrderRepository {

    // 주문 생성
    Order save(Order order);

    // 주문 조회
    Order findById(Long orderId);

}
