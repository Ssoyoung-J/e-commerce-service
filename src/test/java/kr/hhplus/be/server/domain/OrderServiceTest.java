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

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Order Service 단위 테스트
 * */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Nested
    class orderTest {

        @DisplayName("주문 생성")
        @Test
        void createOrder() {
            // given
            long userId = 1L;
            OrderCommand.Create command = OrderCommand.Create.of(
                    userId,
                    List.of(
                            OrderCommand.OrderItem.of(1L, 1, 20L, 2000L, 3000L)
                    )
            );

            Order order = Order.builder()
                    .orderId(1L)
                    .userId(userId)
                    .orderedAt(LocalDateTime.now())
                    .status(Order.OrderStatus.PAYMENT_WAITING)
                    .totalAmount(40000L)
                    .build();

            when(orderRepository.save(any(Order.class))).thenReturn(order);

            // when
            OrderInfo.OrderDetails result = orderService.createOrder(command);

            // then
            assertThat(result.getOrderId()).isEqualTo(order.getOrderId());
            assertThat(result.getTotalAmount()).isEqualTo(40000L);
        }
    }
}
