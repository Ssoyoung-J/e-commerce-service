package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseEntity;
import lombok.*;

@Getter
@Builder
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCoupon extends BaseEntity {

    /**
     *  사용자 보유 쿠폰 고유 ID
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_coupon_id", nullable = false)
    private Long userCouponId;


    /**
     * 사용자 고유 ID
     * */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     *  쿠폰 고유 ID
     * */
    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    /**
     *  쿠폰 이름
     * */
    @Column(name = "coupon_name", nullable = false)
    private String couponName;

    /**
     *  쿠폰 상태
     * */
    @Enumerated(EnumType.STRING)
    @Column(name = "user_coupon_status", nullable = false)
    private UserCouponStatus userCouponStatus;

    public enum UserCouponStatus {
        USED, NOTUSED , EXPIRED
    }


    // 쿠폰 사용 - use
    public void use() {
        if(userCouponStatus == UserCouponStatus.EXPIRED) {
            throw new IllegalArgumentException("사용할 수 없는 쿠폰입니다.");
        }

        this.userCouponStatus = UserCouponStatus.USED;
    }

    // 쿠폰 사용 취소  - cancel
    public void cancel() {
        this.userCouponStatus = UserCouponStatus.NOTUSED;
    }


}
