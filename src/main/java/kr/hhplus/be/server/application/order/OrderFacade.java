package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.order.OrderCommand;
import kr.hhplus.be.server.domain.order.OrderInfo;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.product.ProductCommand;
import kr.hhplus.be.server.domain.product.ProductService;
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
    public OrderResult.Order order(OrderCriteria.Order criteria) {
        userService.getUser(criteria.getUserId());

        // 주문 상품 생성
        List<OrderCommand.OrderItem> orderItemList = criteria.toOrderItemCommand().getOrderItems();

        // 사용자 쿠폰 조회
        Long userCouponId = userCouponService.getUsableCoupon(criteria.getUserId(), criteria.getUserCouponId()).getUserCouponId();

        // 주문 정보 생성
        OrderInfo.Order order = orderService.createOrder(criteria.toOrderCommand(criteria.getUserId(), orderItemList, userCouponId));

        // 재고 차감 시 재고 충분 및 재고 부족 검증
        // 재고 부족일 경우 주문 정보 상태값: 주문 취소로 변경, 재고 충분일 경우 주문 정보 상태 값: 결제 대기로 변경
        
        // orderItemList 순회하면서 주문한 상품 전체 재고 조회
        boolean allInStock = order.getOrderItemList().stream().allMatch(item -> {
            ProductCommand.FindDetail command = new ProductCommand.FindDetail(
                    item.getProductDetailId(),
                    item.getProductQuantity()
            );
            return productService.hasSufficientStock(command);
        });

        if(!allInStock) {
            orderService.cancelOrder(order.getOrderId());
            throw new RuntimeException("주문 취소 : 일부 상품 재고 부족");
        }

        try {
            order.getOrderItemList().forEach(item -> {
                ProductCommand.FindDetail command = new ProductCommand.FindDetail(
                        item.getProductDetailId(),
                        item.getProductQuantity()
                );
                productService.decreaseStock(command);
            });
        } catch (IllegalArgumentException e) {
            orderService.cancelOrder(order.getOrderId());
            throw new RuntimeException("주문 취소 : 재고 차감 중 오류 발생", e);
        }
        

        orderService.waitingForPay(order.getOrderId());

        return OrderResult.Order.of(order);
    }


}
