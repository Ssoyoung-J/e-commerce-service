package kr.hhplus.be.server.infrastructure.point;

import kr.hhplus.be.server.domain.point.Point;
import kr.hhplus.be.server.domain.point.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository {

    private final PointJpaRepository pointJpaRepository;
    
    
    // 사용자 포인트 조회
    @Override
    public Optional<Point> findByUserId(Long userId) {
        return pointJpaRepository.findById(userId);
    }

    @Override
    public Point save(Point balance) {
        return pointJpaRepository.save(balance);
    }

}
