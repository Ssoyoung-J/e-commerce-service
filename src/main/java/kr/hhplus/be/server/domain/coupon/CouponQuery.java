package kr.hhplus.be.server.domain.coupon;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public interface CouponQuery {

    record UserOwnedCoupon(
            long userCouponId,
            long couponId,
            long userId,
            String couponName,
            long discount,
            LocalDateTime expiredAt,
            UserCoupon.UserCouponStatus userCouponStatus
    ) {
        @QueryProjection
        public UserOwnedCoupon {}

        public CouponInfo.UserOwnedCoupon to() {
            return CouponInfo.UserOwnedCoupon.builder()
                    .userCouponId(userCouponId)
                    .couponId(couponId)
                    .userId(userId)
                    .couponName(couponName)
                    .discount(discount)
                    .expiredAt(expiredAt)
                    .userCouponStatus(userCouponStatus)
                    .build();
        }
    }
}
