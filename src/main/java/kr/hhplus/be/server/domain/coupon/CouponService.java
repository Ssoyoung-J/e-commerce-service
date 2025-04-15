package kr.hhplus.be.server.domain.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponJpaRepository couponJpaRepository;

    public Coupon findById(Long couponId) {
       Coupon coupon = couponJpaRepository.findById(couponId).orElseThrow(() -> new IllegalArgumentException("쿠폰 정보 없음"));

        return coupon;
    }
}
