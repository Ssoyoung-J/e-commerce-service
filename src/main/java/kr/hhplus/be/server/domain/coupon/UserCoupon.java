package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseEntity;
import kr.hhplus.be.server.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class UserCoupon extends BaseEntity {

    /**
     *  사용자 보유 쿠폰 고유 ID
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userCouponId", nullable = false)
    private Long userCouponId;


    /**
     * 사용자 고유 ID
     * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     *  쿠폰 고유 ID
     * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    /**
     *  쿠폰 이름
     * */
    @Column(name = "couponName", nullable = false)
    private String couponName;

    /**
     *  쿠폰 상태
     * */
    @Enumerated(EnumType.STRING)
    @Column(name = "couponStatus", nullable = false)
    private CouponStatus couponStatus;

    /**
     *  사용 일시
     * */
    private LocalDateTime usedAt;


    @Builder
    public UserCoupon(User user, Coupon coupon, String couponName , CouponStatus couponStatus, LocalDateTime usedAt) {
        this.user = user;
        this.coupon = coupon;
        this.couponName = couponName;
        this.couponStatus = couponStatus;
        this.usedAt = usedAt;

        // 연관관계 설정
        user.assignUserCoupon(this);
        coupon.assignUserCoupon(this);
    }

    // UserCoupon과 User간의 연관관계 생성
    public void assignUser(User user) {
        this.user = user;
    }

    // UserCoupon과 Coupon간의 연관관계 생성
    public void assignCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
    // 쿠폰 사용 - useCoupon
    // 쿠폰 상태 변경
    public void updateCouponStatus(CouponStatus newCouponStatus) { this.couponStatus = newCouponStatus;}

}
