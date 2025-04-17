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
    public void chargePoint(PointCommand command) {
        pointRepository.findByUserId(command.getUserId())
                .ifPresentOrElse(
                        point -> point.charge(command.getPointAmount()),
                        () -> {
                            Point point = Point.create(command.getUserId(), command.getBalance());
                            pointRepository.save(point);
                        }
                );
    }

    // 포인트 사용
    public void usePoint(PointCommand command) {
        pointRepository.findByUserId(command.getUserId())
                .ifPresentOrElse(point -> point.use(command.getPointAmount()),
                        () -> {
                    throw new IllegalArgumentException("보유 포인트가 존재하지 않습니다.");
                        });
    }
   
}



