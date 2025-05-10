package kr.hhplus.be.server.domain.point;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PointCommand {

    @Getter
    public static class Point {
        private final Long userId;
        private final Long balance;
        private final Long pointAmount;

        @Builder
        private Point(Long userId, Long balance, Long pointAmount) {
            this.userId = userId;
            this.balance = balance;
            this.pointAmount = pointAmount;
        }

        public static Point of(Long userId, Long balance, Long pointAmount) {
            return Point.builder()
                    .userId(userId)
                    .balance(balance)
                    .pointAmount(pointAmount)
                    .build();
        }
    }
    
    // 조회용
    @Getter
    public static class Balance {
        private final Long userId;

        @Builder
        private Balance(Long userId) {
            this.userId = userId;
        }

        public static Balance of(Long userId) {
            return Balance.builder()
                    .userId(userId)
                    .build();
        }
    }
     
    // 충전/사용용
    @Getter
    public static class Transaction {
        private final Long userId;
        private final Long pointAmount;

        @Builder
        private Transaction(Long userId, Long pointAmount) {
            this.userId = userId;
            this.pointAmount = pointAmount;
        }

        public static Transaction of(Long userId, Long pointAmount) {
            return Transaction.builder()
                    .userId(userId)
                    .pointAmount(pointAmount)
                    .build();
        }
    }
}
