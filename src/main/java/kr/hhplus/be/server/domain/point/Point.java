package kr.hhplus.be.server.domain.point;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Point {

    /**
     * 포인트 고유 ID
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pointId", nullable = false)
    private Long pointId;
    
    /**
     * 사용자 고유 ID
     * */
    @Column(name = "userId", nullable = false)
    private Long userId;

    /**
     * 포인트 잔액
     * */
    @Column(name = "balance", nullable = false)
    private Long balance = 0L;


    @Builder
    public Point(Long pointId, Long userId, Long balance) {
        this.pointId = pointId;
        this.userId = userId;
        this.balance = balance;
    }

    // 포인트 충전
    public Long charge(Long pointAmount) {
        if(pointAmount < 0) {
            throw new IllegalArgumentException("충전 포인트는 0보다 작을 수 없습니다.");
        }
        return this.balance += pointAmount;
    }

}
