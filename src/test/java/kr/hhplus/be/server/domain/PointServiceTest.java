package kr.hhplus.be.server.domain;

import kr.hhplus.be.server.common.exception.BusinessException;
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

import java.util.List;

import static org.assertj.core.api.Assertions.*;
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
    @DisplayName("포인트 조회")
    class pointCheckTest {
        @DisplayName("포인트 조회 성공 - 유효한 사용자 ID")
        @Test
        void success() {
            // given
            Long userId = 1L;
            Long balance = 2000L;
            PointCommand.Balance command = PointCommand.Balance.of(userId);
            Point userPoint = Point.builder()
                            .userId(userId)
                            .balance(balance)
                            .build();

            when(pointRepository.findByUserId(command.getUserId())).thenReturn(userPoint);

            // when
            pointService.getUserBalance(command);

            // then
            verify(pointRepository, times(1)).findByUserId(command.getUserId());
            assertThat(userPoint).isNotNull();
            assertThat(userPoint.getUserId()).isEqualTo(userId);
            assertThat(userPoint.getBalance()).isEqualTo(2000L);
        }
    }

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
                    .isInstanceOf(BusinessException.class)
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
                    .isInstanceOf(BusinessException.class)
                    .hasMessage("사용 포인트는 보유 포인트보다 클 수 없습니다.");

        }
    }

    @Nested
    @DisplayName("포인트 내역 조회")
    class PointHistoryTest {

        @DisplayName("사용자의 포인트 내역 조회 성공")
        @Test
        void success() {
            // given
            long userId = 1L;
            PointCommand.Balance command = PointCommand.Balance.of(userId);

            PointHistory chargeHistory = PointHistory.builder()
                    .pointHistoryId(10L)
                    .userId(userId)
                    .type(PointHistory.PointTransactionType.CHARGE)
                    .pointAmount(1000L)
                    .build();

            PointHistory usetHistory = PointHistory.builder()
                    .pointHistoryId(10L)
                    .userId(userId)
                    .type(PointHistory.PointTransactionType.USE)
                    .pointAmount(20_000L)
                    .build();

            List<PointHistory> pointHistories = List.of(chargeHistory, usetHistory);

            when(pointHistoryRepository.findPointHistoryByUserId(userId)).thenReturn(pointHistories);

            // when
            List<PointInfo.History> pointInfo = pointService.getUserPointHistories(command);

            // then
            assertThat(pointInfo).hasSize(2);
            verify(pointHistoryRepository, times(1)).findPointHistoryByUserId(userId);

            PointInfo.History charge = pointInfo.get(0);
            assertThat(charge).isNotNull();
            assertThat(charge.getPointHistoryId()).isEqualTo(10L);
            assertThat(charge.getUserId()).isEqualTo(userId);
            assertThat(charge.getType()).isEqualTo(PointHistory.PointTransactionType.CHARGE);
            assertThat(charge.getPointAmount()).isEqualTo(1000L);

            PointInfo.History use = pointInfo.get(1);
            assertThat(use).isNotNull();
            assertThat(use.getPointHistoryId()).isEqualTo(10L);
            assertThat(use.getUserId()).isEqualTo(userId);
            assertThat(use.getType()).isEqualTo(PointHistory.PointTransactionType.USE);
            assertThat(use.getPointAmount()).isEqualTo(20_000L);
        }
    }


}
