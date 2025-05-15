package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.domain.common.BaseEntity;
import kr.hhplus.be.server.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;

@Getter
@NoArgsConstructor
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



    @Builder
    public OrderItem(Long orderId, Long productDetailId, Long productPrice, int productQuantity, Long userCouponId) {
        if(productPrice == null || productPrice <= 0) {
            throw new BusinessException(400, "상품 가격은 0보다 커야 합니다.");
        }

        if(productQuantity <= 0) {
            throw new BusinessException(400, "상품 수량은 0보다 커야 합니다.");
        }

        this.orderId = orderId;
        this.productDetailId = productDetailId;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.userCouponId = userCouponId;
    }

    public static OrderItem of(Long productDetailId, Long productPrice,int productQuantity, Long userCouponId) {
        return OrderItem.builder()
                .productDetailId(productDetailId)
                .productQuantity(productQuantity)
                .productPrice(productPrice)
                .userCouponId(userCouponId)
                .build();
    }

    // 주문 상품 총 금액 계산
    // 도메인 객체 끼리의 행위에서 비즈니스 로직으로 사용
    public Long calculateAmount() {
        return productQuantity * productPrice;
    }


}
