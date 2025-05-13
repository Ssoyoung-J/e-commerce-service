package kr.hhplus.be.server.domain.coupon;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponCommand {

    @Getter
    @Builder
    public static class IssuedCoupon {
        long userId;
        long couponId;

        public UserCoupon toEntity() {
            return UserCoupon.builder()
                    .userId(userId)
                    .couponId(couponId)
                    .userCouponStatus(UserCoupon.UserCouponStatus.NOTUSED)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class CouponAction {
        private CouponActionType type;
        private List<UserOwnedCoupon> userOwnedCoupons;

        public static CouponAction of(CouponActionType type, List<Long> userCouponIds) {
            List<UserOwnedCoupon> ownedCoupons = userCouponIds.stream()
                    .map(id -> UserOwnedCoupon.builder()
                            .userCouponId(id)
                            .build())
                    .toList();

            return CouponAction.builder()
                    .type(type)
                    .userOwnedCoupons(ownedCoupons)
                    .build();
        }
    }

    public enum CouponActionType {
        USE, // 쿠폰 사용
        CANCEL // 쿠폰 사용 취소
    }

    @Getter
    @Builder
    public static class UserOwnedCoupon {
        private long userCouponId;
    }

    @Getter
    @Builder
    public static class FindUserCoupons {
        private List<Long> userCouponIds;

        public static FindUserCoupons of(List<Long> userCouponIds) {
            return FindUserCoupons.builder()
                    .userCouponIds(userCouponIds)
                    .build();
        }
    }
}
