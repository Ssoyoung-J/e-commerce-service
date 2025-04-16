package kr.hhplus.be.server.infrastructure.product;

import kr.hhplus.be.server.domain.product.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailJpaRepository extends JpaRepository<ProductDetail, Long> {
}
