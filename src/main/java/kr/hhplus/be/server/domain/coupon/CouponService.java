package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.infrastructure.coupon.CouponJpaRepository;
import kr.hhplus.be.server.infrastructure.coupon.CouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    private final TransactionTemplate transactionTemplate;

    // 사용자 쿠폰 발급
    @Transactional
    public CouponInfo.IssuedCoupon publishCoupon(CouponCommand.IssuedCoupon command) {
        Coupon coupon = couponRepository.findByIdForUpdate(command.getCouponId()).orElseThrow(() -> new BusinessException(400, "해당 쿠폰을 조회할 수 없습니다."));

        // 쿠폰 수량 확인
        if(coupon.getQuantity() <= 0) {
            throw new BusinessException(400, "쿠폰 수량이 소진되었습니다.");
        }

        // 쿠폰 중복 발급 체크
        boolean isIssued = couponRepository.existsByUserIdAndCouponId(command.getUserId(), command.getCouponId());

        if(isIssued) {
            throw new BusinessException(400, "이미 발급된 쿠폰입니다.");
        }

        // 쿠폰 발급
        coupon.publish(1L);

        // 사용자 쿠폰 생성
        UserCoupon savedUserCoupon = couponRepository.saveUserCoupon(command.toEntity());
        return CouponInfo.IssuedCoupon.from(savedUserCoupon, coupon);
    }

    // 사용자 보유 쿠폰 목록 조회
    @Transactional(readOnly = true)
    public List<CouponInfo.UserOwnedCoupon> getUserCoupons(long userId) {
        return couponRepository.findAllOwnedCouponsByUserId(userId).stream()
                .map(CouponQuery.UserOwnedCoupon::to)
                .toList();
    }

    // 사용자 쿠폰 Id 목록에 해당 쿠폰 정보들 조회
    @Transactional(readOnly = true)
    public List<CouponInfo.UserOwnedCoupon> findUserCouponsById(CouponCommand.FindUserCoupons command) {
        List<Long> userCouponIds = command.getUserCouponIds();

        List<CouponQuery.UserOwnedCoupon> projections = couponRepository.findUserCouponsByIds(userCouponIds);

        return projections.stream()
                .map(CouponQuery.UserOwnedCoupon::to)
                .toList();
    }

    // 사용자 쿠폰 사용
    @Transactional
    public void use(CouponCommand.CouponAction command) {

        // 사용하려는 쿠폰 ID 추출 (중복 제거)
        List<Long> userCouponIds = command.getUserOwnedCoupons()
                .stream()
                .map(CouponCommand.UserOwnedCoupon::getUserCouponId)
                .distinct()
                .toList();

        // 쿠폰 상세 정보 조회
        List<CouponInfo.UserOwnedCoupon> details = couponRepository.findUserCouponsByIds(userCouponIds)
                .stream()
                .map(CouponQuery.UserOwnedCoupon::to)
                .distinct()
                .toList();

        // 쿠폰이 존재하지 않거나 일부만 존재하면 예외 발생
        if (CollectionUtils.isEmpty(details) || details.size() != userCouponIds.size()) {
            throw new BusinessException(400, "사용자 쿠폰을 조회할 수 없습니다.");
        }

        // 실제 UserCoupon 조회
        List<UserCoupon> userCoupons = couponRepository.findUserCouponsById(userCouponIds);
        if (CollectionUtils.isEmpty(userCoupons) || userCoupons.size() != userCouponIds.size()) {
            throw new BusinessException(400, "사용자 쿠폰을 조회할 수 없습니다.");
        }

        LocalDateTime now = LocalDateTime.now();

        for(int i = 0; i < details.size(); i++) {
            CouponInfo.UserOwnedCoupon detail = details.get(i);
            UserCoupon userCoupon = userCoupons.get(i);

            // 쿠폰이 이미 기간이 지난 경우 예외
             if (now.isAfter(detail.getExpiredAt())) {
                 throw new BusinessException(400, "쿠폰이 만료되었습니다");
             }

             // 쿠폰 사용 (상태 변경)
             userCoupon.use();
        }

        // 변경된 UserCoupon 정보 저장(사용 상태 반영)
        couponRepository.saveUserCoupons(userCoupons);

    }

    // 사용자 쿠폰 취소
    @Transactional
    public void cancel(CouponCommand.CouponAction command) {
        List<Long> userCouponIds = command.getUserOwnedCoupons()
                .stream()
                .map(CouponCommand.UserOwnedCoupon::getUserCouponId)
                .distinct()
                .toList();

        List<UserCoupon> userCoupons = couponRepository.findUserCouponsById(userCouponIds);
        if (CollectionUtils.isEmpty(userCoupons) || userCoupons.size() != userCouponIds.size()) {
            throw new BusinessException(400, "사용자 쿠폰을 조회할 수 없습니다");
        }

        userCoupons.forEach(UserCoupon::cancel);

        couponRepository.saveUserCoupons(userCoupons);

    }

    // 만료된 사용자 쿠폰 처리
    public void expireCouponsProcess() {
        LocalDateTime now = LocalDateTime.now();

        List<UserCoupon> expiredUserCoupons = couponRepository.findExpiredUserCoupons(now);

        expiredUserCoupons.forEach(coupon -> transactionTemplate.execute(status -> {
            coupon.use();
            return couponRepository.saveUserCoupon(coupon);
        }));
    }
}
