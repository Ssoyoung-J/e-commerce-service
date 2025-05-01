package kr.hhplus.be.server.application.point;

import kr.hhplus.be.server.domain.point.Point;
import kr.hhplus.be.server.domain.point.PointInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PointResult {

    @Getter
    @Builder
    public static class UserPoint {
        private Long userId;
        private Long balance;

        @Builder
        private UserPoint(Long userId, Long balance) {
            this.userId = userId;
            this.balance = balance;
        }

        public static UserPoint of(PointInfo.Balance info) {
            return new UserPoint(info.getUserId(), info.getBalance());
        }
    }
}
