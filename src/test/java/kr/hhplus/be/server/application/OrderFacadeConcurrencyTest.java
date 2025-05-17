package kr.hhplus.be.server.application;

import kr.hhplus.be.server.application.order.OrderCriteria;
import kr.hhplus.be.server.application.order.OrderFacade;
import kr.hhplus.be.server.domain.order.OrderInfo;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.product.*;
import kr.hhplus.be.server.test.RandomGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("주문 Facade 동시성 테스트")
public class OrderFacadeConcurrencyTest {

    @Autowired
    private OrderFacade orderFacade;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Nested
    @DisplayName("order 테스트")
    class OrderTest {
        @Test
        @DisplayName("주문 동시성 테스트")
        void orderFacadeConcurrencyTest() throws InterruptedException {
            // given
            Product product = RandomGenerator.getFixtureMonkey()
                    .giveMeBuilder(Product.class)
                    .set("productId", null)
                    .set("price", 10000L)
                    .build()
                    .sample();
            Product savedProduct = productRepository.save(product);

            ProductDetail option = RandomGenerator.getFixtureMonkey()
                    .giveMeBuilder(ProductDetail.class)
                    .set("productDetailId", null)
                    .set("productId", savedProduct.getProductId())
                    .build()
                    .sample();
            List<ProductDetail> savedProductOption = productRepository.saveProductDetails(List.of(option));

            long stockQuantity = 5;

            Stock stock = RandomGenerator.getFixtureMonkey()
                    .giveMeBuilder(Stock.class)
                    .set("stockId", null)
                    .set("productOptionId", savedProductOption.get(0).getProductDetailId())
                    .set("quantity", stockQuantity)
                    .build()
                    .sample();
            productRepository.saveStocks(List.of(stock));

            OrderCriteria.OrderItem item = OrderCriteria.OrderItem.builder()
                    .productDetailId(option.getProductDetailId())
                    .quantity(1)
                    .build();

            long userId = 1L;
            OrderCriteria.OrderDetail detail = OrderCriteria.OrderDetail.builder()
                    .userId(userId)
                    .orderItems(List.of(item))
                    .build();

            int threadCount = 5;

            ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
            CountDownLatch latch = new CountDownLatch(threadCount);
            AtomicInteger successCount = new AtomicInteger(0);
            AtomicInteger failureCount = new AtomicInteger(0);

            // when
            for (int i = 0; i < threadCount; i++) {
                executorService.execute(() -> {
                    try {
                        orderFacade.order(detail);
                        successCount.incrementAndGet();
                    } catch (Throwable t) {
                        failureCount.incrementAndGet();
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();
            executorService.shutdown();

            // then
            assertThat(successCount.get()).isEqualTo(threadCount);

            ProductInfo.ProductDetail productById = productService.getProductById(product.getProductId());
//            List<OrderInfo.OrderHistory> byUserId = orderService.findOrdersByUserId(userId);

            assertThat(productById).isNotNull();
            assertThat(productById.getDetails().get(0).getStockQuantity()).isEqualTo(0);
//            assertThat(byUserId).hasSize(threadCount);
        }
    }
}
