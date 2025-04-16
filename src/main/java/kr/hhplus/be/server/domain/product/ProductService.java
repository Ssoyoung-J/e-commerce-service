package kr.hhplus.be.server.domain.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductDetailRepository productDetailRepository;

    public boolean validateStock(Long productDetailId, Long requiredQuantity) {
        ProductDetail productDetail = productDetailRepository.findById(productDetailId)
                .orElseThrow(() -> new EntityNotFoundException("해당 상품 옵션을 찾을 수 없습니다."));
        return productDetail.hasSufficientStock(requiredQuantity);
    }

    public void decreaseStock(Long productDetailId, Long requiredQuantity) {
        ProductDetail productDetail = productDetailRepository.findById(productDetailId)
                .orElseThrow(() -> new EntityNotFoundException("해당 상품 옵션을 찾을 수 없습니다."));
        productDetail.decreaseStock(requiredQuantity);
    }
}
