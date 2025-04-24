package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.infrastructure.coupon.CouponJpaRepository;
import kr.hhplus.be.server.infrastructure.coupon.CouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public void publishCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId);
        coupon.publish();
    }

    public Coupon getCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId);
        return Coupon.create(coupon.getDiscountAmount(),coupon.getQuantity(), coupon.getStatus(), coupon.getExpiredAt());
    }

}
