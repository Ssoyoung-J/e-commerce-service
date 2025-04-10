package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.infrastructure.CouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponJpaRepository couponJpaRepository;

    public Coupon findById(Long couponId) {
       Coupon coupon = couponJpaRepository.findById(couponId).orElseThrow(() -> new IllegalArgumentException("쿠폰 정보 없음"));

        return coupon;
    }
}
