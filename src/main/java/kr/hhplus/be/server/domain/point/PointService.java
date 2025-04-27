package kr.hhplus.be.server.domain.point;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PointService {

    private final PointRepository pointRepository;
    // 포인트 기본값 지정
    private static final long BALANCE = 0L;

    // 포인트 충전
    public void chargePoint(PointCommand.Point command) {
        try {
            Point point = pointRepository.findByUserId(command.getUserId());
            point.charge(command.getPointAmount());
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    // 포인트 사용
    public void usePoint(PointCommand.Point command) {
        try {
            Point point =  pointRepository.findByUserId(command.getUserId());
            point.use(command.getPointAmount());
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
   
}



