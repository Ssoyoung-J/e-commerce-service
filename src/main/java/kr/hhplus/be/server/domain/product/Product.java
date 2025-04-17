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

    private List<ProductDetail> details = new ArrayList<>();

    @Builder
    public Product(String brand, String productName, List<ProductDetail> details) {
        this.brand = brand;
        this.productName = productName;
    }

    public static Product create(String brand, String productName) {
        return Product.builder()
                .brand(brand)
                .productName(productName)
                .build();
    }

}
