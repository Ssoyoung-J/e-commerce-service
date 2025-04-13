package kr.hhplus.be.server.domain;

import kr.hhplus.be.server.domain.point.*;
import kr.hhplus.be.server.infrastructure.point.PointHistoryRepository;
import kr.hhplus.be.server.infrastructure.point.PointJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Point Service 단위 테스트
 * */
@ExtendWith(MockitoExtension.class)
public class PointServiceTest {

    @Mock
    private PointJpaRepository pointRepository;

    @Mock
    private PointHistoryRepository pointHistoryRepository;

    @InjectMocks
    private PointService service;

    @Nested
    class pointChargeTest {
        @DisplayName("충전 포인트가 0보다 클 경우 - 포인트 충전 성공")
        @Test
        public void success() {
            //  given
            long userId = 1L;
            long pointId = 1L;
            long balance = 10L;
            long pointAmount = 20L;
            Point userPoint = new Point(pointId, userId, balance);

            // 생성자를 통한 불변 객체 생성
            PointCommand command = new PointCommand(userId, pointAmount);

            when(pointRepository.findByUserId(userId)).thenReturn(Optional.of(userPoint));

            //  when
            service.chargePoint(command);

            //  then
            assertThat(userPoint.getBalance()).isEqualTo(pointAmount+balance);
            verify(pointHistoryRepository, times(1)).save(
                    eq(userId),
                    eq(PointTransactionType.CHARGE),
                    eq(pointAmount),
                    any(LocalDateTime.class) // LocalDateTime은 일치하기 어려우니 any로 처리
            );

        }

        @DisplayName("충전 포인트가 음수일 경우 - 포인트 충전 실패")
        @Test
        public void shouldChargeFail_whenNegativeAmount(){
            //  given
            long userId = 1L;
            long pointId = 1L;
            long balance = 10L;
            long pointAmount = -20L;
            Point userPoint = new Point(pointId, userId, balance);

            PointCommand command = new PointCommand(userId, pointAmount);

            when(pointRepository.findByUserId(userId)).thenReturn(Optional.of(userPoint));

            //  when
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
                service.chargePoint(command);
            });

            //  then
            assertEquals("충전 포인트는 0보다 작을 수 없습니다.", exception.getMessage());

        }
    }

    @Nested
    class pointUseTest {
        @DisplayName("보유 포인트가 사용 포인트보다 클 경우 - 포인트 충전 성공")
        @Test
        public void shouldUseSuccessfully_WhenUserHasExactPoints() {
            //  given
            long userId = 1L;
            long pointId = 1L;
            long balance = 1000L;
            long pointAmount = 20L;
            Point userPoint = new Point(pointId, userId, balance);

            PointCommand command = new PointCommand(userId, pointAmount);

            when(pointRepository.findByUserId(userId)).thenReturn(Optional.of(userPoint));

            //  when
            service.usePoint(command);

            //  then
            assertThat(userPoint.getBalance()).isEqualTo(balance-pointAmount);
            verify(pointHistoryRepository, times(1)).save(
                    eq(userId),
                    eq(PointTransactionType.USE),
                    eq(pointAmount),
                    any(LocalDateTime.class) // LocalDateTime은 일치하기 어려우니 any로 처리
            );

        }

        @DisplayName("사용 포인트가 보유 포인트보다 클 경우 - 포인트 사용 실패")
        @Test
        public void shouldUseFail_WhenUserHasInSufficientPoints(){
            //  given
            long userId = 1L;
            long pointId = 1L;
            long balance = 10L;
            long pointAmount = 100L;
            Point userPoint = new Point(pointId, userId, balance);
            PointCommand command = new PointCommand(userId, pointAmount);

            when(pointRepository.findByUserId(userId)).thenReturn(Optional.of(userPoint));

            //  when
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
                service.usePoint(command);
            });

            //  then
            assertEquals("사용 포인트는 보유 포인트보다 클 수 없습니다.", exception.getMessage());

        }
    }


}
