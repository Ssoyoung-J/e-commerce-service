package kr.hhplus.be.server.domain.coupon;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CouponRepository {

    // 쿠폰 저장
    Coupon save(Coupon coupon);

    // 사용자 쿠폰 정보 목록 저장
    List<UserCoupon> saveUserCoupons(List<UserCoupon> userCoupons);

    // 쿠폰 조회
    Coupon findById(Long couponId);

    // 사용자 쿠폰 목록 조회
    List<UserCoupon> findUserCouponsById(List<Long> userCouponIds);

    // 쿠폰 조회(비관적 락)
    Optional<Coupon> findByIdForUpdate(Long couponId);

    // 쿠폰 중복 발급 체크
    boolean existsByUserIdAndCouponId(Long userId, Long couponId);

    //  사용자 쿠폰 정보 저장
    UserCoupon saveUserCoupon(UserCoupon userCoupon);

    // 사용자 쿠폰 목록 조회 - 사용자 Id
    List<CouponQuery.UserOwnedCoupon> findAllOwnedCouponsByUserId(long userId);

    // 사용자 쿠폰 목록 조회 - 사용자 쿠폰 Id 목록
    List<CouponQuery.UserOwnedCoupon> findUserCouponsByIds(List<Long> userCouponIds);

    // 만료된 사용자 쿠폰 목록 조회
    List<UserCoupon> findExpiredUserCoupons(LocalDateTime expiredAt);
}