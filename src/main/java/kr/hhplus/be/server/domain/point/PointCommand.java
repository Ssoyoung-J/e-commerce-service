package kr.hhplus.be.server.domain.point;

import lombok.Value;

@Value
public class PointCommand {

    Long userId;

    Long pointAmount;

}
