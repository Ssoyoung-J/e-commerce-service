package kr.hhplus.be.server.infrastructure.coupon;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
import kr.hhplus.be.server.domain.coupon.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static kr.hhplus.be.server.domain.coupon.QCoupon.coupon;
import static kr.hhplus.be.server.domain.coupon.QUserCoupon.userCoupon;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;
    private final UserCouponJpaRepository userCouponJpaRepository;
    private final JPAQueryFactory queryFactory;

    // 쿠폰 저장
    @Override
    public Coupon save(Coupon coupon) {
        return couponJpaRepository.save(coupon);
    }

    // 쿠폰 조회
    @Override
    public Coupon findById(Long couponId) {
        return couponJpaRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException("쿠폰이 존재하지 않습니다."));
    }

    // 사용자 쿠폰 목록 조회
    @Override
    public List<UserCoupon> findUserCouponsById(List<Long> userCouponIds) {
        return userCouponJpaRepository.findAllById(userCouponIds);
    }

    // 쿠폰 조회(비관적 락)
    @Override
    public Optional<Coupon> findByIdForUpdate(Long couponId) {
        return couponJpaRepository.findByIdForUpdate(couponId);
    }

    // 쿠폰 중복 발급 체크
    @Override
    public boolean existsByUserIdAndCouponId(Long userId, Long couponId) {
        return userCouponJpaRepository.existsByUserIdAndCouponId(userId, couponId);

    }

    // 사용자 쿠폰 정보 저장
    @Override
    public UserCoupon saveUserCoupon(UserCoupon userCoupon) {
        return userCouponJpaRepository.save(userCoupon);
    }

    // 사용자 쿠폰 정보 목록 저장
    @Override
    public List<UserCoupon> saveUserCoupons(List<UserCoupon> userCoupons) {
        return userCouponJpaRepository.saveAll(userCoupons);
    }

    // 사용자 쿠폰 목록 조회 - 사용자 Id
    @Override
    public List<CouponQuery.UserOwnedCoupon> findAllOwnedCouponsByUserId(long userId) {
        return queryFactory
                .select(new QCouponQuery_UserOwnedCoupon(
                        userCoupon.userCouponId,
                        coupon.couponId,
                        userCoupon.userId,
                        coupon.couponName,
                        coupon.discount,
                        coupon.expiredAt,
                        userCoupon.userCouponStatus
                ))
                .from(userCoupon)
                .join(coupon)
                .on(coupon.couponId.eq(userCoupon.couponId))
                .where(userCoupon.userId.eq(userId))
                .fetch();
    }

    // 사용자 쿠폰 목록 조회 - 사용자 쿠폰 Id 목록
    @Override
    public List<CouponQuery.UserOwnedCoupon> findUserCouponsByIds(List<Long> userCouponIds) {
        return queryFactory
                .select(
                        new QCouponQuery_UserOwnedCoupon(
                                userCoupon.userCouponId,
                                coupon.couponId,
                                userCoupon.userId,
                                coupon.couponName,
                                coupon.discount,
                                coupon.expiredAt,
                                userCoupon.userCouponStatus
                        ))
                .from(userCoupon)
                .join(coupon)
                .on(coupon.couponId.eq(userCoupon.couponId))
                .where(userCoupon.userCouponId.in(userCouponIds))
                .fetch();
    }


    // 만료된 사용자 쿠폰 목록 조회
    @Override
    public List<UserCoupon> findExpiredUserCoupons(LocalDateTime expiredAt) {
        return userCouponJpaRepository.findExpiredUserCoupons(expiredAt);
    }

}
