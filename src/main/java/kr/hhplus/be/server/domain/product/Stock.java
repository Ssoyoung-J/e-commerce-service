package kr.hhplus.be.server.domain.product;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.domain.common.BaseEntity;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Stock extends BaseEntity {

    /**
     * 재고 아이디
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id", nullable = false)
    private Long stockId;

    /**
     * 상품 옵션 ID
     * */
    @Column(name = "product_option_id", nullable = false)
    private Long productDetailId;

    /**
     * 재고
     * */
    @Column(name = "quantity", nullable = false)
    private int quantity;

    // 재고 추가
    public void increaseStock(int quantity) {

        if(quantity <= 0){
            throw new BusinessException(400, "재고 추가 수량은 0보다 커야합니다.");
        }

        this.quantity += quantity;
    }

    // 재고 차감
    public void decreaseStock(int quantity) {

        if(quantity <= 0){
            throw new BusinessException(400, "재고 차감 수량은 0보다 커야합니다.");
        }

        if(this.quantity < quantity){
            throw new BusinessException(400, "현재 재고 수량보다 많은 수량을 차감할 수 없습니다.");
        }

        this.quantity -= quantity;
    }
}
