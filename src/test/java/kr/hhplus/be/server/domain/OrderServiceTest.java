package kr.hhplus.be.server.domain;

import kr.hhplus.be.server.domain.order.*;
import kr.hhplus.be.server.infrastructure.order.OrderJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Order Service 단위 테스트
 * */
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderJpaRepository orderJpaRepository;

    @InjectMocks
    private OrderService service;

    @Nested
    class orderTest {
        @DisplayName("주문 비즈니스 로직 검증")
        @Test
        public void success() {
            // given
            long userId = 1L;
            long productId = 1L;
            long productPrice = 2000L;
            long productQuantity = 10L;
            long couponId = 1L;

            List<OrderItemCommand> orderItems = List.of(
                    new OrderItemCommand(productId, productPrice, productQuantity)
            );

            OrderCreateCommand command = new OrderCreateCommand(userId, orderItems, couponId);
            Order order = service.createOrder(command);

            assertEquals(10L, order.getOrderItemList().get(0).getProductQuantity());


        }
    }
}
