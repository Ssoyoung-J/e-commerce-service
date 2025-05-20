package kr.hhplus.be.server.domain.coupon;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.hhplus.be.server.domain.coupon.QCouponQuery_UserOwnedCoupon is a Querydsl Projection type for UserOwnedCoupon
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCouponQuery_UserOwnedCoupon extends ConstructorExpression<CouponQuery.UserOwnedCoupon> {

    private static final long serialVersionUID = -1409705719L;

    public QCouponQuery_UserOwnedCoupon(com.querydsl.core.types.Expression<Long> userCouponId, com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<Long> userId, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<Long> discount, com.querydsl.core.types.Expression<java.time.LocalDateTime> expiredAt, com.querydsl.core.types.Expression<UserCoupon.UserCouponStatus> userCouponStatus) {
        super(CouponQuery.UserOwnedCoupon.class, new Class<?>[]{long.class, long.class, long.class, String.class, long.class, java.time.LocalDateTime.class, UserCoupon.UserCouponStatus.class}, userCouponId, couponId, userId, couponName, discount, expiredAt, userCouponStatus);
    }

}

