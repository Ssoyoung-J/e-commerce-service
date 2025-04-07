package kr.hhplus.be.server.domain.point;

import kr.hhplus.be.server.infrastructure.point.PointJpaRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PointService {

    private final PointJpaRepository pointJpaRepository;

    public Point chargePoint(Long userId, Long pointAmount) {
        return pointRepository.charge(userId, pointAmount);
    }
}
