package kr.hhplus.be.server.domain;

import kr.hhplus.be.server.domain.order.*;
import kr.hhplus.be.server.domain.order.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Order Service 단위 테스트
 * */
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderJpaRepository;

    @InjectMocks
    private OrderService service;

    @Nested
    class orderTest {
//        @DisplayName("주문 비즈니스 로직 검증")
//        @Test
//        public void success() {
//            // given
//            long userId = 1L;
//            long productId = 1L;
//            long productPrice = 2000L;
//            long productQuantity = 10L;
//            long couponId = 1L;
//
//            List<OrderItemCommand> orderItems = List.of(
//                    new OrderItemCommand(productId, productPrice, productQuantity)
//            );
//
//            OrderCommand command = new OrderCommand(userId, orderItems, couponId);
//            Order order = service.createOrder(command);
//
//            assertEquals(10L, order.getOrderItemList().get(0).getProductQuantity());
//
//
//        }
    }
}
