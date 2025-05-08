package kr.hhplus.be.server.domain.product;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductInfo {

    @Getter
    @Builder
    public static class ProductDetail {
        private long productId;
        private String productName;
        private String brand;
        private List<ProductOptionDetail> details;

        public static ProductDetail from(Product product, List<ProductOptionDetail> details) {
            return ProductDetail.builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .brand(product.getBrand())
                    .details(details)
                    .build();
        }
    }


    @Getter
    @Builder
    public static class ProductOptionDetail {
        private long productDetailId;
        private String optionName;
        private long productPrice;
        private int stockQuantity;
    }

    @Getter
    @Builder
    public static class PriceOption {
        private long productDetailId;
        private long productPrice;
        private int stockQuantiity;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    public static class ProductSalesData {
        private List<BestSelling> bestSellings;

        public ProductSalesData(List<BestSelling> bestSellings) {
            this.bestSellings = bestSellings;
        }
    }

    @Getter
    public static class CheckedProductStock {
        private final Long productDetailId;
        private final Long stockQuantity;

        @Builder
        public CheckedProductStock(Long productDetailId, Long stockQuantity) {
            this.productDetailId = productDetailId;
            this.stockQuantity = stockQuantity;
        }

        public static CheckedProductStock of (Long productDetailId, Long stockQuantity) {
            return new CheckedProductStock(productDetailId, stockQuantity);
        }
    }


    @Getter
    @Builder
    public static class BestSelling {
        private final long productId;
        private final String productName;
        private final long salesCount;

        public BestSelling(long productId, String productName, long salesCount) {
            this.productId = productId;
            this.productName = productName;
            this.salesCount = salesCount;
        }
    }

    @Getter
    @Builder
    public static class DecreaseStock {
        private List<ProductOptionStock> optionStocks;
    }

    @Getter
    @Builder
    public static class ProductOptionStock {
        private long productDetailId;
        private long stockId;
        private int quantity;

        public static ProductOptionStock from(Stock stock) {
            return ProductOptionStock.builder()
                    .productDetailId(stock.getProductDetailId())
                    .stockId(stock.getStockId())
                    .quantity(stock.getQuantity())
                    .build();
        }
    }

}
