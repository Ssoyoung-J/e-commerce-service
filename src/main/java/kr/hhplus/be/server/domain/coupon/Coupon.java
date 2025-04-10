package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
     * 사용자 보유 쿠폰 ID
     * */
    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCoupon> userCoupons = new ArrayList<>();

    @Builder
    public Coupon(Long discountAmount, Long quantity) {
        this.discountAmount = discountAmount;
        this.quantity = quantity;
    }

    // Coupon과 UserCoupon의 연관관계 생성
    public void assignUserCoupon(UserCoupon userCoupon) {
        this.userCoupons.add(userCoupon);
    }

    public Long calculateDiscountAmount() {
        return discountAmount != null ? discountAmount : 0L;
    }

}
