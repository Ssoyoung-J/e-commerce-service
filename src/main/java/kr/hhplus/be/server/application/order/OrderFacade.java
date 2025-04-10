package kr.hhplus.be.server.application.order;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.order.OrderCreateCommand;
import kr.hhplus.be.server.domain.order.OrderItem;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final ProductService productService;
    private final CouponService couponService;
    private final PaymentService paymentService;

    /**
     *
     * 주문부터 결제까지 하나의 트랜잭션으로 처리
     *
     * */

}
