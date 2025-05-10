package kr.hhplus.be.server.presentation.product;

import kr.hhplus.be.server.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class ProductScheduler {

    private final ProductService productService;

    @Scheduled(cron = "0 50 23 * * *") // 매일 23시 50분 실행
    public void refreshBestSellingProductsCache() {
        LocalDate days = LocalDate.now().minusDays(3);
        long limit = 5;

        productService.refreshBestSellingProductCache(days, limit);
    }
}
