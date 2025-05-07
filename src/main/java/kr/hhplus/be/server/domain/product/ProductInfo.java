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
    public static class ProductList {

        private final List<ProductInfoList> products;

        @Builder
        public ProductList(List<ProductInfoList> products) {
            this.products = products;
        }

        public static ProductList of(List<ProductInfoList> products) {
            return new ProductList(products);
        }
    }


    @Getter
    public static class ProductInfoList {

        private final Long productId;
        private final String brand;
        private final String productName;
        private final List<ProductDetail> details;

        @Builder
        public ProductInfoList(Long productId, String brand, String productName, List<ProductDetail> details) {
            this.productId = productId;
            this.brand = brand;
            this.productName = productName;
            this.details = details;
        }

        public static ProductInfoList of(Product product, List<ProductDetail> details) {
            return new ProductInfoList(product.getProductId(), product.getBrand(), product.getProductName(), details.stream().map(d ->
                    new ProductDetail(d.getProductDetailId(), d.getOptionName(), d.getProductPrice(), d.getQuantity(), d.getProduct())).toList());
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
    }

}
