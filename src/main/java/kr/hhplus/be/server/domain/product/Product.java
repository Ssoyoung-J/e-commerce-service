package kr.hhplus.be.server.domain.product;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseEntity;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String brand;

    private String productName;

    // 상품 옵션과 연관관계 설정
    @OneToMany(mappedBy ="product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductDetail> details = new ArrayList<>();

    @Builder
    public Product(Long productId, String brand, String productName, List<ProductDetail> details) {
        this.productId = productId;
        this.brand = brand;
        this.productName = productName;
    }

    // 상품 생성 시 양방향 관계 유지를 위한 상품 옵션 추가
    public void addDetails(List<ProductDetail> details) {
        for(ProductDetail detail : details) {
            this.details.add(detail);
            detail.setProduct(this);
        }
    }

    public static Product create(Long productId, String brand, String productName) {
        return Product.builder()
                .productId(productId)
                .brand(brand)
                .productName(productName)
                .build();
    }

    public void decreaseStock(Long detailId, Long quantity) {
        ProductDetail detail = details.stream()
                .filter(d -> d.getProductDetailId().equals(detailId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("옵션이 존재하지 않습니다."));
        detail.decreaseStock(quantity);
    }


}
