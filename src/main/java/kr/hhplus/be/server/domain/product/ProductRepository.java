package kr.hhplus.be.server.domain.product;


import java.util.List;

public interface ProductRepository {

    // 상품 정보 생성
    Product save(Product product);

    // 상품 정보 조회
    Product findById(Long productId);

    // 상품 전체 정보 조회
    List<Product> findAll();


}
