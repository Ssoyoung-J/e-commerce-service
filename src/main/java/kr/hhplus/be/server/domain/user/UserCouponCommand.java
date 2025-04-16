package kr.hhplus.be.server.domain.user;

import lombok.Value;

@Value
public class UserCouponCommand {
    Long userId;

    Long couponId;
}
