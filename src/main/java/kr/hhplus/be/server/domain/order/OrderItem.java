package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.domain.common.BaseEntity;
import kr.hhplus.be.server.domain.product.Product;
import lombok.*;
import org.springframework.scheduling.annotation.EnableScheduling;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderItem extends BaseEntity {
    /**
     *  주문 상품 고유 ID
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    /**
     * 주문 고유 ID
     * */
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    /**
     * 상품 옵션 고유 ID
     * */
    @Column(name = "product_detail_id", nullable = false)
    private Long productDetailId;

    /**
     * 상품 가격
     * */
    @Column(name = "product_price", nullable = false)
    private Long productPrice;

    /**
     * 상품 수량
     * */
    @Column(name = "product_quantity", nullable = false)
    private int productQuantity;

    /**
     * 사용자 쿠폰 ID
     * */
    @Column(name = "user_coupon_id", nullable = false)
    private Long userCouponId;


}
