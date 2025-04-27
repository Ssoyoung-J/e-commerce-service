package kr.hhplus.be.server.domain.coupon;

import java.util.Optional;

public interface CouponRepository {

    // 쿠폰 저장
    Coupon save(Coupon coupon);

    // 쿠폰 조회
    Coupon findById(Long couponId);

}
