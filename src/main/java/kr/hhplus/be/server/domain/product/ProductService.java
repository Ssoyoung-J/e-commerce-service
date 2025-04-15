package kr.hhplus.be.server.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductDetailRepository productDetailRepository;

    /**
     * 상품 옵션의 재고를 확인한다.
     * @param productDetailId 상품 상세 ID
     * @param requiredQuantity 필요한 수량
     */
    public void validateStock(Long productDetailId, Long requiredQuantity) {
        Long stockQuantity = productDetailRepository.findById(productDetailId)
                .map(ProductDetail::getStockQuantity)
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));

        if (stockQuantity == null) {
            throw new IllegalArgumentException("해당 상품 옵션이 존재하지 않습니다.");
        }

        if (stockQuantity < requiredQuantity) {
            throw new IllegalStateException("상품 재고가 부족합니다.");
        }
    }
}
