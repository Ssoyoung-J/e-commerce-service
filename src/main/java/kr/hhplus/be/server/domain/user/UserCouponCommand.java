package kr.hhplus.be.server.domain.user;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCouponCommand {

    @Getter
    public static class Publish {
        private final Long userId;
        private final Long couponId;

        @Builder
        private Publish(Long userId, Long couponId) {
            this.userId = userId;
            this.couponId = couponId;
        }

        public static Publish of(Long userId, Long couponId) {
            return Publish.builder()
                    .userId(userId)
                    .couponId(couponId)
                    .build();
        }

    }


    @Getter
    public static class UsableCoupon {
        private final Long userId;
        private final Long couponId;

        @Builder
        private UsableCoupon(Long userId, Long couponId) {
            this.userId = userId;
            this.couponId = couponId;
        }

        public static UsableCoupon of(Long userId, Long couponId) {
            return UsableCoupon.builder()
                    .userId(userId)
                    .couponId(couponId)
                    .build();
        }

    }
}
