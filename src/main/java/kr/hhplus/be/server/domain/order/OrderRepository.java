package kr.hhplus.be.server.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository {

    // 주문 생성
    Order save(Order order);

    // 주문 조회
    Order findById(Long orderId);

}
