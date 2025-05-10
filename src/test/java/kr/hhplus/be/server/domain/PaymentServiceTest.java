package kr.hhplus.be.server.domain;

import kr.hhplus.be.server.domain.payment.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Nested
    class paymentTest {

        @DisplayName("결제 성공 - 올바른 주문 ID, 0보다 큰 결제 금액")
        @Test
        void paySuccess() {
            // given
            Long orderId = 1L;
            Long paymentPrice = 250000L;

            PaymentCommand.Payment command = PaymentCommand.Payment.of(orderId, paymentPrice);

            Payment payment = Payment.builder()
                    .paymentId(1L)
                    .orderId(orderId)
                    .paymentPrice(paymentPrice)
                    .build();
            when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

            // when
            PaymentInfo.Payment result = paymentService.pay(command);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getPaymentId()).isEqualTo(1L);
            assertThat(result.getOrderId()).isEqualTo(orderId);
            verify(paymentRepository, times(1)).save(any(Payment.class));
        }

        @DisplayName("결제 실패 - 결제 상태: 결제 취소")
        @Test
        void payCanceled() {
            // given
            Payment canceledPayment = Payment.builder()
                    .paymentId(1L)
                    .orderId(1L)
                    .paymentPrice(1000L)
                    .paymentStatus(PaymentStatus.CANCELED)
                    .paidAt(null)
                    .build();

            // when & then
            assertThatThrownBy(canceledPayment::pay)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("결제가 취소되었습니다.");
        }
    }
}
