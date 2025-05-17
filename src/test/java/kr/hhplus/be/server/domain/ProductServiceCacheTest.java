package kr.hhplus.be.server.domain;

import kr.hhplus.be.server.domain.product.ProductQuery;
import kr.hhplus.be.server.domain.product.ProductRepository;
import kr.hhplus.be.server.domain.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("상품 캐시 테스트")
class ProductServiceCacheTest {

    @Autowired
    ProductService sut;

    @MockitoBean
    ProductRepository productRepository;

    @Autowired
    CacheManager cacheManager;

    @Nested
    @DisplayName("인기 상품 데이터가 캐시에 존재할 경우")
    class GetBestSellingProducts {
        @Test
        void success() {
            // given
            List<ProductQuery.BestSelling> items = List.of(
                    new ProductQuery.BestSelling(1L, "상품1", 10),
                    new ProductQuery.BestSelling(2L, "상품2", 20),
                    new ProductQuery.BestSelling(3L, "상품3", 30),
                    new ProductQuery.BestSelling(4L, "상품4", 40),
                    new ProductQuery.BestSelling(5L, "상품5", 50)
            );

            LocalDate days = LocalDate.now().minusDays(3);
            long limit = 3L;
            when(productRepository.findBestSellingProducts(eq(days), eq(limit)))
                    .thenReturn(items);
            sut.refreshBestSellingProductCache(days, limit);

            ProductService productService = spy(sut);

            // when
            productService.getBestSellingProducts(days, limit);

            // then
            verify(productRepository,times(1))
                    .findBestSellingProducts(eq(days), eq(limit));
        }
    }
}
