package kr.hhplus.be.server.domain.product;


import java.time.LocalDate;
import java.util.List;

public interface ProductRepository {

    // 상품 정보 생성
    Product save(Product product);

    // 상품 정보 조회
    Product findById(Long productId);

    // 상품 전체 정보 조회
    List<Product> findAll();

    // 상품 옵션 목록 조회 - productId
    List<ProductQuery.ProductOptionDetail> findProductDetailsByProductId(long productId);

    // 상품 옵션 목록 조회 - productDetailId
    List<ProductQuery.PriceOption> findProductOptionsById(List<Long> optionIds);

    // 인기 상품 조회
    List<ProductQuery.BestSelling> findBestSellingProducts(LocalDate days, long limit);

}
