package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseEntity;
import kr.hhplus.be.server.domain.user.UserCoupon;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Coupon extends BaseEntity {

    /**
     * 쿠폰 고유 ID
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couponId", nullable = false)
    private Long couponId;

    /**
     * 쿠폰 할인 금액
     * */
    @Column(name = "discountAmount", nullable = false)
    private Long discountAmount;

    /**
     * 쿠폰 수량
     * */
    @Column(name = "quantity")
    private Long quantity;

    /**
     * 쿠폰 상태
     * */
    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    /**
     * 만료 일시
     * */
    @Column(name ="expiredAt", nullable = false)
    private LocalDateTime expiredAt;

    /**
     * 사용자 보유 쿠폰 ID
     * */
//    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCoupon> userCoupons = new ArrayList<>();

    @Builder
    public Coupon(Long discountAmount, Long quantity, CouponStatus status, LocalDateTime expiredAt) {
        this.discountAmount = discountAmount;
        this.quantity = quantity;
        this.status = status;
        this.expiredAt = expiredAt;
    }

    // Coupon과 UserCoupon의 연관관계 생성
//    public void assignUserCoupon(UserCoupon userCoupon) {
//        this.userCoupons.add(userCoupon);
//    }

    public static Coupon create(Long discountAmount, Long quantity, CouponStatus status, LocalDateTime expiredAt) {
        return Coupon.builder()
                .discountAmount(discountAmount)
                .quantity(quantity)
                .status(status)
                .expiredAt(expiredAt)
                .build();
    }

    // 쿠폰 발급
    public Coupon publish() {
        if(expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("쿠폰이 만료되었습니다.");
        }

        if(quantity <= 0) {
            throw new IllegalArgumentException("쿠폰 수량이 부족합니다.");
        }

        this.quantity--;
        return this;
    }


    // 할인 금액 계산
    public Long calculateDiscountAmount() {
        return discountAmount != null ? discountAmount : 0L;
    }

}
