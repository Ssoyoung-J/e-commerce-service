package kr.hhplus.be.server.domain.product;

import com.querydsl.core.annotations.QueryProjection;

public interface ProductQuery {

    record ProductOptionDetail (
            long productDetailId,
            String optionName,
            int stockQuantity
    ) {
        @QueryProjection
        public ProductOptionDetail {}

        public ProductInfo.ProductOptionDetail to() {
            return ProductInfo.ProductOptionDetail.builder()
                    .productDetailId(productDetailId)
                    .optionName(optionName)
                    .stockQuantity(stockQuantity)
                    .build();
        }
    }

    record PriceOption (
            long productDetailId,
            long productPrice,
            int stockQuantity
    ) {
        @QueryProjection
        public PriceOption {}

        public ProductInfo.PriceOption to() {
            return ProductInfo.PriceOption.builder()
                    .productDetailId(productDetailId)
                    .productPrice(productPrice)
                    .stockQuantiity(stockQuantity)
                    .build();
        }
    }

    record BestSelling (
            long productId,
            String productName,
            long salesCount
    ) {
        @QueryProjection
        public BestSelling {}

        public ProductInfo.BestSelling to() {
            return ProductInfo.BestSelling.builder()
                    .productId(productId)
                    .productName(productName)
                    .salesCount(salesCount)
                    .build();
        }
    }
}
