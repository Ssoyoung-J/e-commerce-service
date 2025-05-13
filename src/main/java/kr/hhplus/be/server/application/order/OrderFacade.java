package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.order.OrderCommand;
import kr.hhplus.be.server.domain.order.OrderInfo;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.product.ProductCommand;
import kr.hhplus.be.server.domain.product.ProductInfo;
import kr.hhplus.be.server.domain.product.ProductService;
import kr.hhplus.be.server.domain.user.UserCouponInfo;
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
    private final UserService userService;
    private final UserCouponService userCouponService;

    /**
     *
     * 주문
     *
     * */
    public OrderResult.OrderDetails order(OrderCriteria.Create criteria) {
        userService.getUser(criteria.getUserId());

        List<OrderCommand.OrderItem> orderItemList = criteria.toOrderItemCommand().getOrderItems();

        // 주문 상품 옵션 ID
        List<Long> orderItemIds = orderItemList.stream()
                .map(item -> item.getProductDetailId())
                .toList();

        // 옵션 ID 기반 상품 가격 정보 조회
        List<ProductInfo.PriceOption> productDetails = productService.getOptionsById(criteria.toProductDetailIdsCommand(orderItemIds));

        // 사용자 쿠폰 정보 조회
        List<UserCouponInfo.Coupons> userCouponList = userCouponService.getUserCoupons(criteria.getUserId());

        // 주문 상품의 재고 파악을 위해 주문 상품마다 재고 조회
        List<OrderCommand.OrderItem> availableItems = productService.filterAvailableItems(orderItemList);

        // 사용자 쿠폰 조회
        Long userCouponId = userCouponService.getUsableCoupon(criteria.toUsableCouponCommand(criteria.getUserId(), criteria.getUserCouponId())).getUserCouponId();

        // 주문 정보 생성
        OrderInfo.OrderDetails order = orderService.createOrder(criteria.toOrderCommand(criteria.getUserId(), orderItemList));

        try {
            availableItems.forEach(item -> {
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

        return OrderResult.OrderDetails.from(order);
    }


}
