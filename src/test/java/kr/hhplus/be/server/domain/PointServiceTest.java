package kr.hhplus.be.server.domain;

import kr.hhplus.be.server.domain.point.*;
import kr.hhplus.be.server.domain.point.PointHistoryRepository;
import kr.hhplus.be.server.domain.point.PointRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Point Service 단위 테스트
 * */
@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @Mock
    private PointRepository pointRepository;

    @Mock
    private PointHistoryRepository pointHistoryRepository;

    @InjectMocks
    private PointService pointService;

    @Nested
    class pointChargeTest {
        @DisplayName("포인트 충전 검증 성공 - 충전 포인트 >= 0")
        @Test
        void success() {
            // given
            Long userId = 1L;
            Long balance = 10L;
            Long pointAmount = 20L;

            PointCommand.Transaction command = PointCommand.Transaction.of(userId, pointAmount);
            Point userPoint = Point.builder()
                            .userId(userId)
                            .balance(balance)
                            .pointAmount(pointAmount)
                            .build();

            when(pointRepository.findByUserId(command.getUserId())).thenReturn(userPoint);

            // when
            pointService.chargePoint(command);

            // then
            verify(pointRepository, times(1)).findByUserId(command.getUserId());
            verify(pointRepository, times(0)).save(userPoint);
            assertThat(userPoint.getBalance()).isEqualTo(30L);

        }

        @DisplayName("포인트 충전 검증 실패 - 충전 금액: 음수")
        @Test
        public void shouldChargeFail_whenNegativeAmount(){
            //  given
            long userId = 1L;
            long balance = 10L;
            long pointAmount = -20L;

            PointCommand.Transaction command = PointCommand.Transaction.of(userId, pointAmount);

            Point userPoint = Point.builder()
                    .userId(userId)
                    .balance(balance)
                    .pointAmount(pointAmount)
                    .build();

            when(pointRepository.findByUserId(command.getUserId())).thenReturn(userPoint);

            // when & then
            assertThatThrownBy(() -> pointService.chargePoint(command))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("충전 포인트는 0보다 작을 수 없습니다.");

        }
    }

    @Nested
    class pointUseTest {

        @DisplayName("포인트 사용 검증 실패 - 사용 포인트 > 보유 포인트")
        @Test
        public void shouldUseFail_WhenUserHasInSufficientPoints(){

            //  given
            long userId = 1L;
            long balance = 100L;
            long pointAmount = 2000L;

            PointCommand.Point command = PointCommand.Point.of(userId, balance, pointAmount);

            Point userPoint = Point.builder()
                    .userId(userId)
                    .balance(balance)
                    .pointAmount(pointAmount)
                    .build();

            when(pointRepository.findByUserId(command.getUserId())).thenReturn(userPoint);

            // when & then
            assertThatThrownBy(() -> pointService.usePoint(command))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("사용 포인트는 보유 포인트보다 클 수 없습니다.");

        }
    }


}
