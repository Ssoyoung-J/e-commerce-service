package kr.hhplus.be.server.domain.payment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentInfo {

    @Getter
    public static class Payment {
        private final long orderId;
        private final long paymentId;

        @Builder
        private Payment(Long orderId, Long paymentId) {
            this.orderId = orderId;
            this.paymentId = paymentId;
        }

        public static Payment of(Long orderId, Long paymentId) {
            if(orderId == null) {
                throw new IllegalArgumentException("orderId는 null일 수 없습니다.");
            }
            if (paymentId == null) {
                throw new IllegalArgumentException("paymentId는 null일 수 없습니다.");
            }
            return new Payment(orderId, paymentId);
        }
    }



}
