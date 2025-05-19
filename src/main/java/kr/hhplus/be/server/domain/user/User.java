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
    @Column(name = "user_id", nullable = false)
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
    @Column(name = "phone_num", nullable = false)
    private String phoneNum;

}
