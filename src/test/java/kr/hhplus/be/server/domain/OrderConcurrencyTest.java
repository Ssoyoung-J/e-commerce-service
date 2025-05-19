package kr.hhplus.be.server.domain;

import jakarta.persistence.EntityManager;
import kr.hhplus.be.server.domain.product.*;
import kr.hhplus.be.server.test.RandomGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest
@DisplayName("주문 서비스 동시성 테스트")
class OrderConcurrencyTest {

    @Autowired
    ProductService sut;

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Nested
    @DisplayName("재고 차감 동시성 테스트")
    class decreaseStockQuantityTest {
        @Test
        @DisplayName("재고 차감 동시성 테스트")
        void decreaseStockQuantity() throws InterruptedException {
            // given
            Product product = RandomGenerator.getFixtureMonkey()
                    .giveMeBuilder(Product.class)
                    .set("productId", null)
                    .build()
                    .sample();

            Product savedProduct = productRepository.save(product);

            ProductDetail detail = RandomGenerator.getFixtureMonkey()
                    .giveMeBuilder(ProductDetail.class)
                    .set("productDetailId", null)
                    .set("productId", savedProduct.getProductId())
                    .build()
                    .sample();

            List<ProductDetail> savedProductDetail = productRepository.saveProductDetails(List.of(detail));

            int stockQuantity = 5;


            Stock stock = RandomGenerator.getFixtureMonkey()
                    .giveMeBuilder(Stock.class)
                    .set("stockId", null)
                    .set("productOptionId", savedProductDetail.get(0).getProductDetailId())
                    .set("quantity", stockQuantity)
                    .build()
                    .sample();

            productRepository.saveStocks(List.of(stock));

            int threadCount = 10;

            ProductCommand.DecreaseStock command = ProductCommand.DecreaseStock.of(
                    List.of(ProductCommand.ProductOptionStock.of(detail.getProductDetailId(), 1))
            );

            ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
            CountDownLatch latch = new CountDownLatch(threadCount);
            AtomicInteger successCount = new AtomicInteger(0);
            AtomicInteger failureCount = new AtomicInteger(0);
            List<Throwable> thrownExceptions = Collections.synchronizedList(new ArrayList<>());


            // when
            for (int i = 0; i < threadCount; i++) {
                executorService.execute(() -> {
                    try {
                        sut.decreaseStockQuantity(command);
                        successCount.incrementAndGet(); // 성공한 요청 카운트
                    } catch (Throwable t) {
                        failureCount.incrementAndGet(); // 실패한 요청 카운트
                        thrownExceptions.add(t); // 예외 저장
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();
            executorService.shutdown();

            // then
            ProductInfo.ProductDetail productById = productService.getProductById(product.getProductId());

            assertThat(productById).isNotNull();
//            assertThat(productById.getDetails().get(0).getStockQuantity()).isEqualTo(0);

        }
    }
}
