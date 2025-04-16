package kr.hhplus.be.server.infrastructure.product;

import kr.hhplus.be.server.domain.product.ProductDetail;
import kr.hhplus.be.server.domain.product.ProductDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductDetailRepositoryImpl implements ProductDetailRepository {

    private final ProductDetailJpaRepository productDetailJpaRepository;

    @Override
    public ProductDetail findById(Long productId) {
        return productDetailJpaRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품 상세 정보가 존재하지 않습니다."));
    }
}
