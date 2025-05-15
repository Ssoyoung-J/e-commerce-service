package kr.hhplus.be.server.domain.order;

import com.querydsl.core.annotations.QueryProjection;

public interface OrderQuery {

    public record OrderItem(
            long productDetailId,
            long productPrice,
            int productQuantity,
            Long userCouponId
    ) {
        @QueryProjection
        public OrderItem {}

        public OrderInfo.OrderItemDetails to() {
            return OrderInfo.OrderItemDetails.builder()
                    .productDetailId(productDetailId)
                    .productPrice(productPrice)
                    .productQuantity(productQuantity)
                    .userCouponId(userCouponId)
                    .build();

        }

    }
}
