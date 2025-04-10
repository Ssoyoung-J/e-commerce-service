package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseEntity;
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
    public OrderItem(Long orderId, Long productId, Long productPrice, Long productQuantity) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
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
