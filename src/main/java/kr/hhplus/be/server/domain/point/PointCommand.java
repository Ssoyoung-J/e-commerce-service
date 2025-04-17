package kr.hhplus.be.server.domain.point;

import lombok.Value;

@Value
public class PointCommand {

    // 사용자 고유 id
    Long userId;

    // 보유 포인트
    Long balance;

    // 포인트(사용/충전)
    Long pointAmount;

}
