package kr.hhplus.be.server.application.point;

import kr.hhplus.be.server.domain.point.PointCommand;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PointCriteria {
    @Getter
    public static class UserPoint {
        private final Long userId;
        private final Long pointAmount;

        @Builder
        private UserPoint(Long userId, Long pointAmount) {
            this.userId = userId;
            this.pointAmount = pointAmount;
        }

        public static UserPoint of(Long userId, Long pointAmount) {
            return new UserPoint(userId, pointAmount);
        }

        public PointCommand.Point toPointCommand(Long userId, Long balance, Long pointAmount) {
            return PointCommand.Point.of(userId, balance, pointAmount);
        }
    }
}
