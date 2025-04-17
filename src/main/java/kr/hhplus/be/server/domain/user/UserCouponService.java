package kr.hhplus.be.server.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCouponService {

    private final UserCouponRepository userCouponRepository;

    public void createUserCoupon(UserCouponCommand command) {
        UserCoupon userCoupon = UserCoupon.create(command.getUserId(), command.getCouponId());
        userCouponRepository.save(userCoupon);
    }

    // 비즈니스 로직 검증 필요
    public UserCouponInfo.UsableCoupon getUsableCoupon(Long userId, Long couponId) {
        UserCoupon userCoupon = userCouponRepository.findByUserIdAndCouponId(userId, couponId);

        if(userCoupon.getUserCouponStatus() == UserCouponStatus.EXPIRED) {
            throw new IllegalArgumentException("사용할 수 없는 쿠폰입니다.");
        }

        return UserCouponInfo.UsableCoupon.of(userCoupon.getCouponId());

    }

    public void useUserCoupon(Long userCouponId) {
        UserCoupon userCoupon = userCouponRepository.findById(userCouponId);
        userCoupon.use();
    }
    
    public UserCouponInfo.Coupons getUserCoupons(Long userId) {
        List<UserCoupon> coupons = userCouponRepository.findByUserId(userId);

        return UserCouponInfo.Coupons.of(coupons.stream()
                .map(UserCouponService::toCouponInfo)
                .toList());
    }

    private static UserCouponInfo.Coupon toCouponInfo(UserCoupon userCoupon) {
        return UserCouponInfo.Coupon.builder()
                .userCouponId(userCoupon.getUserCouponId())
                .couponId(userCoupon.getCouponId())
                .issuedAt(userCoupon.getIssuedAt())
                .build();
    }
}
