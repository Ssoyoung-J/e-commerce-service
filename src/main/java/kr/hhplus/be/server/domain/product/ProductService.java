package kr.hhplus.be.server.domain.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;

    // 전체 상품 조회
    public ProductInfo.ProductList findAll() {
        List<Product> products = productRepository.findAll();

        List<ProductInfo.ProductInfoList> productInfoLists = products.stream().map(product -> {
            List<ProductDetail> productDetails = productDetailRepository.findByProductId(product.getProductId());
            return ProductInfo.ProductInfoList.of(product, productDetails);
        }).toList();

        return ProductInfo.ProductList.of(productInfoLists);
    }

    // 단건 상품 조회
    public ProductInfo.ProductInfoList findProduct(ProductCommand.Find command) {

        Product product = productRepository.findById(command.getProductId());

        List<ProductDetail> produtDetails = productDetailRepository.findByProductId(command.getProductId());

        return ProductInfo.ProductInfoList.of(product, produtDetails);

    }
// 재고 차감 로직 관련하여 고민 필요
//    public ProductInfo.CheckedProductStock reductStock(Long )

    public boolean validateStock(Long productDetailId, Long requiredQuantity) {
        ProductDetail productDetail = productDetailRepository.findById(productDetailId);
        return productDetail.hasSufficientStock(requiredQuantity);
    }

    public void decreaseStock(Long productDetailId, Long requiredQuantity) {
        ProductDetail productDetail = productDetailRepository.findById(productDetailId);
        productDetail.decreaseStock(requiredQuantity);
    }
}
