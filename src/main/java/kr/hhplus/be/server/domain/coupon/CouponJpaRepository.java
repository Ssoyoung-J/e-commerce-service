package kr.hhplus.be.server.domain.coupon;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {

//    Coupon coupon findByCouponId(Long couponId);


    Optional<Coupon> findById(Long couponId);
}
