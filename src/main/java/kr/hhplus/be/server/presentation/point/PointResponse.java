package kr.hhplus.be.server.presentation.point;


import kr.hhplus.be.server.application.point.PointResult;
import kr.hhplus.be.server.domain.point.PointInfo;

import java.time.LocalDateTime;

public record PointResponse() {

    public record Transaction(
            Long Id,
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

        LocalDateTime createdAt
    ){

    }
}