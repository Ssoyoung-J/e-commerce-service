package kr.hhplus.be.server.domain.point;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.domain.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Point extends BaseEntity {

    /**
     * 포인트 고유 ID
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id", nullable = false)
    private Long pointId;
    
    /**
     * 사용자 고유 ID
     * */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 포인트 잔액
     * */
    @Column(name = "balance", nullable = false)
    private Long balance = 0L;

    /**
     * 포인트
     * */
    @Column(name ="pointAmount", nullable = false)
    private Long pointAmount;




    @Builder
    public Point(Long pointId, Long userId, Long balance, Long pointAmount) {
        this.pointId = pointId;
        this.userId = userId;
        this.balance = balance;
        this.pointAmount = pointAmount;
    }

    public static Point create(Long userId, Long balance) {
        return Point.builder()
                .userId(userId)
                .balance(balance)
                .build();
    }

    // 포인트 충전
    public Long charge(Long pointAmount) {
        if(pointAmount <= 0) {
            throw new BusinessException(400, "충전 포인트는 0보다 작을 수 없습니다.");
        }
        return this.balance += pointAmount;
    }

    // 포인트 사용
    public Long use(Long pointAmount) {
        if (balance < pointAmount) {
            throw new BusinessException(400 ,"사용 포인트는 보유 포인트보다 클 수 없습니다.");
        }
        return this.balance -= pointAmount;
    }

}
