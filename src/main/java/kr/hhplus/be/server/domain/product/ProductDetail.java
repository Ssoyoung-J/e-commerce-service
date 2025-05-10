package kr.hhplus.be.server.domain.product;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseEntity;
import lombok.*;

import java.util.Optional;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProductDetail extends BaseEntity {

    /**
     *  상품 옵션 ID
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_detail_id", nullable = false)
    private Long productDetailId;

    /**
     *  상품 ID
     * */
    @Column(name = "product_id", nullable = false)
    private Long productId;

    /**
     *  상품 옵션명
     * */
    @Column(name = "option_name", nullable = false)
    private String optionName;

    /**
     *  상품 가격
     * */
    @Column(name = "product_price", nullable = false)
    private Long productPrice;

}
