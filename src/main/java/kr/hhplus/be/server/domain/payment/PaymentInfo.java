package kr.hhplus.be.server.domain.payment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentInfo {

    @Getter
    public static class Payment {
        private final long paymentId;

        @Builder
        private Payment(Long paymentId) {
            this.paymentId = paymentId;
        }

        public static Payment of(Long paymentId) {
            if (paymentId == null) {
                throw new IllegalArgumentException("paymentId는 null일 수 없습니다.");
            }
            return new Payment(paymentId);
        }
    }
}
