package kr.hhplus.be.server.domain;

import kr.hhplus.be.server.domain.point.Point;
import kr.hhplus.be.server.domain.point.PointHistory;
import kr.hhplus.be.server.domain.point.PointService;
import kr.hhplus.be.server.domain.point.PointTransactionType;
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
            when(pointRepository.findByUserId(userId)).thenReturn(Optional.of(userPoint));

            //  when
            service.chargePoint(userId, pointAmount);

            //  then
            assertThat(userPoint.getBalance()).isEqualTo(pointAmount+balance);
            verify(pointRepository, times(1)).saveHistory(
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
            Point userPoint = new Point(pointId, userId, balance);
            when(pointRepository.findByUserId(userId)).thenReturn(Optional.of(userPoint));

            //  when
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
                service.chargePoint(userId, -1L);
            });

            //  then
            assertEquals("충전 포인트는 0보다 작을 수 없습니다.", exception.getMessage());

        }
    }


}
