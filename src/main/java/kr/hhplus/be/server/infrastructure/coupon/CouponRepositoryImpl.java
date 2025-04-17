package kr.hhplus.be.server.infrastructure.coupon;

import jakarta.persistence.EntityNotFoundException;
import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;

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

}
