package kr.hhplus.be.server.infrastructure.coupon;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.coupon.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserCouponJpaRepository extends JpaRepository<UserCoupon, Long> {

    boolean existsByUserIdAndCouponId(Long userId, Long couponId);

//    @Query("""
//            SELECT us
//                FROM UserCoupon uc
//                JOIN Coupon c
//                    ON uc.couponId = c.couponId
//                WHERE uc.userCouponStatus = NOTUSED
//            """)
//    List<UserCoupon> fi
}
