package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.order.OrderCommand;
import kr.hhplus.be.server.domain.order.OrderInfo;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.product.ProductCommand;
import kr.hhplus.be.server.domain.product.ProductInfo;
import kr.hhplus.be.server.domain.product.ProductService;
import kr.hhplus.be.server.domain.user.UserCoupon;
import kr.hhplus.be.server.domain.user.UserCouponService;
import kr.hhplus.be.server.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderFacade {


    private final OrderService orderService;
    private final ProductService productService;
    private final CouponService couponService;
    private final PaymentService paymentService;
    private final UserService userService;
    private final UserCouponService userCouponService;

    /**
     *
     * 주문
     *
     * */
    public OrderResult.Order order(OrdeCriteria.Order criteria) {
        userService.getUser(criteria.getUserId());

        // 상품 조회
        ProductInfo.ProductInfoList product = productService.findProduct(new ProductCommand.Find(criteria.getUserId()));

        // 주문 상품 생성
        List<OrderCommand.OrderItem> orderItemList = criteria.toOrderItemCommand().getOrderItems();

        // 사용자 쿠폰 조회
        Long userCouponId = userCouponService.getUsableCoupon(criteria.getUserId(), criteria.getUserCouponId()).getUserCouponId();

        // 주문 정보 생성
        OrderInfo.Order order = orderService.createOrder(criteria.toOrderCommand(criteria.getUserId(), orderItemList, userCouponId));

        // 재고 차감 시 재고 충분 및 재고 부족 검증
        // 재고 부족일 경우 주문 정보 상태값: 주문 취소로 변경, 재고 충분일 경우 주문 정보 상태 값: 결제 대기로 변경





        return OrderResult.Order.of(order);
    }


}
