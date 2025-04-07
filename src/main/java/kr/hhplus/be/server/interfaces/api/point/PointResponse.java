package kr.hhplus.be.server.interfaces.api.point;

import kr.hhplus.be.server.domain.point.Point as PointEntity;

public class PointResponse {

    public static class PointDto {
        private Long userId;
        private Long balance;

        public PointDto(Long userId, Long balance) {
            this.userId = userId;
            this.balance = balance;
        }

        public static PointDto of(kr.hhplus.be.server.domain.point.Point entity) {
            return new PointDto(entity.getUserId(), entity.getBalance());
        }

        public Long getUserId() {
            return userId;
        }

        public Long getBalance() {
            return balance;
        }
    }
}
