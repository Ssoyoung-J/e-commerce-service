package kr.hhplus.be.server.presentation.point;

public record PointRequest() {

    public record Charge(
        Long userId,
        Long pointAmount
    ) {

    }

}
