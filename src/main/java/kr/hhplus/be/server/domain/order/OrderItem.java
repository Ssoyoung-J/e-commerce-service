package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseEntity;
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
    @Column(name = "orderId", nullable = false)
    private Long orderId;
    

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

}
