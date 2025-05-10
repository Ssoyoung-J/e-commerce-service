package kr.hhplus.be.server.domain.product;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.Comparator.comparing;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductCommand {

    @Getter
    @Builder
    public static class ProductDetailIds {
        private List<Long> productDetailIds;

        private static ProductDetailIds of(List<Long> productDetailIds) {
            return ProductDetailIds.builder()
                    .productDetailIds(productDetailIds)
                    .build();}
    }
//
//    @Getter
//    public static class FindDetail {
//        private final Long productDetailId;
//        private final int requiredQuantity;
//
//        @Builder
//        public FindDetail(Long productDetailId, int requiredQuantity) {
//            this.productDetailId = productDetailId;
//            this.requiredQuantity = requiredQuantity;
//        }
//
//        public static FindDetail of(Long productDetailId, int requiredQuantity) {
//            return FindDetail.builder()
//                    .productDetailId(productDetailId)
//                    .requiredQuantity(requiredQuantity)
//                    .build();}
//    }

    @Getter
    @Builder
    public static class DecreaseStock {
        private List<ProductOptionStock> optionStocks;

        public static DecreaseStock of(List<ProductOptionStock> optionStocks) {
                return DecreaseStock.builder()
                        .optionStocks(optionStocks.stream()
                                .sorted(comparing(ProductOptionStock::getProductDetailId))
                                .toList())
                        .build();
            }
    }

    @Getter
    @Builder
    public static class ProductOptionStock {
        private long productDetailId;
        private int quantity;

        public static ProductInfo.ProductOptionStock of(long productDetailId, int quantity) {
            return ProductInfo.ProductOptionStock.builder()
                    .productDetailId(productDetailId)
                    .quantity(quantity)
                    .build();
        }
    }

}
