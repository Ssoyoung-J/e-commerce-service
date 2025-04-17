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
public class PointServiceTest {

    @Mock
    private PointRepository pointRepository;

    @Mock
    private PointHistoryRepository pointHistoryRepository;

    @InjectMocks
    private PointService service;

    @Nested
    class pointChargeTest {
        @DisplayName("충전 포인트가 0보다 클 경우 - 포인트 충전 성공")
        @Test
        void success() {
            // given
            PointCommand command = mock(PointCommand.class);
            Point userPoint = mock(Point.class);

            when(pointRepository.findByUserId(command.getUserId())).thenReturn(Optional.of(userPoint));

            // when
            service.chargePoint(command);

            // then
            verify(userPoint, times(1)).charge(command.getPointAmount());
            verify(pointRepository, times(0)).save(userPoint);

        }

        @DisplayName("충전 포인트가 음수일 경우 - 포인트 충전 실패")
        @Test
        public void shouldChargeFail_whenNegativeAmount(){
            //  given
            long userId = 1L;
            long balance = 10L;
            long pointAmount = -20L;

            PointCommand command = new PointCommand(userId, balance, pointAmount);
            Point userPoint = Point.create(command.getUserId(), command.getBalance());

            when(pointRepository.findByUserId(command.getUserId())).thenReturn(Optional.of(userPoint));

            // when & then
            assertThatThrownBy(() -> service.chargePoint(command))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("충전 포인트는 0보다 작을 수 없습니다.");

        }
    }

    @Nested
    class pointUseTest {

        @DisplayName("사용 포인트가 보유 포인트보다 클 경우 - 포인트 사용 실패")
        @Test
        public void shouldUseFail_WhenUserHasInSufficientPoints(){

            //  given
            long userId = 1L;
            long balance = 100L;
            long pointAmount = 2000L;

            PointCommand command = new PointCommand(userId, balance, pointAmount);
            Point userPoint = Point.create(command.getUserId(), command.getBalance());

            when(pointRepository.findByUserId(command.getUserId())).thenReturn(Optional.of(userPoint));

            // when & then
            assertThatThrownBy(() -> service.usePoint(command))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("사용 포인트는 보유 포인트보다 클 수 없습니다.");

        }
    }


}
