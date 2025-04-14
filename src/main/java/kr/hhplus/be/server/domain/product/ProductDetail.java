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
    @Column(name = "productDetailId", nullable = false)
    private Long id;
//    private Long productDetailId;


    @Column(name = "optionName", nullable = false)
    private String optionName;

    @Column(name = "productPrice", nullable = false)
    private Long productPrice;

    @Column(name = "stockQuantity", nullable = false)
    private Long stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="product_id", nullable = false)
    private Product product;

    @Builder
    public ProductDetail(String optionName, Long productPrice, Long stockQuantity, Product product) {
        this.optionName = optionName;
        this.productPrice = productPrice;
        this.stockQuantity = stockQuantity;
        this.assignProduct(product);
    }

    public void assignProduct(Product product) {
        this.product = product;
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
