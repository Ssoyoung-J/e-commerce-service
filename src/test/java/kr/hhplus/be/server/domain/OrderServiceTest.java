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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Order Service 단위 테스트
 * */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
//    @Mock
//    private OrderRepository orderJpaRepository;

    @InjectMocks
    private OrderService orderService;

    @Nested
    class orderTest {

        @DisplayName("주문 생성")
        @Test
        void createOrder() {
            // given
            OrderCommand.Order command = OrderCommand.Order.of(
                    1L,
                    List.of(
                            OrderCommand.OrderItem.of(1L, 1L, 20L, 2000L)
                    ),
                    1L
            );

            // when
            OrderInfo.Order order = orderService.createOrder(command);

            // then
            assertThat(order.getFinalPrice()).isEqualTo(40000L);
        }
    }
}
