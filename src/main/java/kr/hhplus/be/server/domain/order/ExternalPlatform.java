package kr.hhplus.be.server.domain.order;

public interface ExternalPlatform {

    // 외부 플랫폼으로 주문 정보 전송
    void sendOrder(Order order);
}
