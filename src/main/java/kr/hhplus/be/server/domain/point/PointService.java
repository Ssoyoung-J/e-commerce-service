package kr.hhplus.be.server.domain.point;

import kr.hhplus.be.server.infrastructure.point.PointJpaRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PointService {

    private final PointJpaRepository pointRepository;

    public Point chargePoint(Long userId, Long pointAmount) {
        // 사용자 ID로 포인트 조회
        Point point = pointRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("포인트 정보 없음"));
        Long balance = point.charge(pointAmount);
        point = pointRepository.save(userId, balance);
        pointRepository.saveHistory(userId, PointTransactionType.CHARGE, pointAmount, LocalDateTime.now());

        return point;
    }
}
