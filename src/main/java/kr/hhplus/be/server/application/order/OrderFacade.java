package kr.hhplus.be.server.application.order;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.common.redisson.DistributedLock;
import kr.hhplus.be.server.domain.coupon.CouponCommand;
import kr.hhplus.be.server.domain.coupon.CouponInfo;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.order.OrderCommand;
import kr.hhplus.be.server.domain.order.OrderInfo;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.product.ProductCommand;
import kr.hhplus.be.server.domain.product.ProductInfo;
import kr.hhplus.be.server.domain.product.ProductService;
import kr.hhplus.be.server.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static kr.hhplus.be.server.domain.coupon.QUserCoupon.userCoupon;

@Component
@RequiredArgsConstructor
public class OrderFacade {


    private final OrderService orderService;
    private final ProductService productService;
    private final CouponService couponService;
    /**
     *
     * 주문
     *
     * */
    @Transactional
    @DistributedLock(
            topic = "stock",
            keyExpression = "#criteria.toOptionIds()",
            waitTime = 5,
            leaseTime = 3
    )
    public OrderResult.OrderDetails order(OrderCriteria.OrderDetail criteria) {
        List<OrderCriteria.OrderItem> orderItemList = criteria.getOrderItems();

        // 주문 상품 옵션 ID
        List<Long> orderItemIds = orderItemList.stream()
                .map(item -> item.getProductDetailId())
                .toList();

        // 옵션 ID 기반 상품 가격 정보 조회
        List<ProductInfo.PriceOption> productDetails = productService.getOptionsById(ProductCommand.ProductDetailIds.of(orderItemIds));

        // 사용자 쿠폰 정보 조회
        Map<Long, CouponInfo.UserOwnedCoupon> userOwnedCouponMap = Collections.emptyMap();
        List<Long> userCouponIds = orderItemList.stream()
                .map(OrderCriteria.OrderItem::getUserCouponId)
                .filter(Objects::nonNull)
                .toList();

        if(!CollectionUtils.isEmpty(userCouponIds)) {
            userOwnedCouponMap = couponService.findUserCouponsById(CouponCommand.FindUserCoupons.of(userCouponIds))
                    .stream()
                    .collect(toMap(CouponInfo.UserOwnedCoupon::getUserCouponId, Function.identity()));
        }

        // 도메인 서비스에 넘길 데이터 준비
        List<ProductCommand.ProductOptionStock> optionStocks = new ArrayList<>();
        List<OrderCommand.OrderItem> orderItems = new ArrayList<>();

        for(int i = 0; i < orderItemList.size(); i++) {

            OrderCriteria.OrderItem item = orderItemList.get(i);
            ProductInfo.PriceOption option = productDetails.get(i);
            CouponInfo.UserOwnedCoupon ownedCoupon = userOwnedCouponMap.get(item.getUserCouponId());

            // 상품 옵션 정보 생성 - 재고 차감
            ProductCommand.ProductOptionStock optionStock = ProductCommand.ProductOptionStock
                    .of(item.getProductDetailId(), item.getQuantity());

            optionStocks.add(optionStock);

            // 주문 생성을 위한 주문 상품 정보 생성
            OrderCommand.OrderItem orderItem = OrderCommand.OrderItem.of(item.getProductDetailId(), item.getQuantity(), option.getProductPrice(), item.getUserCouponId(),
                    ownedCoupon == null ? null : ownedCoupon.getDiscount());

            orderItems.add(orderItem);
        }

        // 주문 상품에 대한 재고 차감
        productService.decreaseStockQuantity(ProductCommand.DecreaseStock.of(optionStocks));

        // 주문 생성 요청
        OrderInfo.OrderDetails info = orderService.createOrder(OrderCommand.Create.of(criteria.getUserId(), orderItems));

        return OrderResult.OrderDetails.from(info);

    }


}
