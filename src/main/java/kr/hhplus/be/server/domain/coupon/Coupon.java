package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Coupon extends BaseEntity {

    /**
     * 쿠폰 고유 ID
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    /**
     * 쿠폰 이름
     * */
    @Column(name = "coupon_name", nullable = false)
    private String couponName;

    /**
     * 쿠폰 할인 금액
     * */
    @Column(name = "discount", nullable = false)
    private Long discount;

    /**
     * 쿠폰 수량
     * */
    @Column(name = "quantity")
    private Long quantity;


    /**
     * 만료 일시
     * */
    @Column(name ="expired_at", nullable = false)
    private LocalDateTime expiredAt;


    // 쿠폰 발급
    public Coupon publish(long quantity) {
        if(expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("쿠폰이 만료되었습니다.");
        }

        if(quantity <= 0) {
            throw new IllegalArgumentException("쿠폰 수량이 부족합니다.");
        }

        this.quantity--;
        return this;
    }

}
