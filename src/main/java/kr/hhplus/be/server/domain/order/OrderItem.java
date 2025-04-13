package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
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
    @Column(name = "itemId", nullable = false)
    private Long itemId;

    /**
     * 주문 고유 ID
     * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    

    /**
     * 상품과 단방향 연관관계(N:1)
     * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * 상품 고유 ID
     * */
    @Column(name = "productId", nullable = false)
    private Long productId;

    /**
     * 상품 가격
     * */
    @Column(name = "productPrice", nullable = false)
    private Long productPrice;

    /**
     * 상품 수량
     * */
    @Column(name = "productQuantity", nullable = false)
    private Long productQuantity;


    @Builder
    public OrderItem(Order order, Long productId, Long productPrice, Long productQuantity) {
        if(productPrice == null || productPrice <= 0) {
            throw new IllegalArgumentException("상품 가격은 0보다 커야 합니다.");
        }

        if(productQuantity == null || productQuantity <= 0) {
            throw new IllegalArgumentException("상품 수량은 0보다 커야 합니다.");
        }

        this.order = order;
        this.productId = productId;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    public static OrderItem of(Long productId, Long productQuantity) {
        return OrderItem.builder()
                .productId(productId)
                .productQuantity(productQuantity)
                .build();
    }

    // Order와 OrderItem간의 연관관계 생성
    public void assignOrder(Order order) {
        this.order = order;
    }

    // 주문 상품 총 금액 계산
    public Long calculateAmount() {
        return productQuantity * productPrice;
    }


}
