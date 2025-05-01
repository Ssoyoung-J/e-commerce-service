package kr.hhplus.be.server.application.point;

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
    }
}
