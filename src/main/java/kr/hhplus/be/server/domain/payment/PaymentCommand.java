package kr.hhplus.be.server.domain.payment;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentCommand {

    @Getter
    public static class Payment {
        private final Long orderId;
        private final Long paymentPrice;

        @Builder
        private Payment(Long orderId, Long paymentPrice) {
            this.orderId = orderId;
            this.paymentPrice = paymentPrice;
        }

        public static Payment of(Long orderId, Long paymentPrice) {
            return new Payment(orderId, paymentPrice);
        }
    }
}
