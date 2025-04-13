package kr.hhplus.be.server.domain.point;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class PointHistory {
    /**
     * 포인트 충전/사용 내역 고유 ID
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pointHistoryId", nullable = false)
    private Long pointHistoryId;

    /**
     * 사용자 고유 ID
     * */
    @Column(name = "userId", nullable = false)
    private Long userId;

    /**
     * 포인트 타입(충전/사용)
     * */
    @Column(name="transactionType", nullable = false)
    private PointTransactionType transactionType;


    /**
     * 포인트
     * */
    @Column(name="pointAmount", nullable = false)
    private Long pointAmount;

    /**
     * 포인트 사용/충전 일시
     * */
    @Column(name="createdAt", nullable = false)
    private LocalDateTime createdAt;


    @Builder
    public PointHistory(Long pointHistoryId, Long userId, PointTransactionType transactionType, Long pointAmount, LocalDateTime createdAt) {
        this.pointHistoryId = pointHistoryId;
        this.userId = userId;
        this.transactionType = transactionType;
        this.pointAmount = pointAmount;
        this.createdAt = createdAt;
    }

    // 포인트 충전/사용 내역 저장
    public static PointHistory saveHistory(Long userId, PointTransactionType transactionType, Long pointAmount, LocalDateTime createdAt) {
        return PointHistory.builder()
                .userId(userId)
                .transactionType(transactionType)
                .pointAmount(pointAmount)
                .createdAt(createdAt)
                .build();
    }

}
