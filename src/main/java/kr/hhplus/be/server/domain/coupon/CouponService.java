package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.infrastructure.coupon.CouponJpaRepository;
import kr.hhplus.be.server.infrastructure.coupon.CouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

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
        boolean isIssued = couponRepository.existsByUserIdAndCouponId(command.getUserId(), command.couponId);

        if(isIssued) {
            throw new BusinessException(400, "이미 발급된 쿠폰입니다.");
        }

        // 쿠폰 발급
        coupon.publish(1L);

        // 사용자 쿠폰 생성
        UserCoupon savedUserCoupon = couponRepository.saveUserCoupon(command.toEntity());

        return CouponInfo.IssuedCoupon.from(savedUserCoupon, coupon);
    }

    public Coupon getCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId);
        return Coupon.create(coupon.getDiscountAmount(),coupon.getQuantity(), coupon.getStatus(), coupon.getExpiredAt());
    }

}
