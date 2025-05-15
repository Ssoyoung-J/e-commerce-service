package kr.hhplus.be.server.domain.coupon;

import java.util.List;

public interface UserCouponRepository {

    UserCoupon save(UserCoupon userCoupon);

    UserCoupon findByUserIdAndCouponId(Long userId, Long couponId);

    UserCoupon findById(Long userCouponId);

    List<UserCoupon> findByUserId(Long userId);

}
