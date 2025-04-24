package kr.hhplus.be.server.domain;

import kr.hhplus.be.server.domain.user.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.N;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCouponServicerTest {

    @InjectMocks
    private UserCouponService userCouponService;

    @Mock
    private UserCouponRepository userCouponRepository;

    @Nested
    class userCouponTest {

        @DisplayName("사용자 쿠폰 생성")
        @Test
        void createUserCoupon() {
            // given
            UserCouponCommand.Publish command = UserCouponCommand.Publish.of(1L, 1L);

            // when
            userCouponService.createUserCoupon(command);

            // then
            verify(userCouponRepository, times(1)).save(any(UserCoupon.class));
        }

        @DisplayName("사용 가능한 사용자 쿠폰 조회")
        @Test
        void getUsableCoupon() {
            // given
            UserCouponCommand.UsableCoupon command = UserCouponCommand.UsableCoupon.of(1L, 1L);

            UserCoupon userCoupon = UserCoupon.builder()
                    .userId(1L)
                    .userCouponId(1L)
                    .userCouponStatus(UserCouponStatus.NOTUSED)
                    .build();

            when(userCouponRepository.findByUserIdAndCouponId(1L, 1L))
                    .thenReturn(userCoupon);
            // when
            UserCouponInfo.UsableCoupon usableCoupon = userCouponService.getUsableCoupon(command);

            // then
            assertThat(usableCoupon).isNotNull();
            assertThat(usableCoupon.getUserCouponId()).isNotNull();
        }

        @DisplayName("사용일시가 만료된 쿠폰은 사용 불가")
        @Test
        void useExpiredCoupon() {
            // given
            UserCoupon userCoupon = UserCoupon.builder()
                    .userCouponStatus(UserCouponStatus.EXPIRED)
                    .build();

            when(userCouponRepository.findById(anyLong())).thenReturn(userCoupon);

            // when & then
            assertThatThrownBy(() -> userCouponService.useUserCoupon(anyLong()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("사용할 수 없는 쿠폰입니다.");

        }

        @DisplayName("쿠폰 사용")
        @Test
        void useCoupon() {
            // given
            UserCoupon userCoupon = UserCoupon.builder()
                    .userCouponId(1L)
                    .userCouponStatus(UserCouponStatus.NOTUSED)
                    .build();

            when(userCouponRepository.findById(1L)).thenReturn(userCoupon);

            // when
            userCouponService.useUserCoupon(userCoupon.getUserCouponId());

            // then
            verify(userCouponRepository, times(1)).findById(1L);
            assertThat(userCoupon.getUserCouponStatus()).isEqualTo(UserCouponStatus.USED);

        }

    }

}
