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

        public static Find of(Long productId) {
            return Find.builder()
                    .productId(productId)
                    .build();}
    }
}
