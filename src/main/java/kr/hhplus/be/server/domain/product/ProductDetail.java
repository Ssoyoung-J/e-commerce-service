package kr.hhplus.be.server.domain.product;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@NoArgsConstructor
@Getter
public class ProductDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productDetailId;

    private String optionName;

    private Long productPrice;

    private Long stockQuantity;

    private Long productId;

    @Builder
    public ProductDetail(Long productDetailId, String  optionName, Long productPrice, Long stockQuantity) {
        this.productDetailId = productDetailId;
        this.optionName = optionName;
        this.productPrice = productPrice;
        this.stockQuantity = stockQuantity;
    }


    // 재고 확인
    public boolean hasSufficientStock(Long quantity) {
        return this.stockQuantity >= quantity;
    }

   // 재고 차감
   public void decreaseStock(Long quantity) {
        if(!hasSufficientStock(quantity)) {
            throw new IllegalArgumentException("재고가 부족합니다");
        }
        this.stockQuantity -= quantity;
   }


}
