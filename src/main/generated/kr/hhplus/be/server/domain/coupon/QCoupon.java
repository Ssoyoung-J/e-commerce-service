package kr.hhplus.be.server.domain.coupon;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCoupon is a Querydsl query type for Coupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoupon extends EntityPathBase<Coupon> {

    private static final long serialVersionUID = -1501826927L;

    public static final QCoupon coupon = new QCoupon("coupon");

    public final kr.hhplus.be.server.domain.common.QBaseEntity _super = new kr.hhplus.be.server.domain.common.QBaseEntity(this);

    public final NumberPath<Long> couponId = createNumber("couponId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> discountAmount = createNumber("discountAmount", Long.class);

    public final DateTimePath<java.time.LocalDateTime> expiredAt = createDateTime("expiredAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> quantity = createNumber("quantity", Long.class);

    public final EnumPath<CouponStatus> status = createEnum("status", CouponStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final ListPath<kr.hhplus.be.server.domain.user.UserCoupon, kr.hhplus.be.server.domain.user.QUserCoupon> userCoupons = this.<kr.hhplus.be.server.domain.user.UserCoupon, kr.hhplus.be.server.domain.user.QUserCoupon>createList("userCoupons", kr.hhplus.be.server.domain.user.UserCoupon.class, kr.hhplus.be.server.domain.user.QUserCoupon.class, PathInits.DIRECT2);

    public QCoupon(String variable) {
        super(Coupon.class, forVariable(variable));
    }

    public QCoupon(Path<? extends Coupon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCoupon(PathMetadata metadata) {
        super(Coupon.class, metadata);
    }

}

