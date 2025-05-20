package kr.hhplus.be.server.domain.point;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class PointHistory extends BaseEntity {
    /**
     * 포인트 충전/사용 내역 고유 ID
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id", nullable = false)
    private Long pointHistoryId;

    /**
     * 사용자 고유 ID
     * */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 포인트 타입(충전/사용)
     * */
    @Column(name="transaction_type", nullable = false)
    private PointTransactionType type;

    public enum PointTransactionType {
        CHARGE, USE
    }

    /**
     * 포인트
     * */
    @Column(name="point_amount", nullable = false)
    private Long pointAmount;


    @Builder
    public PointHistory(Long pointHistoryId, Long userId, PointTransactionType type, Long pointAmount) {
        this.pointHistoryId = pointHistoryId;
        this.userId = userId;
        this.type = type;
        this.pointAmount = pointAmount;
    }

    // 포인트 충전/사용 내역 저장
    public static PointHistory saveHistory(Long userId, PointTransactionType type, Long pointAmount) {
        return PointHistory.builder()
                .userId(userId)
                .type(type)
                .pointAmount(pointAmount)
                .build();
    }

}
