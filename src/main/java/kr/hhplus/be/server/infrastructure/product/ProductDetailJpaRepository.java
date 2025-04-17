package kr.hhplus.be.server.infrastructure.product;

import kr.hhplus.be.server.domain.product.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDetailJpaRepository extends JpaRepository<ProductDetail, Long> {
    List<ProductDetail> findByProductId(Long productId);
}
