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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Builder
    public ProductDetail(Long productDetailId, String  optionName, Long productPrice, Long stockQuantity) {
        this.productDetailId = productDetailId;
        this.optionName = optionName;
        this.productPrice = productPrice;
        this.stockQuantity = stockQuantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // 재고 확인
    public boolean hasSufficientStock(Long quantity) {
        if(this.stockQuantity < quantity) {
            throw new IllegalArgumentException("해당 상품 재고가 부족합니다.");
        }
        return this.stockQuantity >= quantity;
    }

   // 재고 차감
   public void decreaseStock(Long quantity) {
        this.stockQuantity -= quantity;
   }


}
