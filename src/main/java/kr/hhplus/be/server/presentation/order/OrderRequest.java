package kr.hhplus.be.server.presentation.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.application.order.OrderCriteria;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderRequest {

    @Getter
    @NoArgsConstructor
    public static class Order {

        @NotNull(message = "사용자 ID는 필수입니다.")
        private Long userId;

        @NotEmpty(message = "주문 상품 목록은 1개 이상이어야 합니다.")
        private List<OrderItem> orderItemList;

        private Long userCouponId;

        private Order(Long userId,List<OrderItem> orderItemList, Long userCouponId) {
            this.userId = userId;
            this.orderItemList = orderItemList;
            this.userCouponId = userCouponId;
        }

        public static Order of(Long userId, List<OrderItem> orderItemList, Long userCouponId) {
            return new Order(userId, orderItemList, userCouponId);
        }

        public OrderCriteria.Create toCriteria() {
            return OrderCriteria.Create.of(userId, orderItemList.stream()
                    .map(OrderRequest.OrderItem::toCriteria)
                    .toList(), userCouponId);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class OrderItem {
        @NotNull(message = "상품 ID는 필수입니다.")
        private Long productId;

        @NotNull(message = "상품 옵션 ID는 필수입니다.")
        private Long productDetailId;

        @NotNull(message = "상품 구매 수량은 필수입니다.")
        private int productQuantity;

        @NotNull(message = "상품 가격은 필수입니다.")
        private Long productPrice;

        private OrderItem(Long productId, Long productDetailId, int productQuantity, Long productPrice) {
            this.productId = productId;
            this.productDetailId = productDetailId;
            this.productQuantity = productQuantity;
            this.productPrice = productPrice;
        }

        public static OrderItem of(Long productId, Long productDetailId, int productQuantity, Long productPrice) {
            return new OrderItem(productId, productDetailId, productQuantity, productPrice);
        }

        public OrderCriteria.OrderItem toCriteria() {
            return OrderCriteria.OrderItem.of(productId, productDetailId, productQuantity, productPrice);
        }
    }
}
