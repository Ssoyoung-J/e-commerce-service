package kr.hhplus.be.server.domain.product;



public interface ProductRepository {

    // 상품 정보 생성
    Product save(Product product);

    // 상품 정보 조회
    Product findById(Long productId);


}
