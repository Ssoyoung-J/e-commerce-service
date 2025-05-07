package kr.hhplus.be.server.domain.product;

import jakarta.persistence.EntityNotFoundException;
import kr.hhplus.be.server.domain.order.OrderCommand;
import kr.hhplus.be.server.domain.order.OrderInfo;
import kr.hhplus.be.server.domain.order.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public boolean hasSufficientStock(ProductCommand.FindDetail command) {
        ProductDetail detail = productDetailRepository.findById(command.getProductDetailId());
        return detail.hasSufficientStock(command.getRequiredQuantity());
    }

    // 주문 가능 상품 재고 조회
    public List<OrderCommand.OrderItem> filterAvailableItems(List<OrderCommand.OrderItem> items) {
        List<OrderCommand.OrderItem> availableItems =  items.stream()
                .filter(item -> {
                    ProductCommand.FindDetail command = new ProductCommand.FindDetail(
                            item.getProductDetailId(),
                            item.getProductQuantity()
                    );
                    return hasSufficientStock(command);
                }).toList();

        if (availableItems.isEmpty()) {
            throw new RuntimeException("주문 실패: 모든 상품의 재고가 부족합니다.");
        }

        return availableItems;
    }
    

    // 재고 차감 동시성 이슈 방지를 위한 Lock 설정
    private static final ConcurrentHashMap<Long, Lock> productQuantityLocks = new ConcurrentHashMap<>();

    // 재고 차감
    public void decreaseStock(ProductCommand.FindDetail command) {

        // 상품별 재고 Lock 가져오기
        Lock lock = productQuantityLocks.computeIfAbsent(command.getProductDetailId(), k -> new ReentrantLock());

        // 특정 상품 재고에 대한 작업이 동시에 실행되지 않도록 보장
        lock.lock();
        try {
            // 상품 조회
            ProductDetail productDetail = productDetailRepository.findById(command.getProductDetailId());

            // 상품 재고 확인
            productDetail.decreaseStock(command.getRequiredQuantity());
        } finally {
            lock.unlock();
        }

    }

//    @Transactional(readOnly = true)
//    public List<ProductInfo.BestSelling> getBestSellingProducts(LocalDateTime days, long limit) {
//
//    }



}
