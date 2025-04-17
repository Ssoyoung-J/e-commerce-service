package kr.hhplus.be.server.domain.payment;

import lombok.Value;

@Value
public class PaymentCommand {

    // 주문 고유 id
    Long orderId;

    // 결제 금액
    Long paymentPrice;
}
