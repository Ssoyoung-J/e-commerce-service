package kr.hhplus.be.server.domain.user;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseEntity;
import kr.hhplus.be.server.domain.coupon.UserCoupon;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class User extends BaseEntity {

    /**
     * 사용자 고유 ID
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private Long userId;
    
    /**
     * 사용자 이름
     * */
    @Column(name = "name", nullable = false)
    private String name;
    
    /**
     * 이메일
     * */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * 핸드폰 번호
     * */
    @Column(name = "phoneNum", nullable = false)
    private String phoneNum;

    /**
     * 사용자 보유 쿠폰 목록
     * */
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCoupon> userCoupons = new ArrayList<>();

    @Builder
    public User(String name, String email, String phoneNum, List<UserCoupon> userCoupons) {
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
        this.userCoupons = userCoupons != null ? userCoupons : new ArrayList<>();

        // 연관관계 설정
//        for(UserCoupon coupon : this.userCoupons) {
//            coupon.assignUser(this);
//        }
    }

    public void assignUserCoupon(UserCoupon userCoupon) {
        this.userCoupons.add(userCoupon);
    }

}
