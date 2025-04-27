package kr.hhplus.be.server.domain.product;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductCommand {

    @Getter
    public static class Find {
        private final Long productId;

        @Builder
        public Find(Long productId) {
            this.productId = productId;
        }

        private static Find of(Long productId) {
            return Find.builder()
                    .productId(productId)
                    .build();}
    }

    @Getter
    public static class FindDetail {
        private final Long productDetailId;
        private final Long requiredQuantity;

        @Builder
        public FindDetail(Long productDetailId, Long requiredQuantity) {
            this.productDetailId = productDetailId;
            this.requiredQuantity = requiredQuantity;
        }

        public static FindDetail of(Long productDetailId, Long requiredQuantity) {
            return FindDetail.builder()
                    .productDetailId(productDetailId)
                    .requiredQuantity(requiredQuantity)
                    .build();}
    }
}
