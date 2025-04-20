package kr.hhplus.be.server.domain.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    // 재고 확인
    public boolean validateStock(Long productDetailId, Long requiredQuantity) {
        ProductDetail productDetail = productDetailRepository.findById(productDetailId);
        return productDetail.hasSufficientStock(requiredQuantity);
    }

    // 재고 차감 동시성 이슈 방지를 위한 Lock 설정
    private static final ConcurrentHashMap<Long, Lock> productQuantityLocks = new ConcurrentHashMap<>();

    // 재고 차감
    public void decreaseStock(Long productDetailId, Long requiredQuantity) {
        // 상품별 재고 Lock 가져오기
        Lock lock = productQuantityLocks.computeIfAbsent(productDetailId, k -> new ReentrantLock());
        
        // 특정 상품 재고에 대한 작업이 동시에 실행되지 않도록 보장
        lock.lock();

        try {
            ProductDetail productDetail = productDetailRepository.findById(productDetailId);

            productDetail.decreaseStock(requiredQuantity);
        } finally {
            lock.unlock();
            productQuantityLocks.remove(productDetailId);
        }
    }



}
