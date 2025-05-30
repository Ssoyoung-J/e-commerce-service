package kr.hhplus.be.server.domain.coupon;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CouponInfo {

    @Getter
    @Builder
    public static class IssuedCoupon {
        private long userCouponId;
        private long couponId;
        private String couponName;
        private long discount;
        private LocalDateTime expiredAt;

        public static IssuedCoupon from(UserCoupon userCoupon, Coupon coupon) {
            return IssuedCoupon.builder()
                    .userCouponId(userCoupon.getUserCouponId())
                    .couponId(coupon.getCouponId())
                    .couponName(coupon.getCouponName())
                    .discount(coupon.getDiscount())
                    .expiredAt(coupon.getExpiredAt())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class UserOwnedCoupon {
        private long userCouponId;
        private long couponId;
        private long userId;
        private String couponName;
        private long discount;
        private LocalDateTime expiredAt;
        private UserCoupon.UserCouponStatus userCouponStatus;
    }
}
