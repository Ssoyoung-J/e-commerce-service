package kr.hhplus.be.server.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProductDetailRepository{

    // 단일 ProductDetail 조회
    ProductDetail findById(Long productId);

    // 전체 ProductDetail 조회
//    List<ProductDetail> getProductDetails(Long id);
}
