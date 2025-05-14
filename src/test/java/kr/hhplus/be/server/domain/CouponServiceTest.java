package kr.hhplus.be.server.domain;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.domain.coupon.*;
import kr.hhplus.be.server.domain.product.ProductRepository;
import kr.hhplus.be.server.domain.product.ProductService;
import org.hibernate.usertype.BaseUserTypeSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {
    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponService couponService;

    @Nested
    @DisplayName("사용자 쿠폰 발급")
    class couponTest {

        @DisplayName("정상적으로 사용자에게 쿠폰 발급")
        @Test
        void successfullyPublishCouponToUser() {
            // given
            long userId = 1L;
            long couponId = 10L;

            Coupon coupon = Coupon.builder()
                    .couponId(couponId)
                    .couponName("2만원 할인")
                    .discount(20000L)
                    .quantity(5L)
                    .expiredAt(LocalDateTime.now().plusDays(1))
                    .build();

            UserCoupon userCoupon = UserCoupon.builder()
                    .userCouponId(100L)
                    .userId(userId)
                    .couponId(couponId)
                    .userCouponStatus(UserCoupon.UserCouponStatus.NOTUSED)
                    .build();

            CouponCommand.IssuedCoupon command = CouponCommand.IssuedCoupon.builder()
                    .userId(userId)
                    .couponId(couponId)
                    .build();

            when(couponRepository.findByIdForUpdate(command.getCouponId())).thenReturn(Optional.of(coupon));
            when(couponRepository.existsByUserIdAndCouponId(command.getUserId(), command.getCouponId())).thenReturn(false);
            when(couponRepository.saveUserCoupon(any(UserCoupon.class))).thenReturn(userCoupon);

            // when
            CouponInfo.IssuedCoupon result = couponService.publishCoupon(command);

            // then
            assertThat(coupon.getQuantity()).isEqualTo(4L);
            assertThat(result.getUserCouponId()).isEqualTo(userCoupon.getUserCouponId());
            assertThat(result.getCouponId()).isEqualTo(coupon.getCouponId());
            assertThat(result.getCouponName()).isEqualTo(coupon.getCouponName());
        }
    }

    @DisplayName("쿠폰이 존재하지 않을 경우 예외 발생")
    @Test
    void throwsExceptionWhenCouponDoesNotExist() {
        // given
        long userId = 1L;
        long couponId = 10L;

        CouponCommand.IssuedCoupon command = CouponCommand.IssuedCoupon.builder()
                .userId(userId)
                .couponId(couponId)
                .build();

        when(couponRepository.findByIdForUpdate(command.getCouponId())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> couponService.publishCoupon(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage("해당 쿠폰을 조회할 수 없습니다.");
    }
}
