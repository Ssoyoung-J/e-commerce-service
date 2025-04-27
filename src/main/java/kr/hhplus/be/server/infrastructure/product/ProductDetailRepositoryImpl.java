package kr.hhplus.be.server.infrastructure.product;

import kr.hhplus.be.server.domain.product.ProductDetail;
import kr.hhplus.be.server.domain.product.ProductDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductDetailRepositoryImpl implements ProductDetailRepository {

    private final ProductDetailJpaRepository productDetailJpaRepository;

    @Override
    public ProductDetail findById(Long productDetailId) {
        return productDetailJpaRepository.findById(productDetailId).orElseThrow(() -> new IllegalArgumentException("해당 상품 옵션이 존재하지 않습니다."));
    }

    @Override
    public List<ProductDetail> findByProductId(Long productId) {
        return productDetailJpaRepository.findByProductId(productId);
    }

//    @Override
//    public List<ProductDetail> findByProductIdIn(List<Long> productIds) {
//        return productDetailJpaRepository.findByProductIdIn(productIds);
//    }

}
