package kr.hhplus.be.server.domain.user;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseEntity;
import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponStatus;
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
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
    private Long userId;

    /**
     *  쿠폰 고유 ID
     * */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "coupon_id", nullable = false)
    private Long couponId;

    /**
     *  쿠폰 이름
     * */
    @Column(name = "couponName", nullable = false)
    private String couponName;

    /**
     *  쿠폰 상태
     * */
    @Enumerated(EnumType.STRING)
    @Column(name = "userCouponStatus", nullable = false)
    private UserCouponStatus userCouponStatus;

    /**
     * 발급 일시
     * */
    private LocalDateTime issuedAt;
    
    /**
     *  사용 일시
     * */
    private LocalDateTime usedAt;


    @Builder
    public UserCoupon(Long userCouponId, Long userId, Long couponId, String couponName , UserCouponStatus userCouponStatus,LocalDateTime issuedAt, LocalDateTime usedAt) {
        this.userCouponId = userCouponId;
        this.userId = userId;
        this.couponId = couponId;
        this.couponName = couponName;
        this.userCouponStatus = userCouponStatus;
        this.issuedAt = issuedAt;
        this.usedAt = usedAt;

        // 연관관계 설정
//        user.assignUserCoupon(this);
//        coupon.assignUserCoupon(this);
    }

    // UserCoupon과 User간의 연관관계 생성
//    public void assignUser(User user) {
//        this.user = user;
//    }

    // UserCoupon과 Coupon간의 연관관계 생성
//    public void assignCoupon(Coupon coupon) {
//        this.coupon = coupon;
//    }

    public static UserCoupon create(Long userId, Long couponId) {
        return UserCoupon.builder()
                .userId(userId)
                .couponId(couponId)
                .issuedAt(LocalDateTime.now())
                .userCouponStatus(UserCouponStatus.NOTUSED)
                .build();
    }

    // 쿠폰 사용 - use
    public void use() {
        if(userCouponStatus == UserCouponStatus.EXPIRED) {
            throw new IllegalArgumentException("사용할 수 없는 쿠폰입니다.");
        }

        this.userCouponStatus = UserCouponStatus.USED;
        this.usedAt = LocalDateTime.now();
    }

    // 쿠폰 사용 취소  - cancel
    public void cancel() {
        this.userCouponStatus = UserCouponStatus.NOTUSED;
    }


}
