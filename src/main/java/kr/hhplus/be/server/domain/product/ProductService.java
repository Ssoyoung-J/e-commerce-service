package kr.hhplus.be.server.domain.product;

import jakarta.persistence.EntityNotFoundException;
import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.domain.order.OrderCommand;
import kr.hhplus.be.server.domain.order.OrderInfo;
import kr.hhplus.be.server.domain.order.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
@Service
public class ProductService {

    private static final String CACHE_NAME = "bestSellingProducts";

    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;

    // 상품 옵션 조회 - 상품 ID
    public ProductInfo.ProductDetail getProductById(long productId) {
        Product detail = productRepository.findById(productId);

        List<ProductQuery.ProductOptionDetail> optionProjections = productRepository.findProductDetailsByProductId(detail.getProductId());

        List<ProductInfo.ProductOptionDetail> options = optionProjections.stream()
                .map(ProductQuery.ProductOptionDetail::to)
                .toList();
        return ProductInfo.ProductDetail.from(detail, options);
    }

    // 상품 옵션 목록 조회
    public List<ProductInfo.PriceOption> getOptionsById(ProductCommand.ProductDetailIds command) {
        List<Long> optionIds = command.getProductDetailIds();

        List<ProductQuery.PriceOption> options = productRepository.findProductOptionsById(optionIds);

        if (options.isEmpty()) {
         throw new BusinessException(400, "해당 상품 옵션 목록을 찾지 못했습니다.");
        }

        return options.stream()
                .map(ProductQuery.PriceOption::to)
                .toList();

    }

    // 재고 확인
//    public boolean hasSufficientStock(ProductCommand.FindDetail command) {
//        ProductDetail detail = productDetailRepository.findById(command.getProductDetailId());
//        return detail.hasSufficientStock(command.getRequiredQuantity());
//    }
//
//    // 주문 가능 상품 재고 조회
//    public List<OrderCommand.OrderItem> filterAvailableItems(List<OrderCommand.OrderItem> items) {
//        List<OrderCommand.OrderItem> availableItems =  items.stream()
//                .filter(item -> {
//                    ProductCommand.FindDetail command = new ProductCommand.FindDetail(
//                            item.getProductDetailId(),
//                            item.getProductQuantity()
//                    );
//                    return hasSufficientStock(command);
//                }).toList();
//
//        if (availableItems.isEmpty()) {
//            throw new RuntimeException("주문 실패: 모든 상품의 재고가 부족합니다.");
//        }
//
//        return availableItems;
//    }
//
//
//    // 재고 차감 동시성 이슈 방지를 위한 Lock 설정
//    private static final ConcurrentHashMap<Long, Lock> productQuantityLocks = new ConcurrentHashMap<>();
//
//    // 재고 차감
//    public void decreaseStock(ProductCommand.FindDetail command) {
//
//        // 상품별 재고 Lock 가져오기
//        Lock lock = productQuantityLocks.computeIfAbsent(command.getProductDetailId(), k -> new ReentrantLock());
//
//        // 특정 상품 재고에 대한 작업이 동시에 실행되지 않도록 보장
//        lock.lock();
//        try {
//            // 상품 조회
//            ProductDetail productDetail = productDetailRepository.findById(command.getProductDetailId());
//
//            // 상품 재고 확인
//            productDetail.decreaseStock(command.getRequiredQuantity());
//        } finally {
//            lock.unlock();
//        }
//
//    }

    // 인기 상품 조회
    @Cacheable(
            cacheNames = CACHE_NAME,
            key = "#days.toString().replace('-', '')",
            unless = "#result == null or #result.bestSellings == null or #result.bestSellings.empty"
    )
    @Transactional(readOnly = true)
    public ProductInfo.ProductSalesData getBestSellingProducts(LocalDate days, long limit) {
        List<ProductInfo.BestSelling> bestSellings = productRepository.findBestSellingProducts(days, limit).stream()
                .map(ProductQuery.BestSelling::to)
                .toList();

        return ProductInfo.ProductSalesData.builder()
                .bestSellings(bestSellings)
                .build();
    }

    @CachePut(
            cacheNames = CACHE_NAME,
            key = "#days.toString().replace('-','')"
    )
    public ProductInfo.ProductSalesData refreshBestSellingProductCache(LocalDate days, long limit) {
        return new ProductInfo.ProductSalesData(productRepository.findBestSellingProducts(days, limit).stream()
                .map(ProductQuery.BestSelling::to)
                .toList());
    }



}
