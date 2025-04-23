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
    }

}
