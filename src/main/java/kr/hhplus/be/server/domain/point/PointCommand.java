package kr.hhplus.be.server.domain.point;


import lombok.Getter;

public class PointCommand {
    private final Long userId;
    private final Long pointAmount;

    public PointCommand(Long userId, Long pointAmount) {
        this.userId = userId;
        this.pointAmount = pointAmount;
    }

}
