package kr.hhplus.be.server.domain.point;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.infrastructure.point.PointHistoryRepository;
import kr.hhplus.be.server.infrastructure.point.PointJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PointService {

    private final PointJpaRepository pointRepository;

    private final PointHistoryRepository pointHistoryRepository;

    // 포인트 충전
    @Transactional
    public Point chargePoint(PointCommand command) {
        // 사용자 ID로 포인트 조회
        Point point = pointRepository.findByUserId(command.getUserId()).orElseThrow(() -> new IllegalArgumentException("포인트 정보 없음"));
        // 포인트 충전
        Long balance = point.charge(command.getPointAmount());
        // 충전한 사용자 포인트 저장
//        point = pointRepository.save(command.getUserId(), balance);
        // 사용자 포인트 충전 이력 저장
//        pointHistoryRepository.save(command.getUserId(), PointTransactionType.CHARGE, command.getPointAmount(), LocalDateTime.now());

        return point;
    }

    // 포인트 사용
    @Transactional
    public Point usePoint(PointCommand command) {
        // 사용자 ID로 포인트 조회
        Point point = pointRepository.findByUserId(command.getUserId()).orElseThrow(() -> new IllegalArgumentException("포인트 정보 없음"));
        // 포인트 사용
        Long balance = point.use(command.getPointAmount());
        // 포인트 사용 후 잔액 저장
//        point = pointRepository.save(command.getUserId(), balance);
        // 사용자 포인트 사용 이력 저장
//        pointHistoryRepository.save(command.getUserId(), PointTransactionType.USE, command.getPointAmount(),  LocalDateTime.now());

        return point;
    }
}



