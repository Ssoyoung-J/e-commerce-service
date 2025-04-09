package kr.hhplus.be.server.infrastructure.point;

import kr.hhplus.be.server.domain.point.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointJpaRepository extends JpaRepository<Point, Long> {

    // 사용자 포인트 조회
    Optional<Point> findByUserId(Long userId);

    // 사용자 포인트 저장
    Point save(Long userId, Long balance);
}
