package kr.hhplus.be.server.application.point;

import kr.hhplus.be.server.domain.point.PointHistory;
import kr.hhplus.be.server.domain.point.PointInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PointResult {

    @Getter
    @Builder
    public static class UserPoint {
        private Long userId;
        private Long balance;

        public static UserPoint from(PointInfo.Balance info) {
            return UserPoint.builder()
                    .userId(info.getUserId())
                    .balance(info.getBalance())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Transaction {
        private Long pointId;
        private Long userId;
        private Long balance;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static PointResult.Transaction from(PointInfo.Transaction info) {
            return Transaction.builder()
                    .pointId(info.getPointId())
                    .userId(info.getUserId())
                    .balance(info.getBalance())
                    .createdAt(info.getCreatedAt())
                    .updatedAt(info.getUpdatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class History {
        private Long pointHistoryId;
        private Long userId;
        private Long pointAmount;
        private PointHistory.PointTransactionType type;
        private LocalDateTime createdAt;

        public static History from(PointInfo.History info) {
            return History.builder()
                    .pointHistoryId(info.getPointHistoryId())
                    .userId(info.getUserId())
                    .pointAmount(info.getPointAmount())
                    .type(info.getType())
                    .createdAt(info.getCreatedAt())
                    .build();
        }
    }



}
