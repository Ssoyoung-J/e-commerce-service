package kr.hhplus.be.server.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductDetailRepository{

    ProductDetail findById(Long productDetailId);

    List<ProductDetail> findByProductId(Long productId);




}
