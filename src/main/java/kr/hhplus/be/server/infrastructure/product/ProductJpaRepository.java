package kr.hhplus.be.server.infrastructure.product;

import kr.hhplus.be.server.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    // 상품 정보 생성
//    Product save(Product product);

    // 상품 정보 조회
//    Product getProduct(Long productId);

    // 상품 목록 조회
//    List<Product> getProducts();



}
