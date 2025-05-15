package kr.hhplus.be.server.presentation.point;


import kr.hhplus.be.server.application.point.PointResult;
import kr.hhplus.be.server.domain.point.PointHistory;
import kr.hhplus.be.server.domain.point.PointInfo;

import java.time.LocalDateTime;

public record PointResponse() {

    public record UserPoint(
            Long userId,
            Long balance
    ) {
        public static UserPoint from(PointResult.UserPoint point) {
            return new UserPoint(
                    point.getUserId(),
                    point.getBalance()
            );
        }
    }

    public record Transaction(
            Long pointId,
            Long userId,
            Long balance,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public static Transaction from(PointResult.Transaction point) {
            return new Transaction(
                    point.getPointId(),
                    point.getUserId(),
                    point.getBalance(),
                    point.getCreatedAt(),
                    point.getUpdatedAt()
            );
        }

    }

    public record History(
        Long pointHistoryId,
        Long userId,
        Long pointAmount,
        PointHistory.PointTransactionType type,
        LocalDateTime createdAt
    ){
        public static History from(PointResult.History history) {
            return new History(
                    history.getPointHistoryId(),
                    history.getUserId(),
                    history.getPointAmount(),
                    history.getType(),
                    history.getCreatedAt()
            );
        }
    }
}