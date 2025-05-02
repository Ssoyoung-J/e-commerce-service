package kr.hhplus.be.server.domain.point;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PointCommand {

    @Getter
    public static class Point {
        private final Long userId;
        private final Long pointAmount;

        @Builder
        private Point(Long userId, Long pointAmount) {
            this.userId = userId;
            this.pointAmount = pointAmount;
        }

        public static Point of(Long userId, Long pointAmount) {
            return Point.builder()
                    .userId(userId)
                    .pointAmount(pointAmount)
                    .build();
        }
    }
}
