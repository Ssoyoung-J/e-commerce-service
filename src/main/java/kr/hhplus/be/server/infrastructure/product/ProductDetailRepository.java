package kr.hhplus.be.server.infrastructure.product;

import kr.hhplus.be.server.domain.product.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    // 단일 ProductDetail 조회
    Optional<ProductDetail> findById(Long productId);

    // 전체 ProductDetail 조회
    List<ProductDetail> findByProduct_ProductId(Long productId);
}
