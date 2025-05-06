package kr.hhplus.be.server.application.point;

import kr.hhplus.be.server.domain.point.PointCommand;
import kr.hhplus.be.server.presentation.point.PointRequest;
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

        public static UserPoint fromRequest(PointRequest.Charge chargeRequest) {
            return new UserPoint(chargeRequest.userId(), chargeRequest.pointAmount());
        }

        public PointCommand.Point toPointCommand(Long userId, Long balance, Long pointAmount) {
            return PointCommand.Point.of(userId, balance, pointAmount);
        }

        public PointCommand.Balance toBalanceCommand(Long userId) {
            return PointCommand.Balance.of(userId);
        }

        public PointCommand.Transaction toTransactionCommand(Long userId, Long pointAmount) {
                return PointCommand.Transaction.of(userId, pointAmount);
        }
    }

    @Getter
    public static class Balance {
        private final Long userId;

        @Builder
        private Balance(Long userId) {
            this.userId = userId;
        }

        public static Balance of(Long userId) {
            return new Balance(userId);
        }

        public PointCommand.Balance toBalanceCommand(Long userId) {
            return PointCommand.Balance.of(userId);
        }
    }
}
