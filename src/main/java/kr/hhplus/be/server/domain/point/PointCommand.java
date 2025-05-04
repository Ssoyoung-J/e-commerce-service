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

        public static Point of(Long userId,Long balance, Long pointAmount) {
            return Point.builder()
                    .userId(userId)
                    .pointAmount(pointAmount)
                    .build();
        }
    }
    
    // 조회용
     
    // 충전/사용용
}
