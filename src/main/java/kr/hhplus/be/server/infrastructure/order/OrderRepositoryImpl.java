package kr.hhplus.be.server.infrastructure.order;

import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.domain.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    // 주문 정보 생성
    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }

    // 주문 정보 조회 - 주문 ID
    @Override
    public Order findOrderById(long orderId) {
        return orderJpaRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
    }

    // 주문 목록 조회 - 사용자 ID
    @Override
    public List<Order> findOrdersByUserId(long userId) {
        return orderJpaRepository.findByUserId(userId);
    }

}
