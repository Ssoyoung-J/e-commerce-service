package kr.hhplus.be.server.domain.order;

import lombok.Value;

@Value
public class OrderItemCommand {
    Long productId;
    Long productPrice;
    Long productQuantity;
    
    // 도메인 만들기 전, 미리 금액 계산
    public Long calculateAmount() {
        return productPrice * productQuantity;
    }    
}
