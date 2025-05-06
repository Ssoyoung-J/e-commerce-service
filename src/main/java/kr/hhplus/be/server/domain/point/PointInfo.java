package kr.hhplus.be.server.domain.point;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PointInfo {

    @Getter
    @Builder
    public static class Balance {
        private Long pointHistoryId;
        private Long userId;
        private Long balance;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static Balance from(Point point) {
            return Balance.builder()
                    .pointHistoryId(point.getPointId())
                    .userId(point.getUserId())
                    .balance(point.getBalance())
                    .createdAt(point.getCreatedAt())
                    .updatedAt(point.getUpdatedAt())
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

        public static Transaction from(Point point) {
            return Transaction.builder()
                    .pointId(point.getPointId())
                    .userId(point.getUserId())
                    .balance(point.getBalance())
                    .createdAt(point.getCreatedAt())
                    .updatedAt(point.getUpdatedAt())
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
        private LocalDateTime updatedAt;


        public static History from(PointHistory pointHistory) {
            return History.builder()
                    .pointHistoryId(pointHistory.getPointHistoryId())
                    .userId(pointHistory.getUserId())
                    .pointAmount(pointHistory.getPointAmount())
                    .type(pointHistory.getType())
                    .createdAt(pointHistory.getCreatedAt())
                    .updatedAt(pointHistory.getUpdatedAt())
                    .build();
        }
    }
}
