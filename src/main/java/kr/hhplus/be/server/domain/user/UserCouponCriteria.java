package kr.hhplus.be.server.domain.user;

import lombok.Value;

@Value
public class UserCouponCriteria {
    Long userId;

    Long couponId;
}
